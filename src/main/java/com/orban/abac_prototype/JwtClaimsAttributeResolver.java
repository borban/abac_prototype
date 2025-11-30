package com.orban.abac_prototype;

import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Component
public class JwtClaimsAttributeResolver implements AttributeResolver {
    @Override public Map<String, Object> resolve(RequestAuthorizationContext ctx, Authentication auth){
        if (!(auth instanceof JwtAuthenticationToken t)) return Map.of();
        Jwt jwt = t.getToken();
        Map<String,Object> subject = new HashMap<>();
        subject.put("sub", jwt.getSubject());
        subject.put("roles", Optional.ofNullable(jwt.getClaimAsStringList("roles")).orElse(List.of()));
        return Map.of("subject", subject);
    }
}
