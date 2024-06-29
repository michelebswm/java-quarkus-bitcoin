package br.com.alura.exception;

import br.com.alura.util.message.MessageBundle;
import br.com.alura.util.message.MessageService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

@Provider
public class ApplicationCircuitBreakerOpenException implements ExceptionMapper<CircuitBreakerOpenException> {
    @Override
    public Response toResponse(CircuitBreakerOpenException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new MessageService(MessageBundle.getMessage("message.circuitbreaker")))
                .build();
    }
}
