package com.xxx.trail.controller;

import com.github.pagehelper.PageInfo;
import com.xxx.trail.entity.StudentEntity;
import com.xxx.trail.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Lilg
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public String add() {

        StudentEntity record = new StudentEntity();
        record.setName("aaa");
        record.setSex("0");
        record.setAddress("aaaaaaaaa");
        record.setBirthday(new Date());
        record.setCreateTime(new Date());
        studentService.insertStudent(record);
        return "success";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<StudentEntity> list() {

        return studentService.listAll(1, 10);
    }

}
