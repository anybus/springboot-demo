package com.eva.common.cn;

import com.eva.common.enums.Sex;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IdCardNoTool {
    private static final int MAINLAND_ID_MIN_LENGTH = 15;
    private static final int MAINLAND_ID_MAX_LENGTH = 18;
    private static final int[] power = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final String[] verifyCode = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    private static final int MIN = 1930;
    private static final Map<String, Integer> twFirstCode = new HashMap();

    private IdCardNoTool() {
    }

    public static String convert15To18(String idCardNo15) {
        if (!StringUtils.isBlank(idCardNo15) && idCardNo15.length() == 15) {
            if (isNumber(idCardNo15)) {
                String birthday = idCardNo15.substring(6, 12);
                Date birthDate = null;

                try {
                    birthDate = (new SimpleDateFormat("yyMMdd")).parse(birthday);
                } catch (ParseException var10) {
                    var10.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();
                if (birthDate != null) {
                    cal.setTime(birthDate);
                }

                String sYear = String.valueOf(cal.get(1));
                String idCard18 = idCardNo15.substring(0, 6) + sYear + idCardNo15.substring(8);
                char[] cArr = idCard18.toCharArray();
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() > 0) {
                    idCard18 = idCard18 + sVal;
                    return idCard18;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isIdCardNo(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return isIdCardNo18(str) || isIdCardNo15(str) || isHkIdCardNo(str) || isMacauIdCardNo(str) || isTwIdCardNo(str);
        }
    }

    public static boolean isIdCardNo18(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            boolean bTrue = false;
            if (str.length() == 18) {
                String code17 = str.substring(0, 17);
                String code18 = str.substring(17, 18);
                if (isNumber(code17)) {
                    char[] cArr = code17.toCharArray();
                    int[] iCard = converCharToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    String val = getCheckCode18(iSum17);
                    if (val.length() > 0 && val.equalsIgnoreCase(code18)) {
                        bTrue = true;
                    }
                }
            }

            return bTrue;
        }
    }

    public static boolean isIdCardNo15(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (str.length() != 15) {
            return false;
        } else if (isNumber(str)) {
            String proCode = str.substring(0, 2);
            if (Province.enumOf(proCode) == null) {
                return false;
            } else {
                String birthCode = str.substring(6, 12);
                Date birthDate = null;

                try {
                    birthDate = (new SimpleDateFormat("yy")).parse(birthCode.substring(0, 2));
                } catch (ParseException var5) {
                    var5.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();
                if (birthDate != null) {
                    cal.setTime(birthDate);
                }

                return valiDate(cal.get(1), Integer.valueOf(birthCode.substring(2, 4)), Integer.valueOf(birthCode.substring(4, 6)));
            }
        } else {
            return false;
        }
    }

    public static boolean isTwIdCardNo(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches("^[a-zA-Z][0-9]{9}$")) {
            return false;
        } else {
            String start = str.substring(0, 1);
            String mid = str.substring(1, 9);
            String end = str.substring(9, 10);
            Integer iStart = (Integer) twFirstCode.get(start);
            if (iStart == null) {
                return false;
            } else {
                Integer sum = iStart / 10 + iStart % 10 * 9;
                char[] chars = mid.toCharArray();
                Integer iflag = 8;
                char[] var8 = chars;
                int var9 = chars.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    char c = var8[var10];
                    sum = sum + Integer.valueOf(c + "") * iflag;
                    iflag = iflag - 1;
                }

                return (sum % 10 == 0 ? 0 : 10 - sum % 10) == Integer.valueOf(end);
            }
        }
    }

    public static boolean isHkIdCardNo(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
            return false;
        } else {
            String card = str.replaceAll("[\\(|\\)]", "");
            Integer sum;
            if (card.length() == 9) {
                sum = (card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 9 + (card.substring(1, 2).toUpperCase().toCharArray()[0] - 55) * 8;
                card = card.substring(1, 9);
            } else {
                sum = 522 + (card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 8;
            }

            String mid = card.substring(1, 7);
            String end = card.substring(7, 8);
            char[] chars = mid.toCharArray();
            Integer iflag = 7;
            char[] var7 = chars;
            int var8 = chars.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                char c = var7[var9];
                sum = sum + Integer.valueOf(c + "") * iflag;
                iflag = iflag - 1;
            }

            if (end.toUpperCase().equals("A")) {
                sum = sum + 10;
            } else {
                sum = sum + Integer.valueOf(end);
            }

            return sum % 11 == 0;
        }
    }

    public static boolean isMacauIdCardNo(String str) {
        return !StringUtils.isBlank(str) && str.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$");
    }

    private static int[] converCharToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];

        for (int i = 0; i < len; ++i) {
            iArr[i] = CharUtils.toIntValue(ca[i]);
        }

        return iArr;
    }

    private static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if (power.length == iArr.length) {
            for (int i = 0; i < iArr.length; ++i) {
                for (int j = 0; j < power.length; ++j) {
                    if (i == j) {
                        iSum += iArr[i] * power[j];
                    }
                }
            }
        }

        return iSum;
    }

    private static String getCheckCode18(int iSum) {
        return verifyCode[iSum % 11];
    }

    public static String getBirthday(String idCardNo) {
        if (StringUtils.isBlank(idCardNo)) {
            return null;
        } else {
            int len = idCardNo.length();
            if (len < 15) {
                return null;
            } else {
                if (len == 15) {
                    idCardNo = convert15To18(idCardNo);
                }

                return idCardNo.substring(6, 14);
            }
        }
    }

    public static Sex getSex(String idCardNo) {
        if (StringUtils.isBlank(idCardNo)) {
            return Sex.UNKNOWN;
        } else if (isTwIdCardNo(idCardNo)) {
            return idCardNo.charAt(1) == '1' ? Sex.MALE : Sex.FEMALE;
        } else if (idCardNo.length() != 15 && idCardNo.length() != 18) {
            return Sex.UNKNOWN;
        } else {
            if (idCardNo.length() == 15) {
                idCardNo = convert15To18(idCardNo);
            }

            String sCardNum = idCardNo.substring(16, 17);
            Sex sGender;
            if (Integer.parseInt(sCardNum) % 2 != 0) {
                sGender = Sex.MALE;
            } else {
                sGender = Sex.FEMALE;
            }

            return sGender;
        }
    }

    public static Province getProvince(String idCardNo) {
        if (StringUtils.isBlank(idCardNo)) {
            return null;
        } else {
            String code = null;
            if (!isIdCardNo15(idCardNo) && !isIdCardNo18(idCardNo)) {
                if (isHkIdCardNo(idCardNo)) {
                    return Province.XIANG_GANG;
                }

                if (isTwIdCardNo(idCardNo)) {
                    return Province.TAI_WAN;
                }

                if (isMacauIdCardNo(idCardNo)) {
                    return Province.AO_MEN;
                }
            } else {
                code = idCardNo.substring(0, 2);
            }

            return code == null ? null : Province.enumOf(code);
        }
    }

    private static boolean isNumber(String str) {
        return !StringUtils.isBlank(str) && str.matches("^[0-9]*$");
    }

    private static boolean valiDate(int iYear, int iMonth, int iDate) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1);
        if (iYear >= 1930 && iYear < year) {
            if (iMonth >= 1 && iMonth <= 12) {
                int datePerMonth;
                switch (iMonth) {
                    case 2:
                        boolean dm = (iYear % 4 == 0 && iYear % 100 != 0 || iYear % 400 == 0) && iYear > 1930 && iYear < year;
                        datePerMonth = dm ? 29 : 28;
                        break;
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    default:
                        datePerMonth = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        datePerMonth = 30;
                }

                return iDate >= 1 && iDate <= datePerMonth;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    static {
        twFirstCode.put("A", 10);
        twFirstCode.put("B", 11);
        twFirstCode.put("C", 12);
        twFirstCode.put("D", 13);
        twFirstCode.put("E", 14);
        twFirstCode.put("F", 15);
        twFirstCode.put("G", 16);
        twFirstCode.put("H", 17);
        twFirstCode.put("J", 18);
        twFirstCode.put("K", 19);
        twFirstCode.put("L", 20);
        twFirstCode.put("M", 21);
        twFirstCode.put("N", 22);
        twFirstCode.put("P", 23);
        twFirstCode.put("Q", 24);
        twFirstCode.put("R", 25);
        twFirstCode.put("S", 26);
        twFirstCode.put("T", 27);
        twFirstCode.put("U", 28);
        twFirstCode.put("V", 29);
        twFirstCode.put("X", 30);
        twFirstCode.put("Y", 31);
        twFirstCode.put("W", 32);
        twFirstCode.put("Z", 33);
        twFirstCode.put("I", 34);
        twFirstCode.put("O", 35);
    }


}


