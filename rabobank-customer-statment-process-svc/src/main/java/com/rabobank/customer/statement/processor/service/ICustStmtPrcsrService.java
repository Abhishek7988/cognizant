package com.rabobank.customer.statement.processor.service;

import java.util.List;

import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;

/**
 * Customer Validation Service Interface.
 * @author Abhishek
 *
 */
public interface ICustStmtPrcsrService {
	/**
	 * Validates the customer records.
	 * @param custStmtRecord
	 * @return
	 */
	CustStmtPrcsrResponse validateCustStmt(List<CustStmtRecord> custStmtRecord);

}
