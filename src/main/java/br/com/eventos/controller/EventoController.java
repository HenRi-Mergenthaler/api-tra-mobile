package br.com.eventos.controller;

import br.com.eventos.model.Evento;
import br.com.eventos.service.EventoService;
import br.com.eventos.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final UsuarioService usuarioService;

    public EventoController(EventoService eventoService, UsuarioService usuarioService) {
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoService.listar();
    }

    @GetMapping("/{id}")
    public Evento buscar(@PathVariable Long id) {
        return eventoService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento criar(@RequestHeader(value = "token", required = false) String token, @RequestBody Evento evento) {
        usuarioService.verificarAdmin(token);
        return eventoService.salvar(evento);
    }

    @PutMapping("/{id}")
    public Evento atualizar(@RequestHeader(value = "token", required = false) String token, @PathVariable Long id, @RequestBody Evento evento) {
        usuarioService.verificarAdmin(token);
        return eventoService.atualizar(id, evento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@RequestHeader(value = "token", required = false) String token, @PathVariable Long id) {
        usuarioService.verificarAdmin(token);
        eventoService.excluir(id);
    }
}
