package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Bitcoin;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import service.BitcoinService;

import java.util.List;

@Path("/api/bitcoins")
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
