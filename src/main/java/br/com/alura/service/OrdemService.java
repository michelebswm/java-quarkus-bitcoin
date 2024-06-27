package br.com.alura.service;

import br.com.alura.dto.OrdemRequestDTO;
import br.com.alura.model.Ordem;
import br.com.alura.model.Usuario;
import br.com.alura.repository.OrdemRespository;
import br.com.alura.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@ApplicationScoped
public class OrdemService {
    @Inject
    OrdemRespository ordemRespository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public void create(OrdemRequestDTO ordemDto){
        Usuario usuario = usuarioRepository.findById(ordemDto.userId());
        if ( usuario == null) {
            throw new IllegalArgumentException("Usuário not found.!");
        }
        Ordem ordem = new Ordem();
        ordem.setPreco(ordemDto.preco());
        ordem.setTipo(ordemDto.tipo());
        ordem.setData(LocalDate.now());
        ordem.setStatus("ENVIADA");
        ordem.setUserId(usuario);

        ordemRespository.persist(ordem);
    }
}
