package com.insightsoop.receipt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tmoney_receipts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TmoneyReceipt {

    @Id
    private String uuid;

    @Column(name = "partner_req_uuid")
    private String partnerReqUuid;

    @Column(name = "partner_code")
    private String partnerCode;

    @Column(name = "source", columnDefinition = "TEXT")
    private String source;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "style", columnDefinition = "TEXT")
    private String style;

    @Column(name = "store_uid")
    private String storeUid;

    @Column(name = "store_type")
    private String storeType;

    @Column(name = "pos_id")
    private String posId;

    @Column(name = "print_option")
    private String printOption;

    @Column(name = "tag_id")
    private String tagId;

    @Column(name = "reg_user_id")
    private String regUserId;

    @Column(name = "json_data", columnDefinition = "TEXT")
    private String jsonData;

    @Column(name = "text_data", columnDefinition = "TEXT")
    private String textData;

    @Column(name = "enc")
    private String enc;

    @Column(name = "receipt_width")
    private String receiptWidth;

    @Column(name = "reg_date")
    private String regDate;
}
