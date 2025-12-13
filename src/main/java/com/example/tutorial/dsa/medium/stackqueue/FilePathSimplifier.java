package com.example.tutorial.dsa.medium.stackqueue;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * FilePathSimplifier
 * ----------------------------------
 * <p>This program simplifies Unix-style file paths using Stack.
 * The core problem solved here is Simplify Path (LeetCode #71).
 *
 * <p><b>Problem Statement:</b>
 * Given a string path representing an absolute Unix file path, simplify it
 * by converting it to the canonical path. The canonical path should:
 * - Start with '/'
 * - Have a single '/' between directories
 * - Not end with '/' (unless it's the root)
 * - Not contain '.' or '..' as directory names
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Normalize API endpoint paths for routing</li>
 *   <li>Clean up resource URLs in offer configurations</li>
 *   <li>Process navigation paths in admin dashboards</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: "/home/" → Output: "/home"</li>
 *   <li>Input: "/../" → Output: "/" (can't go above root)</li>
 *   <li>Input: "/home//foo/" → Output: "/home/foo"</li>
 *   <li>Input: "/a/./b/../../c/" → Output: "/c"</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Microsoft, Google
 *
 * @see <a href="https://leetcode.com/problems/simplify-path/">LeetCode 71 - Simplify Path</a>
 */
@Component
public class FilePathSimplifier implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FilePathSimplifier.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== FilePathSimplifier: Path Normalization Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with API endpoint paths
    System.out.println("--- Simplifying API Endpoint Paths ---\n");
    String[] apiPaths = {
        "/api/v1/../v2/offers/",
        "/api/offers/./category/../merchant//details",
        "/api/../../public/"
    };

    for (String path : apiPaths) {
      String simplified = simplifyPath(path);
      System.out.println("Original:   \"" + path + "\"");
      System.out.println("Simplified: \"" + simplified + "\"\n");
    }

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    String[] testCases = {
        "/home/",
        "/../",
        "/home//foo/",
        "/a/./b/../../c/",
        "/a/b/c/../../../.."
    };

    for (String path : testCases) {
      String result = simplifyPath(path);
      System.out.println("Input:  \"" + path + "\"");
      System.out.println("Output: \"" + result + "\"\n");
    }
  }

  /**
   * Simplifies Unix file path using Stack to track directory hierarchy.
   *
   * <p><b>LOGIC (Stack for Directory Tracking):</b>
   * <ol>
   *   <li>Split path by '/' to get individual components</li>
   *   <li>Use stack to maintain current directory hierarchy</li>
   *   <li>For each component:
   *     <ul>
   *       <li>Empty or "." → skip (current directory)</li>
   *       <li>".." → pop from stack if not empty (go up one level)</li>
   *       <li>Otherwise → push directory name to stack</li>
   *     </ul>
   *   </li>
   *   <li>Build result by joining stack elements with '/'</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * path = "/a/./b/../../c/"
   * Split: ["", "a", ".", "b", "..", "..", "c", ""]
   * stack = []
   *
   * "" → skip (empty)
   * "a" → push → stack = ["a"]
   * "." → skip (current dir)
   * "b" → push → stack = ["a", "b"]
   * ".." → pop → stack = ["a"]
   * ".." → pop → stack = []
   * "c" → push → stack = ["c"]
   * "" → skip (empty)
   *
   * Result: "/" + "c" = "/c"
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We process each character once during split and each component once.
   * <br><i>Like walking through a file system step by step - if the path has
   * n characters, you take about n steps to navigate and clean it up.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Stack and split array may hold up to n/2 directories.
   * <br><i>Like keeping breadcrumbs as you walk - worst case every other
   * character is a directory name, so n/2 breadcrumbs needed.</i>
   *
   * @param path the Unix file path to simplify
   * @return the canonical (simplified) path
   */
  public static String simplifyPath(String path) {
    // Edge case.
    if (path == null || path.isEmpty()) {
      return "/";
    }

    // Stack to track directory hierarchy (use Deque as stack).
    Deque<String> stack = new ArrayDeque<>();

    // Split by '/' to get path components.
    String[] components = path.split("/");

    for (String component : components) {
      // Skip empty strings and current directory markers.
      if (component.isEmpty() || component.equals(".")) {
        continue;
      }

      // Go up one directory level.
      if (component.equals("..")) {
        if (!stack.isEmpty()) {
          stack.pop();
        }
        // If stack is empty, we're at root - can't go higher.
      } else {
        // Valid directory name - push to stack.
        stack.push(component);
      }
    }

    // Build canonical path.
    if (stack.isEmpty()) {
      return "/";
    }

    StringBuilder result = new StringBuilder();
    // Stack is LIFO, so we need to reverse the order.
    // Convert to array and iterate from bottom to top.
    String[] dirs = stack.toArray(new String[0]);
    for (int i = dirs.length - 1; i >= 0; i--) {
      result.append("/").append(dirs[i]);
    }

    return result.toString();
  }
}
