package io.github.n4th05.libraryapi.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.n4th05.libraryapi.model.Autor;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    
    @Test
    public void salvarTest(){
        Autor autor = new Autor();
		autor.setNome("Maria");
		autor.setNacionalidade("Brasileiro");
		autor.setDataNascimento(LocalDate.of(1950, 1, 31));

		var autorSalvo = repository.save(autor);
		System.out.println("Autor Salvo " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("9dbae13a-c94a-45b1-a571-4a632e4f9243");

        Optional<Autor> possivelAutor = repository.findById(id);


        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);

        }
    }
}
