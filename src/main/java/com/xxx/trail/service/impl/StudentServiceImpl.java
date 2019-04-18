package com.xxx.trail.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.trail.entity.StudentEntity;
import com.xxx.trail.mapper.StudentMapper;
import com.xxx.trail.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StudentServiceImpl
 * @Description
 * @Author Lilg
 * @Date 2019/4/17 22:32
 * @Version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int insertStudent(StudentEntity studentEntity) {

        return studentMapper.insertOne(studentEntity);
    }

    @Override
    public PageInfo<StudentEntity> listAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<StudentEntity> result = studentMapper.queryAll();

        return new PageInfo(result);
    }
}
