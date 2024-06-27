package br.com.alura.resource;

import br.com.alura.dto.OrdemRequestDTO;
import br.com.alura.model.Ordem;
import br.com.alura.service.OrdemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/ordens")
public class OrdemResource {

    @Inject
    OrdemService ordemService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(OrdemRequestDTO ordem){
        ordemService.create(ordem);
    }
}
