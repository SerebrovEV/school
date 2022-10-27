package ru.hogwarts.school.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getPort")
public class InfoController {
    @Value("${server.port}")
    private Integer numberWorkPort;

    @GetMapping
    public ResponseEntity<String> getWorkPort() {
        return ResponseEntity.ok("Work app port: " + numberWorkPort);
    }
}
