package br.com.alura.resource;

import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Usuario;
import br.com.alura.service.UsuarioService;
import br.com.alura.util.message.MessageService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Usuario usuario)  {
        try {
            usuarioService.create(usuario);
        }catch (ApplicationServiceException ase){
            return Response.status(ase.getStatusCode()).entity(new MessageService(ase.getMessage(),
                    ase.getErrorList())).build();
        }

        return Response.status(Response.Status.CREATED).build();
    }
}
