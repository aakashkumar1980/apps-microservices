"""
Task data model for producer-consumer and concurrent processing patterns.
Used in: ProducerConsumerPattern, ThreadPoolExecutorDemo
"""

from dataclasses import dataclass, field
from enum import Enum
from typing import Optional
import time


class Priority(Enum):
    """Task priority enumeration."""
    LOW = 1
    MEDIUM = 2
    HIGH = 3
    CRITICAL = 4


class Status(Enum):
    """Task status enumeration."""
    PENDING = "PENDING"
    IN_PROGRESS = "IN_PROGRESS"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"


@dataclass
class Task:
    """Task data model for producer-consumer patterns."""
    task_id: str
    name: str
    priority: Priority
    status: Status = Status.PENDING
    description: Optional[str] = None
    created_at: float = field(default_factory=time.time)
    completed_at: Optional[float] = None

    @staticmethod
    def of(name: str, priority: Priority = Priority.MEDIUM) -> 'Task':
        """Creates a new pending task."""
        return Task(
            task_id=f"TASK-{int(time.time() * 1000000)}",
            name=name,
            priority=priority,
            status=Status.PENDING,
            created_at=time.time()
        )

    def complete(self) -> None:
        """Marks task as completed."""
        self.status = Status.COMPLETED
        self.completed_at = time.time()

    def __str__(self) -> str:
        return f"[{self.priority.name}] {self.name} ({self.status.name})"
