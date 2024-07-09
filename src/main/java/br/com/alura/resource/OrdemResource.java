package br.com.alura.resource;

import br.com.alura.dto.OrdemRequestDTO;
import br.com.alura.dto.OrdemResponseDTO;
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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/ordens")
@Tag(name = "ordens", description = "Ordens de Compra e Venda")
@APIResponse(responseCode = "401", description = "Acesso não autorizado")
@APIResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
@APIResponse(responseCode = "500", description = "Tratamento de erro interno",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MessageService.class)))
public class OrdemResource {

    @Inject
    OrdemService ordemService;

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Inclusão de registro")
    @APIResponse(responseCode = "201", description = "Registro incluído.")
    @APIResponse(responseCode = "400", description = "Parâmetro(s) inválido(s).")
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
    @Operation(description = "Listagem paginada de registros")
    @Parameter(name = "page", description = "Página atual", in = ParameterIn.QUERY)
    @Parameter(name = "size", description = "Quantidade de ocorrências que devem ser retornadas", in = ParameterIn.QUERY)
    @Parameter(name = "sort", description = "Nome do atributo utilizado para ordenação das ocorrências", in = ParameterIn.QUERY)
    @Parameter(name = "order", description = "Sentido da ordenação: ASC|DESC", in = ParameterIn.QUERY)
    @APIResponse(responseCode = "200", description = "Ok",
            headers = @Header(name = "Pagination-Count", description = "Quantidade total de páginas",
                    schema = @Schema(type = SchemaType.INTEGER)))
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
