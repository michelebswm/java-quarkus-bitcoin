package br.com.alura.dto;

import br.com.alura.model.Usuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serial;
import java.io.Serializable;

@RegisterForReflection
public class UsuarioResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String cpf;
    private String username;
    private String role;

    public UsuarioResponseDTO(){}

    //Construtor que transforma um objeto Entity em um objeto
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.username = usuario.getUsername();
        this.role = usuario.getRole();
    }

    //Construtor que transforma um objeto Entity em um objeto
    public Usuario toEntity(){
        Usuario user = new Usuario();
        user.setId(id);
        user.setNome(nome);
        user.setCpf(cpf);
        user.setUsername(username);
        user.setRole(role);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UsuarioResponseDTO[" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ']';
    }
}
