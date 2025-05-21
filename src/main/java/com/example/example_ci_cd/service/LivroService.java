package com.example.example_ci_cd.service;

import com.example.example_ci_cd.entity.Autor;
import com.example.example_ci_cd.entity.Livro;
import com.example.example_ci_cd.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorService autorService;

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    public Livro salvar(Livro livro) {
        Autor autor = autorService.buscarPorId(livro.getAutor().getId());
        livro.setAutor(autor);

        return livroRepository.save(livro);
    }

    public Livro atualizar(Integer id, Livro livroAtualizacao) {
        if (!livroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }

        Autor autor = autorService.buscarPorId(livroAtualizacao.getAutor().getId());
        livroAtualizacao.setAutor(autor);

        livroAtualizacao.setId(id);
        return livroRepository.save(livroAtualizacao);
    }

    public List<Livro> buscaPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    public List<Livro> buscaPorDataPublicacao(LocalDate data) {
        return livroRepository.findByDataPublicacaoAfter(data);
    }

    public List<Livro> buscaPorTituloDataPublicacaoEntre(LocalDate dataInicio, LocalDate dataFim, String titulo) {
        return livroRepository.findByDataPublicacaoBetweenAndTituloContainsIgnoreCase(dataInicio, dataFim, titulo);
    }

    public Double somaPrecos() {
        Double preco = livroRepository.somaPrecos();

        if (preco == null) {
            return 0.0;
        }

        return preco;
    }
}