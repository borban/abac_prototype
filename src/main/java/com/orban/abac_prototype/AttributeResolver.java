package com.orban.abac_prototype;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import java.util.*;

public interface AttributeResolver {
    Map<String,Object> resolve(RequestAuthorizationContext requestCtx, Authentication auth);
}
