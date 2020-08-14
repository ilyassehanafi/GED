package com.Ged.demoSpringFramework.model;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name="document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String docName;
    private String docType;

    @Lob
    private byte[] documentData;

    @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "ver", referencedColumnName = "id")
    private List<DocVersions> versions;

    public Document() {
    }

    public Document(String docName, String docType, byte[] documentData) {
        this.docName = docName;
        this.docType = docType;
        this.documentData = documentData;
    }

    public Document(String docName, String docType, byte[] documentData, List<DocVersions> versions) {
        this.docName = docName;
        this.docType = docType;
        this.documentData = documentData;
        this.versions = versions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public byte[] getDocumentData() {
        return documentData;
    }

    public void setDocumentData(byte[] documentData) {
        this.documentData = documentData;
    }

    public List<DocVersions> getVersions() {
        return versions;
    }

    public void setVersions(List<DocVersions> versions) {
        this.versions = versions;
    }
}