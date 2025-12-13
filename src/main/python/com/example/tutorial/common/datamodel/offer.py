"""
Offer Data Model
----------------------------------
This module defines the data model for credit card offers.
Equivalent to the Java Offer.java class.
"""

from dataclasses import dataclass, field
from typing import List, Optional
from datetime import datetime
from enum import Enum


class Weekday(Enum):
    MON = "MON"
    TUE = "TUE"
    WED = "WED"
    THU = "THU"
    FRI = "FRI"
    SAT = "SAT"
    SUN = "SUN"


class Channel(Enum):
    IN_STORE = "IN_STORE"
    MOBILE_ORDER = "MOBILE_ORDER"
    ONLINE = "ONLINE"


class RewardType(Enum):
    AMOUNT = "AMOUNT"
    PERCENT = "PERCENT"


class OfferState(Enum):
    DRAFT = "DRAFT"
    APPROVED = "APPROVED"
    ACTIVE = "ACTIVE"
    PAUSED = "PAUSED"


class CodeType(Enum):
    NONE = "NONE"
    BARCODE = "BARCODE"
    QRCODE = "QRCODE"
    PROMO_CODE = "PROMO_CODE"


class FundingModel(Enum):
    ISSUER = "ISSUER"
    PARTNER = "PARTNER"


@dataclass
class Money:
    value: Optional[float] = None
    currency: Optional[str] = None


@dataclass
class Geo:
    lat: float = 0.0
    lng: float = 0.0


@dataclass
class Location:
    store_id: Optional[str] = None
    city: Optional[str] = None
    state: Optional[str] = None
    country: Optional[str] = None
    geo: Optional[Geo] = None


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
    locations: List[Location] = field(default_factory=list)


@dataclass
class Schedule:
    start: Optional[str] = None
    end: Optional[str] = None
    timezone: Optional[str] = None
    days_of_week: List[str] = field(default_factory=list)
    blackout_dates: List[str] = field(default_factory=list)


@dataclass
class Eligibility:
    customer_segments: List[str] = field(default_factory=list)
    enrollment_required: bool = False
    min_spend: Optional[Money] = None
    categories: List[str] = field(default_factory=list)
    channels: List[str] = field(default_factory=list)
    card_products: List[str] = field(default_factory=list)
    merchant_allowlist: List[str] = field(default_factory=list)
    merchant_denylist: List[str] = field(default_factory=list)
    excluded_mccs: List[str] = field(default_factory=list)


@dataclass
class PerCustomer:
    count: Optional[int] = None
    amount: Optional[Money] = None


@dataclass
class PerOffer:
    count: Optional[int] = None
    amount: Optional[Money] = None


@dataclass
class Velocity:
    daily_count: Optional[int] = None
    weekly_amount: Optional[Money] = None


@dataclass
class Limits:
    per_txn_cap: Optional[Money] = None
    per_customer: Optional[PerCustomer] = None
    per_offer: Optional[PerOffer] = None
    velocity: Optional[Velocity] = None


@dataclass
class Reward:
    type: Optional[str] = None
    amount: Optional[Money] = None
    percent: Optional[float] = None
    currency: Optional[str] = None
    notes: Optional[str] = None


@dataclass
class Stacking:
    exclusive: bool = False
    allowed_with: List[str] = field(default_factory=list)


@dataclass
class StatusChange:
    state: Optional[str] = None
    at: Optional[str] = None


@dataclass
class Status:
    state: Optional[str] = None
    reason: Optional[str] = None
    updated_at: Optional[str] = None
    history: List[StatusChange] = field(default_factory=list)


@dataclass
class Analytics:
    impressions: Optional[int] = None
    clicks: Optional[int] = None
    redemptions: Optional[int] = None
    ctr: Optional[float] = None


@dataclass
class Technical:
    idempotency_key: Optional[str] = None
    daily_tps_hint: Optional[int] = None


@dataclass
class Audit:
    created_by: Optional[str] = None
    created_at: Optional[str] = None
    last_modified_by: Optional[str] = None
    last_modified_at: Optional[str] = None


@dataclass
class Compliance:
    terms_url: Optional[str] = None
    restricted_regions: List[str] = field(default_factory=list)


@dataclass
class RedemptionRules:
    channels: List[str] = field(default_factory=list)
    code_type: Optional[str] = None
    issuer_funding: Optional[bool] = None


@dataclass
class Settlement:
    funding_model: Optional[str] = None
    reimbursement_window_days: Optional[int] = None


@dataclass
class Offer:
    """
    Main Offer data model representing a credit card offer.
    """
    offer_id: Optional[str] = None
    campaign_id: Optional[str] = None
    partner: Optional[Partner] = None
    merchant: Optional[Merchant] = None
    title: Optional[str] = None
    description: Optional[str] = None
    schedule: Optional[Schedule] = None
    eligibility: Optional[Eligibility] = None
    limits: Optional[Limits] = None
    reward: Optional[Reward] = None
    stacking: Optional[Stacking] = None
    status: Optional[Status] = None
    analytics: Optional[Analytics] = None
    technical: Optional[Technical] = None
    audit: Optional[Audit] = None
    compliance: Optional[Compliance] = None
    redemption_rules: Optional[RedemptionRules] = None
    settlement: Optional[Settlement] = None
