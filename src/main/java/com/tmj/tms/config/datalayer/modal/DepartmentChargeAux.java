package com.tmj.tms.config.datalayer.modal;

import java.util.List;

public class DepartmentChargeAux {

    private Integer moduleSeq;
    private Integer departmentSeq;

    private List<DepartmentCharge> departmentCharges;

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public List<DepartmentCharge> getDepartmentCharges() {
        return departmentCharges;
    }

    public void setDepartmentCharges(List<DepartmentCharge> departmentCharges) {
        this.departmentCharges = departmentCharges;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentChargeAux that = (DepartmentChargeAux) o;

        if (!moduleSeq.equals(that.moduleSeq)) return false;
        if (!departmentSeq.equals(that.departmentSeq)) return false;
        return departmentCharges != null ? departmentCharges.equals(that.departmentCharges) : that.departmentCharges == null;

    }

    @Override
    public int hashCode() {
        int result = moduleSeq.hashCode();
        result = 31 * result + departmentSeq.hashCode();
        result = 31 * result + (departmentCharges != null ? departmentCharges.hashCode() : 0);
        return result;
    }
}
