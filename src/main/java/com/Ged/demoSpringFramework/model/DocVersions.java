package com.Ged.demoSpringFramework.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_version")
public class DocVersions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime date;

    @ManyToOne
    private Document document;

    public DocVersions() {
    }

    public DocVersions(LocalDateTime date, Document document) {
        this.date = LocalDateTime.now();
        this.document = document;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
