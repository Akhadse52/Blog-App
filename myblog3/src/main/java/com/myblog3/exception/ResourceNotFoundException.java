package com.myblog3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fliedName;
    private long fliedValue;

    public ResourceNotFoundException(String resourceName, String fliedName, long fliedValue) {
        super(String.format("%s is not found with %s : '%s'",resourceName,fliedName,fliedValue));
        this.resourceName = resourceName;
        this.fliedName = fliedName;
        this.fliedValue = fliedValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFliedName() {
        return fliedName;
    }

    public long getFliedValue() {
        return fliedValue;
    }
}
