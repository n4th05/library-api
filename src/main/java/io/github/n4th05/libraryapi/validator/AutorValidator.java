package io.github.n4th05.libraryapi.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.n4th05.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.repository.AutorRepository;

@Component
public class AutorValidator {
    
    @Autowired
    private AutorRepository repository;

    // public AutorValidator(AutorRepository repository) {
    //     this.repository = repository;
    // }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
            );

            if(autor.getId() == null){
                return autorEncontrado.isPresent(); // Verifica se existe outro autor com os mesmos dados(true ou false, pq é boolean).
            }

            return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent(); // Se não existir, ele cadastra normalmente.
    }
}
