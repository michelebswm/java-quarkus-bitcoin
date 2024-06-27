package br.com.alura.dto;

public record OrdemRequestDTO(
        Double preco,
        String tipo,
        Long userId
) {
}
