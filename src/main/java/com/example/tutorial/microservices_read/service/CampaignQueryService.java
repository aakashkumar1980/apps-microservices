package com.example.tutorial.microservices_read.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.utils.MockDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignQueryService {

  @Autowired
  private MockDataUtil mockDataUtil;

  /**
   * Returns all campaigns.
   * @return List of Campaigns
   */
  public List<Campaign> getAllCampaigns() {
    return mockDataUtil.campaignSupplier.get();
  }

  /**
   * Returns a campaign by its ID.
   * @param id Campaign ID
   * @return Optional containing the Campaign if found, otherwise empty
   *
   * NOTE: Optional is used to handle cases where the campaign might not exist.
   * It helps avoid null checks and makes the code cleaner. The general syntax are:
   * Optional<Type> optionalVariable = Optional.ofNullable(value) returns an empty Optional if value is null.
   * Optional<Type> optionalVariable = Optional.of(value) throws a NullPointerException if value is null, so use when you are sure the value is never null;
   * Optional<Type> optionalVariable = Optional.empty();
   */
  public Optional<Campaign> getCampaignById(Long id) {
    for (Campaign c : mockDataUtil.campaignSupplier.get()) {
      if (c.getId().equals(id)) {
        return Optional.of(c);
      }
    }
    return Optional.empty();
  }
}
