package com.vistagram.app.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFound() {
        ResponseEntity<?> response = handler.handleResourceNotFoundException(
                new ResourceNotFoundException("User", "id", "1"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleUnauthorizedException_ShouldReturnUnauthorized() {
        ResponseEntity<?> response = handler.handleUnauthorizedException(
                new UnauthorizedException("Not authorized"));
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void handleBadRequestException_ShouldReturnBadRequest() {
        ResponseEntity<?> response = handler.handleBadRequestException(
                new BadRequestException("Invalid request"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
