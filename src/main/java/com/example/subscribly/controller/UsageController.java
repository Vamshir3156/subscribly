package com.example.subscribly.controller;

import com.example.subscribly.dto.UsageRequest;
import com.example.subscribly.model.UsageRecord;
import com.example.subscribly.service.UsageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping
    public ResponseEntity<UsageRecord> recordUsage(@Valid @RequestBody UsageRequest request) {
        UsageRecord record = usageService.recordUsage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }
}
