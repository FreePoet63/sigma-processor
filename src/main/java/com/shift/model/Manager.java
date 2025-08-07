package com.shift.model;

/**
 * The Manager class represents a manager in the organization.
 * It extends the {@link Person} class by adding a specific department
 * to which the manager belongs.
 *
 * <p>Each manager has a unique identifier, a name, a salary,
 * and a department that delineates their area of responsibility within the company.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class Manager extends Person{
    private final String department;

    /**
     * Instantiates a new Manager with the specified properties.
     *
     * @param id         the unique identifier of the manager; must be non-negative
     * @param name       the full name of the manager; must not be null or empty
     * @param salary     the salary amount for the manager; must be non-negative
     * @param department the name of the department managed by this manager; must not be null
     * @throws IllegalArgumentException if {@code id} or {@code salary} is negative,
     *                                  or if {@code name} or {@code department} is empty
     * @throws NullPointerException     if {@code name} or {@code department} is {@code null}
     */
    public Manager(int id, String name, double salary, String department) {
        super(id, name, salary);
        if (department == null || department.isEmpty()) {
            throw new NullPointerException("Department cannot be null or empty");
        }
        this.department = department.trim();
    }

    /**
     * Returns the name of the department managed by this manager.
     *
     * @return the department name, which is never {@code null}
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Returns a string representation of this manager in CSV format.
     * The format is:
     * <pre>
     * Manager,</id>,</name>,</salaryRounded>,</department>
     * </pre>
     * where:
     * <ul>
     *   <li>{@code id} — the manager's unique identifier.</li>
     *   <li>{@code name} — the manager's full name.</li>
     *   <li>{@code salaryRounded} — the salary rounded to the nearest integer.</li>
     *   <li>{@code department} — the name of the department.</li>
     * </ul>
     *
     * @return a {@link String} representing this manager in CSV format
     */
    @Override
    public String toString() {
        return String.format("Manager,%d,%s,%.0f,%s", id, name, salary, department);
    }
}