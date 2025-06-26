package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Offer {

    private UUID offerId;
    private UUID offerConstructId;
    private UUID campaignId;
    private UUID merchantId;
    private RewardType rewardType;
    private BigDecimal rewardValue;
    private String eligibilityCriteria;
    private OfferStatus offerStatus;
    private LocalDateTime createdAt;


    public UUID getOfferid() {
        return offerId;
    }

    public void setOfferid(UUID offerId) {
        this.offerId = offerId;
    }

    public UUID getOfferconstructid() {
        return offerConstructId;
    }

    public void setOfferconstructid(UUID offerConstructId) {
        this.offerConstructId = offerConstructId;
    }

    public UUID getCampaignid() {
        return campaignId;
    }

    public void setCampaignid(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public UUID getMerchantid() {
        return merchantId;
    }

    public void setMerchantid(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public RewardType getRewardtype() {
        return rewardType;
    }

    public void setRewardtype(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardvalue() {
        return rewardValue;
    }

    public void setRewardvalue(BigDecimal rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getEligibilitycriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilitycriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public OfferStatus getOfferstatus() {
        return offerStatus;
    }

    public void setOfferstatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public LocalDateTime getCreatedat() {
        return createdAt;
    }

    public void setCreatedat(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Offer{" + 
               " + ", ".join(['offerId=" + offerId +', 'offerConstructId=" + offerConstructId +', 'campaignId=" + campaignId +', 'merchantId=" + merchantId +', 'rewardType=" + rewardType +', 'rewardValue=" + rewardValue +', 'eligibilityCriteria=" + eligibilityCriteria +', 'offerStatus=" + offerStatus +', 'createdAt=" + createdAt +']) + "}";
    }
}
