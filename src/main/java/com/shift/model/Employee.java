package com.shift.model;

/**
 * The Employee class represents an employee in the organization.
 * It extends the {@link Person} class by adding a unique {@code managerId}
 * which indicates the ID of the manager to whom the employee reports.
 *
 * <p>Each employee has an identifier, a name, a salary, and a reference to their manager.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class Employee extends Person{
    public final int managerId;

    /**
     * Instantiates a new Employee with the specified properties.
     *
     * @param id        the unique identifier of the employee; must be non-negative
     * @param name      the full name of the employee; must not be null or empty
     * @param salary    the salary amount for the employee; must be non-negative
     * @param managerId the identifier of the manager this employee reports to;
     *                  must be non-negative
     * @throws IllegalArgumentException if {@code id}, {@code salary}, or {@code managerId} is negative,
     *                                  or if {@code name} is empty
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Employee(int id, String name, double salary, int managerId) {
        super(id, name, salary);
        if (managerId < 0) {
            throw new IllegalArgumentException("Manager ID cannot be negative.");
        }
        this.managerId = managerId;
    }

    /**
     * Returns the identifier of the manager this employee reports to.
     *
     * @return the {@code managerId}, which corresponds to the ID of a {@link Manager}
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * Returns a string representation of this employee in CSV format.
     * The format is:
     * <pre>
     * Employee,</id>,</name>,</salaryRounded>,</managerId>
     * </pre>
     * where:
     * <ul>
     *   <li>{@code id} — the employee's unique identifier.</li>
     *   <li>{@code name} — the employee's full name.</li>
     *   <li>{@code salaryRounded} — the salary rounded to the nearest integer.</li>
     *   <li>{@code managerId} — the ID of the manager.</li>
     * </ul>
     *
     * @return a {@link String} representing this employee in CSV format
     */
    @Override
    public String toString() {
        return String.format("Employee,%d,%s,%.0f,%d", id, name, salary, managerId);
    }
}