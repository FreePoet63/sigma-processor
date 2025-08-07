package com.shift.parser;

import com.shift.model.Employee;
import com.shift.model.Manager;
import com.shift.model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code SbFileReader} class is responsible for reading and processing
 * .sb files from a specified directory. It extracts information about
 * managers and employees, adding them to respective lists, and also
 * collects any lines that contain errors during parsing.
 *
 * <p>This class utilizes the {@link RecordParser} to convert lines from
 * the .sb files into {@link Person} objects, which can be either
 * {@link Manager} or {@link Employee} instances.</p>
 *
 * <p>It handles file reading errors and can provide the lists of managers,
 * employees, and any error lines encountered during the reading process.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class SbFileReader {
    private final List<Manager> managers = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<String> errorLines = new ArrayList<>();

    /**
     * Reads all .sb files from the specified directory and processes each file.
     *
     * <p>This method scans the provided directory for files with the .sb extension
     * and processes each file to extract managers and employees.</p>
     *
     * @param directory the path to the directory containing .sb files; must not be {@code null}
     * @throws NullPointerException if {@code directory} is {@code null}
     */
    public void readAllSbFiles(String directory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.sb")) {
            for (Path entry : stream) {
                processFile(entry);
            }
        } catch (IOException e) {
            System.err.println("Failed to read files: " + e.getMessage());
        }
    }

    /**
     * Processes a single .sb file to extract Manager and Employee information.
     *
     * <p>This method reads the file line by line and uses the {@link RecordParser}
     * to parse each line into a {@link Person}. If a line represents a Manager,
     * it is added to the manager list; if it represents an Employee, it is added
     * to the employee list. If parsing fails, the line is added to the error list.</p>
     *
     * @param path the path to the .sb file to process; must not be {@code null}
     * @throws NullPointerException if {@code path} is {@code null}
     */
    private void processFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Person> personOpt = RecordParser.parseLine(line);
                if (personOpt.isPresent()) {
                    Person person = personOpt.get();
                    if (person instanceof Manager) {
                        managers.add((Manager) person);
                    } else if (person instanceof Employee) {
                        employees.add((Employee) person);
                    }
                } else {
                    errorLines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read file " + path.getFileName() + ": " + e.getMessage());
        }
    }

    /**
     * Retrieves the list of managers processed from the .sb files.
     *
     * @return a {@link List} of {@link Manager} instances; never {@code null}
     */
    public List<Manager> getManagers() {
        return managers;
    }

    /**
     * Retrieves the list of employees processed from the .sb files.
     *
     * @return a {@link List} of {@link Employee} instances; never {@code null}
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Retrieves the list of error lines encountered during file processing.
     *
     * @return a {@link List} of error lines; never {@code null}
     */
    public List<String> getErrorLines() {
        return errorLines;
    }
}