package com.amit.classmanagement.model;

public class Mark {
  private Long id;
  private Long studentId;
  private Integer mark;

  public Mark(Long id, Long studentId, Integer mark) {
    this.id = id;
    this.studentId = studentId;
    this.mark = mark;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getStudentId() {
    return this.studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public Integer getMark() {
    return this.mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }
}
