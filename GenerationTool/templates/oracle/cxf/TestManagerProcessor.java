package com.{contextpath}.api.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.{contextpath}.api.handler.inputmodel.dto.*;
import com.{contextpath}.api.handler.models.enums.OperationEnum;
import com.{contextpath}.api.handler.models.enums.ResponseCodes;
import com.{contextpath}.api.handler.outputmodel.dto.*;
import com.{contextpath}.api.handler.outputmodel.ResponseHeader;
import com.{contextpath}.api.handler.inputmodel.RequestHeader;
import com.{contextpath}.api.handler.outputmodel.handlers.ResultHandler;
import {daoPkName}.dao.*;
import {daoPkName}.logger.handler.LoggingHandler;
import {daoPkName}.main.MetaData;
import {daoPkName}.model.*;
import java.util.Calendar;

public class TestManagerProcessor implements Processor {

	ResultHandler resultHandler = new ResultHandler();

	public void process(Exchange exchange) throws Exception {
		String operationName = exchange.getIn().getHeader("operationName",String.class);
		List output = null;
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setOperationType(2);
		requestHeader.setTimestamp(
				Calendar.getInstance().getTime());
		requestHeader.setChannel("APP");
		requestHeader.setRequestId("1234");
		requestHeader.setUser("APPUser");
		requestHeader.setTransactionId(
				"1234");
		
		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setResponseCodeEnum(
				ResponseCodes.SUCCESS);
		responseHeader.setTimestamp(
				Calendar.getInstance().getTime());
		responseHeader.setTimestamp(
				Calendar.getInstance().getTime());
		responseHeader.setOperationType(2);		
		responseHeader.setChannel("APP");
		responseHeader.setRequestId("1234");
		responseHeader.setUser("APPUser");
		responseHeader.setTransactionId("1234");
		if(operationName != null){
			{testrequestresponseproces1}						
		}
		exchange.getOut().setBody(exchange.getIn().getBody());
	}
	
}
