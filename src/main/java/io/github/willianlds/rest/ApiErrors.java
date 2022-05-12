package io.github.willianlds.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String messageError){
        this.errors = Arrays.asList(messageError);
    }
}
