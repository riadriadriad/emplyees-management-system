package com.test.employeeRecordManagement.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RSAKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
