package com.shift.parser;

import com.shift.model.Employee;
import com.shift.model.Manager;
import com.shift.model.Person;

import java.util.Optional;

/**
 * The RecordParser class provides utility methods for parsing lines of input
 * data representing either a {@link Manager} or an {@link Employee}.
 *
 * <p>This class is designed to parse strings formatted as CSV (Comma-Separated Values)
 * to create instances of the appropriate type.</p>
 *
 * <p>The input lines must adhere to a specific format, including fields for role,
 * identifier, name, salary, and additional information, such as the managing ID
 * for employees.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class RecordParser {

    /**
     * Parses a line of text and returns an {@link Optional} containing a {@link Person}
     * if the line represents a valid Manager or Employee.
     * If the line cannot be parsed into a valid Person, an empty {@code Optional}
     * is returned.
     *
     * <p>The expected format for the input line is:</p>
     * <pre>
     * role,id,name,salary,lastOrManagerId
     * </pre>
     * <ul>
     *   <li><b>role:</b> "Manager" or "Employee"</li>
     *   <li><b>id:</b> A unique identifier for the person (non-negative integer)</li>
     *   <li><b>name:</b> The name of the person (non-empty string)</li>
     *   <li><b>salary:</b> The salary amount (positive decimal)</li>
     *   <li><b>last:</b> For Managers, this should be the department name. For Employees, it should be the manager ID.</li>
     * </ul>
     *
     * @param line the line to parse; must not be {@code null}
     * @return an {@link Optional<Person>} containing the parsed person if valid,
     *         or an empty Optional if parsing fails
     */
    public static Optional<Person> parseLine(String line) {
        try {
            String[] parts = line.trim().split(",");
            if (parts.length != 5) return Optional.empty();

            String role = parts[0].trim();
            int id = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            String salaryStr = parts[3].trim();
            String last = parts[4].trim();

            if (salaryStr.isEmpty()) return Optional.empty();

            double salary = Double.parseDouble(salaryStr);
            if (salary <= 0) return Optional.empty();

            if ("Manager".equalsIgnoreCase(role)) {
                return Optional.of(new Manager(id, name, salary, last));
            } else if ("Employee".equalsIgnoreCase(role)) {
                int managerId = Integer.parseInt(last);
                return Optional.of(new Employee(id, name, salary, managerId));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Checks if the specified line represents a Manager.
     *
     * <p>This is determined by checking if the line starts with the string "Manager".
     * Leading whitespace is ignored.</p>
     *
     * @param line the line to check; must not be {@code null}
     * @return {@code true} if the line represents a Manager; {@code false} otherwise
     */
    public static boolean isManager(String line) {
        return line.trim().startsWith("Manager");
    }

    /**
     * Checks if the specified line represents an Employee.
     *
     * <p>This is determined by checking if the line starts with the string "Employee".
     * Leading whitespace is ignored.</p>
     *
     * @param line the line to check; must not be {@code null}
     * @return {@code true} if the line represents an Employee; {@code false} otherwise
     */
    public static boolean isEmployee(String line) {
        return line.trim().startsWith("Employee");
    }
}