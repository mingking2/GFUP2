package com.example.gfup2.controller;

import org.springframework.http.ResponseEntity;

import javax.naming.Binding;

public interface ControllerBase {
    public ResponseEntity<?> run() throws Exception;
}
