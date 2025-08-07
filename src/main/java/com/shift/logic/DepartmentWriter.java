package com.shift.logic;

import com.shift.model.Department;
import com.shift.model.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The DepartmentWriter class is responsible for writing department information and error logs to files.
 * It handles the formatting and sorting of employee data within departments and manages the output directory.
 *
 * <p>This class provides methods to write department details to individual files, write errors encountered during processing,
 * and clear the output directory of existing files.</p>
 *
 * <p>Sorting of employee data can be configured based on specified fields and orders.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class DepartmentWriter {

    /**
     * Writes department information to files.
     *
     * <p>This method creates an output file for each department, where the manager's information is written first,
     * followed by the employees sorted according to the specified sort field and order.</p>
     *
     * @param departments a map of department names to their corresponding {@link Department} objects.
     * @param sortField the field by which to sort employees within the department. Supported values are "name" and "salary".
     * @param sortOrder the order in which the employees should be sorted. Acceptable values are "asc" and "desc".
     */
    public static void writeDepartments(Map<String, Department> departments, String sortField, String sortOrder) {
        Comparator<Employee> comparator = getComparator(sortField, sortOrder);

        for (Department dept : departments.values()) {
            if (!dept.hasValidManager()) continue;

            List<Employee> sortedEmployees = new ArrayList<>(dept.getEmployees());
            if (comparator != null) {
                sortedEmployees.sort(comparator);
            }

            String fileName = "output/" + dept.getName() + ".sb";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
                writer.println(dept.getManager().toString());
                for (Employee e : sortedEmployees) {
                    writer.println(e.toString());
                }
            } catch (IOException e) {
                System.err.println("Failed to write to file " + fileName + ": " + e.getMessage());
            }
        }
    }

    /**
     * Returns a Comparator for sorting employees based on the specified field and order.
     *
     * @param sortField the field for sorting. Valid options: "name", "salary".
     * @param sortOrder determines the sort order. Valid options: "asc" for ascending and "desc" for descending.
     * @return a Comparator for Employee objects based on the specified criteria.
     * @throws IllegalArgumentException if the sortField is not supported.
     */
    private static Comparator<Employee> getComparator(String sortField, String sortOrder) {
        if (sortField == null) return null;

        Comparator<Employee> comparator;
        if ("name".equalsIgnoreCase(sortField)) {
            comparator = Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
        } else if ("salary".equalsIgnoreCase(sortField)) {
            comparator = Comparator.comparingDouble(Employee::getSalary);
        } else {
            throw new IllegalArgumentException("Unsupported sort type: " + sortField);
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    /**
     * Writes error lines to an error log file.
     *
     * <p>This method creates or overwrites an error log file and writes each error line to it.</p>
     *
     * @param errorLines the list of error messages to be logged.
     */
    public static void writeErrors(List<String> errorLines) {
        if (errorLines.isEmpty()) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter("output/error.log", false))) {
            for (String line : errorLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to write error.log: " + e.getMessage());
        }
    }

    /**
     * Clears the output directory by deleting all department and error log files.
     *
     * <p>This method removes all files with the .sb extension and the error log from the output directory.
     * If the directory does not exist, it will be created.</p>
     */
    public static void clearOutputDirectory() {
        Path outputDir = Paths.get("output");
        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(outputDir, "*.sb")) {
                for (Path file : stream) {
                    Files.deleteIfExists(file);
                }
            }
            Files.deleteIfExists(outputDir.resolve("error.log"));
        } catch (IOException e) {
            System.err.println("Failed to clear folder output/: " + e.getMessage());
        }
    }
}