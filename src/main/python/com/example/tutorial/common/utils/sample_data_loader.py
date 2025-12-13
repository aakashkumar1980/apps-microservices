"""
Sample Data Loader
----------------------------------
This module loads sample data from JSON files and provides factory methods.
Equivalent to the Java SampleDataLoader.java class.
"""

import json
import os
from typing import List
from com.example.tutorial.common.datamodel.offer import Offer, Partner, Merchant, Eligibility
from com.example.tutorial.common.datamodel.meeting import Meeting
from com.example.tutorial.common.datamodel.vehicle import Vehicle, VehicleType
from com.example.tutorial.common.datamodel.task import Task, Priority


def _parse_offer(data: dict) -> Offer:
    """Parse a single Offer from JSON dict."""
    partner_data = data.get("partner", {})
    merchant_data = data.get("merchant", {})
    eligibility_data = data.get("eligibility", {})

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
        description=data.get("description"),
        eligibility=Eligibility(
            categories=eligibility_data.get("categories", [])
        ) if eligibility_data else None
    )


def load_offers() -> List[Offer]:
    """
    Load offers from the sample JSON file.

    Returns:
        List of Offer objects parsed from offer.json
    """
    json_path = os.path.join(
        os.path.dirname(__file__), "..", "..", "..", "..",
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


def load_meetings() -> List[Meeting]:
    """
    Load sample meeting data for scheduling algorithms.

    Returns:
        List of Meeting objects
    """
    return [
        Meeting.of_hours("Team Standup", 9, 10, "Alice"),
        Meeting.of_hours("Sprint Planning", 10, 12, "Bob"),
        Meeting.of_hours("Design Review", 11, 13, "Charlie"),
        Meeting.of_hours("1:1 Meeting", 14, 15, "Alice"),
        Meeting.of_hours("Tech Talk", 15, 17, "Diana"),
    ]


def load_vehicles() -> List[Vehicle]:
    """
    Load sample vehicle data for parking algorithms.

    Returns:
        List of Vehicle objects
    """
    return [
        Vehicle.of("ABC-1234", VehicleType.SMALL),
        Vehicle.of("XYZ-5678", VehicleType.MEDIUM),
        Vehicle.of("DEF-9012", VehicleType.BIG),
        Vehicle.of("GHI-3456", VehicleType.SMALL),
        Vehicle.of("JKL-7890", VehicleType.MEDIUM),
    ]


def load_tasks() -> List[Task]:
    """
    Load sample task data for producer-consumer patterns.

    Returns:
        List of Task objects
    """
    return [
        Task.of("Process Payment", Priority.HIGH),
        Task.of("Send Notification", Priority.MEDIUM),
        Task.of("Generate Report", Priority.LOW),
        Task.of("Update Cache", Priority.CRITICAL),
        Task.of("Sync Database", Priority.HIGH),
        Task.of("Archive Logs", Priority.LOW),
    ]
