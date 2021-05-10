package com.rabobank.customer.statement.processor.advice;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabobank.customer.statement.processor.exception.CustStmtPrcsrException;
import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception handler Advice Class.
 * @author Abhishek
 *
 */
@Slf4j
@ControllerAdvice
public class ControllerAdviceCustom extends ResponseEntityExceptionHandler {
	/**
	 * Exception handler for any uncaught runtime exception.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		log.info("Entering {}.{}", getClass().getName(), "handleGeneralException()");
		final CustStmtPrcsrResponse custStmtPrcsrResponse = new CustStmtPrcsrResponse("INTERNAL_SERVER_ERROR", new ArrayList<CustStmtRecord>());
		log.info("Exiting {}.{}", getClass().getName(), "handleGeneralException()");
		return new ResponseEntity<>(custStmtPrcsrResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Exception Response handler for Bad Request.
	 */
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("Entering {}.{}", getClass().getName(), "handleHttpMessageNotReadable()");
		final CustStmtPrcsrResponse custStmtPrcsrResponse = new CustStmtPrcsrResponse("BAD_REQUEST", new ArrayList<CustStmtRecord>());
		log.info("Exiting {}.{}", getClass().getName(), "handleHttpMessageNotReadable()");
		return new ResponseEntity<>(custStmtPrcsrResponse, HttpStatus.BAD_REQUEST);
    }
	/**
	 * Exception handler for validation error response.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CustStmtPrcsrException.class)
	protected ResponseEntity<Object> handleCustStmtPrcsrException(CustStmtPrcsrException ex, WebRequest request) {
		log.info("Entering {}.{}", getClass().getName(), "handleGeneralException()");
		log.info("Exiting {}.{}", getClass().getName(), "handleGeneralException()");
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}
}
