package com.amit.classmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amit.classmanagement.model.Class;

import org.springframework.stereotype.Service;

@Service
public class ClassService {
  private List<Class> classList = new ArrayList<Class>();
  private static Long idGen = Long.valueOf(1);

  public List<Class> getAll() {
    return classList;
  }

  public Optional<Class> getById(Long id) {
    return classList.stream().filter(c -> c.getId().equals(id)).findFirst();
  }

  public Class create(String name) {
    boolean isNameExist = classList.stream().anyMatch(s -> s.getName().equals(name));
    if (isNameExist) {
      return null;
    }
    Class clss = new Class(idGen, name);
    classList.add(clss);
    idGen++;
    return clss;
  }

  public Class update(Class clss, String name) {
    clss.setName(name);
    return clss;
  }

  public boolean delete(Long id) {
    return classList.removeIf(c -> c.getId().equals(id));
  }
}
