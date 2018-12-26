package com.tmj.tms.utility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Checker {

    public static <T> boolean listEqualsNoOrder(List<T> l1, List<T> l2) {
        final Set<T> s1 = new HashSet<>(l1);
        final Set<T> s2 = new HashSet<>(l2);
        return s1.equals(s2);
    }

}
