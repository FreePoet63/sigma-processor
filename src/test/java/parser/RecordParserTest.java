package parser;

import com.shift.model.Employee;
import com.shift.model.Manager;
import com.shift.model.Person;
import com.shift.parser.RecordParser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RecordParserTest class
 *
 * @author razlivinsky
 * @since 07.08.2025
 */
public class RecordParserTest {
    @Test
    void testValidEmployeeParsing() {
        String line = "Employee,101,John Doe,3000.0,1";
        Optional<Person> person = RecordParser.parseLine(line);
        assertTrue(person.isPresent());
        assertTrue(person.get() instanceof Employee);
        assertEquals(101, person.get().getId());
    }

    @Test
    void testInvalidSalary() {
        String line = "Employee,102,Jane,abc,1";
        Optional<Person> person = RecordParser.parseLine(line);
        assertFalse(person.isPresent());
    }

    @Test
    void testManagerParsing() {
        String line = "Manager,1,Jane Smith,5000.0,HR";
        Optional<Person> person = RecordParser.parseLine(line);
        assertTrue(person.isPresent());
        assertTrue(person.get() instanceof Manager);
        assertEquals("HR", ((Manager) person.get()).getDepartment());
    }
}