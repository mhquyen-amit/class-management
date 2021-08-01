package com.amit.classmanagement.model;

import com.amit.classmanagement.request.StudentRequest;

public class Student {
  private Long id;
  private String studentId;
  private String name;
  private Long classId;

  public Student(Long id, StudentRequest request) {
    this.id = id;
    this.name = request.getName();
    this.classId = request.getClassId();
    this.studentId = request.getStudentId();
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStudentId() {
    return this.studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getClassId() {
    return this.classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }
}
