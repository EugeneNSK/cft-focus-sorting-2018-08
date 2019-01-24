package com.java.zonov.cft.sorting.sort;

import java.util.ArrayList;
import java.util.List;

public class SortingT {

    public static <T extends Comparable<T>> List<T> sort(List<List<T>> list) {

        List<T> result = list.get(0);
        for (int i=0; i<list.size()-1; i++){
            result=merge(result,list.get(i+1));
        }

//        System.out.println(result);
        return result;
    }

    private static <T extends Comparable<T>> List<T> merge (List<T> a, List<T> b){

        int i = 0, j = 0, k = 0;
        List<T> result = new ArrayList<>();

        while (i < a.size() && j < b.size()) {
            result.add(k++, a.get(i).compareTo(b.get(j))<0 ? a.get(i++) : b.get(j++));
        }

        while (i < a.size()) {
            result.add(k++, a.get(i++));
        }

        while (j < b.size()) {
            result.add(k++, b.get(j++));
        }
        return result;
    }

}
