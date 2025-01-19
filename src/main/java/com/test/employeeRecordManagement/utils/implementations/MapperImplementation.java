package com.test.employeeRecordManagement.utils.implementations;

import com.test.employeeRecordManagement.dtos.EmployeeReducedDto;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.utils.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class MapperImplementation implements Mapper {
    private final ModelMapper mapper=new ModelMapper().registerModule(new RecordModule());
    {
        mapper.addConverter(new Converter<Employee, EmployeeReducedDto>() {
            @Override
            public EmployeeReducedDto convert(MappingContext<Employee, EmployeeReducedDto> mappingContext) {
                Employee source=mappingContext.getSource();
                return new EmployeeReducedDto(source.getEmployeeId(),source.getFullName(),source.getJobTitle(),source.getDepartment());
            }
        });
    }
    @Override
    public <T, U> T map(U o, Class<T> typeToMap) {
        return mapper.map(o,typeToMap);
    }
}
