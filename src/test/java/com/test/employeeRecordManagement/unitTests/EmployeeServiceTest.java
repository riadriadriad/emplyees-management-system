package com.test.employeeRecordManagement.unitTests;

import com.test.employeeRecordManagement.dtos.ContactInformationDto;
import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.entities.ContactInformation;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import com.test.employeeRecordManagement.repositories.EmployeeRepository;
import com.test.employeeRecordManagement.services.UserService;
import com.test.employeeRecordManagement.services.implementations.EmployeeServiceImpl;
import com.test.employeeRecordManagement.utils.Mapper;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployee_whenValidEmployeeDto_thenReturnSavedEmployee() throws BusinessException {
        ContactInformationDto contactInformationDto = new ContactInformationDto("123456789", "test@example.com");
        EmployeeDto employeeDto = new EmployeeDto(
                "E123",
                "John Doe",
                "Software Engineer",
                "IT",
                LocalDate.of(2023, 1, 1),
                EmploymentStatus.FULL_TIME,
                contactInformationDto,
                "123 Main St"
        );

        ContactInformation contactInformation = new ContactInformation(null,"123456789", "test@example.com");
        Employee employee = Employee.builder()
                .employeeId("E123")
                .fullName("John Doe")
                .jobTitle("Software Engineer")
                .department("IT")
                .hireDate(LocalDate.of(2023, 1, 1))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(contactInformation)
                .address("123 Main St")
                .build();

        Mockito.when(employeeRepository.existsById("E123")).thenReturn(false);
        Mockito.when(mapper.map(employeeDto, Employee.class)).thenReturn(employee);
        Mockito.when(mapper.map(contactInformationDto, ContactInformation.class)).thenReturn(contactInformation);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employeeDto);

        assertNotNull(result);
        assertEquals("E123", result.getEmployeeId());
        assertEquals("John Doe", result.getFullName());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
    }

    @Test
    void createEmployee_whenDuplicateId_thenThrowBusinessException() {
        EmployeeDto employeeDto = new EmployeeDto(
                "E123",
                "John Doe",
                "Software Engineer",
                "IT",
                LocalDate.of(2023, 1, 1),
                EmploymentStatus.FULL_TIME,
                new ContactInformationDto("123456789", "test@example.com"),
                "123 Main St"
        );

        Mockito.when(employeeRepository.existsById("E123")).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> employeeService.createEmployee(employeeDto)
        );

        assertEquals("Id is already exists", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void createEmployee_whenInvalidEmail_thenThrowBusinessException() {
        EmployeeDto employeeDto = new EmployeeDto(
                "E123",
                "John Doe",
                "Software Engineer",
                "IT",
                LocalDate.of(2023, 1, 1),
                EmploymentStatus.FULL_TIME,
                new ContactInformationDto("123456789", "invalid-email"),
                "123 Main St"
        );
        ContactInformation contactInformation = new ContactInformation(null,"123456789", "invalid-email");
        Employee employee = Employee.builder()
                .employeeId("E123")
                .fullName("John Doe")
                .jobTitle("Software Engineer")
                .department("IT")
                .hireDate(LocalDate.of(2023, 1, 1))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(contactInformation)
                .address("123 Main St")
                .build();
        Mockito.when(mapper.map(employeeDto, Employee.class)).thenReturn(employee);
        Mockito.when(employeeRepository.existsById("E123")).thenReturn(false);
        Mockito.when(employeeRepository.save(employee)).thenThrow(ConstraintViolationException.class);
        assertThrows(
                ConstraintViolationException.class,
                () -> employeeService.createEmployee(employeeDto)
        );

    }

    @Test
    void getAllEmployees_whenValidRequest_thenReturnPageOfEmployees() {
        Employee employee = Employee.builder()
                .employeeId("E123")
                .fullName("John Doe")
                .jobTitle("Software Engineer")
                .department("IT")
                .hireDate(LocalDate.of(2023, 1, 1))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(new ContactInformation(null,"123456789", "test@example.com"))
                .address("123 Main St")
                .build();

        Page<Employee> employees = new PageImpl<>(List.of(employee));
        Mockito.when(employeeRepository.findAll(PageRequest.of(0, 10))).thenReturn(employees);

        Page<Employee> result = employeeService.getAllEmployees(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("E123", result.getContent().get(0).getEmployeeId());
    }

    @Test
    void updateEmployee_whenEmployeeExists_thenReturnUpdatedEmployee() throws BusinessException {
        ContactInformationDto contactInformationDto = new ContactInformationDto("987654321", "updated@example.com");
        EmployeeDto employeeDto = new EmployeeDto(
                "E123",
                "Updated Name",
                "Senior Developer",
                "R&D",
                LocalDate.of(2020, 6, 15),
                EmploymentStatus.FULL_TIME,
                contactInformationDto,
                "456 Elm St"
        );

        Employee existingEmployee = Employee.builder()
                .employeeId("E123")
                .fullName("John Doe")
                .jobTitle("Software Engineer")
                .department("IT")
                .hireDate(LocalDate.of(2023, 1, 1))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(new ContactInformation(null,"123456789", "test@example.com"))
                .address("123 Main St")
                .build();

        Employee updatedEmployee = Employee.builder()
                .employeeId("E123")
                .fullName("Updated Name")
                .jobTitle("Senior Developer")
                .department("R&D")
                .hireDate(LocalDate.of(2020, 6, 15))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(new ContactInformation(null,"987654321", "updated@example.com"))
                .address("456 Elm St")
                .build();

        Mockito.when(employeeRepository.findById("E123")).thenReturn(Optional.of(existingEmployee));
        Mockito.when(mapper.map(employeeDto, Employee.class)).thenReturn(updatedEmployee);
        Mockito.when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee("E123", employeeDto);

        assertNotNull(result);
        assertEquals("E123", result.getEmployeeId());
        assertEquals("Updated Name", result.getFullName());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(updatedEmployee);
    }

    @Test
    void updateEmployee_whenEmployeeNotFound_thenThrowBusinessException() {
        EmployeeDto employeeDto = new EmployeeDto(
                "E123",
                "Updated Name",
                "Senior Developer",
                "R&D",
                LocalDate.of(2020, 6, 15),
                EmploymentStatus.FULL_TIME,
                new ContactInformationDto("987654321", "updated@example.com"),
                "456 Elm St"
        );

        Mockito.when(employeeRepository.findById("E123")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> employeeService.updateEmployee("E123", employeeDto)
        );

        assertEquals("Employee Not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
