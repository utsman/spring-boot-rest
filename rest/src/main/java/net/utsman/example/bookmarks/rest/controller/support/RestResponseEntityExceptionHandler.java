package net.utsman.example.bookmarks.rest.controller.support;

import lombok.extern.slf4j.Slf4j;
import net.utsman.example.bookmarks.core.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { UserNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse notFoundUserError(UserNotFoundException e, NativeWebRequest request) {
        return createErrorResponse(e, request);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse internalServerError(Exception e, NativeWebRequest request) {
        return createErrorResponse(e, request);
    }

    private ErrorResponse createErrorResponse(Exception e, NativeWebRequest request) {
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        log.error(httpServletRequest.getRequestURL().toString(), e);
        return new ErrorResponse(e.getMessage());
    }

}
