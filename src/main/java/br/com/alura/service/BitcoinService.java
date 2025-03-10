package br.com.alura.service;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import br.com.alura.model.Bitcoin;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/bitcoins")
@RegisterRestClient(configKey = "bitcoin.api")
public interface BitcoinService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bitcoin> listar();
}
