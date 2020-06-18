package com.jhonatan.serviceorderapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jhonatan.serviceorderapi.domain.exception.Exceptions;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(Exceptions.class)
	public ResponseEntity<Object> handleExceptions(Exceptions ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problems problem = new Problems();
		problem.setStatus(status.value());
		problem.setTitle(ex.getMessage());
		problem.setDatahora(LocalDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ArrayList<Problems.Campo> campos = new ArrayList<Problems.Campo>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getObjectName();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
						
			campos.add(new Problems.Campo(name, message));
		}
		
		Problems problem = new Problems();
		problem.setStatus(status.value());
		problem.setTitle("Um ou mais campos estão inválidos, Preencha corretamente e tente novamente");
		problem.setDatahora(LocalDateTime.now());
		problem.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
}
