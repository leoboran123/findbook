package com.example.findbook.author;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.hibernate.Transaction;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;



@Repository
public class AuthorRepository{
    private List<Author> authors = new ArrayList<>();


    List<Author> findAll() { return authors; }
    
    
    Optional<Author> findByAuthorKey(String author_key){
      return authors.stream()
          .filter(author -> author.author_key().equals(author_key))
          .findFirst();
    }

    Optional<Author> findByAuthorName(String author_name){
      return authors.stream()
          .filter(author -> author.author_name().equals(author_name))
          .findAny();
    }

    void create(Author author){
      authors.add(author);
    }



    
}
