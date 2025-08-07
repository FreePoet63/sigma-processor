package com.shift.logic;

import com.shift.model.Department;
import com.shift.model.Employee;
import com.shift.model.Manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The DepartmentBuilder class is responsible for building a mapping of departments based on the given
 * managers and employees. It aggregates managers and their respective employees into department structures.
 * It also keeps track of any errors encountered during the building process, such as when an employee
 * references a non-existent manager.
 *
 * <p>This class assumes that each manager is uniquely identified by their ID, and each employee
 * is associated with a manager through their manager ID. If an employee's manager ID does not match
 * any existing manager, the employee information is added to the error list.</p>
 *
 * <p>The departments are stored in a tree map, which ensures that the departments are sorted by their names.</p>
 *
 * @author razlivinsky
 * @since 06.08.2025
 */
public class DepartmentBuilder {
    private final List<Manager> managers;
    private final List<Employee> employees;
    private final List<String> errorLines;

    /**
     * Instantiates a new DepartmentBuilder with the specified lists of managers, employees, and error lines.
     *
     * @param managers   A list of Manager objects, where each manager has a unique ID and a department name.
     * @param employees  A list of Employee objects, where each employee has a manager ID that associates them with a manager.
     * @param errorLines A list of error lines that will be populated with error messages during the
     *                   department building process if any inconsistencies are found.
     */
    public DepartmentBuilder(List<Manager> managers, List<Employee> employees, List<String> errorLines) {
        this.managers = managers;
        this.employees = employees;
        this.errorLines = errorLines;
    }

    /**
     * Builds a map of departments, associating each department with its manager and the employees
     * that belong to that department.
     *
     * <p>The method iterates through the list of managers to create a mapping of department names
     * to Manager objects. Then, it iterates through the list of employees, retrieving their corresponding
     * manager. If the manager is found, the employee is added to the respective department. If not,
     * the employee's details are recorded in the error list.</p>
     *
     * @return A map where the key is the department name (String) and the value is the corresponding
     *         Department object containing the assigned manager and employees.
     */
    public Map<String, Department> buildDepartments() {
        Map<Integer, Manager> managerById = new HashMap<>();
        Map<String, Department> departments = new TreeMap<>();

        for (Manager manager : managers) {
            managerById.put(manager.getId(), manager);
            departments.computeIfAbsent(manager.getDepartment(), Department::new).setManager(manager);
        }

        for (Employee employee : employees) {
            Manager manager = managerById.get(employee.getManagerId());
            if (manager != null) {
                String deptName = manager.getDepartment();
                Department dept = departments.get(deptName);
                if (dept != null) {
                    dept.addEmployee(employee);
                } else {
                    errorLines.add(employee.toString());
                }
            } else {
                errorLines.add(employee.toString());
            }
        }
        return departments;
    }
}