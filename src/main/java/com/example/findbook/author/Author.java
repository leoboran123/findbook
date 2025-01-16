package com.example.findbook.author;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// @Table(name="Employee")
public record Author(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id,
    String author_key,
    String author_name
) {
    public Author{

    }
}
