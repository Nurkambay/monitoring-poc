package com.monitoring.site.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/status", produces = "text/html")
public class StatusController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String healthCheck() {
        return "Ok";
    }
}
