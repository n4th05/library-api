package io.github.n4th05.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.n4th05.libraryapi.model.Autor;
import io.github.n4th05.libraryapi.model.GeneroLivro;
import io.github.n4th05.libraryapi.model.Livro;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;
    
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
    // @Test
    // public void deletePorObjetoTest(){
    //     var id = UUID.fromString("7968bdeb-307e-4506-bb29-2bdb82407a34");
    //     var maria = repository.findById(id).get();
    //     repository.delete(maria);
    // }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
		autor.setNome("Antonio Testee");
		autor.setNacionalidade("Americado");
		autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        Livro livro = new Livro();
        livro.setIsbn("20887-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo da casa assombrada testee");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("9999-84874");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O roubo da casa assombrada");
        livro2.setDataPublicacao(LocalDate.of(2000, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

       // livroRepository.saveAll(autor.getLivros());
    }

         //Melhor forma de buscar de carregar os dados LAZY de uma entidade, principalmente quando for LAZY. (Não utilize EAGER).
    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("ac37fa37-ba66-4802-a2c5-50d123e5977c");
        var autor = repository.findById(id).get();

        // buscar os livros do autor

     List<Livro> livrosLista = livroRepository.findByAutor(autor);
     autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

}
