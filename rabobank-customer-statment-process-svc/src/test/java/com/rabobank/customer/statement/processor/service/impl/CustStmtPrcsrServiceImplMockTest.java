/**
 * 
 */
package com.rabobank.customer.statement.processor.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rabobank.customer.statement.processor.constants.CustStmtPrcsrConstant;
import com.rabobank.customer.statement.processor.exception.CustStmtPrcsrException;
import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;

/**
 * @author Abhishek
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CustStmtPrcsrServiceImplMockTest {
	
	@Mock
	CustStmtPrcsrServiceImpl custStmtPrcsrServiceImpl;
	
	

	/**
	 * Test method for {@link com.rabobank.customer.statement.processor.service.impl.CustStmtPrcsrServiceImpl#validateCustStmt(java.util.List)}.
	 */
	@Test
	public void testValidateCustStmt() {
		final List<CustStmtRecord> custStmtRecordList = new ArrayList<>();
		final CustStmtPrcsrResponse custStmtPrcsrResponse = new CustStmtPrcsrResponse(CustStmtPrcsrConstant.SUCCESSFUL, new ArrayList<CustStmtRecord>());
		
		when(custStmtPrcsrServiceImpl.validateCustStmt(custStmtRecordList)).
			thenReturn(custStmtPrcsrResponse);
		
		assertEquals(CustStmtPrcsrConstant.SUCCESSFUL, 
				custStmtPrcsrServiceImpl.validateCustStmt(custStmtRecordList).getResult());
		assertEquals(0, 
				custStmtPrcsrServiceImpl.validateCustStmt(custStmtRecordList).getErrorRecords().size());
	}
	
	/**
	 * Test method for {@link com.rabobank.customer.statement.processor.service.impl.CustStmtPrcsrServiceImpl#validateCustStmt(java.util.List)}.
	 */
	@Test(expected = CustStmtPrcsrException.class)
	public void testValidateCustStmtException() {
		final List<CustStmtRecord> custStmtRecordList = new ArrayList<>();
		when(custStmtPrcsrServiceImpl.validateCustStmt(custStmtRecordList)).
			thenThrow(CustStmtPrcsrException.class);
		
		custStmtPrcsrServiceImpl.validateCustStmt(custStmtRecordList);
	}

}
