package com.dockerx.rxjavaweb.repository;

import com.dockerx.rxjavaweb.domain.dto.BaseStudentDTO;
import com.dockerx.rxjavaweb.domain.dto.StudentDTO;
import com.mongodb.rx.client.Success;
import rx.Observable;

/**
 * 定义Student Entity的数据访问接口
 *
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 17:32.
 */
public interface StudentRepository {

    /**
     * Creates a new Student document in the database.
     *
     * @param student
     *            new {@link BaseStudentDTO} instance to be created.
     * @return The status of the operation.
     */
    Observable<Success> createStudent(BaseStudentDTO student);

    /**
     * Fetches a Student with the given name.
     *
     * @param name
     *            name of the student to be fetched.
     * @return The student with the specified name.
     */
    Observable<StudentDTO> findByName(String name);
}
