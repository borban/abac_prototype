package com.orban.abac_prototype;

import org.springframework.stereotype.*;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Component
public class AttributeResolverChain {
    private final List<AttributeResolver> resolvers;
    public AttributeResolverChain(List<AttributeResolver> resolvers){ this.resolvers = resolvers; }
    public Map<String,Object> build(RequestAuthorizationContext ctx, Authentication auth){
        Map<String,Object> out = new HashMap<>();
        for (var r: resolvers) out.putAll(r.resolve(ctx, auth));
        return out;
    }
}
