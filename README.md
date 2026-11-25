# Eventos API

Back-end do app de eventos da cidade. Feito em Java com Spring Boot e banco SQLite.

## Como rodar

Precisa do JDK 17 (pode ser o que vem com o Android Studio).

Dentro da pasta `eventos-api`:

```
.\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`.

Na primeira vez é criado o arquivo `eventos.db` com alguns eventos e dois usuários:

| Login | Senha    | Admin |
|-------|----------|-------|
| admin | admin123 | sim   |
| joao  | 123      | não   |

Para voltar aos dados iniciais é só apagar o `eventos.db` e rodar de novo.

No emulador Android o endereço da API é `http://10.0.2.2:8080/`.

## Endpoints

| Método | Rota                | O que faz              | Precisa ser admin |
|--------|---------------------|------------------------|-------------------|
| POST   | `/api/login`        | Faz login              | não               |
| GET    | `/api/eventos`      | Lista os eventos       | não               |
| GET    | `/api/eventos/{id}` | Mostra um evento       | não               |
| POST   | `/api/eventos`      | Cadastra um evento     | sim               |
| PUT    | `/api/eventos/{id}` | Altera um evento       | sim               |
| DELETE | `/api/eventos/{id}` | Exclui um evento       | sim               |

### Login

Envia:

```json
{ "login": "admin", "senha": "admin123" }
```

Recebe:

```json
{ "id": 1, "nome": "Administrador", "login": "admin", "admin": true, "token": "..." }
```

Para cadastrar, alterar ou excluir eventos, mande o `token` recebido no header `token` da requisição. Sem token dá erro 401, e se o usuário não for admin dá erro 403.

### Evento

```json
{
  "titulo": "Feira de Artesanato",
  "descricao": "Feira com artesaos locais.",
  "local": "Praca Central",
  "dataInicio": "06/12/2026 09:00",
  "dataFim": "06/12/2026 18:00",
  "categoria": "Cultura"
}
```

- `titulo`, `local`, `dataInicio` e `dataFim` são obrigatórios.
- As datas usam o formato `dd/MM/yyyy HH:mm`.
- A data de fim não pode ser antes da data de início.
- Se faltar algum campo ou a data estiver errada, a API devolve erro 400 com a mensagem do problema.
