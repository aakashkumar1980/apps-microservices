# AI/ML Batch Modules — Sorted by Complexity (Easy → Hard)

This table organizes the advanced modules for the credit card offers ecosystem based on:

1. 📘 Algorithmic complexity (Leetcode/Hackerrank level)
2. 🧠 Depth of LLM / AI integration
3. 🔢 Java Streams usage

---

| Module Name                     | 📘 Algorithms Applied                                    | 🧠 LLM / AI Stack Used                          | 🔢 Java Streams Used                         |
|----------------------------------|----------------------------------------------------------|--------------------------------------------------|-----------------------------------------------|
| **1. Batch Offer Upload**        | ✅ Regex validation <br>✅ Trie <br>✅ Dedup (HashSet)      | —                                                | `map`, `filter`, `collect`                    |
| **2. Reporting Engine**          | ✅ Frequency Map <br>✅ Max/Min <br>✅ Aggregation Chains   | —                                                | `groupingBy`, `joining`, `collectingAndThen`  |
| **3. Campaign Comparison Tool**  | ✅ Sorting <br>✅ Median/Bucket <br>✅ Aggregation logic     | Bedrock (for summary insight)                    | `summarizingDouble`, `joining`, `groupingBy`  |
| **4. Smart Offer Creator (LLM)** | ✅ Regex <br>✅ Subset Match (Backtracking)                | Bedrock (Claude, prompt engineering)             | —                                             |
| **5. Customer 360 Profile Builder** | ✅ Set Merge <br>✅ Join <br>✅ Set Intersection           | SageMaker (personalization), Titan Embeddings    | `flatMap`, `toMap`, `anyMatch`                |
| **6. Redemption Funnel Tracker** | ✅ BFS <br>✅ Conversion Graphs                             | Bedrock (LLM-based feedback insight)             | `map`, `reduce`, `groupingBy`                 |
| **7. Offer Forecasting Engine**  | ✅ Sliding Window <br>✅ Top-K (Heap) <br>✅ Prefix Sum      | Bedrock (Claude), SageMaker (XGBoost)            | `groupingBy`, `reduce`, `partitioningBy`      |
| **8. Anomaly & Fraud Detector**  | ✅ Union-Find <br>✅ Cycle Detection <br>✅ Histogram Scan   | AWS Fraud Detector, SageMaker anomaly detection  | —                                             |
| **9. Campaign Summary Generator**| ✅ LCS <br>✅ Word Clustering                               | Bedrock (Claude, Mistral)                        | —                                             |
| **10. Semantic Search (RAG)**    | ✅ Cosine Similarity <br>✅ KNN <br>✅ Embedding Distance    | Bedrock + Titan Embeddings, OpenSearch Vector DB | —                                             |

---

## 🔰 Learning & Execution Order Recommendation

| Level        | Modules to Start With                                      |
|--------------|------------------------------------------------------------|
| 🟢 Beginner   | Batch Offer Upload, Reporting Engine                       |
| 🟡 Intermediate | Campaign Comparison, Customer 360, Redemption Funnel     |
| 🔴 Advanced   | Forecasting, Fraud Detection, Summary Gen, Semantic Search |

