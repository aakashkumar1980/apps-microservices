"""
Sample Data Loader
----------------------------------
This module loads sample offer data from JSON file.
Equivalent to the Java SampleDataLoader.java class.
"""

import json
import os
from typing import List
from com.example.tutorial.common.datamodel.offer import (
    Offer, Partner, Merchant, Location, Geo, Schedule, Eligibility,
    Limits, PerCustomer, PerOffer, Velocity, Reward, Stacking,
    Status, StatusChange, Analytics, Technical, Audit, Compliance,
    RedemptionRules, Settlement, Money
)


def _parse_money(data: dict) -> Money:
    """Parse Money object from dict."""
    if data is None:
        return None
    return Money(
        value=data.get("value") or data.get("amount"),
        currency=data.get("currency")
    )


def _parse_location(data: dict) -> Location:
    """Parse Location object from dict."""
    geo_data = data.get("geo")
    return Location(
        store_id=data.get("storeId"),
        city=data.get("city"),
        state=data.get("state"),
        country=data.get("country"),
        geo=Geo(lat=geo_data.get("lat", 0), lng=geo_data.get("lng", 0)) if geo_data else None
    )


def _parse_offer(data: dict) -> Offer:
    """Parse a single Offer from JSON dict."""

    # Parse Partner
    partner_data = data.get("partner", {})
    partner = Partner(
        partner_id=partner_data.get("partnerId"),
        name=partner_data.get("name")
    ) if partner_data else None

    # Parse Merchant
    merchant_data = data.get("merchant", {})
    merchant = Merchant(
        merchant_id=merchant_data.get("merchantId"),
        name=merchant_data.get("name"),
        mcc=merchant_data.get("mcc"),
        brands=merchant_data.get("brands", []),
        locations=[_parse_location(loc) for loc in merchant_data.get("locations", [])]
    ) if merchant_data else None

    # Parse Schedule
    schedule_data = data.get("schedule", {})
    schedule = Schedule(
        start=schedule_data.get("start"),
        end=schedule_data.get("end"),
        timezone=schedule_data.get("timezone"),
        days_of_week=schedule_data.get("daysOfWeek", []),
        blackout_dates=schedule_data.get("blackoutDates", [])
    ) if schedule_data else None

    # Parse Eligibility
    elig_data = data.get("eligibility", {})
    eligibility = Eligibility(
        customer_segments=elig_data.get("customerSegments", []),
        enrollment_required=elig_data.get("enrollmentRequired", False),
        min_spend=_parse_money(elig_data.get("minSpend")),
        categories=elig_data.get("categories", []),
        channels=elig_data.get("channels", []),
        card_products=elig_data.get("cardProducts", []),
        merchant_allowlist=elig_data.get("merchantAllowlist", []),
        merchant_denylist=elig_data.get("merchantDenylist", []),
        excluded_mccs=elig_data.get("excludedMCCs", [])
    ) if elig_data else None

    # Parse Limits
    limits_data = data.get("limits", {})
    limits = None
    if limits_data:
        per_cust = limits_data.get("perCustomer", {})
        per_off = limits_data.get("perOffer", {})
        vel = limits_data.get("velocity", {})
        limits = Limits(
            per_txn_cap=_parse_money(limits_data.get("perTxnCap")),
            per_customer=PerCustomer(
                count=per_cust.get("count"),
                amount=_parse_money(per_cust.get("amount"))
            ) if per_cust else None,
            per_offer=PerOffer(
                count=per_off.get("count"),
                amount=_parse_money(per_off.get("amount"))
            ) if per_off else None,
            velocity=Velocity(
                daily_count=vel.get("dailyCount"),
                weekly_amount=_parse_money(vel.get("weeklyAmount"))
            ) if vel else None
        )

    # Parse Reward
    reward_data = data.get("reward", {})
    reward = Reward(
        type=reward_data.get("type"),
        amount=_parse_money(reward_data.get("amount")),
        percent=reward_data.get("percent"),
        currency=reward_data.get("currency"),
        notes=reward_data.get("notes")
    ) if reward_data else None

    # Parse Stacking
    stacking_data = data.get("stacking", {})
    stacking = Stacking(
        exclusive=stacking_data.get("exclusive", False),
        allowed_with=stacking_data.get("allowedWith", [])
    ) if stacking_data else None

    # Parse Status
    status_data = data.get("status", {})
    status = Status(
        state=status_data.get("state"),
        reason=status_data.get("reason"),
        updated_at=status_data.get("updatedAt"),
        history=[
            StatusChange(state=h.get("state"), at=h.get("at"))
            for h in status_data.get("history", [])
        ]
    ) if status_data else None

    # Parse Analytics
    analytics_data = data.get("analytics", {})
    analytics = Analytics(
        impressions=analytics_data.get("impressions"),
        clicks=analytics_data.get("clicks"),
        redemptions=analytics_data.get("redemptions"),
        ctr=analytics_data.get("ctr")
    ) if analytics_data else None

    # Parse Technical
    technical_data = data.get("technical", {})
    technical = Technical(
        idempotency_key=technical_data.get("idempotencyKey"),
        daily_tps_hint=technical_data.get("dailyTpsHint")
    ) if technical_data else None

    # Parse Audit
    audit_data = data.get("audit", {})
    audit = Audit(
        created_by=audit_data.get("createdBy"),
        created_at=audit_data.get("createdAt"),
        last_modified_by=audit_data.get("lastModifiedBy"),
        last_modified_at=audit_data.get("lastModifiedAt")
    ) if audit_data else None

    # Parse Compliance
    compliance_data = data.get("compliance", {})
    compliance = Compliance(
        terms_url=compliance_data.get("termsUrl"),
        restricted_regions=compliance_data.get("restrictedRegions", [])
    ) if compliance_data else None

    # Parse RedemptionRules
    redemption_data = data.get("redemptionRules", {})
    redemption_rules = RedemptionRules(
        channels=redemption_data.get("channels", []),
        code_type=redemption_data.get("codeType"),
        issuer_funding=redemption_data.get("issuerFunding")
    ) if redemption_data else None

    # Parse Settlement
    settlement_data = data.get("settlement", {})
    settlement = Settlement(
        funding_model=settlement_data.get("fundingModel"),
        reimbursement_window_days=settlement_data.get("reimbursementWindowDays")
    ) if settlement_data else None

    return Offer(
        offer_id=data.get("offerId"),
        campaign_id=data.get("campaignId"),
        partner=partner,
        merchant=merchant,
        title=data.get("title"),
        description=data.get("description"),
        schedule=schedule,
        eligibility=eligibility,
        limits=limits,
        reward=reward,
        stacking=stacking,
        status=status,
        analytics=analytics,
        technical=technical,
        audit=audit,
        compliance=compliance,
        redemption_rules=redemption_rules,
        settlement=settlement
    )


def load_offers() -> List[Offer]:
    """
    Load offers from the sample JSON file.

    Returns:
        List of Offer objects parsed from offer.json
    """
    # Get the path to the JSON file (relative to project root)
    json_path = os.path.join(
        os.path.dirname(__file__),
        "..", "..", "..", "..", "..", "..",
        "resources", "sample_data", "offer.json"
    )
    json_path = os.path.normpath(json_path)

    try:
        with open(json_path, 'r') as f:
            data = json.load(f)
            return [_parse_offer(offer_data) for offer_data in data]
    except Exception as e:
        print(f"Error loading offers: {e}")
        return []
