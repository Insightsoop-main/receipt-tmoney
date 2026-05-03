package com.insightsoop.receipt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmoneyResponseDto {

    private String resultCode;
    private String resultMessage;
    private ResultData resultData;
    private String errorMessage;
    private String errorCode;

    @Getter
    @Builder
    public static class ResultData {
        private String generatedUuid;
        private String htmlViewLink;
        private String dataReqLink;
    }

    public static TmoneyResponseDto ok(String uuid, String htmlViewLink, String dataReqLink) {
        return TmoneyResponseDto.builder()
                .resultCode("OK")
                .resultMessage("영수증 데이터 저장 완료")
                .resultData(ResultData.builder()
                        .generatedUuid(uuid)
                        .htmlViewLink(htmlViewLink)
                        .dataReqLink(dataReqLink)
                        .build())
                .build();
    }

    public static TmoneyResponseDto error(String errorCode, String errorMessage) {
        return TmoneyResponseDto.builder()
                .resultCode("NOT_OK")
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
