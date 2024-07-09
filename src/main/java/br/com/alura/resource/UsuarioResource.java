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

@Path("/usuarios")
@Tag(name = "usuarios", description = "Operações referentes aos usuários")
@APIResponse(responseCode = "401", description = "Acesso não autorizado")
@APIResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
@APIResponse(responseCode = "500", description = "Tratamento de erro interno",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MessageService.class)))
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Inclusão de registro")
    @APIResponse(responseCode = "201", description = "Registro incluído.")
    @APIResponse(responseCode = "400", description = "Parâmetro(s) inválido(s).")
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
    @Operation(description = "Listagem paginada de registros")
    @Parameter(name = "page", description = "Página atual", in = ParameterIn.QUERY)
    @Parameter(name = "size", description = "Quantidade de ocorrências que devem ser retornadas", in = ParameterIn.QUERY)
    @Parameter(name = "sort", description = "Nome do atributo utilizado para ordenação das ocorrências", in = ParameterIn.QUERY)
    @Parameter(name = "order", description = "Sentido da ordenação: ASC|DESC", in = ParameterIn.QUERY)
    @APIResponse(responseCode = "200", description = "Ok",
            headers = @Header(name = "Pagination-Count", description = "Quantidade total de páginas",
                    schema = @Schema(type = SchemaType.INTEGER)))
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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Alteração de registro")
    @APIResponse(responseCode = "204", description = "Registro alterado")
    @APIResponse(responseCode = "400", description = "Parâmetro(s) inválido(s)")
    public Response alterar(Usuario usuario){
        try{
            usuarioService.alterar(usuario);
        } catch (ApplicationServiceException ase) {
            return Response.status(ase.getStatusCode()).entity(
                    new MessageService(ase.getMessage(), ase.getErrorList())).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Exclusão de registro")
    @Parameter(name = "id", description = "Identificador do registro", in = ParameterIn.PATH)
    @APIResponse(responseCode = "204", description = "Registro excluído")
    @APIResponse(responseCode = "400", description = "Parâmetro(s) inválido(s)")
    public Response excluir(@PathParam("id") Long id){
        try {
            usuarioService.excluir(id);
        } catch (ApplicationServiceException ase) {
            return Response.status(ase.getStatusCode()).entity(new MessageService(ase.getMessage(), ase.getErrorList())).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
