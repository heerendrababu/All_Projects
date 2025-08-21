package com.techtez.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
                        // ({keyName})
    @NotBlank(message = "{firstName.required}")
    @Size(min = 3, max = 50, message = "{firstName.size}")
    private String firstName;

    @Size(min = 3, max = 50, message = "{surName.size}")
    private String surName;

    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    private String email;

    @Min(value = 18, message = "{age.min}")
    @Max(value = 65, message = "{age.max}")
    private Integer age;

    @Pattern(regexp = "\\d{10}", message = "{mobileNo.pattern}")
    private String mobileNo;

    @NotBlank(message = "{password.required}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$", 
             message = "{password.pattern}")
    private String password;

    // constructors
    public User() {
        super();
    }

    public User(Integer id, String firstName, String surName, String email, Integer age, String mobileNo, String password) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.age = age;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method to provide a readable string representation of the object
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
