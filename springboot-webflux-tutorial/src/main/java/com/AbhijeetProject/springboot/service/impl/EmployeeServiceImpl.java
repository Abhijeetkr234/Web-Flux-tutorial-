package com.AbhijeetProject.springboot.service.impl;

import com.AbhijeetProject.springboot.dto.EmployeeDto;
import com.AbhijeetProject.springboot.entity.Employee;
import com.AbhijeetProject.springboot.mapper.EmployeeMapper;
import com.AbhijeetProject.springboot.repository.EmployeeRepository;
import com.AbhijeetProject.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {

        // convert EmployeeDto into Employee Entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
         Mono<Employee> saveEmployee = employeeRepository.save(employee);
        return saveEmployee.map( (employeeEntity) -> EmployeeMapper.mapToEmployeeDto(employeeEntity));
    }

    @Override
    public Mono<EmployeeDto>getEmployee(String employeeId) {

        Mono<Employee> saveEmployee = employeeRepository.findById(employeeId);
        return saveEmployee.map((employee) -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployee() {
         Flux<Employee> employeeFlux = employeeRepository.findAll();
        return employeeFlux.map( (employee) -> EmployeeMapper.mapToEmployeeDto(employee)).switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {

       Mono<Employee> employeeMono = employeeRepository.findById(employeeId);

       Mono<Employee> updatedEmployee = employeeMono.flatMap((existingEmployee) -> {
           existingEmployee.setFirstName(employeeDto.getFirstName());
           existingEmployee.setLastName(employeeDto.getLastName());
           existingEmployee.setEmail(employeeDto.getEmail());

           return employeeRepository.save(existingEmployee);
       });
        return updatedEmployee.map( (employee) -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {

        return employeeRepository.deleteById(employeeId);
    }


}
