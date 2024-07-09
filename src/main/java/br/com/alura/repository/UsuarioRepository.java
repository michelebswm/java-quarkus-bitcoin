package br.com.alura.repository;

import br.com.alura.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public void validAndMerge(@Valid Usuario usuario){
        getEntityManager().merge(usuario);
    }
}
