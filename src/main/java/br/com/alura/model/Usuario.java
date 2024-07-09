package br.com.alura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import io.smallrye.common.constraint.NotNull;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@UserDefinition
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
    @Username
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    @NotNull
    @Password
    private String password;

    @Column(name = "role", nullable = false)
    @NotNull
    @Roles
    private String role;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Ordem> ordens;

    public Usuario(){

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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String passwordCripto(String password){
        return BcryptUtil.bcryptHash(password);
    }

    public boolean checkPassword(String password){
        return BcryptUtil.matches(password, this.password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(Set<Ordem> ordens) {
        this.ordens = ordens;
    }

    public static String validaUsername(String username){
        if(username.equals("alura")){
            return "admin";
        }return "user";
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
