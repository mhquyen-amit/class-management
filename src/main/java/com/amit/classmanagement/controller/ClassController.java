package com.amit.classmanagement.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amit.classmanagement.model.Class;
import com.amit.classmanagement.model.Student;
import com.amit.classmanagement.service.ClassService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classes")
public class ClassController {
  @Autowired
  private ClassService classService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private MarkService markService;

  @GetMapping
  public ResponseEntity<List<Class>> classList() {
    return new ResponseEntity<>(classService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Class> classDetail(@PathVariable Long id) {
    Optional<Class> clss = classService.getById(id);
    if (clss.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(clss.get(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Class> createClass(@RequestBody String name) {
    if (name == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Class clss = classService.create(name);
    if (clss == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(clss, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Class> updateClass(
    @PathVariable Long id,
    @RequestBody String name
  ) {
    if (name == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Class> clss = classService.getById(id);
    if (clss.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(classService.update(clss.get(), name), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<Class>> deleteClass(@PathVariable Long id) {
    boolean result = classService.delete(id);
    if (!result) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}/students")
  public ResponseEntity<List<Student>> classStudents(@PathVariable Long id) {
    return new ResponseEntity<>(studentService.getByClassId(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/marks")
  public ResponseEntity<Integer> classMarks(@PathVariable Long id) {
    List<Long> studentIds = studentService.getByClassId(id).stream()
      .map(s -> s.getId())
      .collect(Collectors.toList());
    Integer totalMark = markService.getByStudentIdIn(studentIds)
      .stream()
      .map(m -> m.getMark())
      .reduce(0, (a, b) -> a + b);
    return new ResponseEntity<>(totalMark, HttpStatus.OK);
  }
}
