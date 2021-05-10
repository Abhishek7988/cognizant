package com.rabobank.customer.statement.processor.model.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Customer Statement Record Request POJO.
 * @author Abhishek
 *
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustStmtRecord {
	private BigDecimal reference;
	
	private String accountNumber;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private BigDecimal startBalance;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private BigDecimal mutation;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private BigDecimal endBalance;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustStmtRecord other = (CustStmtRecord) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}
}
