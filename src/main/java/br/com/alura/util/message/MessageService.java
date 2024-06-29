package br.com.alura.util.message;


import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.ConstraintViolationException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

//Classe que encapsula lista de menssagens de erro.
@RegisterForReflection
public class MessageService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
    private List<MessageServiceError> errors;

    public MessageService(String message){
        super();
        this.message = message;
    }

    public MessageService(String message, List<MessageServiceError> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public MessageService(ConstraintViolationException cve){
        super();
        this.errors = cve.getConstraintViolations().stream().map(violation -> new MessageServiceError(
                violation.getMessage(),
                violation.getPropertyPath().toString()
            )
        ).toList();
        this.message = MessageBundle.getMessage("message.parametrosnaoinformados");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MessageServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<MessageServiceError> errors) {
        this.errors = errors;
    }
}
