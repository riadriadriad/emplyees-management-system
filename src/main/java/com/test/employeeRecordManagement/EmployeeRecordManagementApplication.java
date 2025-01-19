package com.test.employeeRecordManagement;


import com.test.employeeRecordManagement.dtos.ContactInformationDto;
import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.entities.AppUser;
import com.test.employeeRecordManagement.entities.ContactInformation;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.entities.enums.Role;
import com.test.employeeRecordManagement.repositories.EmployeeRepository;
import com.test.employeeRecordManagement.repositories.UserRepository;
import com.test.employeeRecordManagement.security.RSAKeys;
import com.test.employeeRecordManagement.services.EmployeeService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeys.class)
@EnableWebSecurity
public class EmployeeRecordManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeRecordManagementApplication.class, args);
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }
   @Bean
CommandLineRunner commandLineRunner(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
    userRepository.save(AppUser.builder().fullName("Abdelghani Riad").password(passwordEncoder.encode("1234")).username("riad").department("dev").role(Role.ADMIN).build());
    userRepository.save(AppUser.builder().fullName("Said ").password(passwordEncoder.encode("1234")).username("said").department("HR").role(Role.HR).build());
    userRepository.save(AppUser.builder().fullName("mounir ").password(passwordEncoder.encode("1234")).username("mounir").department("dev   ").role(Role.MANAGER).build());
    return args -> {
        Employee[] employees = {
            new Employee("id1", "Abdelghani Riad", "Java Developer", "dev", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id2", "Omar mouslih", "PlSql Developer", "dev", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id3", "Khalid jenani", "Assistant", "dev", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id4", "Abdellah smini", "Dev", "dev", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id5", "Said jabir", "HR Manager", "HR", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id6", "Asmaa Kholidi", "HR Assistant", "HR", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id7", "Jamal kouchi", "JavaScript Developer", "dev", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id8", "Fatima hdioued", "CEO", "admin", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id9", "Amine Idrissi", "CTO", "admin", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca"),
            new Employee("id10", "Adil kouit", "Markenting manager", "Marketing", LocalDate.now(), EmploymentStatus.FULL_TIME, new ContactInformation(null,"+09999999999", "riad@gmail.com"), "Casablanca")
        };

        for (Employee employee : employees) {
            employeeRepository.save(employee);
        }
    };
}

}
