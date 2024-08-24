package ReblanceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RebalanceSimulation {
    private static final List<ConsumerThread> consumers = new ArrayList<>();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    private static int consumerCount = 0;

    public static void main(String[] args) {
        String topic = "reblanceTopic";
        String groupId = "group2";

        // 每10秒创建一个消费者
        executor.scheduleAtFixedRate(() -> {
            if (consumerCount < 3) {
                consumerCount++;
                String consumerId = "consumer-" + consumerCount;
                ConsumerThread consumerThread = new ConsumerThread(topic, groupId, consumerId);
                consumers.add(consumerThread);
                new Thread(consumerThread).start();
                System.out.println("Started " + consumerId);
            }
        }, 0, 10, TimeUnit.SECONDS);

        // 每20秒关闭一个消费者
        executor.scheduleAtFixedRate(() -> {
            if (consumerCount > 1) {
                ConsumerThread consumerToShutdown = consumers.remove(consumers.size() - 1);
                consumerToShutdown.shutdown();
                consumerCount--;
                System.out.println("Shut down a consumer. Remaining: " + consumerCount);
            }
        }, 20, 20, TimeUnit.SECONDS);

        // 如果消费者数量等于1，则重新开始创建
        executor.scheduleAtFixedRate(() -> {
            if (consumerCount == 1) {
                System.out.println("Only one consumer left. Resetting creation...");
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
