package com.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList截取Util
 */
public class SubListUtil {

    /**
     * @param list 需要截取的List
     * @param size 页容量
     * @param <T>
     * @return 根据 size 将 list 分割，并返回所有的subList
     */
    public static <T> List<List<T>> subList(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        if (list == null || list.size() == 0) return result;
        int total = list.size();
        int totalPage = SubListUtil.getTotalPage(total, size);
        for (int page = 1; page <= totalPage; page++) {
            List<T> subList = subList(list, page, size);
            result.add(subList);
        }
        return result;
    }

    /**
     * @param list 需要截取的List
     * @param page 页码(从1开始)
     * @param size 页容量
     * @param <T>
     * @return 每页的subList
     */
    public static <T> List<T> subList(List<T> list, int page, int size) {
        int total = list.size();
        int totalPage = getTotalPage(total, size);
        if (page > totalPage) return null;
        int start = getFromIndex(page, size);
        int end = getToIndex(page, size, total, totalPage);
        return list.subList(start, end);
    }

    public static <T> int getTotalPage(List<T> list, int size) {
        int total = list.size();
        return getTotalPage(total, size);
    }

    public static int getTotalPage(int total, int size) {
        return total % size == 0 ? total / size : total / size + 1;
    }

    public static int getFromIndex(int page, int size) {
        return (page - 1) * size;
    }

    public static int getToIndex(int page, int size, int total, int totalPage) {
        return page == totalPage ? total : page * size;
    }

    /**
     * 将List按指定份数平均分割
     * @param source 来源
     * @param copies 分割份数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> cut(List<T> source, int copies) {
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % copies;  //先计算出余数
        int number = source.size() / copies;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < copies; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + (++offset));
                remainder--;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            if (value.size() > 0) {
                result.add(value);
            }
        }
        return result;
    }

}
