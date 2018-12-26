package com.tmj.tms.utility;

import javax.cache.annotation.CacheResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum YesOrNo {
    NO("No", 0),
    Yes("Yes", 1);

    private String yesOrNo;
    private Integer yesOrNoSeq;

    YesOrNo(String yesOrNo, Integer yesOrNoSeq) {
        this.yesOrNo = yesOrNo;
        this.yesOrNoSeq = yesOrNoSeq;
    }

    public static YesOrNo findOne(Integer yesOrNoSeq) {
        return Arrays.stream(YesOrNo.values())
                .filter(x -> x.yesOrNoSeq.equals(yesOrNoSeq))
                .findFirst()
                .orElse(null);
    }

    @CacheResult
    public static List<YesOrNo> getYesOrNoListForCreate() {
        YesOrNo[] yesOrNos = YesOrNo.values();
        List<YesOrNo> yesOrNoList = new ArrayList<>();
        for (YesOrNo yesOrNo : yesOrNos) {
            yesOrNoList.add(yesOrNo);
        }
        return yesOrNoList;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    public Integer getYesOrNoSeq() {
        return yesOrNoSeq;
    }
}
