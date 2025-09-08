package io.github.n4th05.libraryapi.controller;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    
    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody // Pra esse caso retornar uma String como resposta HTTP.
    public String paginaHome(Authentication authentication){
        return "Olá " + authentication.getUsername();
    }
}
