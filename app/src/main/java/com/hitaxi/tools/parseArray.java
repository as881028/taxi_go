package com.hitaxi.tools;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by DK on 2017/8/4.
 */

public class parseArray {

    public static String[] parseStringArray(ArrayList<String> al) {
        String[] array = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(0);
        }
        return array;
    }

    public static Integer[] parseIntArray(ArrayList<Integer> al) {
        Integer[] array = new Integer[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(0);
        }
        return array;
    }
}
