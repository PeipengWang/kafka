package utils;


import java.util.HashMap;
import java.util.Map;

public class ParameterConditionChecker {
    private Map<String, String> conditions = new HashMap<>();



    public ParameterConditionChecker(String conditionString) {
        String[] conditionParts = conditionString.split("\\|");
        for (String part : conditionParts) {
            String[] params = part.split("&");
            for (String param : params) {
                String[] split = param.split("[><=]");
                conditions.put(split[0], param);
                System.out.println(split[0]);
                System.out.println(param);
            }
        }
    }

    public boolean checkConditions(Map<String, String> data) {
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            String key = entry.getKey();
            String condition = entry.getValue();
            String actualValue = data.get(key);

            if (actualValue == null || !evaluateCondition(condition, actualValue)) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateCondition(String condition, String actualValue) {
        if (condition.contains(">")) {
            String[] parts = condition.split(">");
            return Double.parseDouble(actualValue) > Double.parseDouble(parts[1]);
        } else if (condition.contains("<")) {
            String[] parts = condition.split("<");
            return Double.parseDouble(actualValue) < Double.parseDouble(parts[1]);
        } else if (condition.contains("=")) {
            String[] parts = condition.split("=");
            return actualValue.equals(parts[1]);
        }
        return false;
    }
}
