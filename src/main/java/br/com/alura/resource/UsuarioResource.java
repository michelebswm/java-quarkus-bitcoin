package br.com.alura.resource;

import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Usuario;
import br.com.alura.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/usuarios")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Usuario usuario) throws ApplicationServiceException {
        usuarioService.create(usuario);
    }
}
