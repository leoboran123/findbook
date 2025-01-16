package com.example.findbook;

import com.example.findbook.author.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindbookApplication.class, args);

		// AuthorController au = new AuthorController();
		// au.getWithApi();
	
	}

}
