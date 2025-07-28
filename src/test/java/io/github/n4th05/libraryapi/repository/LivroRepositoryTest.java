package io.github.n4th05.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;

@SpringBootTest
public class LivroRepositoryTest {
    
    @Autowired
    LivroRepository repository;

//Inserimos o autowired com a dependência que tem relação:
    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("90051-6564");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

//Inserimos o autor que já existe no banco de dados com o id dele:
        Autor autor = autorRepository.findById(UUID.fromString("24cb11fb-cb3b-449a-97bd-3182ff93b972"))
        .orElse(null);

        livro.setAutor(autor);
        
// Depois de setar o autor, podemos salvar o livro:
        repository.save(livro);
    }
}
