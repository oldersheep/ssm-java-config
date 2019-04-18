package com.xxx.trail.service;

import com.github.pagehelper.PageInfo;
import com.xxx.trail.entity.StudentEntity;

import java.util.List;

/**
 * @ClassName StudentService
 * @Description
 * @Author Lilg
 * @Date 2019/4/17 22:22
 * @Version 1.0
 */
public interface StudentService {

    int insertStudent(StudentEntity studentEntity);

    PageInfo<StudentEntity> listAll(int page, int pageSize);
}
