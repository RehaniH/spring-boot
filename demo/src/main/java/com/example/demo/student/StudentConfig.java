package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args -> {
			Student marian = new Student(1l, "Marian", "marian@gmail.com", LocalDate.of(2000, Month.JANUARY, 5));
			Student alex = new Student("Alex", "alex@gmail.com", LocalDate.of(1995, Month.JANUARY, 15));
			repository.saveAll(List.of(marian, alex));
			
		};
	}
}
