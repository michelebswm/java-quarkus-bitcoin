package br.com.alura.model;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "usuario",
        schema = "bitcoin",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"cpf"},
                        name = "uk_cpf_usuario"
                )
        }
)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    @NotNull
    private String nome;

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    @NotNull
    private String cpf;

    @Column(name = "username", length = 50, nullable = false)
    @NotNull
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    @NotNull
    private String password;

    public Usuario(){

    }

    public Long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(cpf, usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
