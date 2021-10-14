package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
public Student findByEmail(String email);
	
	@Query("select email from Student e")
	Student selectAll();
	
	@Query("select s from Student s where s.student_id=:student_id")
	public Student findById(@Param("student_id") int student_id);
	
	@SuppressWarnings("unchecked")
	Student save(Student e);

}

