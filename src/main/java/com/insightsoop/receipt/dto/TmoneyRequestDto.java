package com.insightsoop.receipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TmoneyRequestDto {

    @JsonProperty("version")
    private String version;

    @JsonProperty("source")
    private String source;

    @JsonProperty("tagId")
    private String tagId;

    @JsonProperty("partnerCode")
    private String partnerCode;

    @JsonProperty("partnerReqUuid")
    private String partnerReqUuid;

    @JsonProperty("storeUid")
    private String storeUid;

    @JsonProperty("posId")
    private String posId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("merchantId")
    private String merchantId;

    @JsonProperty("enc")
    private String enc;

    @JsonProperty("receiptWidth")
    private Integer receiptWidth;

    @JsonProperty("printOption")
    private String printOption;
}
