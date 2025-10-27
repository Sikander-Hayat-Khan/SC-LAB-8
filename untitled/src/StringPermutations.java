import java.util.*;

/**
 * StringPermutations
 * ------------------
 * This program generates all permutations of a given string recursively and
 * optionally excludes duplicate permutations. A non-recursive version is also included.
 * It also measures and compares the execution time of both methods.
 */
public class StringPermutations {

    /**
     * Recursively generates all permutations of the given string.
     *
     * @param str       the remaining string to permute
     * @param prefix    the accumulated prefix of characters chosen so far
     * @param result    the set where generated permutations will be stored
     * @precondition    str != null, result != null
     * @postcondition   All permutations of the original string are added to result
     */
    public static void generatePermutations(String str, String prefix, Set<String> result) {
        if (str.isEmpty()) {
            result.add(prefix);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String remaining = str.substring(0, i) + str.substring(i + 1);
            generatePermutations(remaining, prefix + ch, result);
        }
    }

    /**
     * Generates permutations using an iterative (non-recursive) Heap’s algorithm.
     *
     * @param str the input string
     * @return list of all generated permutations
     * @precondition str != null
     * @postcondition returns all permutations of str
     */
    public static List<String> generateIterative(String str) {
        List<String> permutations = new ArrayList<>();
        char[] arr = str.toCharArray();
        int n = arr.length;
        int[] c = new int[n];
        permutations.add(new String(arr));

        int i = 0;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    char temp = arr[0];
                    arr[0] = arr[i];
                    arr[i] = temp;
                } else {
                    char temp = arr[c[i]];
                    arr[c[i]] = arr[i];
                    arr[i] = temp;
                }
                permutations.add(new String(arr));
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }
        return permutations;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        if (input.isEmpty()) {
            System.out.println("Error: String cannot be empty!");
            sc.close();
            return;
        }

        System.out.print("Include duplicate permutations? (yes/no): ");
        boolean includeDuplicates = sc.nextLine().trim().equalsIgnoreCase("yes");

        // --- Recursive permutation generation ---
        Set<String> recursiveResult = includeDuplicates ? new LinkedHashSet<>() : new TreeSet<>();
        long startRecursive = System.nanoTime();
        generatePermutations(input, "", recursiveResult);
        long endRecursive = System.nanoTime();
        long recursiveTime = endRecursive - startRecursive;

        // --- Iterative permutation generation ---
        long startIterative = System.nanoTime();
        List<String> iterativeResult = generateIterative(input);
        long endIterative = System.nanoTime();
        long iterativeTime = endIterative - startIterative;

        // --- Display results ---
        System.out.println("\n=== Recursive Permutations (" + recursiveResult.size() + " total) ===");
        for (String s : recursiveResult) System.out.println(s);
        System.out.printf("Recursive execution time: %.6f ms%n", recursiveTime / 1_000_000.0);

        System.out.println("\n=== Iterative Permutations (" + iterativeResult.size() + " total) ===");
        for (String s : iterativeResult) System.out.println(s);
        System.out.printf("Iterative execution time: %.6f ms%n", iterativeTime / 1_000_000.0);

        // --- Compare ---
        System.out.println("\nPerformance Comparison:");
        if (recursiveTime < iterativeTime)
            System.out.println("Recursive version was faster!");
        else if (iterativeTime < recursiveTime)
            System.out.println("Iterative version was faster!");
        else
            System.out.println("Both took roughly the same time!");

        sc.close();
    }
}
