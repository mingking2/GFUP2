package com.example.gfup2.controller;

import com.example.gfup2.exception.EntityException;
import com.example.gfup2.exception.ValidException;
import com.example.gfup2.exception.VerifyException;
import org.springframework.http.ResponseEntity;

public interface ControllerBase {
    public ResponseEntity<?> run() throws ValidException, VerifyException, EntityException;
}
