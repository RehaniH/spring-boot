package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return this.studentRepository.findAll();
	}

	public void createStudent(Student student) {
//		Student findByEmail = this.studentRepository.findByEmail(student.getEmail());
//		if(findByEmail == null) {
//			this.studentRepository.save(student);
//		}else {
//			System.out.println("record found");
//		}
		Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(student.getEmail());
		if (studentByEmail.isPresent()) {
			throw new IllegalStateException("email found");
		}

		this.studentRepository.save(student);

	}

	public void deleteStudent(Long id) {
		boolean exists = this.studentRepository.existsById(id);

		if (!exists) {
			throw new IllegalStateException("student with id: " + id + " not found");
		}

		this.studentRepository.deleteById(id);

	}

	@Transactional //from this annotation it depicts that resources would be updated based on what is saved to the database
	public void updateStudent(Long id, String name, String email) {

		Optional<Student> findById = this.studentRepository.findById(id);

		if (findById.isEmpty()) {
			throw new IllegalStateException("id " + id + " is not present.");
		}
		
		Student student = findById.get();
		if (name != null && name.length() != 0 && (!student.getName().equals(name))) {
			student.setName(name);
		}
		Optional<Student> findByEmail = this.studentRepository.findStudentByEmail(email);
		if (findByEmail.isPresent()) {
			throw new IllegalStateException("email is already present in database");
		} else if (email != null && email.length() >0 && !Objects.equals(student.getEmail(), email)) {
			student.setEmail(email);
		}
		//this.studentRepository.save(student); this is not needed

	}

}
