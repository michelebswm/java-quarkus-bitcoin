package br.com.alura.resource;

import br.com.alura.dto.UsuarioResponseDTO;
import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Usuario;
import br.com.alura.service.UsuarioService;
import br.com.alura.util.message.MessageService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("nome") String sortField,
            @QueryParam("order") @DefaultValue("DESC") String sortOrder
    ){
        Page _page = Page.of(page, size);
        Sort sort = "DESC".equals(sortOrder) ? Sort.descending(sortField) : Sort.ascending(sortField);

        try{
            List<UsuarioResponseDTO> usuarios = usuarioService.listar(_page, sort).stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
            return Response.ok(usuarios).header("Pagination-Count", usuarioService.obterQuantidade()).build();

        } catch (ApplicationServiceException ase) {
            return Response.status(ase.getStatusCode()).entity(new MessageService(ase.getMessage(), ase.getErrorList())).build();
        }
    }
}
