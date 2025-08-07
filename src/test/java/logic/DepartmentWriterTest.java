package logic;

import com.shift.logic.DepartmentWriter;
import com.shift.model.Department;
import com.shift.model.Employee;
import com.shift.model.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the DepartmentWriterTest class
 *
 * @author razlivinsky
 * @since 07.08.2025
 */
public class DepartmentWriterTest {
    private static final Path OUTPUT_DIR = Paths.get("output");
    private static final Path FILE_PATH = OUTPUT_DIR.resolve("HR.sb");
    private static final Path ERROR_LOG_PATH = OUTPUT_DIR.resolve("error.log");

    @BeforeEach
    void setUp() throws IOException {
        // Create the folder if it doesn't exist
        if (!Files.exists(OUTPUT_DIR)) {
            Files.createDirectories(OUTPUT_DIR);
        }

        // Delete old files if any remain
        Files.deleteIfExists(FILE_PATH);
        Files.deleteIfExists(ERROR_LOG_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up after tests
        Files.deleteIfExists(FILE_PATH);
        Files.deleteIfExists(ERROR_LOG_PATH);
    }

    @Test
    void testWriteDepartmentFile() throws IOException {
        Department dept = new Department("HR");
        dept.setManager(new Manager(1, "Jane Smith", 5000, "HR"));
        dept.addEmployee(new Employee(101, "John Doe", 3000, 1));

        Map<String, Department> map = Map.of("HR", dept);
        DepartmentWriter.writeDepartments(map, null, null);
        assertTrue(Files.exists(FILE_PATH), "The file HR.sb must be created");

        List<String> lines = Files.readAllLines(FILE_PATH);
        assertEquals(2, lines.size());
        assertTrue(lines.get(0).startsWith("Manager"));
        assertTrue(lines.get(1).startsWith("Employee"));
    }

    @Test
    void testWriteErrorLog() throws IOException {
        List<String> errors = List.of("bad line 1", "bad line 2");
        DepartmentWriter.writeErrors(errors);

        assertTrue(Files.exists(ERROR_LOG_PATH), "error.log must be created");

        List<String> lines = Files.readAllLines(ERROR_LOG_PATH);
        assertEquals(2, lines.size());
        assertEquals("bad line 1", lines.get(0));
        assertEquals("bad line 2", lines.get(1));
    }
}