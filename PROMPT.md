package br.unisanta.connexa.controller;

import br.unisanta.connexa.dto.StudentDTO;
import br.unisanta.connexa.dto.TokenDTO;
import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.request.LoginRequest;
import br.unisanta.connexa.request.RegisterRequest;
import br.unisanta.connexa.service.AuthService;
import jakarta.validation.Valid;

import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        AuthService authService,
        PasswordEncoder passwordEncoder
    ) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "register")
    public ResponseEntity<StudentDTO> register(@Valid @RequestBody RegisterRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setPassword(passwordEncoder.encode(request.password()));
        student.setGroups(new HashSet<>());

        StudentDTO createdStudentDTO = this.authService.save(student);

        return ResponseEntity
            .ok()
            .body(createdStudentDTO);
    }

    @PostMapping(path = "login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequest request) {
        Student student = new Student();
        student.setEmail(request.email());
        student.setPassword(request.password());

        TokenDTO tokenDTO = new TokenDTO(
            authService.generateToken(student)
        );

        return ResponseEntity
            .ok()
            .body(tokenDTO);
    }
}

Crie um componente React em TypeScript chamado RegisterUser que exiba um formulário para cadastrar usuário.  

Campos: nome, email e senha.  

Ao clicar em "Cadastrar", deve enviar uma requisição POST para o endpoint /register no formato:

{
  "name": "João da Silva",
  "email": "joao@universidade.com",
  "password": "SenhaForte123"
}

🔑 Requisitos:
- Usar useState para controlar os inputs.  
- Usar fetch com try/catch para enviar os dados.  
- Exibir notificação estilo toast no canto inferior direito com mensagens de sucesso ou erro.  
- Estilizar com Tailwind CSS deixando o formulário centralizado, limpo e moderno.

import { useState } from "react";

interface ModalCreateGroupProps {
  onClose: () => void;
  onSuccess: () => void;
  addToast: (message: string, type: "success" | "error") => void;
}

export default function ModalCreateGroup({
  onClose,
  onSuccess,
  addToast,
}: ModalCreateGroupProps) {
  const [form, setForm] = useState({
    name: "",
    subject: "",
    modality: "PRESENCIAL",
    location: "",
    objective: "",
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const token = localStorage.getItem("authToken");
      if (!token) {
        addToast("Token de autenticação não encontrado", "error");
        onClose();
        return;
      }

      const response = await fetch(${process.env.REACT_APP_API_URL}/groups, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: Bearer ${token},
        },
        body: JSON.stringify(form),
      });

      const data = await response.json();

      if (!response.ok) {
        addToast(data.error || "Erro ao criar grupo", "error");
        return;
      }

      addToast("Grupo criado com sucesso!", "success");
      onSuccess();
      onClose();
    } catch (err: any) {
      addToast(err.message || "Erro de conexão", "error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal">
        <div className="modal-content"> {/* <-- DIV INTERNA AQUI */}
          <h2>Criar Grupo</h2>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="name"
              placeholder="Nome do grupo"
              value={form.name}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="subject"
              placeholder="Matéria"
              value={form.subject}
              onChange={handleChange}
              required
            />
            <select
              name="modality"
              value={form.modality}
              onChange={handleChange}
              required
            >
              <option value="PRESENCIAL">Presencial</option>
              <option value="ONLINE">Online</option>
              <option value="HIBRIDO">Híbrido</option>
            </select>
            <input
              type="text"
              name="location"
              placeholder="Localização ou link"
              value={form.location}
              onChange={handleChange}
              required
            />
            <textarea
              name="objective"
              placeholder="Objetivo do grupo"
              value={form.objective}
              onChange={handleChange}
              required
            ></textarea>

            <div className="modal-actions">
              <button type="submit" className="btn primary" disabled={loading}>
                {loading ? "Criando..." : "Criar"}
              </button>
              <button type="button" className="btn cancel-btn" onClick={onClose}>
                Cancelar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
Crie um componente React em TypeScript chamado CreateGroup que exiba um formulário para cadastrar grupo.  

Campos: nome, matéria, modalidade, local e objetivo.  

Ao clicar em "Criar Grupo", deve enviar uma requisição POST para o endpoint /groups no formato:

{
  "name": "Grupo de Física",
  "subject": "Física Teórica",
  "modality": "PRESENCIAL",
  "location": "Sala 202, Bloco A",
  "objective": "Estudar mecânica quântica"
}

🔑 Requisitos:
- Usar useState para controlar os inputs.  
- A requisição deve incluir JWT Token no cabeçalho:
  Authorization: Bearer <token>  
- Usar fetch com try/catch.  
- Exibir mensagens de sucesso ou erro abaixo do formulário.  
- Estilizar usando *CSS tradicional* (arquivo separado .css), deixando o formulário centralizado, limpo e moderno.

import { useState } from "react";
import InputField from "../InputField";
import Toast from "../Toast";
import { useNavigate } from "react-router-dom";

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const [toasts, setToasts] = useState<
    { id: number; message: string; type: "success" | "error" }[]
  >([]);

  const navigate = useNavigate();

  const addToast = (message: string, type: "success" | "error") => {
    const id = Date.now() + Math.random();
    setToasts((prev) => [...prev, { id, message, type }]);

    setTimeout(() => {
      setToasts((prev) => prev.filter((t) => t.id !== id));
    }, 10000);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await fetch(${process.env.REACT_APP_API_URL}/login, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (!response.ok) {
        if (data.errors && Array.isArray(data.errors)) {
          data.errors.forEach((msg: string) => addToast(msg, "error"));
        } else {
          addToast(data.error || "Erro ao fazer login", "error");
        }
        return;
      }

      localStorage.setItem("authToken", data.token);

      addToast("Login realizado com sucesso!", "success");

      navigate("/groups");
    } catch (err: any) {
      addToast(err.message || "Erro desconhecido", "error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <form className="auth-form" onSubmit={handleSubmit}>
        <div className="form-header">
          <h2>Bem-vindo de volta!</h2>
          <p>Entre na sua conta para continuar conectado aos seus estudos</p>
        </div>

        <div className="form-body">
          <InputField
            label="Email"
            type="email"
            placeholder="seu@email.com"
            value={email}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setEmail(e.target.value)
            }
          />
          <InputField
            label="Senha"
            type="password"
            placeholder="Sua senha"
            value={password}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setPassword(e.target.value)
            }
          />
        </div>

        <button type="submit" className="btn btn-full" disabled={loading}>
          {loading ? "Entrando..." : "Entrar"}
        </button>
      </form>

      <div className="toast-container">
        {toasts.map((t) => (
          <Toast
            key={t.id}
            message={t.message}
            type={t.type}
            onClose={() =>
              setToasts((prev) => prev.filter((toast) => toast.id !== t.id))
            }
          />
        ))}
      </div>
    </>
  );
}
