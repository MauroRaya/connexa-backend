# 📘 Documentação da API — Connexa

Este documento descreve os endpoints REST disponíveis da API **Connexa**.  
Todas as requisições e respostas utilizam **JSON**.  

A autenticação é feita via **JWT Bearer Token** para os endpoints protegidos.  

---

## 🔐 Autenticação

### `POST /register`  
Registra um novo estudante.

**Cabeçalhos da requisição**
```
Content-Type: application/json
```

**Corpo da requisição**
```json
{
  "name": "João da Silva",
  "email": "joao@universidade.edu.br",
  "password": "SenhaForte123"
}
```

**Resposta 200 OK**
```json
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao@universidade.edu.br"
}
```

**Resposta 400 Bad Request**
```json
{
  "errors": [
    "Formato de email inválido",
    "A senha deve ter no mínimo 8 caracteres"
  ]
}
```

**Resposta 409 Conflict**
```
{
  "errors": [
    "Este email já está cadastrado no sistema"
  ]
}
```

---

### `POST /login`  
Autentica um estudante e retorna um token JWT.

**Corpo da requisição**
```json
{
  "email": "joao@universidade.edu.br",
  "password": "SenhaForte123"
}
```

**Resposta 200 OK**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

**Resposta 500 Internal Server Error**
```
{
  "errors": [
    "Erro interno: Bad credentials"
  ]
}
```

---

## 👥 Grupos

### `GET /groups`  
Retorna todos os grupos. *(Requer JWT Token)*

**Resposta 200 OK**
```json
[
  {
    "id": 1,
    "name": "Grupo de Estudo de Matemática",
    "subject": "Matemática Avançada",
    "modality": "ONLINE",
    "location": "Sala 101, Bloco A",
    "objective": "Estudar conceitos avançados de cálculo e álgebra",
    "students": [
      {
        "id": 1,
        "name": "João da Silva",
        "email": "joao@universidade.edu.br"
      }
    ]
  }
]
```

### `GET /groups/{id}`  
Retorna os detalhes de um grupo específico.

**Resposta 200 OK**
```json
{
  "id": 1,
  "name": "Grupo de Estudo de Matemática",
  "subject": "Matemática Avançada",
  "modality": "ONLINE",
  "location": "Sala 101, Bloco A",
  "objective": "Estudar conceitos avançados de cálculo e álgebra",
  "students": [
    {
      "id": 1,
      "name": "João da Silva",
      "email": "joao@universidade.edu.br"
    }
  ]
}
```

**Resposta 404 Not Found**
```
{
  "errors": [
    "Grupo com id 99 não encontrado"
  ]
}
```

---

### `POST /groups`  
Cria um novo grupo. *(Requer JWT Token)*

**Corpo da requisição**
```json
{
  "name": "Grupo de Física",
  "subject": "Física Teórica",
  "modality": "PRESENCIAL",
  "location": "Sala 202, Bloco B",
  "objective": "Estudar mecânica quântica"
}
```

**Resposta 200 OK**
```json
{
  "id": 2,
  "name": "Grupo de Física",
  "subject": "Física Teórica",
  "modality": "PRESENCIAL",
  "location": "Sala 202, Bloco B",
  "objective": "Estudar mecânica quântica",
  "students": [
    {
      "id": 1,
      "name": "João da Silva",
      "email": "joao@universidade.edu.br"
    }
  ]
}
```

**Resposta 403 Forbidden**

---

### `PUT /groups/{id}/join`  
Adiciona o estudante autenticado a um grupo existente. *(Requer JWT Token)*

**Resposta 200 OK**
```json
{
  "id": 1,
  "name": "Grupo de Estudo de Matemática",
  "subject": "Matemática Avançada",
  "modality": "ONLINE",
  "location": "Sala 101, Bloco A",
  "objective": "Estudar conceitos avançados de cálculo e álgebra",
  "students": [
    {
      "id": 1,
      "name": "João da Silva",
      "email": "joao@universidade.edu.br"
    },
    {
      "id": 2,
      "name": "Maria Oliveira",
      "email": "maria@universidade.edu.br"
    }
  ]
}
```

**Resposta 400 Bad Request**
```
{
  "errors": [
    "Estudante já faz parte desse grupo"
  ]
}
```

**Resposta 404 Not Found**
```
{
  "errors": [
    "Grupo com id 99 não encontrado"
  ]
}
```
