package com.amit.classmanagement.controller;

import java.util.List;
import java.util.Optional;

import com.amit.classmanagement.model.Mark;
import com.amit.classmanagement.model.Student;
import com.amit.classmanagement.request.StudentRequest;
import com.amit.classmanagement.service.MarkService;
import com.amit.classmanagement.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @Autowired
  private MarkService markService;

  @GetMapping
  public ResponseEntity<List<Student>> studentList(@RequestParam(name = "name", required = false) String name) {
    return new ResponseEntity<>(studentService.getByName(name), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> studentDetail(@PathVariable Long id) {
    Optional<Student> student = studentService.getById(id);
    if (student.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(student.get(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody StudentRequest studentRequest) {
    if (
      studentRequest.getStudentId() == null ||
      studentRequest.getClassId() == null
    ) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Student student = studentService.create(studentRequest);
    if (student == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(student, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent(
    @PathVariable Long id,
    @RequestBody StudentRequest studentRequest
  ) {
    if (
      studentRequest.getStudentId() == null ||
      studentRequest.getClassId() == null
    ) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Student> student = studentService.getById(id);
    if (student.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(studentService.update(student.get(), studentRequest), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<Student>> deleteStudent(@PathVariable Long id) {
    boolean result = studentService.delete(id);
    if (!result) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}/mark")
  public ResponseEntity<Mark> studentMark(@PathVariable Long id) {
    Optional<Mark> mark = markService.getByStudentId(id);
    if (mark.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(mark.get(), HttpStatus.OK);
  }

  @PostMapping("/{id}/mark")
  public ResponseEntity<Mark> markingStudent(@PathVariable Long id, @RequestBody Integer mark) {
    if (mark < 0 || mark > 10) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Student> student = studentService.getById(id);
    if (student.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(markService.marking(student.get().getId(), mark), HttpStatus.OK);
  }
}
