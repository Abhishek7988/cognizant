/**
 * 
 */
package com.rabobank.customer.statement.processor.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabobank.customer.statement.processor.advice.ControllerAdviceCustom;
import com.rabobank.customer.statement.processor.exception.CustStmtPrcsrException;
import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;
import com.rabobank.customer.statement.processor.service.ICustStmtPrcsrService;

/**
 * @author Abhishek
 *
 */
@SpringBootTest(classes = { ControllerAdviceCustom.class, CustStmtPrcsrServiceImpl.class })
@RunWith(SpringRunner.class)
public class CustStmtPrcsrServiceImpITest {

	/**
	 * @throws java.lang.Exception
	 */

	private static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
	private static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
	private static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";
	private static final String SUCCESSFUL = "SUCCESSFUL";

	private static List<CustStmtRecord> custStmtRecordListNOValidationError;
	private static List<CustStmtRecord> custStmtRecordListWithDuplicateRefError;
	private static List<CustStmtRecord> custStmtRecordListWihBalanceMistmatchError;

	@Before
	public void setUpBeforeClass() throws Exception {
		custStmtRecordListNOValidationError = new ArrayList<>();
		custStmtRecordListNOValidationError.add(new CustStmtRecord(new BigDecimal(1), "ABCD", new BigDecimal(1000),
				new BigDecimal(-100), new BigDecimal(900)));
		custStmtRecordListNOValidationError.add(new CustStmtRecord(new BigDecimal(2), "PQRS", new BigDecimal(500),
				new BigDecimal(400), new BigDecimal(900)));
		custStmtRecordListNOValidationError.add(new CustStmtRecord(new BigDecimal(3), "VWXY", new BigDecimal(1000),
				new BigDecimal(2300), new BigDecimal(3300)));

		custStmtRecordListWithDuplicateRefError = new ArrayList<>();
		custStmtRecordListWithDuplicateRefError.add(new CustStmtRecord(new BigDecimal(1), "ABCD", new BigDecimal(1000),
				new BigDecimal(-100), new BigDecimal(900)));
		custStmtRecordListWithDuplicateRefError.add(new CustStmtRecord(new BigDecimal(1), "LMNO", new BigDecimal(1000),
				new BigDecimal(-300), new BigDecimal(700)));
		custStmtRecordListWithDuplicateRefError.add(new CustStmtRecord(new BigDecimal(3), "VWXY", new BigDecimal(1000),
				new BigDecimal(2300), new BigDecimal(3300)));

		custStmtRecordListWihBalanceMistmatchError = new ArrayList<>();
		custStmtRecordListWihBalanceMistmatchError.add(new CustStmtRecord(new BigDecimal(1), "ABCD",
				new BigDecimal(1000), new BigDecimal(-100), new BigDecimal(700)));
		custStmtRecordListWihBalanceMistmatchError.add(new CustStmtRecord(new BigDecimal(2), "PQRS",
				new BigDecimal(500), new BigDecimal(400), new BigDecimal(900)));
		custStmtRecordListWihBalanceMistmatchError.add(new CustStmtRecord(new BigDecimal(3), "VWXY",
				new BigDecimal(1000), new BigDecimal(2300), new BigDecimal(330)));
	}

	@Autowired
	private ICustStmtPrcsrService iCustStmtPrcsrService;

	/**
	 * Test method for
	 * {@link com.rabobank.customer.statement.processor.service.impl.CustStmtPrcsrServiceImpl#validateCustStmt(java.util.List)}.
	 */
	@Test
	public void testValidateCustStmtNOValidationError() {
		assertEquals(SUCCESSFUL,
				iCustStmtPrcsrService.validateCustStmt(custStmtRecordListNOValidationError).getResult());
	}

	@Test
	public void testValidateCustStmtWithDuplicateRefError() {
		CustStmtPrcsrException ex = Assertions.assertThrows(CustStmtPrcsrException.class, () -> {
			iCustStmtPrcsrService.validateCustStmt(custStmtRecordListWithDuplicateRefError).getResult();
		});
		assertTrue(DUPLICATE_REFERENCE.equals(((CustStmtPrcsrResponse) ex.getResponse()).getResult()));
	}

	@Test
	public void testValidateCustStmtWithBalanceMisMatchError() {
		CustStmtPrcsrException ex = Assertions.assertThrows(CustStmtPrcsrException.class, () -> {
			iCustStmtPrcsrService.validateCustStmt(custStmtRecordListWihBalanceMistmatchError).getResult();
		});
		assertTrue(INCORRECT_END_BALANCE.equals(((CustStmtPrcsrResponse) ex.getResponse()).getResult()));
	}

	@Test
	public void testValidateCustStmtWithDuplicateRefErrorAndBalanceMisMatchError() {
		custStmtRecordListWithDuplicateRefError.addAll(custStmtRecordListWihBalanceMistmatchError);
		CustStmtPrcsrException ex = Assertions.assertThrows(CustStmtPrcsrException.class, () -> {
			iCustStmtPrcsrService.validateCustStmt(custStmtRecordListWithDuplicateRefError);
		});
		assertTrue(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE
				.equals(((CustStmtPrcsrResponse) ex.getResponse()).getResult()));
	}

	@Ignore
	@Test
	public void testValidateException() {
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			iCustStmtPrcsrService.validateCustStmt(null);
		});
	}
}
