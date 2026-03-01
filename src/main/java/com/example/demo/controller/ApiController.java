package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @GetMapping("/enderecos")
    public List<String> listarEnderecos() {
        return List.of(
                "consolelog.com.br",
                "www.consolelog.com.br",
                "https://consolelog.com.br",
                "https://www.consolelog.com.br"
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {

        if (!("usuario1".equals(request.getUsuario()) && "123".equals(request.getSenha()))) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.noContent()
                .header("access-token", "teste access token")
                .build();
    }
}