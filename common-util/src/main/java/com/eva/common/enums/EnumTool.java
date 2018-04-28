package com.eva.common.enums;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnumTool {
    private EnumTool() {
    }

    public static <E extends ICodeEnum> String trans(String enumClass, String code) {
        if (StringUtils.isBlank(enumClass)) {
            throw new IllegalArgumentException("enumClass参数不能为null");
        } else {
            ICodeEnum enumClazz = enumOf(enumClass, code);
            if (enumClazz != null) {
                return enumClazz.getTrans();
            } else {
                return null;
            }
        }
    }

    public static <E extends ICodeEnum> E enumOf(Class<E> enumClass, String code) {
        if (enumClass == null) {
            throw new IllegalArgumentException("enumClass参数不能为null");
        } else {
            ICodeEnum[] var2 = (ICodeEnum[])enumClass.getEnumConstants();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                E e = (E) var2[var4];
                if (e.getCode().equals(code)) {
                    return e;
                }
            }

            return null;
        }
    }

    public static ICodeEnum enumOf(String enumClass, String code) {
        Class<? extends ICodeEnum> enumClazz = getCodeEnumClass(enumClass);
        return enumOf(enumClazz, code);
    }

    public static Map<String, String> getCodeMap(Class<? extends ICodeEnum> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("enumClass参数不能为null！");
        } else {
            ICodeEnum[] enumConstants = (ICodeEnum[])enumClass.getEnumConstants();
            Map<String, String> codeMap = new LinkedHashMap(enumConstants.length);
            ICodeEnum[] var3 = enumConstants;
            int var4 = enumConstants.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ICodeEnum e = var3[var5];
                codeMap.put(e.getCode(), e.getTrans());
            }

            return codeMap;
        }
    }

    public static Map<String, String> getCodeMap(String enumClass) {
        Class<? extends ICodeEnum> enumClazz = getCodeEnumClass(enumClass);
        return getCodeMap(enumClazz);
    }

    public static Class<? extends ICodeEnum> getCodeEnumClass(String enumClass) {
        if (StringUtils.isBlank(enumClass)) {
            throw new IllegalArgumentException("enumClass参数不能为null！");
        } else {
            Class enumClazz;
            try {
                enumClazz = Class.forName(enumClass);
            } catch (ClassNotFoundException var3) {
                throw new IllegalArgumentException(enumClass + "不存在！");
            }

            if (!enumClazz.isEnum()) {
                throw new IllegalArgumentException(enumClass + "不是枚举！");
            } else if (!ICodeEnum.class.isAssignableFrom(enumClazz)) {
                throw new IllegalArgumentException(enumClass + "没有实现" + ICodeEnum.class);
            } else {
                return enumClazz;
            }
        }
    }

    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("enumClass参数不能为null");
        } else {
            return EnumUtils.getEnumMap(enumClass);
        }
    }

    public static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("enumClass参数不能为null");
        } else {
            return EnumUtils.getEnumList(enumClass);
        }
    }

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName) {
        return EnumUtils.isValidEnum(enumClass, enumName);
    }

    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName) {
        return EnumUtils.getEnum(enumClass, enumName);
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> enumClass, Iterable<E> values) {
        return EnumUtils.generateBitVector(enumClass, values);
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> enumClass, E... values) {
        return EnumUtils.generateBitVector(enumClass, values);
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> enumClass, long value) {
        return EnumUtils.processBitVector(enumClass, value);
    }
}
