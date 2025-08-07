package com.shift.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Department class represents an organizational unit within a company.
 * It contains information about the department's name, its manager, and the list of
 * employees working within that department.
 *
 * <p>A department can have only one manager, and employees can be added or retrieved.
 * This class ensures that a department can only operate if a valid manager is assigned.</p>
 *
 * <p>The name of the department must be provided during construction and cannot be null.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class Department {
    private final String name;
    private Manager manager;
    private final List<Employee> employees = new ArrayList<>();

    /**
     * Instantiates a new Department with the specified name.
     * Any leading or trailing whitespace in the name will be removed.
     *
     * @param name the name of the department; must be non-null
     * @throws NullPointerException if {@code name} is {@code null}
     */
    public Department(String name) {
        if (name == null) {
            throw new NullPointerException("Department name cannot be null");
        }
        this.name = name.trim();
    }

    /**
     * Assigns a {@link Manager} to this department. If a manager is already set,
     * this method will replace the existing manager.
     *
     * @param manager the manager to assign to this department; must be non-null
     * @throws NullPointerException if {@code manager} is {@code null}
     */
    public void setManager(Manager manager) {
        if (manager == null) {
            throw new NullPointerException("Manager cannot be null");
        }
        this.manager = manager;
    }

    /**
     * Retrieves the current {@link Manager} assigned to this department.
     *
     * @return the {@link Manager} object; may be {@code null} if no manager has been set
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Adds an {@link Employee} to this department's list of employees.
     * The employee is stored in insertion order.
     *
     * @param employee the employee to add; must be non-null
     * @throws NullPointerException if {@code employee} is {@code null}
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new NullPointerException("Employee cannot be null");
        }
        employees.add(employee);
    }

    /**
     * Retrieves the name of this department.
     *
     * @return the name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves an unmodifiable view of the employees in this department.
     * This prevents external code from modifying the internal list directly.
     *
     * @return an unmodifiable {@link List} of {@link Employee} objects; never {@code null}
     */
    public List<Employee> getEmployees() {
        return List.copyOf(employees);
    }

    /**
     * Checks whether this department has a valid manager assigned.
     *
     * @return {@code true} if a valid manager has been set; {@code false} otherwise
     */
    public boolean hasValidManager() {
        return manager != null;
    }
}