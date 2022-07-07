package com.example.demo.controller;

import com.example.demo.service.auth.AbstractService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractController<S extends AbstractService> {
    protected final S service;
    protected final String API = "/api";
    protected final String VERSION = "/v1";
    protected final String PATH = API + VERSION;
}
