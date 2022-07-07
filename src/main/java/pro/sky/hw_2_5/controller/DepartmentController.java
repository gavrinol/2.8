package pro.sky.hw_2_5.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hw_2_5.model.Employee;
import pro.sky.hw_2_5.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalary(@RequestParam("departmentId") int department){
        return departmentService.findEmployeeWithMaxSalary(department);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalary(@RequestParam("departmentId") int department){
        return departmentService.findEmployeeWithMinSalary(department);
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> findEmployeeByDepartment(@RequestParam("departmentId") int department){
        return departmentService.findEmployeeByDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmployee(@RequestParam("departmentId") int department){
        return departmentService.findEmployee(department);
    }
}
