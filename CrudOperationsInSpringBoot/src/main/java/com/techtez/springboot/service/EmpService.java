package com.techtez.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtez.springboot.entity.Employee;
import com.techtez.springboot.repository.EmpRepo;

@Service
public class EmpService 
{
  private final EmpRepo empRepo;
  
  @Autowired
  public EmpService(EmpRepo empRepo)
  {
	  this.empRepo = empRepo;
  }
  
//CRUD Operations:-
  public Employee saveEmployee(Employee p)
  {
	   return empRepo.save(p);// save()is a method of JpaRepository used to insert data into table with the help of Hibernate.
  }
  
  // Read all rows from the product table.
  public List<Employee> getAllEmployees()
  {
	  return empRepo.findAll();// finAll() method of JpaRepository used to read all rows from Table then convert into each row into each object.and return List(of Products).
  }
  
  // Update a row(Product) with new product based on pno
  public Employee updateEmployee(int pno,Employee newEmployee)
  {
	  Optional<Employee> existingEmployee  = empRepo.findById(pno);
	  if(existingEmployee.isPresent()==true)
	  {	
		  Employee e=existingEmployee.get();
		  e.setEname(newEmployee.getEname());
		  e.setEsal(newEmployee.getEsal());

		// Save a new product to the database using JPA's save() method
		  return empRepo.save(e);
	  }
	  else
		  throw new RuntimeException("employee not availble");
  }
  
  public void deleteEmployee(int eid)
  {
	   empRepo.deleteById(eid);
  }
}
