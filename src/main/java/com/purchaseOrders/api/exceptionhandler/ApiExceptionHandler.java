package com.purchaseOrders.api.exceptionhandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.purchaseOrders.api.domain.exception.EntityInUseException;
import com.purchaseOrders.api.domain.exception.EntityNoFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	/*----------------------------------------------------------------------------------------------------------*/
	@ExceptionHandler(EntityNoFoundException.class)
	public ResponseEntity<?> handleEntityNoFoundException(EntityNoFoundException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorType errorType = ErrorType.ENTITY_NOT_FOUND;
		String detail = ex.getMessage();
		
		Error error = createErrorBuilder(status, errorType, detail).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	/*-------------------------------------------------------------------------------------------------------------*/
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handleEntityNotFoundException(NoSuchElementException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorType errorType = ErrorType.ENTITY_NOT_FOUND;
		String detail = ex.getMessage();
		
		Error error = createErrorBuilder(status, errorType, detail).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	/*--------------------------------------------------------------------------------------------------------------*/
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> HandleEntityInUseException(EntityInUseException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorType errorType = ErrorType.ENTITY_IN_USE;
		String detail = ex.getMessage();
		
		Error error = createErrorBuilder(status, errorType, detail).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	
	/*Tratando quando enviamos um objeto faltando um atributo por exemplo --------------------------------------------------*/
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
			if(rootCause instanceof InvalidFormatException) {
				return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
			}
		
		ErrorType errorType = ErrorType.MESSAGE_INCOMPREHENSIBLE;
		String detail = ex.getMessage();
		Error error = createErrorBuilder(status, errorType, detail).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	/*tratando a excessao de quando enviamos um valor int quando deveriamos enviar uma String (method PUT)------------------*/
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ErrorType errorType = ErrorType.MESSAGE_INCOMPREHENSIBLE;
		String detail = String.format("Property '%s' has been assigned a value '%s' "
				+ "that is of an invalid type. Correct and enter a value compatible with the type %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
		Error error = createErrorBuilder(status, errorType, detail).build();;
		return handleExceptionInternal(ex, error, headers, status, request);
	}
	
	/*Metodo que vai ser usado en todos os metodos de tratamento para construir um problema----------------------------------*/

	private Error.ErrorBuilder createErrorBuilder(HttpStatusCode status, 
			ErrorType errorType, String detail){
		
		return Error.builder()
				.status(status.value())
				.type(errorType.getUri())
				.title(errorType.getTitle())
				.detail(detail);
	}

}
