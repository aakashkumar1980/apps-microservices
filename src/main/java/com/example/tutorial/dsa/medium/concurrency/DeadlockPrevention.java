package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DeadlockPrevention
 * ----------------------------------
 * <p>This program demonstrates deadlock scenarios and prevention techniques.
 * Shows how to identify, prevent, and resolve deadlock situations.
 *
 * <p><b>Problem Statement:</b>
 * Prevent threads from getting stuck waiting for each other indefinitely
 * when acquiring multiple locks.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Concurrent account transfers</li>
 *   <li>Multi-resource booking systems</li>
 *   <li>Order processing with inventory locks</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Microsoft, Goldman Sachs
 */
@Component
public class DeadlockPrevention implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DeadlockPrevention.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DeadlockPrevention: Deadlock Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Show potential deadlock scenario (commented for safety)
    System.out.println("--- Demo 1: Deadlock Scenario (explained) ---\n");
    explainDeadlock();

    // Demo 2: Prevention using lock ordering
    System.out.println("\n--- Demo 2: Prevention - Lock Ordering ---\n");
    demoLockOrdering();

    // Demo 3: Prevention using tryLock with timeout
    System.out.println("\n--- Demo 3: Prevention - TryLock with Timeout ---\n");
    demoTryLock();

    // Demo 4: Safe transfer example
    System.out.println("\n--- Demo 4: Safe Account Transfer ---\n");
    demoSafeTransfer();
  }

  private void explainDeadlock() {
    System.out.println("  Deadlock occurs when:");
    System.out.println("  1. Thread-1 holds Lock-A, waits for Lock-B");
    System.out.println("  2. Thread-2 holds Lock-B, waits for Lock-A");
    System.out.println("  → Both threads wait forever!\n");

    System.out.println("  Four conditions for deadlock:");
    System.out.println("  1. Mutual Exclusion - resources can't be shared");
    System.out.println("  2. Hold and Wait - holding one, waiting for another");
    System.out.println("  3. No Preemption - can't force release of locks");
    System.out.println("  4. Circular Wait - circular chain of waiting\n");

    System.out.println("  Prevention strategies:");
    System.out.println("  • Lock ordering (always acquire in same order)");
    System.out.println("  • Lock timeout (tryLock with timeout)");
    System.out.println("  • Detect and recover");
    System.out.println("  • Single lock (coarse-grained locking)");
  }

  /**
   * Demonstrates deadlock prevention using consistent lock ordering.
   *
   * <p><b>LOGIC:</b> Always acquire locks in a consistent order (e.g., by ID).
   * This breaks the circular wait condition.
   */
  private void demoLockOrdering() throws InterruptedException {
    BankAccount account1 = new BankAccount(1, "Account-1", 1000);
    BankAccount account2 = new BankAccount(2, "Account-2", 1000);

    System.out.println("  Initial balances: " + account1.name + "=$" + account1.balance
        + ", " + account2.name + "=$" + account2.balance);

    Thread t1 = new Thread(() -> {
      transferWithOrdering(account1, account2, 100);
    }, "Transfer-1-to-2");

    Thread t2 = new Thread(() -> {
      transferWithOrdering(account2, account1, 50);
    }, "Transfer-2-to-1");

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    System.out.println("  Final balances: " + account1.name + "=$" + account1.balance
        + ", " + account2.name + "=$" + account2.balance);
    System.out.println("  No deadlock - locks acquired in consistent order!");
  }

  private void transferWithOrdering(BankAccount from, BankAccount to, int amount) {
    // Always lock the account with smaller ID first.
    BankAccount first = from.id < to.id ? from : to;
    BankAccount second = from.id < to.id ? to : from;

    synchronized (first) {
      synchronized (second) {
        if (from.balance >= amount) {
          from.balance -= amount;
          to.balance += amount;
          System.out.println("    " + Thread.currentThread().getName()
              + ": Transferred $" + amount);
        }
      }
    }
  }

  /**
   * Demonstrates deadlock prevention using tryLock with timeout.
   *
   * <p><b>LOGIC:</b> Use tryLock with timeout instead of blocking forever.
   * If lock not acquired, release held locks and retry.
   */
  private void demoTryLock() throws InterruptedException {
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    Thread t1 = new Thread(() -> {
      tryTransfer(lock1, lock2, "Lock1→Lock2");
    });

    Thread t2 = new Thread(() -> {
      tryTransfer(lock2, lock1, "Lock2→Lock1");
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    System.out.println("  Both threads completed without deadlock!");
  }

  private void tryTransfer(Lock first, Lock second, String name) {
    while (true) {
      boolean gotFirst = false;
      boolean gotSecond = false;

      try {
        gotFirst = first.tryLock(100, TimeUnit.MILLISECONDS);
        gotSecond = second.tryLock(100, TimeUnit.MILLISECONDS);

        if (gotFirst && gotSecond) {
          System.out.println("    " + name + ": Acquired both locks, performing operation");
          Thread.sleep(50);  // Simulate work.
          return;
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      } finally {
        if (gotSecond) {
          second.unlock();
        }
        if (gotFirst) {
          first.unlock();
        }
      }

      // Backoff before retry.
      System.out.println("    " + name + ": Couldn't get both locks, retrying...");
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  private void demoSafeTransfer() throws InterruptedException {
    SafeBankAccount acc1 = new SafeBankAccount(1, "Alice", 500);
    SafeBankAccount acc2 = new SafeBankAccount(2, "Bob", 500);

    System.out.println("  Initial: Alice=$" + acc1.getBalance() + ", Bob=$" + acc2.getBalance());

    // Run multiple concurrent transfers
    Thread[] threads = new Thread[4];
    threads[0] = new Thread(() -> acc1.transferTo(acc2, 100), "Alice→Bob");
    threads[1] = new Thread(() -> acc2.transferTo(acc1, 75), "Bob→Alice");
    threads[2] = new Thread(() -> acc1.transferTo(acc2, 50), "Alice→Bob-2");
    threads[3] = new Thread(() -> acc2.transferTo(acc1, 25), "Bob→Alice-2");

    for (Thread t : threads) {
      t.start();
    }
    for (Thread t : threads) {
      t.join();
    }

    System.out.println("  Final: Alice=$" + acc1.getBalance() + ", Bob=$" + acc2.getBalance());
    System.out.println("  Total preserved: $" + (acc1.getBalance() + acc2.getBalance()));
  }

  // Simple bank account for demo
  static class BankAccount {
    final int id;
    final String name;
    int balance;

    BankAccount(int id, String name, int balance) {
      this.id = id;
      this.name = name;
      this.balance = balance;
    }
  }

  /**
   * Thread-safe bank account using lock ordering.
   */
  static class SafeBankAccount {
    private final int id;
    private final String name;
    private int balance;
    private final Lock lock = new ReentrantLock();

    SafeBankAccount(int id, String name, int balance) {
      this.id = id;
      this.name = name;
      this.balance = balance;
    }

    void transferTo(SafeBankAccount target, int amount) {
      // Lock ordering by ID.
      SafeBankAccount first = this.id < target.id ? this : target;
      SafeBankAccount second = this.id < target.id ? target : this;

      first.lock.lock();
      try {
        second.lock.lock();
        try {
          if (this.balance >= amount) {
            this.balance -= amount;
            target.balance += amount;
            System.out.println("    " + Thread.currentThread().getName()
                + ": Transferred $" + amount);
          }
        } finally {
          second.lock.unlock();
        }
      } finally {
        first.lock.unlock();
      }
    }

    int getBalance() {
      return balance;
    }
  }
}
