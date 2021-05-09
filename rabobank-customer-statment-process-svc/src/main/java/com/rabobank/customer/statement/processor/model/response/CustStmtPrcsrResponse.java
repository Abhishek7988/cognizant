package com.rabobank.customer.statement.processor.model.response;

import java.util.List;

import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Customer Validation Response POJO.
 * @author Abhishek
 *
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustStmtPrcsrResponse {
	private String result;
	private List<CustStmtRecord> errorRecords;
}
