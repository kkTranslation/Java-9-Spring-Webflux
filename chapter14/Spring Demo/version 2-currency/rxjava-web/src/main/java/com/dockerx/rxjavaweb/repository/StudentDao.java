package com.dockerx.rxjavaweb.repository;

import com.dockerx.rxjavaweb.domain.dto.BaseStudentDTO;
import com.dockerx.rxjavaweb.domain.dto.StudentDTO;
import com.dockerx.rxjavaweb.transformer.DocumentToStudentTransformer;
import com.mongodb.rx.client.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;


/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 17:51.
 */
@Repository
public class StudentDao implements StudentRepository {
    private static final String STUDENT_COLLECTION = "student";

    MongoCollection<Document> collection;

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    @Autowired
    StudentDao(@Value("${spring.data.mongodb.uri}") String connectionUrl,
               @Value("${spring.data.mongodb.database}") String dbName) {
        MongoClient mongoClient = MongoClients.create(connectionUrl);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(STUDENT_COLLECTION);
    }

    @Override
    public Observable<Success> createStudent(BaseStudentDTO student) {
        return collection.insertOne(createStudentDocument(student))
                         .doOnNext(s -> logger.debug("Student was created successfully."))
                         .doOnError(e -> logger.error("An ERROR occurred while creating a new Student", e));
    }

    private Document createStudentDocument(BaseStudentDTO student) {
        return new Document(DocumentToStudentTransformer.NAME, student.getName())
                .append(DocumentToStudentTransformer.AGE, student.getAge())
                .append(DocumentToStudentTransformer.CREDIT, student.getCredit())
                .append(DocumentToStudentTransformer.MAJOR, student.getMajor());
    }
    @Override
    public Observable<StudentDTO> findByName(String name) {

        logger.debug("Fetching the student with name: " + name);
        return collection.find(eq(DocumentToStudentTransformer.NAME, name))
                         .toObservable()
                         .map(document -> new DocumentToStudentTransformer().transform(document))
                         .doOnNext(s -> logger.debug("Student with the given name was retrieved."))
                         .doOnError(e -> logger.error("An ERROR occurred while fetching the student", e));
    }
}
