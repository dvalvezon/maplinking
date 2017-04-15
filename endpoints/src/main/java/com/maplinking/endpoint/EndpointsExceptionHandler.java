package com.maplinking.endpoint;

import com.maplinking.endpoint.entity.ErrorJson;
import com.maplinking.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EndpointsExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointsExceptionHandler.class);

    @ExceptionHandler({ValidationException.class, ServiceException.class})
    protected ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorJson(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleUnpredictedException(RuntimeException ex, WebRequest request) {
        LOGGER.error("RuntimeException caught on Global ExceptionHandler. message=" + ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorJson(ex.getMessage()), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
