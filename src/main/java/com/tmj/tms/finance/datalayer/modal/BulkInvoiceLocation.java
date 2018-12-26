package com.tmj.tms.finance.datalayer.modal;

import com.tmj.tms.master.datalayer.modal.FinalDestination;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bulk_invoice_location")
public class BulkInvoiceLocation {

    private Integer bulkInvoiceLocationSeq;
    private Integer bulkInvoiceSeq;
    private Integer finalDestinationSeq;

    private FinalDestination finalDestination;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "bulk_invoice_location_seq", allocationSize = 1)
    @Column(name = "bulk_invoice_location_seq", unique = true)
    public Integer getBulkInvoiceLocationSeq() {
        return bulkInvoiceLocationSeq;
    }

    public void setBulkInvoiceLocationSeq(Integer bulkInvoiceLocationSeq) {
        this.bulkInvoiceLocationSeq = bulkInvoiceLocationSeq;
    }

    @Basic
    @Column(name = "bulk_invoice_seq", updatable = false, insertable = false)
    public Integer getBulkInvoiceSeq() {
        return bulkInvoiceSeq;
    }

    public void setBulkInvoiceSeq(Integer bulkInvoiceSeq) {
        this.bulkInvoiceSeq = bulkInvoiceSeq;
    }

    @Basic
    @Column(name = "final_destination_seq", nullable = false)
    public Integer getFinalDestinationSeq() {
        return finalDestinationSeq;
    }

    public void setFinalDestinationSeq(Integer finalDestinationSeq) {
        this.finalDestinationSeq = finalDestinationSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(FinalDestination finalDestination) {
        this.finalDestination = finalDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkInvoiceLocation that = (BulkInvoiceLocation) o;
        return Objects.equals(bulkInvoiceLocationSeq, that.bulkInvoiceLocationSeq) &&
                Objects.equals(bulkInvoiceSeq, that.bulkInvoiceSeq) &&
                Objects.equals(finalDestinationSeq, that.finalDestinationSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulkInvoiceLocationSeq, bulkInvoiceSeq, finalDestinationSeq);
    }
}
