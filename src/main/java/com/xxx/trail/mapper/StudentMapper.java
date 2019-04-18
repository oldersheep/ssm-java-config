package com.xxx.trail.mapper;

import com.xxx.trail.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName StudentMapper
 * @Description
 * @Author Lilg
 * @Date 2019/4/17 22:27
 * @Version 1.0
 */
@Repository
public interface StudentMapper {

    int insertOne(StudentEntity studentEntity);

    List<StudentEntity> queryAll();
}
