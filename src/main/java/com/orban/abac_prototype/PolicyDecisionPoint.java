package com.orban.abac_prototype;

import java.util.*;

public interface PolicyDecisionPoint {
    enum Effect { ALLOW, DENY }
    record Decision(Effect effect, String reason, Map<String,Object> obligations) {}
    Decision evaluate(String action, String resourceType, Map<String,Object> context);
}
