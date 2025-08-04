package io.github.n4th05.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.n4th05.libraryapi.Service.TransacaoService;

@SpringBootTest
public class TransacoesTest {
    
    @Autowired
    TransacaoService transacaoService;

    /*
     * Commit -> Confirmar as alterações no banco de dados.
     * Rollback -> Desfazer as alterações no banco de dados.
     */
    
    // @Test
    // void transacaoSimples(){
    //     transacaoService.executar();
    // }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
