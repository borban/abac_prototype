package com.orban.abac_prototype;

import org.springframework.stereotype.Component;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Component
public class ResourceDbAttributeResolver implements AttributeResolver {
    private final DocumentRepository repo;
    public ResourceDbAttributeResolver(DocumentRepository repo){ this.repo = repo; }
    @Override public Map<String, Object> resolve(RequestAuthorizationContext ctx, Authentication auth){
        String id = ctx.getRequest().getRequestURI().replaceAll("^.*/([0-9a-fA-F-]{36})$", "$1");
        try { var doc = repo.findById(java.util.UUID.fromString(id)).orElse(null);
            if (doc == null) return Map.of();
            return Map.of("resource", Map.of(
                    "type", "document",
                    "id", doc.getId().toString(),
                    "ownerId", doc.getOwnerSub(),
                    "classification", doc.getClassification()
            ));
        } catch (Exception e){ return Map.of(); }
    }
}
