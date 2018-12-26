package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "authority.default", attributeNodes = {
                @NamedAttributeNode("documentLink")
        })
})
@Entity
@Table(name = "authorities")
public class Authority {
    private Integer authoritySeq;
    private Integer documentLinkSeq;
    private String authority;
    private String createdBy;
    private Date createdDate;

    private DocumentLink documentLink;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "authority_seq", allocationSize = 1)
    @Column(name = "authority_seq", nullable = false, precision = 0, unique = true)
    public Integer getAuthoritySeq() {
        return authoritySeq;
    }

    public void setAuthoritySeq(Integer authoritySeq) {
        this.authoritySeq = authoritySeq;
    }

    @Basic
    @Column(name = "authority", nullable = false, length = 200)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "document_link_seq", updatable = false, insertable = false, nullable = false)
    public Integer getDocumentLinkSeq() {
        return documentLinkSeq;
    }

    public void setDocumentLinkSeq(Integer documentLinkSeq) {
        this.documentLinkSeq = documentLinkSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_link_seq", insertable = false, updatable = false)
    public DocumentLink getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(DocumentLink documentLink) {
        this.documentLink = documentLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority1 = (Authority) o;
        return Objects.equals(getAuthoritySeq(), authority1.getAuthoritySeq()) &&
                Objects.equals(getDocumentLinkSeq(), authority1.getDocumentLinkSeq()) &&
                Objects.equals(getAuthority(), authority1.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthoritySeq(), getDocumentLinkSeq(), getAuthority());
    }
}
