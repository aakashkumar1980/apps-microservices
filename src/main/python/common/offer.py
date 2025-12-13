"""
Offer Data Model
----------------------------------
This module defines the data model for credit card offers.
Equivalent to the Java Offer.java class.
"""

from dataclasses import dataclass, field
from typing import List, Optional


@dataclass
class Money:
    value: Optional[float] = None
    currency: Optional[str] = None


@dataclass
class Partner:
    partner_id: Optional[str] = None
    name: Optional[str] = None


@dataclass
class Merchant:
    merchant_id: Optional[str] = None
    name: Optional[str] = None
    mcc: Optional[str] = None
    brands: List[str] = field(default_factory=list)


@dataclass
class Offer:
    """Main Offer data model representing a credit card offer."""
    offer_id: Optional[str] = None
    campaign_id: Optional[str] = None
    partner: Optional[Partner] = None
    merchant: Optional[Merchant] = None
    title: Optional[str] = None
    description: Optional[str] = None
