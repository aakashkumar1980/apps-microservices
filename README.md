# 💼 DSA + Java Interview Roadmap for Senior Backend Engineers

**Target Audience:** 7+ years experienced Java/Spring Boot developers preparing for technical interviews

This roadmap covers two distinct preparation tracks based on your target companies and available preparation time.

---

## 📊 Quick Overview

| Track | Duration | Daily | Problems | Target Companies |
|-------|----------|-------|----------|------------------|
| **Part 1 - Practical DSA** | 4-6 weeks | 1-2 hrs | ~70 | Service-based, Mid-tier, Startups |
| **Part 2 - FAANG-Level** | 8-12 weeks | 2-3 hrs | ~108 | FAANG, Unicorns, Tier-1 |

---

<details open>
<summary>🚀 <b>PART 1 – Practical DSA (4-6 Weeks)</b></summary>

**Focus:** Core problem-solving patterns that directly map to real-world backend scenarios

**Interview Pattern:** Online assessments, coding rounds focusing on practical logic, mini-simulations, and clean Java implementation

---

### 🟢 WEEK 1-2: Foundation & Core Patterns

#### String Manipulation & Validation
| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.basics.strings` | `TextParserUtility` | String parsing, trimming, length | User input sanitization, API request parsing | 58 | Amazon, Microsoft |
| `com.aakash.dsa.basics.strings` | `SimpleAnagramMatcher` | Frequency counting, HashMap | Tag matching, duplicate detection | 242 | Facebook, Google |
| `com.aakash.dsa.basics.strings` | `RuleValidatorEngine` | Valid parentheses, bracket matching | JSON/XML validation, config parsing | 20 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.basics.strings` | `SubstringSearcher` | Pattern matching (KMP optional) | Log searching, text filtering | 28 | Google |
| `com.aakash.dsa.basics.strings` | `StringCompressor` | Run-length encoding | Data compression, API response optimization | 443 | Microsoft |

#### Mathematical & Logical Operations
| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.basics.math` | `FizzBuzzBatchProcessor` | Conditional logic, modulo operations | Batch job categorization | 412 | LinkedIn |
| `com.aakash.dsa.basics.math` | `ReverseDataSanitizer` | Digit/character reversal | Data masking, ID obfuscation | 7 | Facebook |
| `com.aakash.dsa.basics.math` | `SymmetricDataValidator` | Palindrome checking | Transaction ID validation | 9 | Facebook, Bloomberg |
| `com.aakash.dsa.basics.math` | `PrimeNumberValidator` | Prime checking, Sieve of Eratosthenes | Hashing algorithms, cryptography basics | 204 | Amazon |
| `com.aakash.dsa.basics.math` | `GCDLCMCalculator` | Number theory basics | Scheduling problems, timing calculations | Custom | Google |
| `com.aakash.dsa.basics.math` | `PowerCalculator` | Exponentiation (Binary exponentiation) | Rate calculations, compound interest | 50 | **Facebook, Amazon** ⭐ |

#### Array Fundamentals
| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.basics.arrays` | `DuplicateDataDetector` | HashSet, duplicate detection | Fraud detection, data deduplication | 217 | **Google, Amazon** ⭐ |
| `com.aakash.dsa.basics.arrays` | `DataRotationHandler` | Array rotation (left/right) | Circular buffer, log rotation | 189 | Microsoft |
| `com.aakash.dsa.basics.arrays` | `RecentTransactionCompactor` | Move zeros, element shifting | Data cleanup, sparse array handling | 283 | Facebook |
| `com.aakash.dsa.basics.arrays` | `MergeSortedDataStreams` | Merge sorted arrays | Log merging, stream consolidation | 88 | Microsoft |
| `com.aakash.dsa.basics.arrays` | `MissingNumberFinder` | XOR trick, mathematical formula | Data integrity checks | 268 | Amazon |

**📝 Week 1-2 Total: 16 problems**

---

### 🟡 WEEK 3-4: Intermediate Patterns

#### 🎯 Amazon/Google Core Pattern: HashMaps & Two-Pointer
**Why These Companies Love This:** Efficient data lookup and optimization problems are staples in Amazon/Google interviews.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.arrays` | `DataPairReconciler` | Two Sum, Two Pointer | Payment reconciliation, credit-debit matching | 1, 167 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.medium.arrays` | `TripletSumFinder` | Three Sum, sorting + two pointer | Risk analysis combinations | 15 | **Facebook** ⭐⭐ |
| `com.aakash.dsa.medium.arrays` | `ContainerWaterMaximizer` | Container with most water | Resource optimization | 11 | Amazon |
| `com.aakash.dsa.medium.arrays` | `LongestSubstringFinder` | Sliding window, HashSet | Session analysis, streaming metrics | 3 | **Google, Amazon** ⭐⭐ |
| `com.aakash.dsa.medium.arrays` | `SubarrayTargetSum` | Prefix sum, HashMap | Financial analytics, cumulative metrics | 560 | Facebook |

**💡 Amazon Interview Tip:** Two Sum appears in 60%+ of Amazon phone screens. Master both HashMap and two-pointer approaches. They'll often ask you to optimize from O(n²) to O(n).

**💡 Google Interview Tip:** Sliding window problems (like Longest Substring) are Google favorites. Practice explaining the window expansion/contraction logic clearly.

#### 🎯 Microsoft/Facebook Core Pattern: Stack & Queue Applications
**Why These Companies Love This:** Microsoft tests data structure fundamentals. Facebook loves practical applications.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.stackqueue` | `ValidParenthesesChecker` | Stack for bracket matching | Configuration validation | 20 | **Facebook, Amazon** ⭐⭐ |
| `com.aakash.dsa.medium.stackqueue` | `FilePathSimplifier` | Stack for path normalization | URL/path canonicalization | 71 | Microsoft |
| `com.aakash.dsa.medium.stackqueue` | `NextGreaterElementFinder` | Monotonic stack | Price alerts, threshold monitoring | 496 | Google |
| `com.aakash.dsa.medium.stackqueue` | `MinStackImplementation` | Stack with O(1) min operation | Real-time min/max tracking | 155 | Amazon |
| `com.aakash.dsa.medium.stackqueue` | `QueueUsingStacks` | Queue implementation | Message queue simulation | 232 | Microsoft |
| `com.aakash.dsa.medium.stackqueue` | `RoundRobinProcessor` | Circular queue | Load balancing, task scheduling | Custom | Bloomberg |

**💡 Microsoft Interview Tip:** They love asking "Implement X using Y" questions. Practice implementing queue using stacks, stack using queues. Show you understand the trade-offs.

**💡 Facebook Interview Tip:** Valid Parentheses often appears as a warm-up. Be ready to extend it to more complex bracket types or nested structures.

#### 🎯 Amazon Must-Know: Heap & Priority Queue
**Why Amazon Loves This:** Top-K problems appear in 40%+ of Amazon interviews for senior roles.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.heaps` | `DataLeaderboardFinder` | Kth largest/smallest | Top-K queries, ranking systems | 215 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.medium.heaps` | `StreamMedianCalculator` | Two heaps (max + min) | Real-time statistics | 295 | Google |
| `com.aakash.dsa.medium.heaps` | `MeetingRoomScheduler` | Min heap for intervals | Calendar management, resource booking | 253 | **Facebook, Amazon** ⭐⭐ |
| `com.aakash.dsa.medium.heaps` | `TaskSchedulerOptimizer` | Frequency-based scheduling | Job queue optimization | 621 | Amazon |

**💡 Amazon Interview Tip:** Kth Largest (LC 215) is THE most asked Amazon problem. Know both heap (O(n log k)) and QuickSelect (O(n) average) solutions. They WILL ask you to optimize.

#### Sorting & Searching
| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.sorting` | `CustomComparatorSorter` | Comparator interface, sorting | Multi-field data ordering | Custom | Bloomberg |
| `com.aakash.dsa.medium.sorting` | `MergeSortImplementation` | Divide & conquer | External sorting, large dataset handling | Custom | **Amazon, Microsoft** ⭐ |
| `com.aakash.dsa.medium.sorting` | `QuickSelectAlgorithm` | Quick select for Kth element | Percentile calculations | 215 | Google |
| `com.aakash.dsa.medium.search` | `BinarySearchVariants` | Binary search templates | Efficient lookups, range queries | 704, 35 | Infosys, Oracle |
| `com.aakash.dsa.medium.search` | `RotatedArraySearch` | Modified binary search | Circular data structures | 33 | **Facebook** ⭐⭐ |

**💡 Microsoft Interview Tip:** They often ask about merge sort's stability. Be ready to explain when and why you'd choose merge sort over quicksort.

**📝 Week 3-4 Total: 20 problems**

---

### 🟠 WEEK 5: Matrix + Simulations (Microsoft/Amazon Focus Week)

#### ⚠️ CRITICAL: Matrix Problems (30%+ Interview Frequency!)
**Why Backend Engineers Skip This:** Matrix problems seem "algorithmic" but they appear constantly in real interviews.

**🎯 Microsoft Matrix Pattern:** Microsoft asks matrix problems in 35% of onsite rounds. They test both algorithmic thinking and careful implementation.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.matrix` | `MatrixRotator` | Rotate 90° clockwise/anticlockwise | Data transformation | 48 | **Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.medium.matrix` | `SpiralMatrixTraversal` | Spiral order | Dashboard display order | 54 | Amazon |
| `com.aakash.dsa.medium.matrix` | `SetMatrixZeroes` | In-place zero setting | Data normalization | 73 | **Facebook** ⭐⭐ |
| `com.aakash.dsa.medium.matrix` | `SearchSorted2DMatrix` | Binary search in matrix | Efficient lookups | 74 | Amazon |
| `com.aakash.dsa.medium.matrix` | `IslandCounter` | Connected components (BFS/DFS) | Network analysis | 200 | **Microsoft, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.medium.matrix` | `RangeSumQuery2D` | Prefix sum in 2D | Analytics queries | 304 | Amazon |

**💡 Matrix Pattern Recognition:**
- "Rotate matrix" → Transpose + Reverse
- "Spiral traversal" → Four-pointer boundary tracking
- "Set zeroes in-place" → Use first row/col as markers
- "Islands" → DFS/BFS or Union-Find
- "Range sum" → 2D prefix sum

**💡 Microsoft Interview Tip:** Rotate Image (LC 48) appears in 25% of Microsoft interviews. Practice doing it in-place without extra matrix. Explain the transpose + reverse trick clearly.

**💡 Amazon Interview Tip:** Number of Islands (LC 200) is Amazon's go-to graph warm-up. Master both DFS and BFS solutions. They may ask you to extend it (diagonal connections, multiple islands types, etc.).

#### 🎯 Simulation Problems (Common in OA - Amazon/Uber/Bloomberg)
**Why Simulations Matter:** Online assessments from product companies are 60% simulation-based logic problems.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.simulation` | `ATMTransactionSimulator` | State machine, map operations | Banking system simulation | Custom | Amazon OA |
| `com.aakash.dsa.medium.simulation` | `DataTimeScheduler` | Merge intervals | Meeting scheduler, resource conflicts | 56 | **Google, Facebook** ⭐ |
| `com.aakash.dsa.medium.simulation` | `RateLimiterController` | Sliding window, token bucket | API rate limiting | 359 | **Uber, Lyft** ⭐⭐⭐ |
| `com.aakash.dsa.medium.simulation` | `ElevatorSystemDesign` | Queue, state management | System design mini-problem | Custom | Amazon |
| `com.aakash.dsa.medium.simulation` | `ParkingLotManager` | HashMap, slot management | Resource allocation | Custom | Uber |

**💡 Uber/Lyft Interview Tip:** Rate Limiter implementation appears in 50%+ of Uber backend interviews. Know both token bucket and sliding window approaches. Be ready to discuss distributed rate limiting.

#### Logic Games (Bloomberg/Microsoft OA Favorites)
| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.logicgames` | `TicTacToeValidator` | 2D grid, win condition checking | Game state validation | 348 | Amazon, Microsoft |
| `com.aakash.dsa.medium.logicgames` | `HangmanGame` | String tracking, state management | Interactive system logic | Custom | Bloomberg OA |
| `com.aakash.dsa.medium.logicgames` | `SnakeLadderSimulator` | BFS, game simulation | Process flow modeling | 909 | Google |
| `com.aakash.dsa.medium.logicgames` | `CardShuffler` | Random sampling, Fisher-Yates | Data randomization | 384 | Facebook |

**📝 Week 5 Total: 15 problems**

---

### 🔴 WEEK 6: Caching, Concurrency & Optimization (Amazon/Google/Netflix Critical Week)

#### 🎯 Amazon/Google Must-Know: Caching & System Design
**Why This Is Critical:** LRU Cache is THE most asked design problem at Amazon. Appears in 70% of Amazon interviews.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.caching` | `LRUCacheImplementation` | DoublyLinkedList + HashMap | Session management | 146 | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.medium.caching` | `LFUCacheImplementation` | Multi-level HashMap + frequency | Access pattern optimization | 460 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.medium.caching` | `TimeBasedKeyValueStore` | TreeMap, versioning | Configuration history | 981 | Google |

**💡 Amazon Interview Tip:** LRU Cache (LC 146) is mandatory. You MUST know the DoublyLinkedList + HashMap approach by heart. Practice implementing it in 20 minutes. Amazon interviewers will ask about thread-safety extensions.

**💡 Google Interview Tip:** Be ready to discuss cache eviction policies. They may ask: "When would you use LRU vs LFU vs FIFO?" Connect to real CDN/browser caching scenarios.

#### ⚠️ CRITICAL: Concurrency & Multithreading (MANDATORY FOR 8-YEAR BACKEND ENGINEERS!)
**Why You CANNOT Skip This:** With Spring Boot, Kafka, and microservices on your resume, concurrency questions are GUARANTEED. Skipping this is interview suicide.

**🎯 Amazon/Netflix/LinkedIn Concurrency Focus:** These companies run massive distributed systems. They WILL test your thread-safety knowledge.

| Package | Class | Concept | Real-World Analogy | Interview Frequency | Company Tags |
|---------|-------|---------|-------------------|---------------------|--------------|
| `com.aakash.dsa.medium.concurrency` | `ProducerConsumerPattern` | BlockingQueue, wait/notify | Kafka-like message pipeline | 40% of interviews | **Amazon, LinkedIn** ⭐⭐⭐ |
| `com.aakash.dsa.medium.concurrency` | `ThreadSafeCounter` | AtomicInteger, synchronized | Distributed counter service | 35% of interviews | **Microsoft, Google** ⭐⭐ |
| `com.aakash.dsa.medium.concurrency` | `ReadWriteLockExample` | ReentrantReadWriteLock | Concurrent cache access | 25% of interviews | Amazon ⭐ |
| `com.aakash.dsa.medium.concurrency` | `ThreadPoolExecutorDemo` | ExecutorService, Future | Task parallelization | 30% of interviews | **Uber, Netflix** ⭐⭐ |
| `com.aakash.dsa.medium.concurrency` | `DeadlockPrevention` | Lock ordering, tryLock | Database transaction handling | 20% of interviews | Microsoft ⭐ |
| `com.aakash.dsa.medium.concurrency` | `CompletableFutureChaining` | Async processing | Microservices orchestration | 40% of interviews | **Amazon, Netflix** ⭐⭐⭐ |
| `com.aakash.dsa.medium.concurrency` | `ConcurrentHashMapUsage` | Thread-safe collections | Shared state management | 30% of interviews | Google, Microsoft ⭐ |

**🎯 Topics You MUST Master:**
- `synchronized` keyword vs `Lock` interface - *When to use each?*
- `volatile` keyword and memory visibility - *Happens-before relationship*
- `AtomicInteger/AtomicReference` - *Compare-and-swap operations*
- `CountDownLatch`, `CyclicBarrier`, `Semaphore` - *Coordination primitives*
- `BlockingQueue` implementations - *Producer-consumer variations*
- Thread pool sizing and configuration - *How to determine optimal size?*
- `CompletableFuture` for async operations - *Chaining, error handling*

**💡 Amazon Interview Questions (Asked 60% of the Time):**
1. **"How do you handle concurrent updates to a shared counter?"**
   - Expected: AtomicInteger, synchronized block comparison, trade-offs

2. **"Explain the difference between synchronized and ReentrantLock"**
   - Expected: Flexibility, tryLock, fairness policies, condition variables

3. **"What happens when multiple threads access a HashMap simultaneously?"**
   - Expected: Data corruption, infinite loop, ConcurrentHashMap alternative

4. **"Design a thread-safe singleton"**
   - Expected: Double-checked locking, static inner class, enum approach

5. **"How would you implement a rate limiter with concurrency in mind?"**
   - Expected: Semaphore, token bucket, sliding window with locks

6. **"Explain how CompletableFuture works in your microservices"**
   - Expected: Async processing, non-blocking, exception handling, chaining

7. **"What's the difference between CountDownLatch and CyclicBarrier?"**
   - Expected: One-time vs reusable, use cases, implementation details

**💡 Netflix/Uber Interview Tip:** They love asking about ThreadPoolExecutor tuning. Be ready to discuss: core pool size, max pool size, queue capacity, rejection policies. Connect to your actual production experience with async processing.

**💡 Microsoft Interview Tip:** Deadlock scenarios are Microsoft favorites. Practice explaining circular wait conditions and prevention strategies (lock ordering, timeouts).

#### ⚠️ CRITICAL: Bit Manipulation (High ROI - 5 Problems = 90% Coverage)
**Why This Matters:** Shows mathematical optimization thinking. Small time investment, high interview ROI.

**🎯 Amazon/Facebook Bit Pattern:** Bit manipulation appears in 15-20% of interviews, usually as an optimization question.

| Package | Class | Concept | Real-World Analogy | LeetCode | Company Tags |
|---------|-------|---------|-------------------|----------|--------------|
| `com.aakash.dsa.medium.bits` | `SingleNumberFinder` | XOR properties (a ^ a = 0) | Finding unique items in duplicates | 136, 137 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.medium.bits` | `CountingBitsEfficient` | Brian Kernighan's algorithm | Bit counting optimization | 191, 338 | Google ⭐ |
| `com.aakash.dsa.medium.bits` | `PowerOfTwoChecker` | n & (n-1) == 0 trick | Validation checks | 231 | Facebook |
| `com.aakash.dsa.medium.bits` | `BitwiseSubsetGenerator` | Bit masking for combinations | Subset generation | 78 | Microsoft |
| `com.aakash.dsa.medium.bits` | `ReverseBitsUtility` | Bit operations | Data encoding | 190 | Amazon |

**💡 Bit Manipulation Pattern Recognition:**
- "Find unique number" → XOR all elements
- "Count set bits" → Brian Kernighan's (n & (n-1))
- "Check power of 2" → n & (n-1) == 0
- "Generate subsets" → Iterate through 2^n bitmasks
- "Swap without temp" → XOR swap (rare, but impressive)

**💡 Amazon Interview Tip:** Single Number (LC 136) often appears as a follow-up optimization. You solve with HashSet first (O(n) space), then they ask for O(1) space → XOR solution.

**📝 Week 6 Total: 15 problems (3 caching + 7 concurrency + 5 bits)**

---

### 📋 Part 1 Complete Weekly Schedule with Company Focus

| Week | Topics | Problems | Daily Target | Primary Company Focus | Key Pattern |
|------|--------|----------|--------------|----------------------|-------------|
| **Week 1** | Strings (5) + Math (6) + Arrays (5) | 16 | 2-3 problems | All companies | Basic data structures |
| **Week 2** | Two-pointer + HashMap | 5 | 2 problems | **Amazon, Google** | Two Sum variants ⭐⭐⭐ |
| **Week 3** | Stack/Queue (6) + Heaps (4) | 10 | 2-3 problems | **Amazon, Microsoft** | Top-K, Valid Parentheses ⭐⭐ |
| **Week 4** | Sorting/Search (5) + Review | 5 + revision | 2 problems | Facebook, Google | Binary Search variants |
| **Week 5** | **Matrix (6)** + Simulation (5) + Games (4) | 15 | 2-3 problems | **Microsoft, Amazon** | Matrix rotation, Islands ⭐⭐⭐ |
| **Week 6** | Caching (3) + **Concurrency (7)** + **Bits (5)** | 15 | 3 problems + mock | **Amazon, Netflix** | LRU Cache, Thread-safety ⭐⭐⭐ |

**Total Problems: 66 + revisions = ~70 problems**  
**Daily Commitment:** 1-2 hours  
**Weekly Mock:** 1 interview (starting Week 3)

### 🎯 Part 1 Company Success Checklist

#### Targeting Amazon? Focus on:
- ✅ Week 2: Two Sum variants (LC 1, 167) - **Mandatory**
- ✅ Week 3: Kth Largest Element (LC 215) - **Mandatory**
- ✅ Week 5: Number of Islands (LC 200) - **Very Common**
- ✅ Week 6: LRU Cache (LC 146) - **Mandatory**
- ✅ Week 6: All 7 concurrency problems - **Critical for backend role**

#### Targeting Google? Focus on:
- ✅ Week 2: Longest Substring Without Repeating (LC 3) - **Common**
- ✅ Week 3: All heap problems - **Frequent**
- ✅ Week 5: Merge Intervals (LC 56) - **Very Common**
- ✅ Week 6: Time-based KV Store (LC 981) - **System design tie-in**

#### Targeting Microsoft? Focus on:
- ✅ Week 3: Stack/Queue implementations - **Fundamental tests**
- ✅ Week 5: Matrix Rotation (LC 48) - **Very Common** (25% of interviews)
- ✅ Week 6: Concurrency problems - **Critical**

#### Targeting Uber/Lyft? Focus on:
- ✅ Week 5: Rate Limiter implementation - **Mandatory**
- ✅ Week 5: All simulation problems - **OA staples**
- ✅ Week 6: ThreadPoolExecutor - **Backend system questions**

**🎯 Success Metrics:**
- Can solve medium problems in 20-30 minutes
- Write production-quality Java code with proper variable naming
- Explain time/space complexity trade-offs clearly
- Handle concurrency questions confidently (synchronized, locks, atomic variables)
- Relate problems to real backend systems (caching, rate limiting, message queues)

</details>

---

<details>
<summary>🧠 <b>PART 2 – FAANG-Level Preparation (8-12 Weeks)</b></summary>

**Focus:** Deep algorithmic thinking, complex data structures, optimization techniques

**Interview Pattern:** Multiple rounds with progressively harder problems, system design tie-ins, optimization focus

---

### 🟢 PHASE 1 (Weeks 1-3): Data Structure Mastery

#### 🎯 Microsoft/Amazon Core: Linked List Deep Dive
**Why Microsoft Loves This:** Linked list manipulation tests pointer understanding and careful implementation - core skills for senior engineers.

**Interview Frequency:** Linked lists appear in 40% of Microsoft interviews, 30% of Amazon interviews.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.linkedlist` | `LinkedListReverser` | Reverse (iterative, recursive, in-place) | Pointer manipulation | 206 | **Google, Facebook** ⭐⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `LinkedListCycleDetector` | Floyd's cycle detection | Fast-slow pointer | 141, 142 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `ListIntersectionFinder` | Find intersection point | Two-pointer technique | 160 | Microsoft ⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `ReverseNodesInKGroup` | K-group reversal | Complex pointer rewiring | 25 | **Microsoft, Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `PalindromeListChecker` | Palindrome detection | Reverse + compare | 234 | Microsoft ⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `ReorderListSolver` | List reordering | Multiple techniques combined | 143 | Amazon, LinkedIn |
| `com.aakash.dsa.advanced.linkedlist` | `MergeSortedLists` | Merge K sorted lists | Min heap approach | 23 | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.linkedlist` | `FlattenMultilevelList` | DFS on multilevel list | Recursion/Stack | 430 | Amazon |

**💡 Microsoft Interview Pattern:** They love asking "Reverse Linked List in K-groups" (LC 25) as a follow-up to basic reversal. Practice explaining the edge cases (last group < k, empty list). They want to see careful pointer manipulation without bugs.

**💡 Amazon Interview Pattern:** "Merge K Sorted Lists" (LC 23) appears in 35% of Amazon onsite rounds. Know both the min-heap approach (O(n log k)) and the divide-and-conquer approach. They'll ask you to optimize.

**💡 Floyd's Algorithm Must-Know:** Cycle detection with fast-slow pointers is asked at EVERY FAANG company. Master both detecting cycle AND finding cycle start point.

#### 🎯 Facebook/Google Core: Binary Tree Fundamentals
**Why Facebook Loves This:** Tree problems test recursion mastery and edge case handling. Facebook asks tree questions in 60%+ of interviews.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.trees` | `TreeTraversalMethods` | BFS, DFS (preorder, inorder, postorder) | Iterative + recursive | 102, 144, 94, 145 | **Amazon, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `MorrisTraversal` | O(1) space traversal | Threaded binary tree | 94 (Morris variant) | **Google** ⭐ |
| `com.aakash.dsa.advanced.trees` | `TreeViewPrinter` | Right/left/top/bottom view | Level-order + tracking | 199, Custom | Amazon |
| `com.aakash.dsa.advanced.trees` | `VerticalOrderTraversal` | Column-based traversal | TreeMap + BFS | 987 | **Facebook** ⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `BoundaryTraversal` | Boundary of binary tree | Multiple DFS passes | Custom | Amazon |
| `com.aakash.dsa.advanced.trees` | `MaxPathSumCalculator` | Maximum path sum | Post-order recursion | 124 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `DiameterCalculator` | Tree diameter | Height calculation | 543 | **Microsoft, Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `LowestCommonAncestor` | LCA finding | Recursive tracking | 236 | **Google, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `SerializeDeserializeTree` | Tree serialization | Level-order encoding | 297 | **Facebook, Amazon** ⭐⭐⭐ |

**💡 Google Interview Pattern:** "Maximum Path Sum" (LC 124) is Google's tree benchmark problem. Appears in 25% of Google tree rounds. Practice the bottom-up approach. They'll ask: "What if path doesn't have to go through root?" "What if we want top-K paths?"

**💡 Facebook Interview Pattern:** "Serialize/Deserialize Binary Tree" (LC 297) is Facebook's go-to design problem. They want to see you handle null nodes elegantly. Practice both level-order and preorder approaches. Follow-up: "How would you handle N-ary trees?"

**💡 Microsoft Interview Pattern:** "Diameter of Binary Tree" (LC 543) and "Lowest Common Ancestor" (LC 236) appear in 30% of Microsoft interviews. They test your recursive thinking. Be ready to extend LCA to BST version.

**🎯 Tree Traversal Must-Know:** All FAANG companies expect you to write iterative traversals (using stack) in addition to recursive. Google specifically asks about Morris Traversal for O(1) space optimization.

#### Binary Search Tree Operations (Amazon's BST Specialty)
**Why Amazon Loves BST:** BST problems test both tree recursion AND binary search properties - double the value.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.trees` | `BSTValidator` | Validate BST | Range-based validation | 98 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `BSTInserterDeleter` | Insert/Delete operations | Predecessor/successor logic | 450 | Microsoft |
| `com.aakash.dsa.advanced.trees` | `KthSmallestInBST` | Kth smallest element | Inorder traversal | 230 | **Google, Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `BSTFromPreorder` | Construct BST from preorder | Range-based construction | 1008 | Amazon |
| `com.aakash.dsa.advanced.trees` | `BalancedBSTFromArray` | Array to balanced BST | Divide & conquer | 108 | Facebook |
| `com.aakash.dsa.advanced.trees` | `BSTIterator` | BST iterator | Controlled inorder | 173 | **Meta, Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.trees` | `RecoverBST` | Fix swapped nodes | Inorder anomaly detection | 99 | Google |

**💡 Amazon Interview Pattern:** "Validate BST" (LC 98) appears in 40% of Amazon tree rounds. Common mistake: Using only left < root < right check. Must use range validation. Amazon loves asking: "What if BST has duplicates? How would you modify?"

**💡 Google Interview Pattern:** "Kth Smallest in BST" (LC 230) - Google asks this then immediately asks you to optimize for multiple queries. Expected answer: Augment BST nodes with subtree sizes for O(h) lookups.

**📝 Phase 1 (Weeks 1-3) Total: 30 problems (8 linked list + 15 tree + 7 BST)**

---

### 🟡 PHASE 2 (Weeks 4-6): Graph Algorithms & Union-Find

#### 🎯 Facebook/Google Core: Graph Traversal & Fundamentals
**Why Facebook Loves Graphs:** Social network == graph. Facebook asks graph problems in 70%+ of interviews.

**Interview Frequency:** Graphs are THE most important topic for Facebook/Google roles.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.graphs` | `GraphRepresentations` | Adjacency list, matrix, edge list | Graph construction | Custom | All companies |
| `com.aakash.dsa.advanced.graphs` | `BFSDFSTraversal` | BFS and DFS templates | Queue vs Stack | 133, 200 | **Google, Facebook** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.graphs` | `ConnectedComponentsFinder` | Count components | Union-Find or DFS | 323 | Amazon |
| `com.aakash.dsa.advanced.graphs` | `BipartiteGraphChecker` | Bipartite validation | 2-coloring with BFS | 785 | **Facebook, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.graphs` | `CycleDetector` | Detect cycle (directed/undirected) | DFS with color coding | 207 | Amazon, Google ⭐ |
| `com.aakash.dsa.advanced.graphs` | `AllPathsFinder` | Find all paths source to target | Backtracking | 797 | **Google, Bloomberg** ⭐⭐ |
| `com.aakash.dsa.advanced.graphs` | `GraphCloner` | Clone graph | HashMap + BFS/DFS | 133 | **Facebook, Amazon** ⭐⭐⭐ |

**💡 Facebook Interview Pattern:** "Clone Graph" (LC 133) is Facebook's signature graph problem. Appears in 50% of Facebook interviews. They want to see HashMap to track visited nodes. Follow-up: "How would you handle disconnected components?" "What if the graph is cyclic?"

**💡 Google Interview Pattern:** "All Paths from Source to Target" (LC 797) tests backtracking in graphs. Google loves this because it combines graph traversal + backtracking. Be ready to optimize with memoization.

#### 🎯 Google/Amazon Advanced: Graph Algorithms
**Why Google Loves This:** Shortest path algorithms test both algorithmic knowledge AND optimization thinking.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.graphs` | `TopologicalSorter` | Kahn's algorithm, DFS-based | Directed acyclic graph | 207, 210 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.graphs` | `ShortestPathUnweighted` | BFS for shortest path | Level tracking | Custom | Facebook |
| `com.aakash.dsa.advanced.graphs` | `DijkstraAlgorithm` | Shortest path (weighted) | Priority queue | 743, 787 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.graphs` | `BellmanFordAlgorithm` | Negative weight handling | Edge relaxation | 787 (variant) | Google ⭐ |
| `com.aakash.dsa.advanced.graphs` | `FloydWarshallAlgorithm` | All-pairs shortest path | DP approach | Custom | Microsoft (rare) |
| `com.aakash.dsa.advanced.graphs` | `MinimumSpanningTree` | Kruskal's & Prim's | Union-Find & Priority Queue | 1584 | Google ⭐ |
| `com.aakash.dsa.advanced.graphs` | `ArticulationPointsFinder` | Tarjan's algorithm | DFS with timestamps | Custom | Google (rare) |
| `com.aakash.dsa.advanced.graphs` | `StronglyConnectedComponents` | Kosaraju's algorithm | Two-pass DFS | Custom | Google (rare) |

**💡 Amazon Interview Pattern:** "Course Schedule" (LC 207/210) - Topological Sort - appears in 45% of Amazon graph interviews. They want both Kahn's algorithm (BFS + in-degree) AND DFS approaches. Follow-up: "What if there are prerequisites with weights?"

**💡 Google Interview Pattern:** Dijkstra's algorithm is Google's graph gold standard. They'll give you "Network Delay Time" (LC 743) or similar. Expected: Priority queue implementation, O((V+E) log V) complexity. Follow-up: "What if edges have negative weights?" → Bellman-Ford.

**🎯 Must-Know Graph Algorithms Ranking:**
1. **BFS/DFS** - 100% must know
2. **Topological Sort** - 80% must know (Amazon/Microsoft)
3. **Dijkstra** - 70% must know (Google/Amazon)
4. **Union-Find** - 70% must know (See next section)
5. **Bellman-Ford** - 40% good to know
6. **MST (Kruskal/Prim)** - 30% good to know
7. **Tarjan's/Kosaraju's** - 10% rare, but impressive

#### ⚠️ CRITICAL: Union-Find (Disjoint Set) - Hidden FAANG Favorite
**Why This Is Critical:** Union-Find solves an entire class of connectivity problems efficiently. Appears in 25-30% of Amazon/Google graph interviews but most candidates don't know it well.

**🎯 Amazon/Google Union-Find Pattern:** "Whenever you see words like 'connected', 'groups', 'components', 'network', 'merge similar' - think Union-Find first!"

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.unionfind` | `UnionFindBasic` | Union by rank, path compression | Optimization techniques | Template | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.unionfind` | `RedundantConnectionFinder` | Cycle in undirected graph | Union-Find application | 684 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.unionfind` | `AccountsMerger` | Merge connected components | Union-Find with HashMap | 721 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.unionfind` | `NumberOfProvincesCounter` | Connected components count | Union-Find variant | 547 | Microsoft |
| `com.aakash.dsa.advanced.unionfind` | `MinCostToConnectPoints` | Minimum spanning tree variant | Union-Find + sorting | 1584 | **Amazon** ⭐⭐ |

**🎯 Union-Find Template (Memorize This!):**
```java
class UnionFind {
    int[] parent, rank;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }
    
    boolean union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return false; // Already connected
        
        // Union by rank
        if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}
```

**💡 Amazon Interview Pattern:** "Accounts Merge" (LC 721) is Amazon's Union-Find benchmark. Appears in 20% of Amazon interviews. They want to see you recognize it's a Union-Find problem (grouping by email). Follow-up: "What's the time complexity with path compression?" Answer: Nearly O(n α(n)) where α is inverse Ackermann (effectively O(n)).

**💡 Google Interview Pattern:** Union-Find often appears disguised. Google will give you "Number of Islands II" (premium) or similar where islands are added dynamically. You can't use DFS (too slow) - must use Union-Find.

**💡 Pattern Recognition Guide:**
- ❓ "Are X and Y connected?" → Union-Find
- ❓ "Group similar items" → Union-Find
- ❓ "Count connected components" → Union-Find or DFS (Union-Find faster for dynamic)
- ❓ "Minimum operations to connect all" → MST with Union-Find
- ❓ "Redundant connection" → Cycle detection with Union-Find

**📝 Phase 2 (Weeks 4-6) Total: 20 problems (7 graph fundamentals + 8 advanced + 5 union-find)**

---

### 🟠 PHASE 3 (Weeks 7-9): Dynamic Programming Mastery

**Why DP Is FAANG's Secret Filter:** DP problems separate senior engineers from mid-level. Google/Amazon use hard DP as their difficulty benchmark.

#### 1D DP Patterns (Foundation - Build Intuition First)

| Package | Class | Concept | Pattern Type | LeetCode | Company Tags |
|---------|-------|---------|--------------|----------|--------------|
| `com.aakash.dsa.advanced.dp` | `ClimbingStairs` | Fibonacci-like | Basic recurrence | 70 | **Google, Microsoft** ⭐ |
| `com.aakash.dsa.advanced.dp` | `HouseRobber` | Non-adjacent selection | Decision-based DP | 198 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `DecodeWays` | Count ways to decode | String DP | 91 | **Meta, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `WordBreak` | Dictionary-based split | String + set | 139 | **Microsoft, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `LongestIncreasingSubsequence` | LIS (O(n²) and O(n log n)) | Subsequence DP | 300 | **Google, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `CoinChange` | Minimum coins | Unbounded knapsack | 322 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `PerfectSquares` | Minimum squares to sum | Variant of coin change | 279 | Google |
| `com.aakash.dsa.advanced.dp` | `JumpGame` | Can reach end | Greedy + DP | 55, 45 | **Google** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `MaximumProductSubarray` | Track min/max products | State tracking | 152 | **Amazon** ⭐⭐ |

**💡 Amazon DP Pattern:** "House Robber" (LC 198) is Amazon's DP warm-up. Appears in 35% of Amazon DP rounds. Master the pattern: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`. They'll extend to circular houses (LC 213) or binary tree houses (LC 337).

**💡 Microsoft DP Pattern:** "Word Break" (LC 139) appears in 30% of Microsoft interviews. They want DP solution (O(n²)) not just recursion. Follow-up: "Return all possible sentences" (LC 140) - adds backtracking.

**💡 Google DP Pattern:** "Longest Increasing Subsequence" is Google's DP benchmark. Must know both O(n²) DP AND O(n log n) binary search + DP hybrid approach. They'll ask you to optimize.

#### 🎯 Google Specialty: 2D DP Patterns (String DP Master Class)
**Why Google Loves String DP:** Tests both DP intuition AND careful implementation. Google asks string DP in 50% of hard rounds.

| Package | Class | Concept | Pattern Type | LeetCode | Company Tags |
|---------|-------|---------|--------------|----------|--------------|
| `com.aakash.dsa.advanced.dp` | `UniquePaths` | Grid paths | 2D traversal DP | 62 | **Amazon, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `MinPathSumInGrid` | Minimum path cost | Grid optimization | 64 | Amazon |
| `com.aakash.dsa.advanced.dp` | `LongestCommonSubsequence` | LCS | String comparison | 1143 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `EditDistance` | Levenshtein distance | String transformation | 72 | **Google, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `DistinctSubsequences` | Count subsequences | Combinatorial DP | 115 | **Google** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `RegularExpressionMatching` | Pattern matching | Complex string DP | 10 | **Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `InterleavingString` | Three-string DP | Multi-dimensional | 97 | **Google, Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `MaximalSquare` | Largest square in matrix | Matrix DP | 221 | Amazon |

**💡 Google Interview Pattern:** "Edit Distance" (LC 72) is THE Google DP problem. Appears in 40% of Google hard DP rounds. You MUST know the DP recurrence:
- If chars match: `dp[i][j] = dp[i-1][j-1]`
- Else: `dp[i][j] = 1 + min(insert, delete, replace)`

Google follow-ups: "What if delete costs 2?" "What if certain character replacements are not allowed?"

**💡 Google Interview Pattern #2:** "Regular Expression Matching" (LC 10) is Google's hardest DP problem. Only 15% of candidates solve it. If you master this, you're in the top tier. The `*` wildcard handling is tricky.

**💡 Amazon DP Pattern:** "Longest Common Subsequence" (LC 1143) appears in 25% of Amazon DP interviews. Master the 2D DP table approach. Common follow-up: "Now print the actual LCS" (requires backtracking through DP table).

#### Knapsack Variants (Amazon's Favorite DP Pattern)
**Why Amazon Loves Knapsack:** Optimization problems are core to Amazon's business (warehouse, delivery, pricing). Knapsack tests optimization thinking.

| Package | Class | Concept | Pattern Type | LeetCode | Company Tags |
|---------|-------|---------|--------------|----------|--------------|
| `com.aakash.dsa.advanced.dp` | `ZeroOneKnapsack` | Classic 0/1 knapsack | Inclusion/exclusion | Template | **Google, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `SubsetSumProblem` | Target sum existence | Boolean DP | Template | Amazon |
| `com.aakash.dsa.advanced.dp` | `PartitionEqualSubsetSum` | Partition into equal sums | Knapsack variant | 416 | **Google, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `TargetSum` | Add +/- to reach target | Count ways DP | 494 | Facebook ⭐ |
| `com.aakash.dsa.advanced.dp` | `UnboundedKnapsack` | Unlimited items | Unbounded variant | 518 | Amazon |

**💡 0/1 Knapsack Template (Master This - Solves 20+ Problems!):**
```java
int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = 1; w <= capacity; w++) {
            if (weights[i-1] <= w) {
                // Include or exclude
                dp[i][w] = Math.max(
                    dp[i-1][w],  // Exclude
                    values[i-1] + dp[i-1][w - weights[i-1]]  // Include
                );
            } else {
                dp[i][w] = dp[i-1][w];  // Can't include
            }
        }
    }
    return dp[n][capacity];
}
```

**💡 Google/Microsoft Pattern:** "Partition Equal Subset Sum" (LC 416) is a disguised 0/1 knapsack. Google asks this in 20% of DP interviews. The insight: "Can we partition?" becomes "Can we make sum/2 with subset?" = Knapsack with capacity = sum/2.

#### 🎯 Google Hard DP: Advanced Patterns (Benchmark Problems)
**Warning:** These problems separate candidates. Google uses these for L5+ (senior) roles.

| Package | Class | Concept | Pattern Type | LeetCode | Company Tags |
|---------|-------|---------|--------------|----------|--------------|
| `com.aakash.dsa.advanced.dp` | `BurstBalloons` | Interval DP | Optimal split points | 312 | **Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `StrangePrinter` | Interval optimization | Complex interval DP | 664 | Google (rare) |
| `com.aakash.dsa.advanced.dp` | `StockProblems` | All stock variants (I-VI) | State machine DP | 121-188, 309, 714 | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.dp` | `PalindromePartitioning` | Minimum cuts | Partition DP | 131, 132 | Amazon |
| `com.aakash.dsa.advanced.dp` | `ScrambleString` | String permutation DP | Recursive intervals | 87 | Google (rare) |
| `com.aakash.dsa.advanced.dp` | `WildcardMatching` | Pattern with * and ? | String matching DP | 44 | **Google, Amazon** ⭐⭐ |

**💡 Google's Hardest DP:** "Burst Balloons" (LC 312) is Google's DP final boss. Only 10-15% of candidates solve it in interviews. The insight: Don't burst in order - burst LAST. `dp[i][j] = max(dp[i][k-1] + nums[i-1]*nums[k]*nums[j+1] + dp[k+1][j])` for each k in [i, j].

**💡 Amazon Stock DP Pattern:** Stock problems (LC 121, 122, 123, 188, 309, 714) test state machine DP. Amazon's favorite is "Best Time III" (LC 123) - at most 2 transactions. Must track 4 states: `buy1, sell1, buy2, sell2`. Appears in 15% of Amazon hard rounds.

**📝 Phase 3 (Weeks 7-9) Total: 28 problems (9 basic 1D + 8 string 2D + 5 knapsack + 6 advanced)**

---

### 🔴 PHASE 4 (Weeks 10-12): Advanced Topics & Company Specialties

#### 🎯 Google Favorite: Backtracking Mastery
**Why Google Loves Backtracking:** Tests recursion depth, pruning optimization, and systematic enumeration. Backtracking appears in 30% of Google interviews.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.backtracking` | `PermutationsGenerator` | Generate all permutations | Swap-based backtracking | 46, 47 | **Google, Facebook** ⭐⭐ |
| `com.aakash.dsa.advanced.backtracking` | `CombinationsGenerator` | Generate combinations | Choose/not-choose | 77 | Google, Amazon |
| `com.aakash.dsa.advanced.backtracking` | `CombinationSum` | Target sum with reuse | Unbounded backtracking | 39, 40 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.backtracking` | `SubsetsGenerator` | All subsets | Bit manipulation alternate | 78, 90 | Facebook |
| `com.aakash.dsa.advanced.backtracking` | `NQueensSolver` | N-Queens problem | Constraint satisfaction | 51, 52 | **Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.backtracking` | `SudokuSolver` | Fill Sudoku | Complex constraints | 37 | Google, Apple |
| `com.aakash.dsa.advanced.backtracking` | `WordSearchInGrid` | Find word in 2D grid | DFS with backtracking | 79 | **Microsoft, Apple** ⭐⭐ |
| `com.aakash.dsa.advanced.backtracking` | `PalindromePartitioning` | All palindrome partitions | Backtrack + DP | 131 | Amazon |
| `com.aakash.dsa.advanced.backtracking` | `LetterCombinations` | Phone number combinations | Mapping + recursion | 17 | **Facebook, Microsoft** ⭐⭐ |
| `com.aakash.dsa.advanced.backtracking` | `GenerateParentheses` | Valid parentheses | Constraint tracking | 22 | **Facebook, Microsoft** ⭐⭐⭐ |

**💡 Google Interview Pattern:** "N-Queens" (LC 51) is Google's backtracking benchmark. Appears in 20% of Google backtracking rounds. They want optimized constraint checking (diagonal tracking with sets, not O(n) validation each time).

**💡 Google Interview Pattern #2:** "Combination Sum" (LC 39) appears in 25% of Google interviews. Master the backtracking template. Follow-up: "What if numbers can't be reused?" (LC 40). "What if we want exactly k numbers?" (LC 216).

**💡 Microsoft/Facebook Pattern:** "Generate Parentheses" (LC 22) is a Microsoft/Facebook favorite. Tests constraint tracking (open count, close count). Appears in 20% of interviews. Practice explaining the pruning logic clearly.

#### ⚠️ CRITICAL: Trie (Prefix Tree) - String Company Secret Weapon
**Why You Can't Skip This:** Trie problems appear in 25% of Google string interviews. Most candidates don't know Trie well - easy differentiation point.

**🎯 Google/Amazon Trie Pattern:** "Whenever you see autocomplete, dictionary, prefix matching, or multiple word searches - think Trie!"

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.trie` | `TrieImplementation` | Insert, search, startsWith | HashMap-based nodes | 208 | **Amazon, Microsoft** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.trie` | `WordDictionary` | Add word, search with wildcard | DFS in Trie | 211 | **Google** ⭐⭐ |
| `com.aakash.dsa.advanced.trie` | `WordSearchII` | Find multiple words in grid | Trie + backtracking | 212 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.trie` | `LongestWordInDictionary` | Build from previous words | Trie + BFS | 720 | Google |
| `com.aakash.dsa.advanced.trie` | `ReplaceWords` | Replace with shortest root | Trie prefix matching | 648 | Amazon |
| `com.aakash.dsa.advanced.trie` | `AutocompleteSystem` | Search autocomplete | Trie + heap | 642 | **Google, Amazon** ⭐⭐⭐ |

**🎯 Trie Template (Memorize This!):**
```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isWord = false;
}

class Trie {
    TrieNode root = new TrieNode();
    
    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isWord = true;
    }
    
    boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) return false;
            node = node.children.get(c);
        }
        return node.isWord;
    }
    
    boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return false;
            node = node.children.get(c);
        }
        return true;
    }
}
```

**💡 Google Interview Pattern:** "Word Search II" (LC 212) is Google's Trie masterpiece. Appears in 30% of Google Trie rounds. Combines Trie + Backtracking + Matrix traversal. Must optimize: prune Trie nodes as words are found. Without Trie, it's O(m*n*4^L*k) - too slow. With Trie: O(m*n*4^L).

**💡 Amazon Interview Pattern:** "Implement Trie" (LC 208) is Amazon's Trie basics test. Appears in 40% of Amazon Trie rounds. Follow-up: "How would you handle case-insensitive search?" "How to add word frequency tracking?" "Space optimization for large dictionaries?"

**💡 Google Interview Pattern #2:** "Design Search Autocomplete System" (LC 642 - premium but heavily asked) combines Trie + Heap. Must return top 3 matching results. Tests both Trie AND data structure combination skills.

#### ⚠️ CRITICAL: Monotonic Stack/Deque - O(n) Optimization Master Pattern
**Why This Matters:** Solves "next greater/smaller" class of problems in O(n). Most candidates use O(n²) brute force. Monotonic stack is instant optimization.

**🎯 Amazon/Google Monotonic Pattern:** High-frequency but under-prepared. Mastering this pattern gives you an edge.

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.monotonic` | `NextGreaterElement` | Next greater in array | Monotonic decreasing stack | 496, 503 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.monotonic` | `DailyTemperatures` | Days until warmer | Monotonic stack with indices | 739 | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.monotonic` | `LargestRectangleHistogram` | Max rectangle area | Monotonic increasing stack | 84 | **Google, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.monotonic` | `SlidingWindowMaximum` | Max in sliding window | Monotonic decreasing deque | 239 | **Amazon, Google** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.monotonic` | `TrappingRainWater` | Water trapped between bars | Monotonic stack / two pointer | 42 | **Amazon, Google** ⭐⭐⭐ |

**💡 Pattern Recognition (Critical!):**
- ❓ "Next greater/smaller element" → Monotonic stack
- ❓ "Max/min in sliding window" → Monotonic deque
- ❓ "Largest rectangle" → Monotonic increasing stack
- ❓ "Trapped water" → Monotonic stack or two-pointer

**💡 Google Interview Pattern:** "Largest Rectangle in Histogram" (LC 84) is Google's monotonic stack benchmark. Appears in 25% of Google hard interviews. The insight: Maintain monotonic increasing stack. When you see smaller bar, pop and calculate rectangles. O(n) solution vs O(n²) brute force.

**💡 Amazon Interview Pattern:** "Daily Temperatures" (LC 739) is Amazon's warm-up monotonic problem. Appears in 30% of Amazon interviews. Tests if you recognize the monotonic stack pattern. Follow-up: "What if we want next k days of warmer temperatures?"

**💡 Amazon/Google Hard:** "Trapping Rain Water" (LC 42) - THE hardest monotonic stack problem. Multiple solutions: monotonic stack, two-pointer, DP. Google wants all three approaches discussed. Know trade-offs.

#### Greedy Algorithms (Interview Staples)

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.greedy` | `ActivitySelection` | Maximum non-overlapping intervals | Sort by end time | 435 | Google |
| `com.aakash.dsa.advanced.greedy` | `MeetingRoomsII` | Minimum rooms needed | Min heap | 253 | **Facebook, Amazon** ⭐⭐⭐ |
| `com.aakash.dsa.advanced.greedy` | `JumpGameII` | Minimum jumps | Greedy range | 45 | **Google** ⭐⭐ |
| `com.aakash.dsa.advanced.greedy` | `GasStationCircuit` | Circular route | Cumulative sum | 134 | Amazon |
| `com.aakash.dsa.advanced.greedy` | `TaskScheduler` | Task with cooldown | Frequency-based greedy | 621 | Amazon, Facebook |
| `com.aakash.dsa.advanced.greedy` | `PartitionLabels` | Partition by last occurrence | Last seen tracking | 763 | **Amazon** ⭐⭐ |
| `com.aakash.dsa.advanced.greedy` | `QueueReconstruction` | Reconstruct queue | Sort + insert | 406 | Google |
| `com.aakash.dsa.advanced.greedy` | `MinimumArrows` | Burst balloons | Interval greedy | 452 | Microsoft |
| `com.aakash.dsa.advanced.greedy` | `CandyDistribution` | Fair distribution | Two-pass greedy | 135 | Amazon |

**💡 Facebook/Amazon Pattern:** "Meeting Rooms II" (LC 253) appears in 40% of Facebook interviews, 35% of Amazon. Master the min-heap approach. Sort by start time, use heap for end times. O(n log n).

**💡 Amazon Pattern:** "Partition Labels" (LC 763) is an Amazon favorite. Appears in 20% of greedy rounds. Track last occurrence of each character. Greedy partition at last occurrence.

#### Advanced Data Structures (Rare But Impressive)

| Package | Class | Concept | Key Techniques | LeetCode | Company Tags |
|---------|-------|---------|----------------|----------|--------------|
| `com.aakash.dsa.advanced.segmenttree` | `SegmentTreeBasic` | Range queries | Tree-based aggregation | Template | Google (rare) |
| `com.aakash.dsa.advanced.segmenttree` | `RangeSumQuery` | Mutable range sum | Segment tree application | 307 | Google |
| `com.aakash.dsa.advanced.segmenttree` | `FenwickTree` | Binary indexed tree | Efficient updates | Template | Google (rare) |

**💡 Note:** Segment Tree rarely asked (<5% of interviews) but impressive if you know it. Skip if time-constrained. Focus on high-frequency patterns first.

**📝 Phase 4 (Weeks 10-12) Total: 30 problems (10 backtracking + 6 trie + 5 monotonic + 9 greedy)**

---

### 📋 Part 2 Complete Weekly Schedule with Company Focus

| Phase | Weeks | Topics | Problems | Daily Target | Primary Company Focus | Must-Know Problems |
|-------|-------|--------|----------|--------------|----------------------|-------------------|
| **Phase 1** | 1-3 | Linked Lists (8) + Trees/BST (22) | 30 | 3-4 problems | **Microsoft, Amazon, Facebook** | Floyd's Cycle ⭐⭐⭐, Max Path Sum ⭐⭐⭐, LCA ⭐⭐⭐ |
| **Phase 2** | 4-6 | Graphs (15) + **Union-Find (5)** | 20 | 3-4 problems | **Google, Facebook, Amazon** | Clone Graph ⭐⭐⭐, Topological Sort ⭐⭐⭐, Accounts Merge ⭐⭐⭐ |
| **Phase 3** | 7-9 | All DP Patterns (28) | 28 | 3-4 problems | **Google, Amazon, Microsoft** | Edit Distance ⭐⭐⭐, LCS ⭐⭐⭐, Stock III ⭐⭐⭐ |
| **Phase 4** | 10-12 | Backtracking (10) + **Trie (6)** + Greedy (9) + **Monotonic (5)** | 30 | 3-4 problems + 2 mocks/week | **Google, Amazon** | N-Queens ⭐⭐⭐, Word Search II ⭐⭐⭐, Largest Rectangle ⭐⭐⭐ |

**Total Problems: 108 problems**  
**Daily Commitment:** 2-3 hours  
**Weekly Mocks:** 2 interviews (starting Week 4)

### 🎯 Part 2 Company Success Checklist

#### Targeting Google? Master These:
- ✅ **DP:** Edit Distance (LC 72) ⭐⭐⭐, Regular Expression Matching (LC 10) ⭐⭐⭐
- ✅ **Graphs:** Dijkstra, Clone Graph (LC 133) ⭐⭐⭐
- ✅ **Trie:** Word Search II (LC 212) ⭐⭐⭐
- ✅ **Backtracking:** N-Queens (LC 51) ⭐⭐⭐, Combination Sum (LC 39) ⭐⭐
- ✅ **Monotonic:** Largest Rectangle (LC 84) ⭐⭐⭐, Sliding Window Max (LC 239) ⭐⭐

#### Targeting Amazon? Master These:
- ✅ **Linked List:** Merge K Sorted Lists (LC 23) ⭐⭐⭐, Cycle Detection ⭐⭐
- ✅ **Trees:** Validate BST (LC 98) ⭐⭐⭐, Serialize Tree (LC 297) ⭐⭐
- ✅ **Graphs:** Topological Sort (LC 207/210) ⭐⭐⭐, Number of Islands (LC 200) ⭐⭐
- ✅ **Union-Find:** Accounts Merge (LC 721) ⭐⭐⭐
- ✅ **DP:** House Robber (LC 198) ⭐⭐⭐, Stock Problems ⭐⭐⭐
- ✅ **Monotonic:** Daily Temperatures (LC 739) ⭐⭐, Trapping Rain Water (LC 42) ⭐⭐

#### Targeting Microsoft? Master These:
- ✅ **Linked Lists:** Reverse in K-groups (LC 25) ⭐⭐, Cycle Detection ⭐⭐
- ✅ **Trees:** LCA (LC 236) ⭐⭐⭐, Diameter (LC 543) ⭐⭐
- ✅ **Graphs:** Topological Sort ⭐⭐
- ✅ **DP:** Word Break (LC 139) ⭐⭐⭐, LCS (LC 1143) ⭐⭐
- ✅ **Backtracking:** Word Search (LC 79) ⭐⭐

#### Targeting Facebook? Master These:
- ✅ **Trees:** Serialize Tree (LC 297) ⭐⭐⭐, Vertical Order (LC 987) ⭐⭐
- ✅ **Graphs:** Clone Graph (LC 133) ⭐⭐⭐, Bipartite Check (LC 785) ⭐⭐
- ✅ **Greedy:** Meeting Rooms II (LC 253) ⭐⭐⭐
- ✅ **Backtracking:** Generate Parentheses (LC 22) ⭐⭐⭐

**🎯 Success Metrics:**
- Solve hard problems in 35-45 minutes (with hints: 25-30 minutes)
- Explain multiple approaches and optimization trade-offs
- Strong time/space complexity analysis (asymptotic + practical)
- Recognize problem patterns instantly (Union-Find, Monotonic Stack, Trie triggers)
- Connect to system design concepts (caching, distributed systems, consistency)

</details>

---

## 📚 Mock Interview Resources

1. **Pramp** - Free peer-to-peer mock interviews
2. **Interviewing.io** - Anonymous with real engineers
3. **LeetCode Mock** - Company-specific assessments
4. **AlgoExpert** - Video explanations + system design
5. **CodeSignal** - OA practice for specific companies

*Last Updated: November 2025*  
*Curated for 7+ years Java/Spring Boot Backend Engineers*  
