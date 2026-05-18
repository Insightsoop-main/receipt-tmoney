package com.insightsoop.receipt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponseDto {

    private String resultCode;
    private String resultMessage;
    private ResultData resultData;
    private String errorMessage;
    private String errorCode;

    @Getter
    @Builder
    public static class ResultData {
        private String html;
        private String json;
        private String text;
        private String link;
    }

    public static DataResponseDto ok(String html, String json, String text, String link) {
        return DataResponseDto.builder()
                .resultCode("OK")
                .resultMessage("정상")
                .resultData(ResultData.builder()
                        .html(html)
                        .json(json)
                        .text(text)
                        .link(link)
                        .build())
                .build();
    }

    public static DataResponseDto error(String errorCode, String errorMessage) {
        return DataResponseDto.builder()
                .resultCode("NOT_OK")
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
