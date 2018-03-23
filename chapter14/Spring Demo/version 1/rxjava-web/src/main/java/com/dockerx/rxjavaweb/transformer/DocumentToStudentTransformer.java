package com.dockerx.rxjavaweb.transformer;

import com.dockerx.rxjavaweb.domain.dto.BaseStudentDTO;
import com.dockerx.rxjavaweb.domain.dto.StudentDTO;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/23 18:01.
 */
public class DocumentToStudentTransformer implements Transformer<Document, BaseStudentDTO> {

    private static final String ID = "_id";
    public static final String MAJOR = "major";
    public static final String CREDIT = "credit";
    public static final String AGE = "age";
    public static final String NAME = "name";
    @Override
    public StudentDTO transform(Document source) {
        return new StudentDTO(source.getString(NAME), source.getInteger(AGE), source.getDouble(CREDIT),
                source.getString(MAJOR), ((ObjectId) source.get(ID)).toString());
    }
}
