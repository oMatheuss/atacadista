package com.example.atacadista.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException implements ErrorResponse {
    private final List<String> errors = new ArrayList<>();

    public BusinessException(String message) {
        errors.add(message);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ProblemDetail getBody() {
        var problem = ProblemDetail.forStatusAndDetail(getStatusCode(), "Erro ao processar requisição");
        problem.setProperty("errors", errors);
        return problem;
    }

    public void addError(String message) {
        errors.add(message);
    }
}
