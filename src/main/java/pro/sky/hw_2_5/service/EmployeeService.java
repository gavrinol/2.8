package pro.sky.hw_2_5.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.hw_2_5.exceptions.EmployeeAlreadyAddedException;
import pro.sky.hw_2_5.exceptions.EmployeeNotFoundException;
import pro.sky.hw_2_5.exceptions.EmployeeStorageIsFullException;
import pro.sky.hw_2_5.exceptions.InvalidInputException;
import pro.sky.hw_2_5.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final List<Employee> employees = new ArrayList<>();

    private final ValidatorService validatorService;

    public  EmployeeService (ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public Employee add(String name,
                        String surname,
                        int department,
                        double salary) {
        Employee employee = new Employee(
                validatorService.validateName(name),
                validatorService.validateSurname(surname),
                department,
                salary
        );
        if (employees.contains(employee)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name, String surname) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getName().equals(name) && emp.getLastName().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employees.remove(employee);
        return  employee;
    }

    public Employee find(String name, String surname) {
        return employees.stream()
                .filter(emp -> emp.getName().equals(name) && emp.getLastName().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees);
    }

}
