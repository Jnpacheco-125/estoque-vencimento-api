package me.dio.controller.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " com id " + id + " não encontrado.");
    }
}
