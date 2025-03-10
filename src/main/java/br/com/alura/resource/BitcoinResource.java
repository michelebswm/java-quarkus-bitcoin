package br.com.alura.resource;

import br.com.alura.service.BitcoinService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import br.com.alura.model.Bitcoin;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/bitcoins")
public class BitcoinResource {
    @Inject
    @RestClient
    BitcoinService bitcoinService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bitcoin> listar(){
        return bitcoinService.listar();
    }
}
