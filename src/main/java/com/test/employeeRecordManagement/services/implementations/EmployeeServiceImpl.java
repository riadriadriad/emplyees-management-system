package com.test.employeeRecordManagement.services.implementations;

import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.dtos.EmployeeReducedDto;
import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.entities.AppUser;
import com.test.employeeRecordManagement.entities.ContactInformation;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import com.test.employeeRecordManagement.repositories.EmployeeRepository;
import com.test.employeeRecordManagement.services.EmployeeService;
import com.test.employeeRecordManagement.services.UserService;
import com.test.employeeRecordManagement.utils.EmployeeSpecifications;
import com.test.employeeRecordManagement.utils.Mapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
@Transactional(rollbackOn = Exception.class)
@Service
public class EmployeeServiceImpl  implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final Mapper mapper;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Mapper mapper, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) throws BusinessException {
        if(this.employeeRepository.existsById(employeeDto.employeeId())) throw new BusinessException("Id is already exists", HttpStatus.CONFLICT);
        Employee employee=mapper.map(employeeDto,Employee.class);
        ContactInformation contactInformation=mapper.map(employeeDto.contactInformationDto(),ContactInformation.class);
        employee.setContactInformation(contactInformation);
        logger.info("Employee created with id: "+employee.getEmployeeId()+ " by user: "+userService.getCurrentUser().getUsername());
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAllEmployees(int page, int size) {
        return this.employeeRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Employee updateEmployee(String employeeId, EmployeeDto employeeDto) throws BusinessException {
        Employee employee=getEmployeeById(employeeId);
        employee=mapper.map(employeeDto,Employee.class);
        ContactInformation contactInformation=mapper.map(employeeDto.contactInformationDto(),ContactInformation.class);
        employee.setContactInformation(contactInformation);
        employee.setEmployeeId(employeeId);
        logger.info("Employee updated with id: "+employee.getEmployeeId()+ " by user: "+userService.getCurrentUser().getUsername());
        return this.employeeRepository.save(employee);
    }

    @Override
    public Response deleteEmployee(String employeeId) throws BusinessException {
        Employee employee=getEmployeeById(employeeId);
        this.employeeRepository.delete(employee);
        logger.info("Employee deleted with id: "+employee.getEmployeeId()+ " by user: "+userService.getCurrentUser().getUsername());
        return new Response();
    }

    @Override
    public Page<Employee> searchEmployee(int page,int size,String kw) {
        return this.employeeRepository.findAll(EmployeeSpecifications.searchByKw(kw),PageRequest.of(page,size));
    }

    @Override
    public Page<Employee> filterEmployees(EmploymentStatus employmentStatus, String department, LocalDate hireDateFrom,LocalDate hireDateTo,int page,int size) {
        Specification<Employee> specification=EmployeeSpecifications.hasDepartment(department).
                and(EmployeeSpecifications.hasEmploymentStatus(employmentStatus)).
                and(EmployeeSpecifications.hasHireDate(hireDateFrom,hireDateTo));
        return this.employeeRepository.findAll(specification,PageRequest.of(page,size));
    }


    @Override
    public Employee getEmployeeById(String id) throws BusinessException {
        return this.employeeRepository.findById(id).orElseThrow(()->new BusinessException("Employee Not found",HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<EmployeeReducedDto> getAllEmployeesOfDepartment(String department, int page, int size) {
        return this.employeeRepository.findAll(EmployeeSpecifications.hasDepartment(department),PageRequest.of(page,size))
                .map(emp->mapper.map(emp,EmployeeReducedDto.class));
    }
    @Override
    public EmployeeReducedDto updateEmployee(String id,String department,EmployeeReducedDto employeeReducedDto) throws BusinessException {
        Employee employee=this.employeeRepository.findOne(EmployeeSpecifications.hasIdAndDepartment(id,department))
                .orElseThrow(()->new BusinessException("Employee Not found",HttpStatus.NOT_FOUND));
            employee.setJobTitle(employeeReducedDto.jobTitle());
        employee.setDepartment(employeeReducedDto.department());
        employee.setFullName(employeeReducedDto.fullName());
        logger.info("Employee updated with id: "+employee.getEmployeeId()+ " by user: "+userService.getCurrentUser().getUsername());
        return mapper.map(this.employeeRepository.save(employee),EmployeeReducedDto.class);
    }

    @Override
    public Page<EmployeeReducedDto> searchEmployee(String department, int page, int size, String kw) {
        Specification<Employee> specification=EmployeeSpecifications.searchByKw(kw).and(EmployeeSpecifications.hasStrictDepartment(department));
        logger.info("Employee searched with keyword: "+kw+ " by user: "+userService.getCurrentUser().getUsername());
        return this.employeeRepository.findAll(specification,PageRequest.of(page,size)).map(emp->mapper.map(emp,EmployeeReducedDto.class));
    }

}
