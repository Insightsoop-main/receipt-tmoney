package com.insightsoop.receipt.controller;

import com.insightsoop.receipt.dto.TmoneyRequestDto;
import com.insightsoop.receipt.dto.TmoneyResponseDto;
import com.insightsoop.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/e-receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping("/parser")
    public TmoneyResponseDto parser(@RequestBody TmoneyRequestDto request) {
        log.info("수신: tagId={}, partnerCode={}, partnerReqUuid={}",
                request.getTagId(), request.getPartnerCode(), request.getPartnerReqUuid());
        return receiptService.process(request);
    }
}
