package com.example.example_ci_cd.repository;

import com.example.example_ci_cd.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutorRepository extends JpaRepository<Autor, Integer> {
}