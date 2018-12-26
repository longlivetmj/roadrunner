package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;

@Entity
@Table(name = "table_constraint")
public class TableConstraint {
    private String constraintName;
    private String description;

    @Id
    @Column(name = "constraint_name", nullable = false, length = 200)
    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableConstraint that = (TableConstraint) o;

        if (constraintName != null ? !constraintName.equals(that.constraintName) : that.constraintName != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = constraintName != null ? constraintName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
