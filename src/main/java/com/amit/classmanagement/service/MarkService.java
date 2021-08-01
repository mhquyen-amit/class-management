package com.amit.classmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amit.classmanagement.model.Mark;

import org.springframework.stereotype.Service;

@Service
public class MarkService {
  private List<Mark> markList = new ArrayList<Mark>();
  private static Long idGen = Long.valueOf(1);

  public List<Mark> list() {
    return markList;
  }

  public Optional<Mark> getByStudentId(Long studentId) {
    return markList.stream().filter(m -> m.getStudentId().equals(studentId)).findFirst();
  }

  public List<Mark> getByStudentIdIn(List<Long> studentIds) {
    return markList.stream().filter(m -> studentIds.contains(m.getStudentId())).collect(Collectors.toList());
  }

  public Mark marking(Long studentId, Integer m) {
    markList.removeIf(s -> s.getStudentId().equals(studentId));
    Mark mark = new Mark(idGen, studentId, m);
    markList.add(mark);
    idGen++;
    return mark;
  }
}
