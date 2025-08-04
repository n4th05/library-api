package io.github.n4th05.libraryapi.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.AutorRepository;
import io.github.n4th05.libraryapi.repository.LivroRepository;

@Service
public class TransacaoService {
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    /// livro (titulo,..., nome_arquivo) -> id.png
    @Transactional
    public void salvarLivroComFoto(){
        // salva o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("5bcd08e8-e540-4012-9bea-ef10177b011d"))
        .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
    }

    @Transactional
    public void executar(){
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Test Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        autorRepository.save(autor);

        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Test Livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Test Francisco")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
