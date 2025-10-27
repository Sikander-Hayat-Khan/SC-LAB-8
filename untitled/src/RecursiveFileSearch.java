import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecursiveFileSearch {
    /**
     * Recursively searches for a given file within the specified directory and its subdirectories.
     *
     * @param directory      The directory in which to begin searching.
     * @param fileName       The exact name of the file to search for.
     * @param caseSensitive  If true, the search is case-sensitive; if false, it ignores case.
     * @param foundPaths     A list that will store the absolute paths of all matching files found.
     *
     * @precondition  directory is not null and represents an existing directory.
     * @postcondition All occurrences of the specified file within the directory tree are added to foundPaths.
     * @throws SecurityException if access to a directory is denied by the system.
     */

    // Recursive method to search for files
    public static void searchFile(File directory, String fileName, boolean caseSensitive, List<String> foundPaths) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursive call
                searchFile(file, fileName, caseSensitive, foundPaths);
            } else {
                if (caseSensitive) {
                    if (file.getName().equals(fileName)) {
                        foundPaths.add(file.getAbsolutePath());
                    }
                } else {
                    if (file.getName().equalsIgnoreCase(fileName)) {
                        foundPaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * Main method to run the recursive file search program.
     * Prompts the user for a directory path, file names, and case-sensitivity option.
     * Displays results or appropriate error messages.
     *
     * @throws IllegalArgumentException if the directory does not exist or is invalid.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory path: ");
        String directoryPath = sc.nextLine();
        System.out.print("Enter file names to search (comma-separated): ");
        String[] fileNames = sc.nextLine().split(",");
        System.out.print("Case-sensitive search? (yes/no): ");
        boolean caseSensitive = sc.nextLine().trim().equalsIgnoreCase("yes");
        sc.close();

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: Directory does not exist!");
            return;
        }

        for (String rawName : fileNames) {
            String fileName = rawName.trim();
            List<String> foundPaths = new ArrayList<>();
            searchFile(directory, fileName, caseSensitive, foundPaths);

            if (foundPaths.isEmpty()) {
                System.out.println("File not found: " + fileName);
            } else {
                System.out.println("Found " + fileName + " " + foundPaths.size() + " time(s):");
                for (String path : foundPaths) {
                    System.out.println(" → " + path);
                }
            }
        }
    }
}
