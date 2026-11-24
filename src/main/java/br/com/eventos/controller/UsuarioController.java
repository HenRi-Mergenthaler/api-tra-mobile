package br.com.eventos.controller;

import br.com.eventos.model.Usuario;
import br.com.eventos.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Map<String, String> dados) {
        return usuarioService.login(dados.get("login"), dados.get("senha"));
    }
}
