package service;

import com.shift.model.Department;
import com.shift.model.Employee;
import com.shift.model.Manager;
import com.shift.service.StatisticsGenerator;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the StatisticsGeneratorTest class
 *
 * @author razlivinsky
 * @since 07.08.2025
 */
public class StatisticsGeneratorTest {
    @Test
    void testGenerateStatisticsToFile() throws IOException {
        Department dept = new Department("HR");
        dept.setManager(new Manager(1, "Jane", 5000, "HR"));
        dept.addEmployee(new Employee(101, "A", 1000, 1));
        dept.addEmployee(new Employee(102, "B", 2000, 1));
        dept.addEmployee(new Employee(103, "C", 3000, 1));

        Map<String, Department> map = Map.of("HR", dept);
        String path = "outputtest_stat.txt";
        StatisticsGenerator.generateStatistics(map, "file", path);

        File f = new File(path);
        assertTrue(f.exists());

        List<String> lines = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = r.readLine()) != null) {
                lines.add(l);
            }
        }

        assertEquals("department, min, max, mid", lines.get(0));
        assertTrue(lines.get(1).startsWith("HR,1000,00,3000,00,2000,00"));

        f.delete();
    }
}