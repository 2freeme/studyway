package com.base.util3.numberconvert;

/**
 * @author dingpf
 * @since 2020/6/10
 **/

public abstract class LangArabicNumberConvert {

    abstract boolean isDecimalNum(String word);

    abstract String toArabicNumber(String word);

    abstract String toLangNumber(String word);

    abstract String toNoDecimalLangNumber(String word);

}
