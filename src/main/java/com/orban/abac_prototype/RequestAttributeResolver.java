package com.orban.abac_prototype;

import org.springframework.stereotype.Component;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Component
public class RequestAttributeResolver implements AttributeResolver {
    @Override public Map<String, Object> resolve(RequestAuthorizationContext ctx, Authentication auth){
        return Map.of("env", Map.of(
                "method", ctx.getRequest().getMethod(),
                "path", ctx.getRequest().getRequestURI()
        ));
    }
}