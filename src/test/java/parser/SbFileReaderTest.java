package parser;

import com.shift.parser.SbFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the SbFileReaderTest class
 *
 * @author razlivinsky
 * @since 07.08.2025
 */
public class SbFileReaderTest {
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("test");
        List<String> lines = List.of(
                "Manager,1,Jane Smith,5000,HR",
                "Employee,101,John Doe,3000,1",
                "Employee,999,Bad Guy,abc,1"
        );
        Files.write(tempDir.resolve("test_input.sb"), lines);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testReaderParsesManagersAndEmployees() {
        SbFileReader reader = new SbFileReader();
        reader.readAllSbFiles(tempDir.toString());

        assertEquals(1, reader.getManagers().size(), "1 manager expected");
        assertEquals(1, reader.getEmployees().size(), "1 employee expected");
        assertEquals(1, reader.getErrorLines().size(), "Expected one line with an error");
    }
}