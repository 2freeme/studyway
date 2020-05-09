package com.base.util;

import java.math.BigDecimal;

public class UnitConversion {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static BigDecimal changeF2Y(BigDecimal amount){
        return stripTrailingZeros(amount.divide(ONE_HUNDRED));
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static BigDecimal changeY2F(BigDecimal amount){
        return stripTrailingZeros(amount.multiply(ONE_HUNDRED));
    }

    /**
     * 去除小数末尾的0 （100.10 ==> 100.1）
     *
     * @param amount
     * @return
     */
    public static BigDecimal stripTrailingZeros(BigDecimal amount) {
        return new BigDecimal(amount.stripTrailingZeros().toPlainString());
    }
}
