"""
Sample Data Loader
----------------------------------
This module loads sample offer data from JSON file.
Equivalent to the Java SampleDataLoader.java class.
"""

import json
import os
from typing import List
from common.offer import Offer, Partner, Merchant, Money


def _parse_offer(data: dict) -> Offer:
    """Parse a single Offer from JSON dict."""
    partner_data = data.get("partner", {})
    merchant_data = data.get("merchant", {})

    return Offer(
        offer_id=data.get("offerId"),
        campaign_id=data.get("campaignId"),
        partner=Partner(
            partner_id=partner_data.get("partnerId"),
            name=partner_data.get("name")
        ) if partner_data else None,
        merchant=Merchant(
            merchant_id=merchant_data.get("merchantId"),
            name=merchant_data.get("name"),
            mcc=merchant_data.get("mcc"),
            brands=merchant_data.get("brands", [])
        ) if merchant_data else None,
        title=data.get("title"),
        description=data.get("description")
    )


def load_offers() -> List[Offer]:
    """
    Load offers from the sample JSON file.

    Returns:
        List of Offer objects parsed from offer.json
    """
    json_path = os.path.join(
        os.path.dirname(__file__), "..", "..",
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
