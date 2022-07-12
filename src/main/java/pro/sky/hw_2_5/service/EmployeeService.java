package pro.sky.hw_2_5.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.hw_2_5.exceptions.EmployeeAlreadyAddedException;
import pro.sky.hw_2_5.exceptions.EmployeeNotFoundException;
import pro.sky.hw_2_5.exceptions.EmployeeStorageIsFullException;
import pro.sky.hw_2_5.exceptions.InvalidInputException;
import pro.sky.hw_2_5.model.Employee;

import java.util.*;

@Service
public class EmployeeService {

    private static final int limit = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    private String getKey(String name, String surname){
        return name + " " + surname;
    }

    public Employee add(String name,
                        String surname,
                        int department,
                        double salary) {
        if (!validateInput(name, surname)){
            throw new InvalidInputException();
        }
        Employee employee = new Employee(name, surname, department, salary);
        if (employees.containsKey(getKey(name, surname))) {
            throw new EmployeeAlreadyAddedException(); 
        }
        if (employees.size() < limit) {
            employees.put(getKey(name, surname), employee);
            return employee;
        }
        throw new InvalidInputException();
    }

    public Employee remove(String name, String surname) {
        if (!validateInput(name, surname)){
            throw new InvalidInputException();
        }
        String key = getKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        if (!validateInput(name, surname)){
            throw new IllegalArgumentException();
        }
        String key = getKey(name, surname);
        if (!employees.containsKey(getKey(name, surname))){
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees.values());
    }

    private boolean validateInput(String name, String surname){
        return !StringUtils.isAlpha(name) || !StringUtils.isAlpha(surname);
    }

}
