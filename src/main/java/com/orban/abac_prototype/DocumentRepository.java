package com.orban.abac_prototype;

import org.springframework.data.jpa.repository.*; import java.util.*;
public interface DocumentRepository extends JpaRepository<Document, UUID> {}
