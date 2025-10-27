import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.*;

public class RecursiveTests {

    // ---------- Tests for RecursiveFileSearch ----------
    @Test
    public void testFileExistsCaseInsensitive() {
        List<String> found = new ArrayList<>();
        File dir = new File(System.getProperty("user.dir"));
        RecursiveFileSearch.searchFile(dir, "RecursiveFileSearch.java", false, found);
        assertFalse(found.isEmpty(), "File should be found at least once");
    }

    @Test
    public void testFileNotFound() {
        List<String> found = new ArrayList<>();
        File dir = new File(System.getProperty("user.dir"));
        RecursiveFileSearch.searchFile(dir, "nonexistentfile123.txt", false, found);
        assertTrue(found.isEmpty(), "Non-existent file should not be found");
    }

    // ---------- Tests for StringPermutations ----------
    @Test
    public void testPermutationsCount() {
        Set<String> result = new HashSet<>();
        StringPermutations.generatePermutations("abc", "", result);
        assertEquals(6, result.size(), "3! permutations should be generated");
    }

    @Test
    public void testPermutationsEmptyString() {
        Set<String> result = new HashSet<>();
        StringPermutations.generatePermutations("", "", result);
        assertTrue(result.contains(""), "Empty string permutation should be itself");
    }

    @Test
    public void testIterativeMatchesRecursive() {
        Set<String> recursive = new HashSet<>();
        StringPermutations.generatePermutations("abc", "", recursive);
        List<String> iterative = StringPermutations.generateIterative("abc");
        assertEquals(recursive.size(), iterative.size(), "Iterative and recursive should produce same number");
    }
}
