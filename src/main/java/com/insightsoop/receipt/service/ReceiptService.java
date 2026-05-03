package com.insightsoop.receipt.service;

import com.insightsoop.receipt.dto.TmoneyRequestDto;
import com.insightsoop.receipt.dto.TmoneyResponseDto;
import com.insightsoop.receipt.entity.TmoneyReceipt;
import com.insightsoop.receipt.repository.TmoneyReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final TmoneyReceiptRepository receiptRepository;

    @Value("${app.html-view-host}")
    private String htmlViewHost;

    @Value("${app.data-host}")
    private String dataHost;

    public TmoneyResponseDto process(TmoneyRequestDto req) {
        if (isEmpty(req.getTagId()) || isEmpty(req.getPartnerCode()) || isEmpty(req.getPartnerReqUuid())) {
            return TmoneyResponseDto.error("4220", "필수 파라미터 누락");
        }

        if (isEmpty(req.getSource())) {
            return processQuery(req);
        }

        return processSave(req);
    }

    private TmoneyResponseDto processQuery(TmoneyRequestDto req) {
        Optional<TmoneyReceipt> latest = receiptRepository.findLatestByTagId(req.getTagId());
        if (latest.isEmpty()) {
            return TmoneyResponseDto.error("4040", "영수증 데이터 결과 없음");
        }
        TmoneyReceipt receipt = latest.get();
        return TmoneyResponseDto.ok(
                receipt.getUuid(),
                buildHtmlViewLink(receipt.getUuid()),
                buildDataReqLink(receipt.getPartnerCode(), receipt.getPartnerReqUuid())
        );
    }

    private TmoneyResponseDto processSave(TmoneyRequestDto req) {
        String uuid = UUID.randomUUID().toString();
        String regDate = OffsetDateTime.now(ZoneOffset.ofHours(9))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX"));

        TmoneyReceipt receipt = TmoneyReceipt.builder()
                .uuid(uuid)
                .partnerReqUuid(req.getPartnerReqUuid())
                .partnerCode(req.getPartnerCode())
                .source(req.getSource())
                .storeUid(nullToEmpty(req.getStoreUid()))
                .storeType("")
                .posId(nullToEmpty(req.getPosId()))
                .printOption(nullToEmpty(req.getPrintOption()))
                .tagId(req.getTagId())
                .regUserId(nullToEmpty(req.getUserId()))
                .enc(nullToEmpty(req.getEnc()))
                .receiptWidth(req.getReceiptWidth() != null ? req.getReceiptWidth().toString() : "0")
                .regDate(regDate)
                .build();

        receiptRepository.save(receipt);
        log.info("저장 완료: uuid={}, tagId={}, partnerCode={}", uuid, req.getTagId(), req.getPartnerCode());

        return TmoneyResponseDto.ok(
                uuid,
                buildHtmlViewLink(uuid),
                buildDataReqLink(req.getPartnerCode(), req.getPartnerReqUuid())
        );
    }

    private String buildHtmlViewLink(String uuid) {
        return htmlViewHost + "?uuid=" + uuid;
    }

    private String buildDataReqLink(String partnerCode, String partnerReqUuid) {
        return dataHost + "?partnerCode=" + partnerCode + "&partnerReqUuid=" + partnerReqUuid + "&type=link";
    }

    private boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    private String nullToEmpty(String value) {
        return value != null ? value : "";
    }
}
