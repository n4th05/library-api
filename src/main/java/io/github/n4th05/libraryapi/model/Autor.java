package io.github.n4th05.libraryapi.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = "livros") // Exclui a lista de livros na representação do autor para evitar loops infinitos.
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (name= "nome", length = 100, nullable = false)
    private String nome;

    @Column (name= "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column (name= "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros; // Lista de livros associados a este autor.

    
// Sobre ter construtor ou não: No Java, se você não tiver um construtor na classe, automaticamente ele vai ter um construtor vazio.
}
