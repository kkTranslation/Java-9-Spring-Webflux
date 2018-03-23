package com.dockerx.rxjavaweb.controller;

import com.dockerx.rxjavaweb.domain.dto.BaseStudentDTO;
import com.dockerx.rxjavaweb.domain.dto.StudentDTO;
import com.dockerx.rxjavaweb.repository.StudentRepository;
import com.mongodb.rx.client.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 21:03.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping(value = "",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Observable<Success> createStudent(@RequestBody BaseStudentDTO student) {
        logger.debug("Creating a new Student.");
        return studentRepository.createStudent(student);
    }
    @GetMapping(value = "",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Observable<StudentDTO> getStudentByName(@RequestParam String name) {
        logger.debug("Fetching a new student with the Name: " + name);
        return studentRepository.findByName(name);
    }
}
