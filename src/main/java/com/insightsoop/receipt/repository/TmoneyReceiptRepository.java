package com.insightsoop.receipt.repository;

import com.insightsoop.receipt.entity.TmoneyReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TmoneyReceiptRepository extends JpaRepository<TmoneyReceipt, String> {

    @Query(value = "SELECT * FROM tmoney_receipts WHERE tag_id = :tagId ORDER BY reg_date DESC LIMIT 1", nativeQuery = true)
    Optional<TmoneyReceipt> findLatestByTagId(@Param("tagId") String tagId);

    @Query(value = "SELECT * FROM tmoney_receipts WHERE partner_code = :partnerCode AND partner_req_uuid = :partnerReqUuid ORDER BY reg_date DESC LIMIT 1", nativeQuery = true)
    Optional<TmoneyReceipt> findByPartnerCodeAndPartnerReqUuid(@Param("partnerCode") String partnerCode, @Param("partnerReqUuid") String partnerReqUuid);
}
