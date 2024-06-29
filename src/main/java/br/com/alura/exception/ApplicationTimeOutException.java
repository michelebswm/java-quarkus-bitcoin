package br.com.alura.exception;

import br.com.alura.util.message.MessageBundle;
import br.com.alura.util.message.MessageService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.concurrent.TimeoutException;

@Provider
public class ApplicationTimeOutException implements ExceptionMapper<TimeoutException> {

    @Override
    public Response toResponse(TimeoutException e) {
        return Response.status(Response.Status.REQUEST_TIMEOUT)
                .entity(new MessageService(MessageBundle.getMessage("message.timeout")))
                .build();
    }
}
