package br.com.alura.resource;

import br.com.alura.dto.OrdemRequestDTO;
import br.com.alura.dto.OrdemResponseDTO;
import br.com.alura.dto.UsuarioResponseDTO;
import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.service.OrdemService;
import br.com.alura.util.message.MessageService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@Path("/ordens")
public class OrdemResource {

    @Inject
    OrdemService ordemService;

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context SecurityContext securityContext, OrdemRequestDTO ordem){
        try{
            ordemService.create(securityContext, ordem);
        }catch (ApplicationServiceException ase){
            System.out.println(ase.getMessage());
            return Response.status(ase.getStatusCode()).entity(new MessageService(ase.getMessage(),
                    ase.getErrorList())).build();
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response listarOrdens(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("tipo") String sortField,
            @QueryParam("order") @DefaultValue("DESC") String sortOrder
    ){
        Page _page = Page.of(page, size);
        Sort sort = "DESC".equals(sortOrder) ? Sort.descending(sortField) : Sort.ascending(sortField);

        try{
            List<OrdemResponseDTO> ordens = ordemService.listar(_page, sort).stream().map(OrdemResponseDTO::new).collect(Collectors.toList());
            return Response.ok(ordens).header("Pagination-Count", ordemService.obterQuantidade()).build();

        } catch (ApplicationServiceException ase) {
            return Response.status(ase.getStatusCode()).entity(new MessageService(ase.getMessage(), ase.getErrorList())).build();
        }
    }
}
