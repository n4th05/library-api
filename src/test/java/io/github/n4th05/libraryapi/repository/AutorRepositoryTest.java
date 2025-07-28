package io.github.n4th05.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;
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

    @Test
    public void listarTest(){
        List<Autor> listaAutores = repository.findAll();
        listaAutores.forEach(System.out::println);

    }

    @Test
    public void countTest(){
        System.out.println("Contagem de Autores: " + repository.count());
    }

    //Teste de deletar por ID
    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("35a05c8e-0484-4533-905a-ab422001efa5");

        repository.deleteById(id);
    }

    // Teste de deletar por objeto
    @Test
    public void deletePorObjetoTest(){
        var id = UUID.fromString("034d1b4e-0438-41d5-925d-bbce2f968d99");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
}
