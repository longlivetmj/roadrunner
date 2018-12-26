package com.tmj.tms.master.datalayer.modal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "region")
public class Region {
    private Integer regionSeq;
    private String regionName;
    private String description;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "region_seq", allocationSize = 1)
    @Column(name = "region_seq", nullable = false, precision = 0, unique = true)
    public Integer getRegionSeq() {
        return regionSeq;
    }

    public void setRegionSeq(Integer regionSeq) {
        this.regionSeq = regionSeq;
    }

    @Basic
    @Column(name = "region_name", nullable = false, length = 100)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status", nullable = false, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return regionSeq == region.regionSeq &&
                status == region.status &&
                Objects.equals(regionName, region.regionName) &&
                Objects.equals(description, region.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionSeq, regionName, description, status);
    }
}
