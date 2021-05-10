package com.rabobank.customer.statement.processor.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rabobank.customer.statement.processor.constants.Constant;
import com.rabobank.customer.statement.processor.exception.CustStmtPrcsrException;
import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;
import com.rabobank.customer.statement.processor.service.ICustStmtPrcsrService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/**
 * Service class for validating Customer Records.
 * @author Abhishek
 *
 */
public class CustStmtPrcsrServiceImpl implements ICustStmtPrcsrService {
	/**
	 * Validates the customer records.
	 * @param custStmtRecord
	 * @return
	 */
	
	public CustStmtPrcsrResponse validateCustStmt(final List<CustStmtRecord> custStmtRecord) {
		log.info("Entering {}.{}", getClass().getName(), "validate()");
		
		final List<CustStmtRecord> duplicateReference = validateDuplicate(custStmtRecord);
		final List<CustStmtRecord> incorrectEndBalance = validateEndbalance(custStmtRecord);
		
		if(!duplicateReference.isEmpty() && !incorrectEndBalance.isEmpty()) {
			duplicateReference.addAll(incorrectEndBalance);
			throw new CustStmtPrcsrException(new CustStmtPrcsrResponse(Constant.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, duplicateReference));
		} else {
			if(!duplicateReference.isEmpty()) {
				throw new CustStmtPrcsrException(new CustStmtPrcsrResponse(Constant.DUPLICATE_REFERENCE, duplicateReference));
			}
			
			if(!incorrectEndBalance.isEmpty()) {
				throw new CustStmtPrcsrException(new CustStmtPrcsrResponse(Constant.INCORRECT_END_BALANCE, incorrectEndBalance));
			}
		}
		
		log.info("Exiting {}.{}", getClass().getName(), "validate()");
		return new CustStmtPrcsrResponse(Constant.SUCCESSFUL, new ArrayList<CustStmtRecord>());
	}
	/**
	 * validates customer records for Duplicate records wrt reference no.
	 * @param custStmtRecords
	 * @return
	 */
	private List<CustStmtRecord> validateDuplicate (final List<CustStmtRecord> custStmtRecords) {
		log.info("Entering {}.{}", getClass().getName(), "validateDuplicate()");
		final List<CustStmtRecord> duplicateReference = custStmtRecords
				.parallelStream()
					.filter(e -> Collections.frequency(custStmtRecords, e) > 1)
					.collect(Collectors.toList());
					;
			
		log.info("duplicateReference {}", duplicateReference);
			
		log.info("Exiting {}.{}", getClass().getName(), "validateDuplicate()");
		return duplicateReference;
	}
	/**
	 * validates endbalance integrity with startBalance and endBalance.
	 * @param custStmtRecords
	 * @return
	 */
	private List<CustStmtRecord> validateEndbalance (final List<CustStmtRecord> custStmtRecords) {
		log.info("Entering {}.{}", getClass().getName(), "validateDuplicate()");
		final List<CustStmtRecord> incorrectEndBalance = custStmtRecords
				.parallelStream()
					.filter(custStmtRecord -> isBalanceIntegrity(custStmtRecord))
					.collect(Collectors.toList());
					;
			
		log.info("incorrectEndBalance {}", incorrectEndBalance);
			
		log.info("Exiting {}.{}", getClass().getName(), "validateDuplicate()");
		return incorrectEndBalance;
	}
	/**
	 * check if the balance integrity is maintained for endBalance.
	 * @param custStmtRecord
	 * @return
	 */
	private boolean isBalanceIntegrity(final CustStmtRecord custStmtRecord) {
		return !(custStmtRecord.getStartBalance().add(custStmtRecord.getMutation()) 
				).equals(custStmtRecord.getEndBalance());
	}
}
