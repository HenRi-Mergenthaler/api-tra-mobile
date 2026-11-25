package br.com.eventos.service;

import br.com.eventos.model.Evento;
import br.com.eventos.repository.EventoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class EventoService {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
        if (vazio(evento.getTitulo())) {
            throw erro("Titulo obrigatorio");
        }
        if (vazio(evento.getLocal())) {
            throw erro("Local obrigatorio");
        }
        if (vazio(evento.getDataInicio())) {
            throw erro("Data de inicio obrigatoria");
        }
        if (vazio(evento.getDataFim())) {
            throw erro("Data de fim obrigatoria");
        }

        LocalDateTime inicio = converterData(evento.getDataInicio());
        LocalDateTime fim = converterData(evento.getDataFim());
        if (fim.isBefore(inicio)) {
            throw erro("Data de fim nao pode ser antes da data de inicio");
        }

        return repository.save(evento);
    }

    private LocalDateTime converterData(String data) {
        try {
            return LocalDateTime.parse(data, FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw erro("Data invalida: " + data + ". Use o formato dd/MM/yyyy HH:mm");
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }

    private ResponseStatusException erro(String mensagem) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem);
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
