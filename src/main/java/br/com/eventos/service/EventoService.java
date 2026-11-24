package br.com.eventos.service;

import br.com.eventos.model.Evento;
import br.com.eventos.repository.EventoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public List<Evento> listar() {
        return repository.findAll();
    }

    public Evento buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento nao encontrado"));
    }

    public Evento salvar(Evento evento) {
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titulo obrigatorio");
        }
        return repository.save(evento);
    }

    public Evento atualizar(Long id, Evento evento) {
        buscar(id);
        evento.setId(id);
        return salvar(evento);
    }

    public void excluir(Long id) {
        buscar(id);
        repository.deleteById(id);
    }
}
