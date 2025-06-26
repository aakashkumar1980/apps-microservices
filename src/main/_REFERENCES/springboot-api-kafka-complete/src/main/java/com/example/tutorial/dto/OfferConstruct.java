package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OfferConstruct {

    private UUID offerConstructId;
    private UUID campaignId;
    private RewardType rewardType;
    private BigDecimal rewardValue;
    private UUID merchantId;
    private String eligibilityCriteria;
    private Integer maxRedemptions;


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

    public UUID getMerchantid() {
        return merchantId;
    }

    public void setMerchantid(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getEligibilitycriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilitycriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public Integer getMaxredemptions() {
        return maxRedemptions;
    }

    public void setMaxredemptions(Integer maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
    }

    @Override
    public String toString() {
        return "OfferConstruct{" + 
               " + ", ".join(['offerConstructId=" + offerConstructId +', 'campaignId=" + campaignId +', 'rewardType=" + rewardType +', 'rewardValue=" + rewardValue +', 'merchantId=" + merchantId +', 'eligibilityCriteria=" + eligibilityCriteria +', 'maxRedemptions=" + maxRedemptions +']) + "}";
    }
}
