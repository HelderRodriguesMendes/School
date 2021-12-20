package br.com.alura.school.exception;

public class BusinessRuleReport extends RuntimeException{
    public BusinessRuleReport(String message) {
        super(message);
    }
}
