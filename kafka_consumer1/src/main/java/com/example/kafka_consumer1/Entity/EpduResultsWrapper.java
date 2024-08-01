package com.example.kafka_consumer1.Entity;

import java.util.List;

public class EpduResultsWrapper {
    private String channel;
    private List<EpduResult> epduResults;

    // getters and setters
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<EpduResult> getEpduResults() {
        return epduResults;
    }

    public void setEpduResults(List<EpduResult> epduResults) {
        this.epduResults = epduResults;
    }
}

class EpduResult {
    private int apid;
    private ExtraInfo extraInfo;
    private int parameterNum;
    private List<ParameterResult> parameterResults;

    // getters and setters
    public int getApid() {
        return apid;
    }

    public void setApid(int apid) {
        this.apid = apid;
    }

    public ExtraInfo getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(ExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getParameterNum() {
        return parameterNum;
    }

    public void setParameterNum(int parameterNum) {
        this.parameterNum = parameterNum;
    }

    public List<ParameterResult> getParameterResults() {
        return parameterResults;
    }

    public void setParameterResults(List<ParameterResult> parameterResults) {
        this.parameterResults = parameterResults;
    }
}

class ExtraInfo {
    private boolean success;

    // getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

class ParameterResult {
    private String code;
    private boolean enumValue;
    private String name;
    private String origData;
    private boolean overrun;
    private String result;
    private String value;
    private String valueType;

    // getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnumValue() {
        return enumValue;
    }

    public void setEnumValue(boolean enumValue) {
        this.enumValue = enumValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigData() {
        return origData;
    }

    public void setOrigData(String origData) {
        this.origData = origData;
    }

    public boolean isOverrun() {
        return overrun;
    }

    public void setOverrun(boolean overrun) {
        this.overrun = overrun;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
