package com.tmj.tms.utility;

import javax.cache.annotation.CacheResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TrueOrFalse {
    FALSE("False", 0),
    TRUE("True", 1);

    private String trueOrFalse;
    private Integer trueOrFalseSeq;

    TrueOrFalse(String trueOrFalse, Integer trueOrFalseSeq) {
        this.trueOrFalse = trueOrFalse;
        this.trueOrFalseSeq = trueOrFalseSeq;
    }

    public static TrueOrFalse findOne(Integer trueOrFalseSeq) {
        return Arrays.stream(TrueOrFalse.values())
                .filter(x -> x.trueOrFalseSeq.equals(trueOrFalseSeq))
                .findFirst()
                .orElse(null);
    }

    @CacheResult
    public static List<TrueOrFalse> getTrueOrFalseListForCreate() {
        TrueOrFalse[] trueOrFalses = TrueOrFalse.values();
        List<TrueOrFalse> trueOrFalseList = new ArrayList<>();
        for (TrueOrFalse trueOrFalse : trueOrFalses) {
            trueOrFalseList.add(trueOrFalse);
        }
        return trueOrFalseList;
    }

    public String getTrueOrFalse() {
        return trueOrFalse;
    }

    public Integer getTrueOrFalseSeq() {
        return trueOrFalseSeq;
    }
}
