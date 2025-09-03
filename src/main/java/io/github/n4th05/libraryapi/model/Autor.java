package io.github.n4th05.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = {"livros"}) // Exclui a lista de livros na representação do autor para evitar loops infinitos.
@EntityListeners(AuditingEntityListener.class) // Essa entidade vai ter escuta automática de eventos, como quando foi criada e quando foi modificada.
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

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY) // cascade = CascadeType.ALL
    private List<Livro> livros; // Lista de livros associados a este autor.

    @CreatedDate // Quando você salvar um novo autor, o dataCadastro será preenchido automaticamente.
    @Column (name= "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate // Quando você atualizar o autor, o dataAtualizacao será atualizado automaticamente.
    @Column (name= "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn (name = "id_usuario")
    private Usuario usuario;
    
// Sobre ter construtor ou não: No Java, se você não tiver um construtor na classe, automaticamente ele vai ter um construtor vazio.
}
