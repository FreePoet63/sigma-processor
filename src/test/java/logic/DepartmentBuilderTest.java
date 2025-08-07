package logic;

import com.shift.logic.DepartmentBuilder;
import com.shift.model.Department;
import com.shift.model.Employee;
import com.shift.model.Manager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DepartmentBuilderTest class
 *
 * @author razlivinsky
 * @since 07.08.2025
 */
public class DepartmentBuilderTest {
    @Test
    void testBuildDepartmentWithOneEmployee() {
        Manager m = new Manager(1, "Jane", 5000, "HR");
        Employee e = new Employee(101, "John", 3000, 1);
        List<String> errors = new ArrayList<>();

        DepartmentBuilder builder = new DepartmentBuilder(List.of(m), List.of(e), errors);
        Map<String, Department> departments = builder.buildDepartments();

        assertEquals(1, departments.size());
        Department dept = departments.get("HR");
        assertNotNull(dept);
        assertEquals(1, dept.getEmployees().size());
        assertEquals("John", dept.getEmployees().get(0).getName());
        assertTrue(errors.isEmpty());
    }

    @Test
    void testEmployeeWithInvalidManager() {
        Employee e = new Employee(101, "Ghost", 1000, 99);
        DepartmentBuilder builder = new DepartmentBuilder(List.of(), List.of(e), new ArrayList<>());
        Map<String, Department> departments = builder.buildDepartments();

        assertTrue(departments.isEmpty());
    }
}