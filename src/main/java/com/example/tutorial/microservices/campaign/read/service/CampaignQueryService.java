package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.utils.MockDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignQueryService {

  private static final Logger log = LoggerFactory.getLogger(CampaignQueryService.class);

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
   * Retrieves a campaign by its unique ID.
   *
   * @param id the unique identifier of the campaign
   * @return an {@link Optional} containing the found {@link Campaign}, or {@link Optional#empty()} if not found
   *
   * <p>Uses {@code Optional} to avoid null checks and improve code readability.
   * <ul>
   *   <li>{@code Optional.ofNullable(value)} returns an empty {@code Optional} if value is null.</li>
   *   <li>{@code Optional.of(value)} throws {@code NullPointerException} if value is null.</li>
   *   <li>{@code Optional.empty()} returns an empty {@code Optional}.</li>
   * </ul>
   * </p>
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
