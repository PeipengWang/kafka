import Entity.EpduResultsWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonTest {
    public static void main(String[] args) {
        String json = "{ \"channel\": \"TJS\", \"epduResults\": [ { \"apid\": 2721, \"extraInfo\": { \"success\": true }, \"parameterNum\": 2, \"parameterResults\": [ { \"code\": \"P_TF_WSBW\", \"enum\": false, \"name\": \"识别码\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" }, { \"code\": \"P_TF_WSBW2\", \"enum\": false, \"name\": \"识别码2\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" } ] }, { \"apid\": 2722, \"extraInfo\": { \"success\": true }, \"parameterNum\": 1, \"parameterResults\": [ { \"code\": \"P_TF_WSBW3\", \"enum\": false, \"name\": \"识别码3\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" } ] } ] }";

        // 反序列化 JSON 字符串为 EpduResultsWrapper 对象
        EpduResultsWrapper wrapper = JSON.parseObject(json, EpduResultsWrapper.class);

        // 打印 channel 和 epduResults 的大小
        System.out.println("Channel: " + wrapper.getChannel());
        System.out.println("Number of epduResults: " + wrapper.getEpduResults().size());
    }
}
