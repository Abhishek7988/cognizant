/**
 * 
 */
package com.rabobank.customer.statement.processor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rabobank.customer.statement.processor.constants.Constant;
import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;
import com.rabobank.customer.statement.processor.service.ICustStmtPrcsrService;

/**
 * @author Abhishek
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CustStmtPrcsrControllerTest {
	
	@InjectMocks
	private CustStmtPrcsrController custStmtPrcsrController;
	
	@Mock
	private ICustStmtPrcsrService iCustStmtPrcsrService;
	

	/**
	 * Test method for {@link com.rabobank.customer.statement.processor.controller.CustStmtPrcsrController#validateCustStmt(java.util.List)}.
	 */
	@Test
	public void testValidateCustStmt() {
		final List<CustStmtRecord> custStmtRecordList = new ArrayList<>();
		final CustStmtPrcsrResponse custStmtPrcsrResponse = new CustStmtPrcsrResponse(Constant.SUCCESSFUL, new ArrayList<CustStmtRecord>());
		
		when(iCustStmtPrcsrService.validateCustStmt(custStmtRecordList)).
			thenReturn(custStmtPrcsrResponse);
		
		assertEquals(Constant.SUCCESSFUL, 
				custStmtPrcsrController.validateCustStmt(custStmtRecordList).getResult());
		assertEquals(0, 
				custStmtPrcsrController.validateCustStmt(custStmtRecordList).getErrorRecords().size());
	}

}
