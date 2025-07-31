package io.github.n4th05.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        livro.setTitulo("Outro livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

//Inserimos o autor que já existe no banco de dados com o id dele:
         Autor autor = autorRepository.findById(UUID.fromString("76e7c418-ccf9-4e2a-af20-c28b9e50ab55"))
         .orElse(null);


         livro.setAutor(autor);

// Depois de setar o autor, podemos salvar o livro:
        repository.save(livro);
    }    

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

// Cria o autor diretamente no livro antes de salvar o livro.
// Ele salva tudo junto com o livro por causa do CascadeType.ALL.
// O cascade é basicamente para dizer que quando salvar o livro, também salva o autor. Sendo assim o efeito cascata.
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        livro.setAutor(autor);

        repository.save(livro);
    }  

    @Test
    void atualizarAutorDoLivro(){
       UUID id = UUID.fromString("5972b3ea-d84c-4e2a-a817-1a8ef7d6fd8d");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("6a3fb371-6fa9-4b06-95d5-2d2b3baac3d0");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("5972b3ea-d84c-4e2a-a817-1a8ef7d6fd8d");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("41afcbe8-922e-451b-bcae-bf4c7d8b8b8c");
        repository.deleteById(id);
    }

    @Test
    @Transactional // Mantém a transação ativa para que o Hibernate possa carregar os dados do autor. getAutor().getNome() por exemplo.
    void buscarLivroTest(){
        UUID id = UUID.fromString("f16a8662-a0cf-4417-ae61-a225acf59554");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("O roubo da casa assombrada testee");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> lista = repository.findByIsbn("9999-84874");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){

        var preco = BigDecimal.valueOf(100);
        var titulo = "Terceiro Livro"; 

        List<Livro> lista = repository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }
}
