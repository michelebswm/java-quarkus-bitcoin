package br.com.alura.exception;


import br.com.alura.util.message.MessageBundle;
import br.com.alura.util.message.MessageServiceError;

import java.io.Serial;
import java.util.List;

/**
 * Encapsula exceções tratadas pela aplicação.
 */
public class ApplicationServiceException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    private Throwable rootCause = null;
    private Integer statusCode = 500;
    private List<MessageServiceError> errorList;

    public ApplicationServiceException(String messageKeyLoc){
        super(MessageBundle.getMessage(messageKeyLoc));
    }

    public ApplicationServiceException(String messageKeyLoc, Integer statusCode){
        super(MessageBundle.getMessage(messageKeyLoc));
        this.statusCode = statusCode;
    }

    public ApplicationServiceException(String messageKeyLoc, Integer statusCode, List<MessageServiceError> errorList) {
        super(MessageBundle.getMessage(messageKeyLoc));
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    public ApplicationServiceException(String messageKeyLoc, String[] parameters) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
    }


    public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
        this.statusCode = statusCode;
    }


    public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode, List<MessageServiceError> errorList) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
        this.statusCode = statusCode;
        this.errorList = errorList;
    }


    public ApplicationServiceException(Throwable causa) {
        super(MessageBundle.getMessage("message.default"));
        setRootCause(causa);
    }


    public ApplicationServiceException(Throwable causa, Integer statusCode) {
        super(MessageBundle.getMessage("message.default"));
        setRootCause(causa);
        this.statusCode = statusCode;
    }


    public ApplicationServiceException(Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
        super(MessageBundle.getMessage("message.default"));
        setRootCause(causa);
        this.statusCode = statusCode;
        this.errorList = errorList;
    }


    public ApplicationServiceException(String messageKeyLoc, Throwable causa) {
        super(MessageBundle.getMessage(messageKeyLoc));
        setRootCause(causa);
    }


    public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode) {
        super(MessageBundle.getMessage(messageKeyLoc));
        setRootCause(causa);
        this.statusCode = statusCode;
    }


    public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
        super(MessageBundle.getMessage(messageKeyLoc));
        setRootCause(causa);
        this.statusCode = statusCode;
        this.errorList = errorList;
    }


    public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
        setRootCause(rootCause);
    }


    public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
        setRootCause(rootCause);
        this.statusCode = statusCode;
    }


    public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode, List<MessageServiceError> errorList) {
        super(MessageBundle.getMessage(messageKeyLoc, parameters));
        setRootCause(rootCause);
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    public Throwable getRootCause() {
        return rootCause;
    }

    public void setRootCause(Throwable rootCause) {
        this.rootCause = rootCause;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<MessageServiceError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<MessageServiceError> errorList) {
        this.errorList = errorList;
    }
}
