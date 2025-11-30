package com.orban.abac_prototype;

import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="documents")
public class Document {
    @Id private UUID id;
    private String title; private String ownerSub; private String classification;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerSub() {
        return ownerSub;
    }

    public String getClassification() {
        return classification;
    }
    // getters/setters
}