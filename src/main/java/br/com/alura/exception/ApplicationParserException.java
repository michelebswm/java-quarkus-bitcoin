package br.com.alura.exception;

import br.com.alura.util.message.MessageBundle;
import br.com.alura.util.message.MessageService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationParserException implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new MessageService(MessageBundle.getMessage("message.parsererror")))
                .build();
    }
}
