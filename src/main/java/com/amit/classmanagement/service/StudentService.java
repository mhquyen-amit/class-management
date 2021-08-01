package com.amit.classmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amit.classmanagement.model.Student;
import com.amit.classmanagement.request.StudentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
  @Autowired
  private ClassService classService;

  private List<Student> studentList = new ArrayList<Student>();
  private static Long idGen = Long.valueOf(1);

  public List<Student> getByName(String name) {
    if (name == null || name.isBlank()) {
      return studentList;
    }
    return studentList.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
  }

  public Optional<Student> getById(Long id) {
    return studentList.stream().filter(s -> s.getId().equals(id)).findFirst();
  }

  public List<Student> getByClassId(Long classId) {
    return studentList.stream().filter(s -> s.getClassId().equals(classId)).collect(Collectors.toList());
  }

  public Student create(StudentRequest request) {
    boolean isNameExist = studentList.stream().anyMatch(s -> s.getName().equals(request.getName()));
    boolean isClassExist = classService.getById(request.getClassId()).isPresent();
    if (isNameExist || !isClassExist) {
      return null;
    }
    Student student = new Student(idGen, request);
    studentList.add(student);
    idGen++;
    return student;
  }

  public Student update(Student student, StudentRequest request) {
    student.setStudentId(request.getStudentId());
    student.setName(request.getName());
    student.setClassId(request.getClassId());
    return student;
  }

  public boolean delete(Long id) {
    return studentList.removeIf(s -> s.getId().equals(id));
  }
}
