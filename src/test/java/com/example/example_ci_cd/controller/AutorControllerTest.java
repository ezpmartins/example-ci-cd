package com.example.example_ci_cd.controller;

import com.example.example_ci_cd.entity.Autor;
import com.example.example_ci_cd.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorControllerTest {

    private AutorService autorService;
    private AutorController autorController;

    @BeforeEach
    void setUp() {
        autorService = Mockito.mock(AutorService.class);
        autorController = new AutorController(autorService);
    }

    @Test
    void testCriarAutor() {
        Autor novoAutor = new Autor();
        novoAutor.setNome("Autor Exemplo");

        Autor autorSalvo = new Autor();
        autorSalvo.setId(1);
        autorSalvo.setNome("Autor Exemplo");

        when(autorService.salvar(novoAutor)).thenReturn(autorSalvo);

        ResponseEntity<Autor> response = autorController.criar(novoAutor);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(autorSalvo, response.getBody());
        assertEquals("/autores/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void testListarAutores() {
        Autor autor1 = new Autor();
        autor1.setId(1);
        autor1.setNome("Autor 1");

        Autor autor2 = new Autor();
        autor2.setId(2);
        autor2.setNome("Autor 2");

        List<Autor> autores = Arrays.asList(autor1, autor2);

        when(autorService.listar()).thenReturn(autores);

        ResponseEntity<List<Autor>> response = autorController.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(autor1, response.getBody().get(0));
        assertEquals(autor2, response.getBody().get(1));
    }

    @Test
    void testListarAutoresVazio() {
        when(autorService.listar()).thenReturn(List.of());

        ResponseEntity<List<Autor>> response = autorController.listar();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testBuscaPorId() {
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNome("Autor Exemplo");

        when(autorService.buscarPorId(1)).thenReturn(autor);

        ResponseEntity<Autor> response = autorController.buscaPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(autor, response.getBody());
    }
}
