package com.orban.abac_prototype;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

public class JwtGenerator {

    public static void main(String[] args) throws Exception {
        // 1) Load signing key from resources/keys/dev.key
        PrivateKey privateKey = loadPrivateKeyFromClasspath("keys/dev.key"); // <-- only change you need

        // 2) Timestamps
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(3600); // 1 hour

        String jwt = Jwts.builder()
                .subject("user-sub-1")
                .issuer("http://local-dev")
                .audience().add("abac-strangler").and()
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(expiry))
                .claim("roles", List.of("doc_reader"))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        System.out.println("JWT: " + jwt);
    }

    // --- helpers ---

    /**
     * Expects a PKCS#8 PEM formatted key (-----BEGIN PRIVATE KEY-----).
     * Place file at src/main/resources/keys/dev.key
     */
    private static PrivateKey loadPrivateKeyFromClasspath(String classpathLocation) throws Exception {
        try (InputStream is = JwtGenerator.class.getClassLoader().getResourceAsStream(classpathLocation)) {
            if (is == null) {
                throw new IllegalStateException("Private key not found on classpath: " + classpathLocation);
            }
            String pem = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String base64 = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] der = Base64.getDecoder().decode(base64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(der);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
    }
}
