package com.dockerx.rxjavaweb.domain.dto;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 17:45.
 */
public class StudentDTO extends BaseStudentDTO{
    private String id;

    public StudentDTO(String name, int age, double credit, String stream) {
        super(name, age, credit, stream);
    }

    public StudentDTO(String name, int age, double credit, String stream, String id) {
        super(name, age, credit, stream);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
