package br.com.alura.dto;

import br.com.alura.model.Ordem;
import br.com.alura.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@RegisterForReflection
public class OrdemResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Double preco;
    private String tipo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private String status;
    private Usuario userId;

    public OrdemResponseDTO(){}

    /**
     * Construtor que transforma um objeto Entity em um objeto VO.
     */
    public OrdemResponseDTO(Ordem ordem){
        this.id = ordem.getId();
        this.preco = ordem.getPreco();
        this.tipo = ordem.getTipo();
        this.data = ordem.getData() != null ? ordem.getData() : null;
        this.status = ordem.getStatus() != null ? ordem.getStatus() : null;
        this.userId = ordem.getUserId();
    }

    /**
     * Transforma um objeto VO em um objeto Entity.
     */
    public Ordem toEntity(){
        Ordem ord = new Ordem();
        ord.setId(id);
        ord.setPreco(preco);
        ord.setTipo(tipo);
        ord.setData(data);
        ord.setStatus(status);
        ord.setUserId(userId);
        return ord;
    }

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
    public String toString() {
        return "OrdemResponseDTO[" +
                "id=" + id +
                ", preco=" + preco +
                ", tipo='" + tipo + '\'' +
                ", data=" + data +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ']';
    }
}
