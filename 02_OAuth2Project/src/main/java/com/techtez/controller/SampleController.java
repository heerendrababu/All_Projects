package com.techtez.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController 
{
	  @GetMapping("/protected")
	  public String protectedResource() {
	        return "This is a protected resource, you are authenticated!";
	    }
}