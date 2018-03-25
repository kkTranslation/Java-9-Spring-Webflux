package com.dockerx.rxjavaweb.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 17:41.
 */
public class BaseStudentDTO {

    @NotNull
    @NotEmpty
    private String name;
    @Min(value = 1, message = "Age should be a positive number")
    private int age;
    @Min(value = 0, message = "Invalid credit value was given")
    private double credit;
    @NotNull
    @NotEmpty
    private String major;



    private BaseStudentDTO() {
        super();
    }

    public BaseStudentDTO(String name, int age, double credit, String major) {
        super();
        this.name = name;
        this.age = age;
        this.credit = credit;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public BaseStudentDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public BaseStudentDTO setAge(int age) {
        this.age = age;
        return this;
    }

    public double getCredit() {
        return credit;
    }

    public BaseStudentDTO setCredit(double credit) {
        this.credit = credit;
        return this;
    }

    public String getMajor() {
        return major;
    }

    public BaseStudentDTO setMajor(String major) {
        this.major = major;
        return this;
    }

}
