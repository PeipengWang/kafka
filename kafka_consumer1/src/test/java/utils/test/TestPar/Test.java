package utils.test.TestPar;

import utils.ParameterConditionChecker;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        ParameterConditionChecker checker =
                new ParameterConditionChecker("PK001=5|PK002=4&PK006>4");

        Map<String, String> map = new HashMap<>();
        map.put("PK001", "PK001=5");
        map.put("PK006", "Pk006=6");
        map.put("PK002", "PK002=4");
        checker.checkConditions(map);
    }
}
