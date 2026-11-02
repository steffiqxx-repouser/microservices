
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VersionController {

    @Value("${app.version}")
    private String appVersion;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/api/version")
    public Map<String, String> getVersion() {

        return Map.of(
            "application", applicationName,
            "applicationVersion", appVersion,
            "springBootVersion", SpringBootVersion.getVersion(),
            "springFrameworkVersion", SpringVersion.getVersion(),
            "javaVersion", System.getProperty("java.version")
        );
    }
}
