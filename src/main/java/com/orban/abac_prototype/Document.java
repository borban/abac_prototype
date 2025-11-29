package com.orban.abac_prototype;

import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="documents")
public class Document {
    @Id private UUID id;
    private String title; private String ownerSub; private String classification;
// getters/setters
}