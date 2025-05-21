package com.example.example_ci_cd.controller;

import com.example.example_ci_cd.entity.Livro;
import com.example.example_ci_cd.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar_shouldReturnListOfLivros() {
        List<Livro> livros = Arrays.asList(new Livro(), new Livro());
        when(livroService.listar()).thenReturn(livros);

        ResponseEntity<List<Livro>> response = livroController.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(livros, response.getBody());
        verify(livroService, times(1)).listar();
    }

    @Test
    void listar_shouldReturnNoContentWhenListIsEmpty() {
        when(livroService.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Livro>> response = livroController.listar();

        assertEquals(204, response.getStatusCodeValue());
        verify(livroService, times(1)).listar();
    }

    @Test
    void buscaPorId_shouldReturnLivro() {
        Livro livro = new Livro();
        livro.setId(1);
        when(livroService.buscarPorId(1)).thenReturn(livro);

        ResponseEntity<Livro> response = livroController.buscaPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(livro, response.getBody());
        verify(livroService, times(1)).buscarPorId(1);
    }

    @Test
    void criar_shouldReturnCreatedLivro() {
        Livro novoLivro = new Livro();
        Livro livroSalvo = new Livro();
        livroSalvo.setId(1);
        when(livroService.salvar(novoLivro)).thenReturn(livroSalvo);

        ResponseEntity<Livro> response = livroController.criar(novoLivro);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(livroSalvo, response.getBody());
        verify(livroService, times(1)).salvar(novoLivro);
    }

    @Test
    void atualizar_shouldReturnUpdatedLivro() {
        Livro livroAtualizacao = new Livro();
        Livro livroSalvo = new Livro();
        livroSalvo.setId(1);
        when(livroService.atualizar(1, livroAtualizacao)).thenReturn(livroSalvo);

        ResponseEntity<Livro> response = livroController.criar(1, livroAtualizacao);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(livroSalvo, response.getBody());
        verify(livroService, times(1)).atualizar(1, livroAtualizacao);
    }

    @Test
    void buscaPorTitulo_shouldReturnListOfLivros() {
        List<Livro> livros = Arrays.asList(new Livro(), new Livro());
        when(livroService.buscaPorTitulo("titulo")).thenReturn(livros);

        ResponseEntity<List<Livro>> response = livroController.buscaPorTitulo("titulo");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(livros, response.getBody());
        verify(livroService, times(1)).buscaPorTitulo("titulo");
    }

    @Test
    void buscaPorDataApos_shouldReturnListOfLivros() {
        List<Livro> livros = Arrays.asList(new Livro(), new Livro());
        LocalDate data = LocalDate.of(2023, 1, 1);
        when(livroService.buscaPorDataPublicacao(data)).thenReturn(livros);

        ResponseEntity<List<Livro>> response = livroController.buscaPorDataApos(data);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(livros, response.getBody());
        verify(livroService, times(1)).buscaPorDataPublicacao(data);
    }

    @Test
    void somaPrecos_shouldReturnSumOfPrices() {
        double soma = 150.0;
        when(livroService.somaPrecos()).thenReturn(soma);

        ResponseEntity<Double> response = livroController.somaPrecos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(soma, response.getBody());
        verify(livroService, times(1)).somaPrecos();
    }
}
