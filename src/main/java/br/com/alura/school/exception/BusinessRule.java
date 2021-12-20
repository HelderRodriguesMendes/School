package br.com.alura.school.exception;

public class BusinessRule extends RuntimeException{

    public BusinessRule(String message) {
        super(message);
    }
}
