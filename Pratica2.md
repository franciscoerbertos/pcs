'# Prática 02 

Esta prática foi elaborada a partir da estrutura atual do seu projeto Spring Boot, que já possui:

- `ApiController.java`
- `LoginRequest.java`
- `index.html`
- `suporte.html`
- recursos estáticos em `src/main/resources/static`
- execução via Maven Wrapper

A proposta desta lista é aprofundar a prática de **programação cliente/servidor**, com foco em:

- criação de novos endpoints
- manipulação de JSON
- uso de diferentes métodos HTTP
- integração entre front-end e back-end
- leitura de headers e status HTTP
- testes com navegador e DevTools

A atividade está alinhada com a disciplina de **Linguagem de Programação Cliente-Servidor**, que envolve aplicações cliente/servidor, interação entre aplicações Web, mecanismos de autenticação e integração entre camadas fileciteturn1file0. Também segue a lógica de produtividade e simplificação trazida pelo Spring Boot para criação de aplicações web e APIs REST fileciteturn1file1 fileciteturn1file3.

---

# Orientações gerais

Use como base o projeto:

    ~/pcs/pratica_01/mockup_requisition/demo

Arquivos que você provavelmente irá alterar durante os exercícios:

- `src/main/java/com/example/demo/controller/ApiController.java`
- `src/main/java/com/example/demo/controller/LoginRequest.java`
- `src/main/resources/static/index.html`
- opcionalmente `src/main/resources/static/suporte.html`

Para executar o projeto:

Linux/Mac:

    ./mvnw spring-boot:run

Windows:

    mvnw spring-boot:run

Depois, acessar no navegador:

    http://localhost:8080

---

# Exercício 1 — Criar endpoint `GET /perfil`

## Objetivo

Criar um novo endpoint que retorne os dados de um usuário em formato JSON.

## Resultado esperado

Ao acessar:

    http://localhost:8080/perfil

deve aparecer um JSON semelhante a:

```json
{
  "id": 1,
  "nome": "Aluno Teste",
  "email": "aluno@unibra.com",
  "perfil": "ADMIN"
}
```

## Passo a passo de como fazer

### Passo 1
Abra o arquivo:

    src/main/java/com/example/demo/controller/ApiController.java

### Passo 2
Adicione os imports necessários no topo do arquivo, caso ainda não existam:

```java
import java.util.Map;
```

### Passo 3
Dentro da classe `ApiController`, crie um novo método com `@GetMapping("/perfil")`.

### Passo 4
Nesse método, monte um `Map<String, Object>` contendo:
- id
- nome
- email
- perfil

### Passo 5
Retorne esse `Map`.

## Exemplo de implementação

```java
@GetMapping("/perfil")
public Map<String, Object> perfil() {
    return Map.of(
            "id", 1,
            "nome", "Aluno Teste",
            "email", "aluno@unibra.com",
            "perfil", "ADMIN"
    );
}
```

## Como testar

1. Salve o arquivo
2. Reinicie ou deixe o Spring Boot recompilar
3. Abra no navegador:

       http://localhost:8080/perfil

4. Verifique se o JSON aparece corretamente

## Desafio extra

Altere o front-end (`index.html`) para adicionar um botão:

```html
<button onclick="carregarPerfil()">Carregar Perfil</button>
```

E crie uma função JavaScript para mostrar os dados na tela.

---

# Exercício 2 — Criar endpoint `GET /produtos`

## Objetivo

Criar um endpoint que retorne uma lista de produtos em JSON.

## Resultado esperado

```json
[
  { "id": 1, "nome": "Notebook", "preco": 3500.0 },
  { "id": 2, "nome": "Mouse", "preco": 90.0 },
  { "id": 3, "nome": "Teclado", "preco": 150.0 }
]
```

## Passo a passo de como fazer

### Passo 1
No pacote:

    src/main/java/com/example/demo/controller

crie uma nova classe chamada:

    Produto.java

### Passo 2
Na classe `Produto`, crie os atributos:
- id
- nome
- preco

### Passo 3
Crie:
- construtor
- getters

## Exemplo da classe `Produto.java`

```java
package com.example.demo.controller;

public class Produto {

    private Integer id;
    private String nome;
    private Double preco;

    public Produto(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }
}
```

### Passo 4
Abra `ApiController.java`.

### Passo 5
Adicione um novo método com `@GetMapping("/produtos")`.

### Passo 6
Retorne uma lista com objetos `Produto`.

## Exemplo de implementação no controller

```java
@GetMapping("/produtos")
public List<Produto> listarProdutos() {
    return List.of(
            new Produto(1, "Notebook", 3500.0),
            new Produto(2, "Mouse", 90.0),
            new Produto(3, "Teclado", 150.0)
    );
}
```

## Como testar

1. Execute a aplicação
2. Acesse:

       http://localhost:8080/produtos

3. Verifique a lista JSON no navegador

## Desafio extra no front-end

Adicione um botão em `index.html`:

```html
<button onclick="carregarProdutos()">Carregar Produtos</button>
```

Depois implemente uma função JavaScript que:
- faça `fetch("/produtos")`
- percorra a lista
- mostre nome e preço na tela

---

# Exercício 3 — Criar endpoint `POST /usuarios`

## Objetivo

Criar um endpoint que receba um JSON com dados de usuário e retorne uma confirmação.

## Entrada esperada

```json
{
  "nome": "Maria",
  "email": "maria@email.com"
}
```

## Saída esperada

Status HTTP:

    201 Created

Body:

```json
{
  "mensagem": "Usuário cadastrado com sucesso"
}
```

## Passo a passo de como fazer

### Passo 1
Crie uma nova classe chamada:

    UsuarioRequest.java

no pacote:

    src/main/java/com/example/demo/controller

### Passo 2
Adicione os atributos:
- nome
- email

### Passo 3
Crie getters e setters.

## Exemplo da classe

```java
package com.example.demo.controller;

public class UsuarioRequest {

    private String nome;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

### Passo 4
Abra `ApiController.java`.

### Passo 5
Adicione imports necessários:

```java
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
```

### Passo 6
Crie um método com `@PostMapping("/usuarios")`.

### Passo 7
Receba o JSON com `@RequestBody UsuarioRequest request`.

### Passo 8
Monte um `Map<String, String>` contendo a mensagem de sucesso.

### Passo 9
Retorne com:

```java
ResponseEntity.status(201).body(...)
```

## Exemplo de implementação

```java
@PostMapping("/usuarios")
public ResponseEntity<Map<String, String>> criarUsuario(@RequestBody UsuarioRequest request) {
    Map<String, String> resposta = new HashMap<>();
    resposta.put("mensagem", "Usuário cadastrado com sucesso");
    resposta.put("nomeRecebido", request.getNome());
    return ResponseEntity.status(201).body(resposta);
}
```

## Como testar

### Opção A — pelo Postman ou Thunder Client
Faça uma requisição `POST` para:

    http://localhost:8080/usuarios

Com body JSON:

```json
{
  "nome": "Maria",
  "email": "maria@email.com"
}
```

### Opção B — pelo terminal com `curl`

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria","email":"maria@email.com"}'
```

## Desafio extra no front-end

Crie um botão em `index.html` para enviar um usuário de teste via `fetch` com método `POST`.

---

# Exercício 4 — Criar endpoint `GET /admin/relatorio` usando header

## Objetivo

Treinar leitura de headers enviados pelo cliente.

## Regra

Se o header abaixo existir:

    x-admin: true

então a resposta deve ser sucesso.

Caso contrário, deve retornar:

    403 Forbidden

## Resultado esperado

### Quando autorizado
```json
{
  "mensagem": "Relatório liberado"
}
```

### Quando não autorizado
Status:

    403 Forbidden

Body:

```json
{
  "erro": "Acesso negado"
}
```

## Passo a passo de como fazer

### Passo 1
Abra `ApiController.java`.

### Passo 2
Crie um método com:

```java
@GetMapping("/admin/relatorio")
```

### Passo 3
Receba o header usando:

```java
@RequestHeader(value = "x-admin", required = false) String admin
```

### Passo 4
Crie uma condição:
- se `admin == null` ou diferente de `"true"` → retornar 403
- senão → retornar sucesso

## Exemplo de implementação

```java
@GetMapping("/admin/relatorio")
public ResponseEntity<Map<String, String>> relatorio(
        @RequestHeader(value = "x-admin", required = false) String admin) {

    Map<String, String> resposta = new HashMap<>();

    if (admin == null || !admin.equals("true")) {
        resposta.put("erro", "Acesso negado");
        return ResponseEntity.status(403).body(resposta);
    }

    resposta.put("mensagem", "Relatório liberado");
    return ResponseEntity.ok(resposta);
}
```

## Como testar

### Sem header

```bash
curl http://localhost:8080/admin/relatorio
```

### Com header

```bash
curl http://localhost:8080/admin/relatorio -H "x-admin: true"
```

## O que observar

- a mesma URL responde de forma diferente dependendo do header
- isso aproxima o aluno de mecanismos de autorização e autenticação, tema importante em aplicações cliente-servidor e segurança no ecossistema Spring fileciteturn1file1 fileciteturn1file3

---

# Exercício 5 — Criar endpoint `POST /logout` com status 204 e header customizado

## Objetivo

Praticar respostas HTTP sem body e com cabeçalhos personalizados.

## Resultado esperado

Status:

    204 No Content

Header:

    x-logout: ok

Sem body.

## Passo a passo de como fazer

### Passo 1
Abra `ApiController.java`.

### Passo 2
Crie um método com:

```java
@PostMapping("/logout")
```

### Passo 3
Faça o retorno com:

```java
ResponseEntity.noContent()
```

### Passo 4
Adicione um header chamado:

    x-logout

com valor:

    ok

### Passo 5
Finalize com `.build()`.

## Exemplo de implementação

```java
@PostMapping("/logout")
public ResponseEntity<Void> logout() {
    return ResponseEntity.noContent()
            .header("x-logout", "ok")
            .build();
}
```

## Como testar

### Usando curl

```bash
curl -i -X POST http://localhost:8080/logout
```

### O que observar
- status HTTP 204
- ausência de body
- presença do header `x-logout`

## Desafio extra no front-end

Crie um botão:

```html
<button onclick="logout()">Logout</button>
```

Depois implemente uma função JavaScript que:
- faça `fetch("/logout", { method: "POST" })`
- leia o header `x-logout`
- mostre na tela algo como:

    Logout realizado: ok

---

# Critérios de entrega

Para cada exercício, o aluno deve entregar:

1. Código implementado
2. Captura de tela do navegador ou da aba Network
3. Explicação curta do que foi feito
4. Evidência do teste realizado

---

# Fechamento

Essas 5 atividades foram pensadas para evoluir o projeto já existente, sem trocar a estrutura base. Com isso, os alunos praticam:

- criação de rotas REST
- uso de DTOs simples
- serialização JSON
- diferentes métodos HTTP
- status de resposta
- headers de requisição e resposta
- integração cliente/servidor com Spring Boot

Esses pontos dialogam diretamente com a ementa da disciplina, especialmente em aplicações cliente/servidor, interação web, autenticação e integração entre serviços fileciteturn1file0.