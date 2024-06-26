package br.com.alura.service;

import br.com.alura.model.Usuario;
import br.com.alura.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public void create(Usuario usuario){
        usuarioRepository.persist(usuario);
    }
}
