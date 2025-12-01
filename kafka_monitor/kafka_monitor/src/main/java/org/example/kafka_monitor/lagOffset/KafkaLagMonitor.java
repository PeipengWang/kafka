package org.example.kafka_monitor.lagOffset;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class KafkaLagMonitor {

    private final AdminClient adminClient;

    public KafkaLagMonitor(Properties props) {
        this.adminClient = AdminClient.create(props);
    }

    /**
     * 获取消费者组的延迟信息
     */
    public Map<TopicPartition, ConsumerLagInfo> getConsumerGroupLags(String groupId)
            throws ExecutionException, InterruptedException {

        Map<TopicPartition, ConsumerLagInfo> result = new HashMap<>();

        try {
            // 1. 获取消费者组的已提交偏移量
            Map<TopicPartition, OffsetAndMetadata> committedOffsets =
                    adminClient.listConsumerGroupOffsets(groupId)
                            .partitionsToOffsetAndMetadata()
                            .get();

            if (committedOffsets.isEmpty()) {
                System.out.println("警告: 消费者组 " + groupId + " 没有找到已提交的偏移量");
                return result;
            }

            // 2. 获取分区的最新偏移量
            Set<TopicPartition> partitions = committedOffsets.keySet();

            // 按主题分组，减少请求
            Map<String, List<TopicPartition>> topicPartitionsMap = partitions.stream()
                    .collect(Collectors.groupingBy(TopicPartition::topic));

            for (Map.Entry<String, List<TopicPartition>> entry : topicPartitionsMap.entrySet()) {
                String topic = entry.getKey();
                List<TopicPartition> topicPartitions = entry.getValue();

                // 为每个分区获取最新偏移量
                Map<TopicPartition, OffsetSpec> requestMap = topicPartitions.stream()
                        .collect(Collectors.toMap(
                                tp -> tp,
                                tp -> OffsetSpec.latest()
                        ));

                ListOffsetsResult listOffsetsResult = adminClient.listOffsets(requestMap);

                for (TopicPartition tp : topicPartitions) {
                    try {
                        // 获取已提交偏移量
                        OffsetAndMetadata committed = committedOffsets.get(tp);
                        long committedOffset = committed.offset();

                        // 获取最新偏移量
                        ListOffsetsResult.ListOffsetsResultInfo offsetInfo =
                                listOffsetsResult.partitionResult(tp).get();
                        long endOffset = offsetInfo.offset();

                        // 计算延迟
                        long lag = Math.max(0, endOffset - committedOffset);

                        // 创建延迟信息对象
                        ConsumerLagInfo lagInfo = new ConsumerLagInfo(
                                groupId,
                                tp.topic(),
                                tp.partition(),
                                committedOffset,
                                endOffset,
                                lag,
                                System.currentTimeMillis()
                        );

                        result.put(tp, lagInfo);

                    } catch (Exception e) {
                        System.err.println("获取分区 " + tp + " 信息失败: " + e.getMessage());
                    }
                }
            }

        } catch (ExecutionException e) {
            if (e.getCause() instanceof org.apache.kafka.common.errors.GroupIdNotFoundException) {
                System.err.println("消费者组 " + groupId + " 不存在");
            } else {
                throw e;
            }
        }

        return result;
    }

    /**
     * 获取所有消费者组的延迟信息
     */
    public Map<String, Map<TopicPartition, ConsumerLagInfo>> getAllConsumerGroupLags()
            throws ExecutionException, InterruptedException {

        Map<String, Map<TopicPartition, ConsumerLagInfo>> allGroupsLags = new HashMap<>();

        // 获取所有消费者组
        ListConsumerGroupsResult groupsResult = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> groups = groupsResult.all().get();

        for (ConsumerGroupListing group : groups) {
            String groupId = group.groupId();
            try {
                Map<TopicPartition, ConsumerLagInfo> groupLags = getConsumerGroupLags(groupId);
                if (!groupLags.isEmpty()) {
                    allGroupsLags.put(groupId, groupLags);
                }
            } catch (Exception e) {
                System.err.println("获取消费者组 " + groupId + " 延迟信息失败: " + e.getMessage());
            }
        }

        return allGroupsLags;
    }

    /**
     * 获取延迟最大的N个分区
     */
    public List<ConsumerLagInfo> getTopNLags(String groupId, int topN)
            throws ExecutionException, InterruptedException {

        Map<TopicPartition, ConsumerLagInfo> allLags = getConsumerGroupLags(groupId);

        return allLags.values().stream()
                .sorted((a, b) -> Long.compare(b.getLag(), a.getLag()))
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * 关闭AdminClient
     */
    public void close() {
        if (adminClient != null) {
            adminClient.close();
        }
    }

    /**
     * 延迟信息对象
     */
    public static class ConsumerLagInfo {
        private final String groupId;
        private final String topic;
        private final int partition;
        private final long committedOffset;
        private final long endOffset;
        private final long lag;
        private final long timestamp;

        public ConsumerLagInfo(String groupId, String topic, int partition,
                               long committedOffset, long endOffset, long lag, long timestamp) {
            this.groupId = groupId;
            this.topic = topic;
            this.partition = partition;
            this.committedOffset = committedOffset;
            this.endOffset = endOffset;
            this.lag = lag;
            this.timestamp = timestamp;
        }

        public String getGroupId() { return groupId; }
        public String getTopic() { return topic; }
        public int getPartition() { return partition; }
        public long getCommittedOffset() { return committedOffset; }
        public long getEndOffset() { return endOffset; }
        public long getLag() { return lag; }
        public long getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return String.format("Group: %s | Topic: %s | Partition: %d | " +
                            "Committed: %d | End: %d | Lag: %d | Time: %s",
                    groupId, topic, partition, committedOffset, endOffset, lag,
                    new Date(timestamp));
        }
    }

    /**
     * 示例使用
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "152.136.246.11:9092");
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 30000);

        KafkaLagMonitor monitor = null;

        try {
            monitor = new KafkaLagMonitor(props);
            String groupId = "atguigu";

            // 方法1: 获取指定消费者组的所有分区延迟
            System.out.println("=== 消费者组 " + groupId + " 的延迟信息 ===");
            Map<TopicPartition, ConsumerLagInfo> lags = monitor.getConsumerGroupLags(groupId);

            lags.values().forEach(lagInfo ->
                    System.out.println(lagInfo)
            );

            // 计算总延迟
            long totalLag = lags.values().stream()
                    .mapToLong(ConsumerLagInfo::getLag)
                    .sum();
            System.out.println("总延迟: " + totalLag);

            // 方法2: 获取延迟最大的5个分区
            System.out.println("\n=== 延迟最大的5个分区 ===");
            List<ConsumerLagInfo> topLags = monitor.getTopNLags(groupId, 5);
            topLags.forEach(System.out::println);

            // 方法3: 获取所有消费者组的延迟（谨慎使用，可能数据量大）
            /*
            System.out.println("\n=== 所有消费者组的延迟信息 ===");
            Map<String, Map<TopicPartition, ConsumerLagInfo>> allLags =
                monitor.getAllConsumerGroupLags();

            allLags.forEach((gid, groupLags) -> {
                long groupTotalLag = groupLags.values().stream()
                    .mapToLong(ConsumerLagInfo::getLag)
                    .sum();
                System.out.printf("消费者组: %s | 总延迟: %d | 分区数: %d%n",
                    gid, groupTotalLag, groupLags.size());
            });
            */

        } catch (Exception e) {
            System.err.println("监控失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (monitor != null) {
                monitor.close();
            }
        }
    }
}