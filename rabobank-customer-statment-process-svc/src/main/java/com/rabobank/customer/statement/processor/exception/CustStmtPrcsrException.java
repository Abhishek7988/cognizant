package com.rabobank.customer.statement.processor.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception Class
 * @author Abhishek
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class CustStmtPrcsrException extends RuntimeException {
	private static final long serialVersionUID = -7752482715841636072L;
	
	private Object response;
}
