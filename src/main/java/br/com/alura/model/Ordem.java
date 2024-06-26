package br.com.alura.model;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "ordem",
        schema = "bitcoin"
)
public class Ordem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "preco", nullable = false)
    @NotNull
    private Double preco;

    @Column(name = "tipo", length = 20, nullable = false)
    @NotNull
    private String tipo;

    @Column(name = "data", nullable = false)
    @NotNull
    private LocalDate data;

    @Column(name = "status", length = 30, nullable = false)
    @NotNull
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Usuario userId;


    public Ordem(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUserId() {
        return userId;
    }

    public void setUserId(Usuario userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordem ordem = (Ordem) o;
        return Objects.equals(id, ordem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ordem{" +
                "id=" + id +
                ", preco=" + preco +
                ", tipo='" + tipo + '\'' +
                ", data=" + data +
                ", status='" + status + '\'' +
                ", user_id=" + userId +
                '}';
    }
}
