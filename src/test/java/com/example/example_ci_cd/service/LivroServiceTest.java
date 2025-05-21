package com.example.example_ci_cd.service;

import com.example.example_ci_cd.entity.Autor;
import com.example.example_ci_cd.entity.Livro;
import com.example.example_ci_cd.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorService autorService;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar_DeveRetornarListaDeLivros() {
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNome("Autor 1");
        autor.setNacionalidade("Brasileira");

        Livro livro1 = new Livro();
        livro1.setId(1);
        livro1.setTitulo("Livro 1");
        livro1.setDescricao("Descricao 1");
        livro1.setDataPublicacao(LocalDate.of(2023, 1, 1));
        livro1.setPreco(29.99);
        livro1.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setId(2);
        livro2.setTitulo("Livro 2");
        livro2.setDescricao("Descricao 2");
        livro2.setDataPublicacao(LocalDate.of(2024, 1, 1));
        livro2.setPreco(49.99);
        livro2.setAutor(autor);

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<Livro> livros = livroService.listar();

        assertNotNull(livros);
        assertEquals(2, livros.size());
        assertEquals("Livro 1", livros.get(0).getTitulo());
        assertEquals("Brasileira", livros.get(0).getAutor().getNacionalidade());
        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarLivro() {
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNome("Autor 1");

        Livro livro = new Livro();
        livro.setId(1);
        livro.setTitulo("Livro 1");
        livro.setDescricao("Descricao 1");
        livro.setDataPublicacao(LocalDate.of(2023, 1, 1));
        livro.setPreco(29.99);
        livro.setAutor(autor);

        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

        Livro resultado = livroService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Livro 1", resultado.getTitulo());
        assertEquals("Autor 1", resultado.getAutor().getNome());
        verify(livroRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_ComIdInexistente_DeveLancarExcecao() {
        when(livroRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> livroService.buscarPorId(1));
        assertEquals("404 NOT_FOUND \"Livro não encontrado\"", exception.getMessage());
        verify(livroRepository, times(1)).findById(1);
    }

    @Test
    void salvar_DeveRetornarLivroSalvo() {
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNome("Autor 1");

        Livro livro = new Livro();
        livro.setTitulo("Livro 1");
        livro.setDescricao("Descricao 1");
        livro.setDataPublicacao(LocalDate.of(2023, 1, 1));
        livro.setPreco(29.99);
        livro.setAutor(autor);

        Livro livroSalvo = new Livro();
        livroSalvo.setId(1);
        livroSalvo.setTitulo("Livro 1");
        livroSalvo.setDescricao("Descricao 1");
        livroSalvo.setDataPublicacao(LocalDate.of(2023, 1, 1));
        livroSalvo.setPreco(29.99);
        livroSalvo.setAutor(autor);

        when(autorService.buscarPorId(1)).thenReturn(autor);
        when(livroRepository.save(livro)).thenReturn(livroSalvo);

        Livro resultado = livroService.salvar(livro);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Livro 1", resultado.getTitulo());
        verify(autorService, times(1)).buscarPorId(1);
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void atualizar_ComIdExistente_DeveAtualizarLivro() {
        Autor autor = new Autor();
        autor.setId(1);

        Livro livroAtualizacao = new Livro();
        livroAtualizacao.setTitulo("Livro Atualizado");
        livroAtualizacao.setDescricao("Descricao Atualizada");
        livroAtualizacao.setDataPublicacao(LocalDate.of(2024, 1, 1));
        livroAtualizacao.setPreco(39.99);
        livroAtualizacao.setAutor(autor);

        when(livroRepository.existsById(1)).thenReturn(true);
        when(autorService.buscarPorId(1)).thenReturn(autor);
        when(livroRepository.save(livroAtualizacao)).thenReturn(livroAtualizacao);

        Livro resultado = livroService.atualizar(1, livroAtualizacao);

        assertNotNull(resultado);
        assertEquals("Livro Atualizado", resultado.getTitulo());
        verify(livroRepository, times(1)).existsById(1);
        verify(autorService, times(1)).buscarPorId(1);
        verify(livroRepository, times(1)).save(livroAtualizacao);
    }

    @Test
    void atualizar_ComIdInexistente_DeveLancarExcecao() {
        when(livroRepository.existsById(1)).thenReturn(false);

        Livro livroAtualizacao = new Livro();
        livroAtualizacao.setTitulo("Livro Atualizado");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> livroService.atualizar(1, livroAtualizacao));
        assertEquals("404 NOT_FOUND \"Livro não encontrado\"", exception.getMessage());
        verify(livroRepository, times(1)).existsById(1);
    }
}
