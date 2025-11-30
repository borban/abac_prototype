package com.orban.abac_prototype;

import org.slf4j.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ShadowLoggingPdp implements PolicyDecisionPoint {
    private static final Logger log = LoggerFactory.getLogger(ShadowLoggingPdp.class);

    @Override
    public Decision evaluate(String action, String resourceType, Map<String, Object> ctx){
        boolean allow = true; // placeholder: always allow in shadow
        log.info("ABAC_SHADOW decision action={} resourceType={} allow={} ctx={}", action, resourceType, allow, ctx);
        return new Decision(allow ? Effect.ALLOW : Effect.DENY, "shadow", Map.of());
    }
}
