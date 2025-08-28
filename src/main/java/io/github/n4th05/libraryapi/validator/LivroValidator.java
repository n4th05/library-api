package io.github.n4th05.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.n4th05.libraryapi.exceptions.CampoInvalidoException;
import io.github.n4th05.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.n4th05.libraryapi.model.Livro;
import io.github.n4th05.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;
    
    private final LivroRepository repository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatoriNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório.");
        }
    }

    private boolean isPrecoObrigatoriNulo(Livro livro) {
       return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent(); // Verifica se existe outro livro com o mesmo ISBN (true ou false, pq é boolean).
        }

        return livroEncontrado
                 .map(Livro::getId) // Pega o ID do livro encontrado
                 .stream() // Transforma em stream
                 .anyMatch(id -> !id.equals(livro.getId())); // Verifica se o ID do livro encontrado é diferente do ID do livro que está sendo validado.
    }

}
