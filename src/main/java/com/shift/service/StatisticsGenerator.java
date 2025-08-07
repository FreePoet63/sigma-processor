package com.shift.service;

import com.shift.model.Department;
import com.shift.model.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * The StatisticsGenerator class is responsible for generating statistics
 * about departments and their associated employees. It calculates the minimum,
 * maximum, and average salaries for each department and provides options to output
 * these statistics to a file or the console.
 *
 * <p>The class utilizes the {@link Department} and {@link Employee} classes to
 * retrieve and process employee salary data, and it represents department statistics
 * through a nested {@link Stat} class.</p>
 *
 * <p>Results can be organized and sorted by department name before outputting.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class StatisticsGenerator {

    /**
     * The Stat class represents statistical information related to a department.
     * It holds the department name, minimum, maximum, and average salary.
     */
    private static class Stat {
        String department;
        double min;
        double max;
        double avg;

        /**
         * Instantiates a new {@code Stat} object with the specified values.
         *
         * @param department the name of the department; must not be null
         * @param min        the minimum salary; must be non-negative
         * @param max        the maximum salary; must be non-negative
         * @param avg        the average salary; must be non-negative
         * @throws NullPointerException     if {@code department} is {@code null}
         * @throws IllegalArgumentException if {@code min}, {@code max}, or {@code avg} is negative
         */
        Stat(String department, double min, double max, double avg) {
            if (department == null) {
                throw new NullPointerException("Department cannot be null");
            }
            if (min < 0 || max < 0 || avg < 0) {
                throw new IllegalArgumentException("Salaries cannot be negative.");
            }
            this.department = department;
            this.min = min;
            this.max = max;
            this.avg = avg;
        }

        /**
         * Returns a string representation of this Stat in CSV format.
         * The format is:
         * <pre>
         * department,min,max,avg
         * </pre>
         *
         * @return a {@link String} representing the statistical data in CSV format
         */
        @Override
        public String toString() {
            return String.format("%s,%.2f,%.2f,%.2f", department, min, max, avg);
        }
    }

    /**
     * Generates statistics for each department, calculating the minimum, maximum,
     * and average salaries of employees. The results can be output to a file or
     * printed to the console.
     *
     * <p>The method iterates through each department, gathers salary data, calculates
     * necessary statistics, and organizes the data for output based on the specified mode.</p>
     *
     * @param departments a map of department names to their corresponding {@link Department} objects; must not be null
     * @param outputMode  the desired output mode; can be "file" for file output or any other string for console output
     * @param outputPath  the file path where statistics should be written if outputMode is "file"; must be non-empty if used
     * @throws NullPointerException if {@code departments} is {@code null}
     */
    public static void generateStatistics(Map<String, Department> departments, String outputMode, String outputPath) {
        List<Stat> stats = new ArrayList<>();

        for (Department dept : departments.values()) {
            List<Employee> employees = dept.getEmployees();
            List<Double> salaries = new ArrayList<>();

            for (Employee e : employees) {
                salaries.add(e.getSalary());
            }

            double min, max, avg;

            if (!salaries.isEmpty()) {
                min = roundUp(Collections.min(salaries));
                max = roundUp(Collections.max(salaries));
                avg = roundUp(salaries.stream().mapToDouble(Double::doubleValue).average().orElse(0));
            } else {
                min = max = avg = 0.0;
            }
            stats.add(new Stat(dept.getName(), min, max, avg));
        }
        stats.sort(Comparator.comparing(stat -> stat.department));

        if ("file".equalsIgnoreCase(outputMode)) {
            writeToFile(stats, outputPath);
        } else {
            writeToConsole(stats);
        }
    }

    /**
     * Writes the statistics to the console in a formatted manner.
     *
     * <p>The output format includes headers for department, minimum salary, maximum salary, and average salary.</p>
     *
     * @param stats the list of statistics to print; must not be null
     * @throws NullPointerException if {@code stats} is {@code null}
     */
    private static void writeToConsole(List<Stat> stats) {
        System.out.println("department, min, max, mid");
        for (Stat stat : stats) {
            System.out.println(stat);
        }
    }

    /**
     * Writes the statistics to a specified file.
     *
     * <p>The method creates or overwrites a file at the specified output path and writes
     * statistics in a CSV format. If the path is null or empty, an error message is printed.</p>
     *
     * @param stats the list of statistics to write to the file; must not be null
     * @param path  the path where the statistics should be written; must not be empty
     * @throws NullPointerException if {@code stats} is {@code null} or
     *                              if {@code path} is {@code null}
     */
    private static void writeToFile(List<Stat> stats, String path) {
        if (path == null || path.isEmpty()) {
            System.err.println("Error: no path specified for --output=file");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(path, false))) {
            writer.println("department, min, max, mid");
            for (Stat stat : stats) {
                writer.println(stat);
            }
        } catch (IOException e) {
            System.err.println("Failed to write statistics to file: " + e.getMessage());
        }
    }

    /**
     * Rounds a given salary amount to two decimal places using half-up rounding mode.
     *
     * @param value the salary amount to round
     * @return the rounded salary
     */
    private static double roundUp(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}