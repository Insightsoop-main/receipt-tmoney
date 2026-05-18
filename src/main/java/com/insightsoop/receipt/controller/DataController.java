package com.insightsoop.receipt.controller;

import com.insightsoop.receipt.dto.DataResponseDto;
import com.insightsoop.receipt.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/e-receipt")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping("/data")
    public DataResponseDto getData(
            @RequestParam(required = false) String partnerCode,
            @RequestParam(required = false) String partnerReqUuid,
            @RequestParam(required = false) String type) {
        log.info("데이터 요청: partnerCode={}, partnerReqUuid={}, type={}", partnerCode, partnerReqUuid, type);
        return dataService.getData(partnerCode, partnerReqUuid, type);
    }
}
