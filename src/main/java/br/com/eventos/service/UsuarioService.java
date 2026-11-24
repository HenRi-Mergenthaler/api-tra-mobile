package br.com.eventos.service;

import br.com.eventos.model.Usuario;
import br.com.eventos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario login(String login, String senha) {
        Usuario usuario = repository.findByLoginAndSenha(login, senha);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login ou senha invalidos");
        }
        usuario.setToken(UUID.randomUUID().toString());
        return repository.save(usuario);
    }

    public void verificarAdmin(String token) {
        Usuario usuario = token == null ? null : repository.findByToken(token);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Faca login primeiro");
        }
        if (!usuario.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas admin pode fazer isso");
        }
    }
}
