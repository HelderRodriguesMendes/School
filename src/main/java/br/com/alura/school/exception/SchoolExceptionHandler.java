package br.com.alura.school.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class SchoolExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_LENGTH = "Size";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<MessagesError> messagesErrors = getListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, messagesErrors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<MessagesError> getListErrors(BindingResult bindingResult){
        List<MessagesError> messagesErrors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String msgUser = MsgErrorUser(fieldError);
            String msgDev = fieldError.toString();
            messagesErrors.add(new MessagesError(msgUser, msgDev));
        });
        return messagesErrors;
    }

    private String MsgErrorUser(FieldError fieldError){
        if(fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)){
            return fieldError.getDefaultMessage().concat(" is required");
        }

        if(fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH)){
            return fieldError.getDefaultMessage().concat(String.format(" must have at most %s characters",
                    fieldError.getArguments()[1]));
        }
        return fieldError.getField();
    }

    @ExceptionHandler(BusinessRule.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRule ex, WebRequest request){
        String msgUser = ex.getMessage();
        String msgDev = ex.getMessage();
        List<MessagesError> messagesErrors = Arrays.asList(new MessagesError(msgUser, msgDev));
        return handleExceptionInternal(ex, messagesErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessRuleReport.class)
    public ResponseEntity<Object> handleBusinessRuleReportException(BusinessRuleReport ex, WebRequest request){
        String msgUser = ex.getMessage();
        String msgDev = ex.getMessage();
        List<MessagesError> messagesErrors = Arrays.asList(new MessagesError(msgUser, msgDev));
        return handleExceptionInternal(ex, messagesErrors, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }
}
