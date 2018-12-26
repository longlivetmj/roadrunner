package com.tmj;

import com.tmj.tms.config.datalayer.modal.SystemProperty;
import com.tmj.tms.config.datalayer.modal.TableConstraint;
import com.tmj.tms.config.datalayer.service.SystemPropertyService;
import com.tmj.tms.config.datalayer.service.TableConstraintService;
import com.tmj.tms.utility.ApiError;
import com.tmj.tms.utility.ResponseObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.RollbackException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final TableConstraintService tableConstraintService;
    private final SystemPropertyService systemPropertyService;

    @Autowired
    public GlobalExceptionHandler(TableConstraintService tableConstraintService,
                                  SystemPropertyService systemPropertyService) {
        this.tableConstraintService = tableConstraintService;
        this.systemPropertyService = systemPropertyService;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        this.logException(ex);
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                         final HttpHeaders headers,
                                                         final HttpStatus status,
                                                         final WebRequest request) {
        this.logException(ex);
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                        final HttpHeaders headers,
                                                        final HttpStatus status,
                                                        final WebRequest request) {
        this.logException(ex);
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatus status,
                                                                     final WebRequest request) {
        this.logException(ex);
        final String error = ex.getRequestPartName() + " part is missing";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                          final HttpHeaders headers,
                                                                          final HttpStatus status,
                                                                          final WebRequest request) {
        this.logException(ex);
        final String error = ex.getParameterName();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                                                                   final WebRequest request) {
        this.logException(ex);
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleFormatExceptions(final NumberFormatException ex,
                                                         final WebRequest request) {
        this.logException(ex);
        final String error = " cannot convert to Number";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<Object> handleFormatExceptions(final IllegalStateException ex,
                                                         final WebRequest request) {
        this.logException(ex);
        final String error = "Authetication Error. Session Not found";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

/*    @ExceptionHandler({BindException.class})
    public ResponseEntity<Object> handleSpringBinding(final NumberFormatException ex,
                                                         final WebRequest request) {
        this.logException(ex);
        final String error = " cannot convert to Number";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }*/


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
                                                            final WebRequest request) {
        this.logException(ex);
        final String error = "Operation failed due to the violation of constraint " + ex.getConstraintName();
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({RollbackException.class})
    public ResponseEntity<Object> handleTransactionRollback(final RollbackException ex,
                                                            final WebRequest request) {
        this.logException(ex);
        Throwable throwable = ExceptionUtils.getRootCause(ex);
        if (throwable instanceof SQLIntegrityConstraintViolationException) {
            final String error = "Operation failed due to the violtion of constraint " + ((ConstraintViolationException) throwable).getConstraintName();
            final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        } else {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        this.logException(ex);
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                         final HttpHeaders headers, final HttpStatus status,
                                                                         final WebRequest request) {
        this.logException(ex);
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected String handleAccessDeniedException() {
        return "accessDenied";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ModelAndView handleNotFoundRequest(HttpServletRequest req, Exception ex) {
        return new ModelAndView("/404");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatus status,
                                                                     final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(final NullPointerException ex) {
        Throwable throwable = ExceptionUtils.getRootCause(ex);
        if (throwable instanceof NullPointerException) {
            final String error = "Mandatory Data are missing";
            final ResponseObject responseObject = new ResponseObject(error, false, null);
            return new ResponseEntity<Object>(responseObject, new HttpHeaders(), HttpStatus.CREATED);
        } else {
            this.logException(ex);
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred ", ExceptionUtils.getRootCauseMessage(ex));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        Throwable throwable = ExceptionUtils.getRootCause(ex);
        if (throwable instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException violationException = (SQLIntegrityConstraintViolationException) throwable;
            TableConstraint tableConstraint = this.constraintFinder(violationException);
            final String error = tableConstraint.getDescription();
            final ResponseObject responseObject = new ResponseObject(error, false, null);
            return new ResponseEntity<Object>(responseObject, new HttpHeaders(), HttpStatus.CREATED);
        } else {
            this.logException(ex);
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred ", ExceptionUtils.getRootCauseMessage(ex));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    private void logException(Exception e) {
        logger.info(e.getClass().getName());
        logger.info(ExceptionUtils.getRootCause(e));
        e.printStackTrace();
    }

    private TableConstraint constraintFinder(SQLIntegrityConstraintViolationException violationException) {
        TableConstraint tableConstraint = null;
        try {
            String localizedMessage = violationException.getLocalizedMessage();
            SystemProperty systemProperty = this.systemPropertyService.findByPropertyName("DB_SCHEMA");
            localizedMessage = localizedMessage.split("[\\(\\)]")[1].replace(systemProperty.getPropertyValue() + ".", "");
            tableConstraint = this.tableConstraintService.findOne(localizedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConstraint;
    }

}

