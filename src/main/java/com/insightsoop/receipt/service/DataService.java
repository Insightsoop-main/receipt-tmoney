package com.insightsoop.receipt.service;

import com.insightsoop.receipt.dto.DataResponseDto;
import com.insightsoop.receipt.entity.TmoneyReceipt;
import com.insightsoop.receipt.repository.TmoneyReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {

    private final TmoneyReceiptRepository receiptRepository;

    @Value("${app.html-view-host}")
    private String htmlViewHost;

    private static final List<String> VALID_TYPES = Arrays.asList("html", "json", "text", "link");

    public DataResponseDto getData(String partnerCode, String partnerReqUuid, String type) {
        if (isEmpty(partnerCode) || isEmpty(partnerReqUuid) || isEmpty(type)) {
            return DataResponseDto.error("4220", "필수 파라미터 누락");
        }

        List<String> types = Arrays.stream(type.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(t -> !t.isBlank())
                .toList();

        boolean hasInvalidType = types.stream().anyMatch(t -> !VALID_TYPES.contains(t));
        if (types.isEmpty() || hasInvalidType) {
            return DataResponseDto.error("4221", "필수 파라미터 오류");
        }

        Optional<TmoneyReceipt> result = receiptRepository.findByPartnerCodeAndPartnerReqUuid(partnerCode, partnerReqUuid);
        if (result.isEmpty()) {
            return DataResponseDto.error("4040", "요청 데이터를 찾을 수 없음");
        }

        TmoneyReceipt receipt = result.get();
        log.info("데이터 조회: partnerCode={}, partnerReqUuid={}, types={}", partnerCode, partnerReqUuid, types);

        String html = "No Data";
        String json = "No Data";
        String text = "No Data";
        String link = "No Data";

        for (String t : types) {
            switch (t) {
                case "html" -> html = buildHtml(receipt);
                case "json" -> json = isEmpty(receipt.getJsonData()) ? "No Data" : receipt.getJsonData();
                case "text" -> text = isEmpty(receipt.getTextData()) ? "No Data" : receipt.getTextData();
                case "link" -> link = htmlViewHost + "?t=" + receipt.getTagId();
            }
        }

        return DataResponseDto.ok(html, json, text, link);
    }

    private String buildHtml(TmoneyReceipt receipt) {
        String style = receipt.getStyle();
        String body = receipt.getBody();
        if (isEmpty(style) && isEmpty(body)) {
            return "No Data";
        }
        return "<html><head><style>" + (isEmpty(style) ? "" : style)
                + "</style></head><body>" + (isEmpty(body) ? "" : body) + "</body></html>";
    }

    private boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }
}
