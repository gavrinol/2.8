package hw_2_5.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.hw_2_5.exceptions.EmployeeNotFoundException;
import pro.sky.hw_2_5.model.Employee;
import pro.sky.hw_2_5.service.DepartmentService;
import pro.sky.hw_2_5.service.EmployeeService;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Vyacheslav", "Vasiliev", 1, 80000),
                new Employee("Sergey", "Kruglov",  2, 100000),
                new Employee("Nikita", "Kvadratov", 1, 90000),
                new Employee("Leonid", "Utesov",  2, 120000),
                new Employee("Nickolai", "Nikolaev", 2, 80000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    };

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.employeeWithMaxSalary(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest(){
        assertThatExceptionOfType(EmployeeNotFoundException.class)
            .isThrownBy(()-> departmentService.employeeWithMaxSalary(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeWithMinSalaryPositiveTest(int department, Employee expected) {
        assertThat(departmentService.employeeWithMinSalary(department)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> departmentService.employeeWithMinSalary(3));
    }


    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Nikita", "Kvadratov", 1, 90000)),
                Arguments.of(2, new Employee("Leonid", "Utesov",  2, 120000))
        );
    }

    public static Stream<Arguments> employeeWithMinSalaryParams(){
        return Stream.of(
                Arguments.of(1, new Employee("Vyacheslav", "Vasiliev", 1, 80000)),
                Arguments.of(2, new Employee("Nickolai", "Nikolaev", 2, 80000))
        );
    }
}

