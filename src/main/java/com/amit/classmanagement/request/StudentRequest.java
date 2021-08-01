package com.amit.classmanagement.request;

public class StudentRequest {
  private String studentId;
  private String name;
  private Long classId;

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
