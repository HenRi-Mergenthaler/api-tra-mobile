# Eventos API

Back-end do app de eventos da cidade. Feito em Java com Spring Boot e banco SQLite.

## Como rodar

Precisa do JDK 17 (Temurin 17) com o `JAVA_HOME` apontando para ele.

Dentro da pasta `api-tra-mobile`:

```
.\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`.

O banco é SQLite (arquivo `eventos.db`), não precisa de Docker nem de instalar banco. Na primeira vez ele é criado com alguns eventos, e toda vez que a API sobe ela garante estes dois usuários:

| Login   | Senha   | Admin |
|---------|---------|-------|
| admin   | admin   | sim   |
| usuario | usuario | não   |

Para voltar aos dados iniciais é só apagar o `eventos.db` e rodar de novo.

No emulador Android o endereço da API é `http://10.0.2.2:8080/`.

## Endpoints

| Método | Rota                | O que faz              | Precisa ser admin |
|--------|---------------------|------------------------|-------------------|
| POST   | `/api/login`        | Faz login              | não               |
| GET    | `/api/eventos`      | Lista os eventos       | não (mas precisa de login) |
| GET    | `/api/eventos/{id}` | Mostra um evento       | não (mas precisa de login) |
| POST   | `/api/eventos`      | Cadastra um evento     | sim               |
| PUT    | `/api/eventos/{id}` | Altera um evento       | sim               |
| DELETE | `/api/eventos/{id}` | Exclui um evento       | sim               |

### Login

Envia:

```json
{ "login": "admin", "senha": "admin" }
```

Recebe:

```json
{ "id": 1, "nome": "Administrador", "login": "admin", "admin": true, "token": "..." }
```

Todas as rotas de eventos precisam do `token` recebido no header `token` da requisição. Sem token dá erro 401. Cadastrar, alterar e excluir são só para admin (senão dá erro 403).

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
