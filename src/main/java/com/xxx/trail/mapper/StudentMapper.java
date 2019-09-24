package com.xxx.trail.mapper;

import com.xxx.trail.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Lilg
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface StudentMapper {

    int insertOne(StudentEntity studentEntity);

    List<StudentEntity> queryAll();
}
