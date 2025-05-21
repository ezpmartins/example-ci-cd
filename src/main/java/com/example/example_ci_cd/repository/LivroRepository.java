package com.example.example_ci_cd.repository;

import com.example.example_ci_cd.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByDataPublicacaoAfter(LocalDate data);

    List<Livro> findByDataPublicacaoBetweenAndTituloContainsIgnoreCase(LocalDate dataInicio, LocalDate dataFim, String titulo);

    @Query("SELECT SUM(l.preco) FROM Livro l")
    Double somaPrecos();
}
