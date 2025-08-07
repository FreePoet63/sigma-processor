package com.shift.model;

/**
 * The Person class serves as an abstract base class representing a person
 * in the organization. It encapsulates common attributes shared by all personnel,
 * including an identifier, name, and salary.
 *
 * <p>This class is an abstract type and cannot be instantiated directly.
 * It provides getter methods for accessing its properties, which can be utilized in
 * derived classes, such as {@link Employee} and {@link Manager}.</p>
 *
 * <p>This class ensures that the name is trimmed of whitespace during instantiation,
 * and stores salary as a double to accommodate various salary formats.</p>
 *
 * <p>Subclasses should provide specific implementations as needed.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public abstract class Person {
    protected int id;
    protected String name;
    protected double salary;

    /**
     * Constructs a new Person with the specified identifier, name, and salary.
     *
     * @param id     the unique identifier of the person; must be non-negative
     * @param name   the name of the person; must not be null or empty
     * @param salary the salary of the person; must be non-negative
     * @throws IllegalArgumentException if {@code id} is negative or {@code salary} is negative
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Person(int id, String name, double salary) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative.");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Name cannot be null or empty.");
        }
        this.id = id;
        this.name = name.trim();
        this.salary = salary;
    }

    /**
     * Gets the unique identifier of this person.
     *
     * @return the ID of the person
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this person.
     *
     * @return the name of the person, which is never null
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the salary of this person.
     *
     * @return the salary amount as a double
     */
    public double getSalary() {
        return salary;
    }
}