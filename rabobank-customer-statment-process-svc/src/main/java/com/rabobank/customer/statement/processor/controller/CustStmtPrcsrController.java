package com.rabobank.customer.statement.processor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.customer.statement.processor.model.request.CustStmtRecord;
import com.rabobank.customer.statement.processor.model.response.CustStmtPrcsrResponse;
import com.rabobank.customer.statement.processor.service.ICustStmtPrcsrService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
/**
 * Customer Records Processor Controller.
 * @author Abhishek
 *
 */
public class CustStmtPrcsrController {
	
	@Autowired
	private ICustStmtPrcsrService custStmtPrcsrService;
	
	/**
	 * Customer Records validator endpoint.
	 * @param custStmtRecords
	 * @return
	 */
	@ApiOperation(value = "Validates the Customer Records",response = CustStmtPrcsrResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Validation Response with error records if any"),
            @ApiResponse(code = 400, message = "Bad Request while accessing the endpoint"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    }
    )
	@PostMapping(path = "/validate")
	@Validated
	public CustStmtPrcsrResponse validateCustStmt(@RequestBody(required = true) final List<CustStmtRecord> custStmtRecords) {
		log.info("Entering {}.{}", getClass().getName(), "validateCustStmt()");
		
		log.info("Input payload "+custStmtRecords);
		
		log.info("Exiting {}.{}", getClass().getName(), "validateCustStmt()");
		return custStmtPrcsrService.validateCustStmt(custStmtRecords);
	}
	
}
