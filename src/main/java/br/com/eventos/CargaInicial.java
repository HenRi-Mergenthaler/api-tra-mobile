package br.com.eventos;

import br.com.eventos.model.Evento;
import br.com.eventos.model.Usuario;
import br.com.eventos.repository.EventoRepository;
import br.com.eventos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicial implements CommandLineRunner {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public CargaInicial(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        // Garante os dois usuarios do trabalho, mesmo se o banco ja existia
        garantirUsuario("Administrador", "admin", "admin", true);
        garantirUsuario("Usuario", "usuario", "usuario", false);

        // Usuario antigo de teste, substituido pelo "usuario"
        Usuario antigo = usuarioRepository.findByLogin("joao");
        if (antigo != null) {
            usuarioRepository.delete(antigo);
        }

        if (eventoRepository.count() == 0) {
            eventoRepository.save(new Evento("Feira de Artesanato", "Feira com artesaos locais, comidas tipicas e musica ao vivo.", "Praca Central", "06/12/2026 09:00", "06/12/2026 18:00", "Cultura"));
            eventoRepository.save(new Evento("Cinema ao Ar Livre", "Sessao gratuita de filmes nacionais. Leve sua cadeira!", "Parque da Cidade", "12/12/2026 19:30", "12/12/2026 22:00", "Cultura"));
            eventoRepository.save(new Evento("Corrida de Rua 5K", "Corrida aberta ao publico com inscricao gratuita.", "Parque da Cidade", "13/12/2026 07:00", "13/12/2026 10:00", "Esporte"));
            eventoRepository.save(new Evento("Show de Natal", "Apresentacao do coral municipal e da orquestra da cidade.", "Teatro Municipal", "20/12/2026 20:00", "20/12/2026 22:30", "Musica"));
            eventoRepository.save(new Evento("Festival Gastronomico", "Food trucks e restaurantes da regiao reunidos em um so lugar.", "Centro de Eventos", "10/01/2027 11:00", "12/01/2027 23:00", "Gastronomia"));
        }
    }

    private void garantirUsuario(String nome, String login, String senha, boolean admin) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            usuario = new Usuario(nome, login, senha, admin);
        } else {
            usuario.setNome(nome);
            usuario.setSenha(senha);
            usuario.setAdmin(admin);
        }
        usuarioRepository.save(usuario);
    }
}
