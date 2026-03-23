# Laboratório: Mock de respostas HTTP no Chrome DevTools com Spring Boot

## Pré-requisitos

É necessário ter:

1.  Java 17 (ou 21) instalado\
    Verificação no terminal:

        java -version

2.  Maven instalado (ou uso do Maven Wrapper do projeto)\
    Verificação:

        mvn -version

3.  Editor de código (VS Code, IntelliJ ou equivalente)

4.  Google Chrome instalado

Se algum comando não funcionar, verificar se o terminal está no
diretório correto do projeto.

------------------------------------------------------------------------

# Passo 1 --- Criar o projeto Spring Boot

## Utilizando Spring Initializr

1.  Acesse o Spring Initializr
2.  Configure:
    -   Project: Maven
    -   Language: Java
    -   Spring Boot: versão estável
    -   Group: `com.exemplo`
    -   Artifact: `override-demo`
    -   Packaging: Jar
    -   Java: 17 ou 21
3.  Adicione dependências:
    -   Spring Web
    -   Spring Boot DevTools (opcional)
4.  Clique em **Generate**
5.  Extraia o arquivo `.zip` em uma pasta local

### Conferência

A pasta deve conter: - `pom.xml` - `src` - `mvnw` (se presente)

------------------------------------------------------------------------

# Passo 2 --- Abrir o projeto no editor

1.  Abrir o editor
2.  Abrir a pasta do projeto

Estrutura esperada:

    src/main/java
    src/main/resources

------------------------------------------------------------------------

# Passo 3 --- Implementar o backend

## 3.1 Criar o Controller

Caminho:

    src/main/java/com/exemplo/overridedemo/controller

Arquivo: `ApiController.java`

``` java
package com.exemplo.overridedemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @GetMapping("/enderecos")
    public List<String> listarEnderecos() {
        return List.of(
                "consolelog.com.br",
                "www.consolelog.com.br",
                "https://consolelog.com.br",
                "https://www.consolelog.com.br"
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {

        if (!("usuario1".equals(request.getUsuario()) && "123".equals(request.getSenha()))) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.noContent()
                .header("access-token", "teste access token")
                .build();
    }
}
```

## 3.2 Criar o DTO

Arquivo: `LoginRequest.java`

``` java
package com.exemplo.overridedemo.controller;

public class LoginRequest {

    private String usuario;
    private String senha;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
```

------------------------------------------------------------------------

# Passo 4 --- Criar a interface HTML

Caminho:

    src/main/resources/static/index.html

``` html
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Override Demo</title>
</head>
<body>

<h2>Teste de Override com DevTools</h2>

<button onclick="carregar()">Carregar Lista</button>
<button onclick="loginCorreto()">Login Correto</button>
<button onclick="loginErrado()">Login Errado</button>

<script>
function mostrar(msg){
  const div = document.createElement("div");
  div.textContent = msg;
  document.body.appendChild(div);
}

async function carregar(){
  const resp = await fetch("/enderecos");
  if(!resp.ok){
    mostrar("Erro ao carregar: " + resp.status);
    return;
  }
  const dados = await resp.json();
  dados.forEach(mostrar);
}

function loginCorreto(){
  login("usuario1","123");
}

function loginErrado(){
  login("teste","111");
}

async function login(usuario,senha){
  const resp = await fetch("/login",{
    method:"POST",
    headers:{"Content-Type":"application/json"},
    body: JSON.stringify({usuario,senha})
  });

  if(!resp.ok){
    mostrar("Erro: " + resp.status);
    return;
  }

  mostrar("Token: " + resp.headers.get("access-token"));
}
</script>

</body>
</html>
```

------------------------------------------------------------------------

# Passo 5 --- Executar a aplicação

### Usando Maven

    mvn spring-boot:run

### Usando Maven Wrapper

Linux/Mac:

    ./mvnw spring-boot:run

Windows:

    mvnw spring-boot:run

### Verificação

No terminal deve aparecer mensagem indicando inicialização do servidor
na porta 8080.

------------------------------------------------------------------------

# Passo 6 --- Testar no navegador

Acessar:

    http://localhost:8080

Testes esperados:

-   Carregar Lista → exibe 4 endereços
-   Login Errado → retorna erro 401
-   Login Correto → mostra token

------------------------------------------------------------------------

# Passo 7 --- Inspecionar requisições no DevTools

1.  Abrir DevTools\
    Windows/Linux: `Ctrl + Shift + I`\
    Mac: `Cmd + Option + I`

2.  Ir para aba **Network**

3.  Clicar em "Carregar Lista"

A requisição `/enderecos` deve aparecer com status 200.

------------------------------------------------------------------------

# Passo 8 --- Override do Body

1.  Botão direito na requisição `/enderecos`
2.  Selecionar **Override content**
3.  Escolher uma pasta local para armazenar overrides
4.  Permitir acesso

Editar o JSON retornado, por exemplo:

``` json
[
  "consolelog.com.br",
  "conteudo-simulado.com.br"
]
```

Salvar e recarregar a página.

Resultado esperado: novo item aparece na lista.

------------------------------------------------------------------------

# Passo 9 --- Override de Headers

1.  Executar "Login Correto"
2.  Localizar requisição `/login`
3.  Botão direito → **Override headers**
4.  Alterar valor de `access-token`

Exemplo:

    token-simulado-123

Atualizar a página e repetir o login.

Resultado esperado: novo token exibido.

------------------------------------------------------------------------

# Passo 10 --- Exercício de fixação

Simular:

1.  Lista vazia no endpoint `/enderecos`
2.  Token diferente no endpoint `/login`

Responder:

-   Qual resposta foi modificada?
-   Qual comportamento mudou?
-   Em que cenário real isso seria aplicado?

------------------------------------------------------------------------

# Checklist de acompanhamento

-   Aplicação executando na porta 8080
-   Requisições visíveis no Network
-   Override do body funcionando
-   Override de headers funcionando
-   Compreensão da diferença entre conteúdo e cabeçalhos
