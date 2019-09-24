package com.xxx.trail.service;

import com.github.pagehelper.PageInfo;
import com.xxx.trail.entity.StudentEntity;

import java.util.List;

/**
 *
 * @author Lilg
 * @version 1.0
 * @since 1.0
 */
public interface StudentService {

    int insertStudent(StudentEntity studentEntity);

    PageInfo<StudentEntity> listAll(int page, int pageSize);
}
