"""
Meeting data model for scheduling-related DSA problems.
Used in: MeetingRoomScheduler, DateTimeScheduler
"""

from dataclasses import dataclass
from typing import Optional


@dataclass
class Meeting:
    """Meeting data model for scheduling algorithms."""
    meeting_id: str
    title: str
    start_time: int  # Minutes from midnight (e.g., 540 = 9:00 AM)
    end_time: int    # Minutes from midnight
    organizer: str
    room: Optional[str] = None

    @staticmethod
    def of_hours(title: str, start_hour: int, end_hour: int, organizer: str) -> 'Meeting':
        """Creates a Meeting with start and end times as hours."""
        import time
        return Meeting(
            meeting_id=f"MTG-{int(time.time() * 1000000)}",
            title=title,
            start_time=start_hour * 60,
            end_time=end_hour * 60,
            organizer=organizer
        )

    def get_start_time_formatted(self) -> str:
        """Returns time in HH:MM format."""
        return f"{self.start_time // 60:02d}:{self.start_time % 60:02d}"

    def get_end_time_formatted(self) -> str:
        """Returns time in HH:MM format."""
        return f"{self.end_time // 60:02d}:{self.end_time % 60:02d}"

    def to_interval(self) -> tuple:
        """Returns interval as (start, end) tuple (for algorithm compatibility)."""
        return (self.start_time, self.end_time)

    def __str__(self) -> str:
        return f"{self.title} ({self.get_start_time_formatted()} - {self.get_end_time_formatted()}) by {self.organizer}"
