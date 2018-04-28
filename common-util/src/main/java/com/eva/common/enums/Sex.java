package com.eva.common.enums;

import java.util.Map;

public enum Sex implements ICodeEnum {
    UNKNOWN("0", "未知的性别"),
    MALE("1", "男"),
    FEMALE("2", "女"),
    UNSPECIFIED("9", "未说明的性别");

    public static final String CODE_TABLE_ID = "time_unit";
    private final String code;
    private String desc;

    private Sex(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static void initTrans(Map<String, String> map) {
        Sex[] values = values();
        Sex[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Sex sex = var2[var4];
            sex.desc = (String)map.get(sex.getCode());
        }

    }

    public String getCode() {
        return this.code;
    }

    public String getTrans() {
        return this.desc;
    }

    public static Sex enumOf(String code) {
        return (Sex)EnumTool.enumOf(Sex.class, code);
    }

    public String toString() {
        return this.desc;
    }
}
