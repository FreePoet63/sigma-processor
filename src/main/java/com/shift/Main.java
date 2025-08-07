package com.shift;

import com.shift.logic.DepartmentBuilder;
import com.shift.logic.DepartmentWriter;
import com.shift.model.Department;
import com.shift.parser.SbFileReader;
import com.shift.service.StatisticsGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * The Main class serves as the entry point of the application.
 * It is responsible for parsing command-line arguments, reading input files,
 * processing department and employee data, and generating output in the specified formats.
 *
 * <p>This class orchestrates the interaction between different components of
 * the application, including reading files, managing department data, and
 * outputting statistics or errors.</p>
 *
 * <p>It handles the following command-line arguments:</p>
 * <ul>
 *   <li><b>--sort=<i>field</i></b>: Specifies the field to sort by (e.g., "name" or "salary").</li>
 *   <li><b>--order=<i>order</i></b>: Specifies the sort order ("asc" for ascending or "desc" for descending).</li>
 *   <li><b>--stat</b>: If specified, generates statistics for the departments.</li>
 *   <li><b>--output=<i>type</i></b>: Specifies the output mode - "console" for standard output or "file" for file output.</li>
 *   <li><b>--path=<i>path</i></b>: Specifies the output path for files, required if output mode is "file".</li>
 * </ul>
 *
 * <p>Any parsing errors or invalid parameters will cause the application to print an error message and terminate.</p>
 *
 * <p>When invoked, it performs the following steps:</p>
 * <ol>
 *   <li>Parses command-line arguments.</li>
 *   <li>Reads .sb files containing employee and manager data from the "input" directory.</li>
 *   <li>Builds department structures based on the read data.</li>
 *   <li>Clears the output directory and writes department information to files or the console.</li>
 *   <li>Records any errors encountered during file reading.</li>
 *   <li>Generates and outputs statistics if requested.</li>
 * </ol>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class Main {

    /**
     * The main method acts as the application's entry point.
     *
     * <p>This method is responsible for orchestrating the overall flow of the application,
     * including argument parsing, reading input files, building departments,
     * writing outputs, and generating statistics.</p>
     *
     * @param args the command-line arguments; must not be {@code null}
     * @throws NullPointerException if {@code args} is {@code null}
     */
    public static void main(String[] args) {
        Map<String, String> params = parseArgs(args);

        // Sorting parameters
        String sort = params.get("sort");
        String order = params.get("order");
        if (order != null && sort == null) {
            System.err.println("Error: --order specified without --sort");
            return;
        }

        if (sort != null && !(sort.equals("name") || sort.equals("salary"))) {
            System.err.println("Error: Invalid sort format: " + sort);
            return;
        }

        if (order != null && !(order.equals("asc") || order.equals("desc"))) {
            System.err.println("Error: Incorrect sort order: " + order);
            return;
        }

        boolean stat = params.containsKey("stat");
        String output = params.getOrDefault("output", "console");
        String path = params.get("path");

        if ("file".equals(output) && (path == null || path.isEmpty())) {
            System.err.println("Error: path not specified --path для вывода в файл");
            return;
        }

        if (!"console".equals(output) && !"file".equals(output)) {
            System.err.println("Error: unknown type --output: " + output);
            return;
        }

        // 1. Reading from input/
        SbFileReader reader = new SbFileReader();
        reader.readAllSbFiles("input");

        // 2. Department structuring
        DepartmentBuilder builder = new DepartmentBuilder(reader.getManagers(), reader.getEmployees(), reader.getErrorLines());
        Map<String, Department> departments = builder.buildDepartments();

        // 3. Cleaning and writing output files
        DepartmentWriter.clearOutputDirectory();
        DepartmentWriter.writeDepartments(departments, sort, order);

        // 4. Errors
        DepartmentWriter.writeErrors(reader.getErrorLines());

        // 5. Statistic
        if (stat) {
            StatisticsGenerator.generateStatistics(departments, output, path);
        }
    }

    /**
     * Parses the command-line arguments into a map for easier access.
     *
     * <p>The method processes recognized arguments and maps them to their respective keys.
     * Unrecognized arguments will result in an error message.</p>
     *
     * @param args the command-line arguments to parse; must not be {@code null}
     * @return a {@link Map} containing the parsed arguments
     * @throws NullPointerException if {@code args} is {@code null}
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                map.put("sort", arg.substring(arg.indexOf('=') + 1).trim());
            } else if (arg.startsWith("--order=")) {
                map.put("order", arg.substring("--order=".length()).trim());
            } else if (arg.equals("--stat")) {
                map.put("stat", "true");
            } else if (arg.startsWith("--output=") || arg.startsWith("-o=")) {
                map.put("output", arg.substring(arg.indexOf('=') + 1).trim());
            } else if (arg.startsWith("--path=")) {
                map.put("path", arg.substring("--path=".length()).trim());
            } else {
                System.err.println("Error: unknown parameter: " + arg);
                System.exit(1);
            }
        }
        return map;
    }
}