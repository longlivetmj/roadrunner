package com.tmj.tms.finance.datalayer.modal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bulk_invoice_detail")
public class BulkInvoiceDetail {

    private Integer bulkInvoiceDetailSeq;
    private Integer bulkInvoiceSeq;
    private Integer localInvoiceSeq;
    private Integer status;

    private LocalInvoice localInvoice;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "bulk_invoice_detail_seq", allocationSize = 1)
    @Column(name = "bulk_invoice_detail_seq", unique = true)
    public Integer getBulkInvoiceDetailSeq() {
        return bulkInvoiceDetailSeq;
    }

    public void setBulkInvoiceDetailSeq(Integer bulkInvoiceDetailSeq) {
        this.bulkInvoiceDetailSeq = bulkInvoiceDetailSeq;
    }

    @Basic
    @Column(name = "bulk_invoice_seq", nullable = false, insertable = false, updatable = false)
    public Integer getBulkInvoiceSeq() {
        return bulkInvoiceSeq;
    }

    public void setBulkInvoiceSeq(Integer bulkInvoiceSeq) {
        this.bulkInvoiceSeq = bulkInvoiceSeq;
    }

    @Basic
    @Column(name = "local_invoice_seq", nullable = false)
    public Integer getLocalInvoiceSeq() {
        return localInvoiceSeq;
    }

    public void setLocalInvoiceSeq(Integer localInvoiceSeq) {
        this.localInvoiceSeq = localInvoiceSeq;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "local_invoice_seq", insertable = false, updatable = false)
    public LocalInvoice getLocalInvoice() {
        return localInvoice;
    }

    public void setLocalInvoice(LocalInvoice localInvoice) {
        this.localInvoice = localInvoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkInvoiceDetail that = (BulkInvoiceDetail) o;
        return Objects.equals(bulkInvoiceDetailSeq, that.bulkInvoiceDetailSeq) &&
                Objects.equals(bulkInvoiceSeq, that.bulkInvoiceSeq) &&
                Objects.equals(localInvoiceSeq, that.localInvoiceSeq) &&
                Objects.equals(status, that.status) &&
                Objects.equals(localInvoice, that.localInvoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulkInvoiceDetailSeq, bulkInvoiceSeq, localInvoiceSeq, status, localInvoice);
    }
}
