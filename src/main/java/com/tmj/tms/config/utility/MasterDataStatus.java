package com.tmj.tms.config.utility;

import com.tmj.tms.utility.AuthorityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.cache.annotation.CacheResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum MasterDataStatus {

    DELETED(0, "Deleted"),
    OPEN(1, "Open"),
    APPROVED(2, "Approved"),
    CLOSED(3, "Closed");

    private static AuthorityChecker authorityChecker;
    private final Integer statusSeq;
    private final String status;

    MasterDataStatus(Integer statusSeq, String status) {
        this.statusSeq = statusSeq;
        this.status = status;
    }

    public static MasterDataStatus findOne(Integer statusSeq) {
        return Arrays.stream(MasterDataStatus.values())
                .filter(x -> x.statusSeq.equals(statusSeq))
                .findFirst()
                .orElse(null);
    }

    @CacheResult
    public static List<Integer> getCommonStatusSeqList() {
        MasterDataStatus[] masterDataStatuses = MasterDataStatus.values();
        List<Integer> masterDataStatusList = new ArrayList<>();
        for (MasterDataStatus masterDataStatus : masterDataStatuses) {
            if (!masterDataStatus.getStatusSeq().equals(0)) {
                masterDataStatusList.add(masterDataStatus.getStatusSeq());
            }
        }
        return masterDataStatusList;
    }

    public static List<Integer> getStatusListForViewing(String role) {
        MasterDataStatus[] masterDataStatuses = MasterDataStatus.values();
        List<Integer> masterDataStatusList = new ArrayList<>();
        if (authorityChecker.hasRole(role)) {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                masterDataStatusList.add(masterDataStatus.getStatusSeq());
            }
        } else {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                if (!masterDataStatus.getStatusSeq().equals(0) && !masterDataStatus.getStatusSeq().equals(3)) {
                    masterDataStatusList.add(masterDataStatus.getStatusSeq());
                }
            }
        }
        return masterDataStatusList;
    }

    public static List<MasterDataStatus> getStatusListForTransactions(String role) {
        MasterDataStatus[] masterDataStatuses = MasterDataStatus.values();
        List<MasterDataStatus> masterDataStatusList = new ArrayList<>();
        if (authorityChecker.hasRole(role)) {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                if (!masterDataStatus.getStatusSeq().equals(0) && !masterDataStatus.getStatusSeq().equals(3)) {
                    masterDataStatusList.add(masterDataStatus);
                }
            }
        } else {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                if (!masterDataStatus.getStatusSeq().equals(0) && !masterDataStatus.getStatusSeq().equals(2) && !masterDataStatus.getStatusSeq().equals(3)) {
                    masterDataStatusList.add(masterDataStatus);
                }
            }
        }
        return masterDataStatusList;
    }

    public static List<MasterDataStatus> getStatusListForTransaction(List<String> roleList) {
        boolean status = false;
        boolean viewDeleteStatus = false;
        MasterDataStatus[] masterDataStatuses = MasterDataStatus.values();
        List<MasterDataStatus> masterDataStatusList = new ArrayList<>();
        for (String role : roleList) {
            if (authorityChecker.hasRole(role)) {
                status = true;
                String permissionList[] = role.split("_");
                for (String s : permissionList) {
                    if (s.trim().equals("VIEW-DELETE")) {
                        viewDeleteStatus = true;
                    }
                }
            }
        }
        if (status) {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                if (viewDeleteStatus) {
                    masterDataStatusList.add(masterDataStatus);
                } else {
                    if (!masterDataStatus.getStatusSeq().equals(0)) {
                        masterDataStatusList.add(masterDataStatus);
                    }
                }
            }
        } else {
            for (MasterDataStatus masterDataStatus : masterDataStatuses) {
                if (!masterDataStatus.getStatusSeq().equals(0) && !masterDataStatus.getStatusSeq().equals(2)) {
                    masterDataStatusList.add(masterDataStatus);
                }
            }
        }
        return masterDataStatusList;
    }

    public static AuthorityChecker getAuthorityChecker() {
        return authorityChecker;
    }

    public static void setAuthorityChecker(AuthorityChecker authorityChecker) {
        MasterDataStatus.authorityChecker = authorityChecker;
    }

    public Integer getStatusSeq() {
        return statusSeq;
    }

    public String getStatus() {
        return status;
    }

    @Component
    public static class AuthorityCheckerInjector {
        @Autowired
        private AuthorityChecker authorityChecker;

        @PostConstruct
        public void postConstruct() {
            for (MasterDataStatus masterDataStatus : EnumSet.allOf(MasterDataStatus.class))
                masterDataStatus.setAuthorityChecker(authorityChecker);
        }
    }
}
