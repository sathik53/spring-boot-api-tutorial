package com.satya.demo.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.demo.employee.exception.ResourceNotFoundException;
import com.satya.demo.employee.model.Employee;
import com.satya.demo.employee.repository.EmployeeRepository;

@RequestMapping("/api/emp")
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;

	@GetMapping("/all")
	public List<Employee> getEmployees() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getAllId(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
		Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + id));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/")
	public Employee createUser(@Valid @RequestBody Employee employee) {
		
		System.out.println("emp Post Method: "+employee);
		return repository.save(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@Valid @PathVariable(value = "id") Long id, @RequestBody Employee employee) throws ResourceNotFoundException {
		
		System.out.println(" Update Emp Object: "+id+" emp :"+employee);
		Employee emp = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + id));
		emp.setName("updated"+employee.getName());
		emp.setEmail("updated"+employee.getEmail());
	    repository.save(emp);
	    return ResponseEntity.ok(emp); 

	}
	
	
	  @DeleteMapping("/{id}")
	  public Map<String, Boolean> delete(@PathVariable(value = "id") Long empId) throws Exception {
	    Employee emp =repository.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + empId));
	    repository.delete(emp);
	    Map<String, Boolean> response = new HashMap();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }	  
}

