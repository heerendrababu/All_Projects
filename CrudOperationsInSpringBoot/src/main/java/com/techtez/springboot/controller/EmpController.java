package com.techtez.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtez.springboot.entity.Employee;
import com.techtez.springboot.service.EmpService;

@RestController
@RequestMapping("/capi")
public class EmpController 
{
  private final EmpService empService;
  
  @Autowired
  public EmpController(EmpService empService)
  {
	  this.empService = empService;
  }
  
  @PostMapping("/entry")
  public Employee createNewEmployee(@RequestBody Employee e)
  {
	  return empService.saveEmployee(e);
  }
  
  @GetMapping("/reademp")
  public List<Employee> getEmpData()
  {
	  return empService.getAllEmployees();
  }
  
  @DeleteMapping("/delete/{id}")
  public void deleteEmp(@PathVariable int eid)
  {
	   empService.deleteEmployee(eid);
  }
  
  @PutMapping("/update/{eid}")
  public ResponseEntity<Employee> updateProductData(@PathVariable int eid, @RequestBody Employee e) 
  {
      try
      {
          Employee updatedEmployee = empService.updateEmployee(eid, e);
          return ResponseEntity.ok(updatedEmployee);
      } 
      catch (Exception e1) 
      {
          e1.printStackTrace(); // Print the stack trace to logs
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
  }
}
