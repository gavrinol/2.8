package hw_2_5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.hw_2_5.exceptions.*;
import pro.sky.hw_2_5.model.Employee;
import pro.sky.hw_2_5.service.EmployeeService;
import pro.sky.hw_2_5.service.ValidatorService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest1(String name,
                                 String surname,
                                 int department,
                                 double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add(name, surname, department, salary));
    }

    @Test
    public void addNegativeTest2(){
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(()->employeeService.add("Олеu", "Gavrin", 1, 100000));

        assertThatExceptionOfType(IncorrectSurnameException.class)
                .isThrownBy(()->employeeService.add("Олег", "Гаврин!", 1, 100000));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeNegativeTest(String name,
                                   String surname,
                                   int department,
                                   double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.remove("test", "test"));

        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.remove("test", "test"));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removePositiveTest(String name,
                                   String surname,
                                   int department,
                                   double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);

        assertThat(employeeService.remove(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findNegativeTest(String name,
                                 String surname,
                                 int department,
                                 double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.find("test", "test"));
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.find("test", "test"));
    }



    private List<Employee> generateEmployees(int size){
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employee("Name" + (char) ((int) 'a' + i), "surname"+ (char) ((int) 'a' + i), i, 10000 + i))
                .collect(Collectors.toList());
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Dmitry", "Tretyakov", 1, 60000),
                Arguments.of("Semen", "Semenov", 2, 55000),
                Arguments.of("Ivan", "Kuznezov", 1, 40000)
        );
    }
}
