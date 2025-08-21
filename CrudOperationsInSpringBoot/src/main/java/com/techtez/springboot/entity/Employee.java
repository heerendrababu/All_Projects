package com.techtez.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee 
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int eid;
  private String ename;
  private long esal;
public Employee(int eid, String ename, long esal) {
	super();
	this.eid = eid;
	this.ename = ename;
	this.esal = esal;
}
public Employee() {
	super();
	// TODO Auto-generated constructor stub
}
public int getEid() {
	return eid;
}
public void setEid(int eid) {
	this.eid = eid;
}
public String getEname() {
	return ename;
}
public void setEname(String ename) {
	this.ename = ename;
}
public long getEsal() {
	return esal;
}
public void setEsal(long esal) {
	this.esal = esal;
}
  
  
}
