package com.example.example_ci_cd.service;

import com.example.example_ci_cd.entity.Autor;
import com.example.example_ci_cd.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar_DeveRetornarListaDeAutores() {
        Autor autor1 = new Autor();
        autor1.setId(1);
        autor1.setNome("Autor 1");
        autor1.setNacionalidade("Brasileira");

        Autor autor2 = new Autor();
        autor2.setId(2);
        autor2.setNome("Autor 2");
        autor2.setNacionalidade("Americana");

        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        List<Autor> autores = autorService.listar();

        assertNotNull(autores);
        assertEquals(2, autores.size());
        assertEquals("Autor 1", autores.get(0).getNome());
        assertEquals("Brasileira", autores.get(0).getNacionalidade());
        assertEquals("Autor 2", autores.get(1).getNome());
        assertEquals("Americana", autores.get(1).getNacionalidade());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarAutor() {
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNome("Autor 1");
        autor.setNacionalidade("Brasileira");

        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Autor resultado = autorService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Autor 1", resultado.getNome());
        assertEquals("Brasileira", resultado.getNacionalidade());
        verify(autorRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_ComIdInexistente_DeveLancarExcecao() {
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> autorService.buscarPorId(1));
        assertEquals("404 NOT_FOUND \"Autor não encontrado\"", exception.getMessage());
        verify(autorRepository, times(1)).findById(1);
    }

    @Test
    void salvar_DeveRetornarAutorSalvo() {
        Autor autor = new Autor();
        autor.setNome("Novo Autor");
        autor.setNacionalidade("Italiana");

        Autor autorSalvo = new Autor();
        autorSalvo.setId(1);
        autorSalvo.setNome("Novo Autor");
        autorSalvo.setNacionalidade("Italiana");

        when(autorRepository.save(autor)).thenReturn(autorSalvo);

        Autor resultado = autorService.salvar(autor);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Novo Autor", resultado.getNome());
        assertEquals("Italiana", resultado.getNacionalidade());
        verify(autorRepository, times(1)).save(autor);
    }
}
