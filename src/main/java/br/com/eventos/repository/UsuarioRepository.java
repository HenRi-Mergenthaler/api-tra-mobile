package br.com.eventos.repository;

import br.com.eventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLoginAndSenha(String login, String senha);

    Usuario findByToken(String token);
}
