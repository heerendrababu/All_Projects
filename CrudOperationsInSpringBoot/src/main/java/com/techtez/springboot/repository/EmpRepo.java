package com.techtez.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techtez.springboot.entity.Employee;

public interface EmpRepo extends JpaRepository<Employee, Integer>
{

}
