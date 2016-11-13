package com.{contextpath}.api.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.{contextpath}.api.handler.inputmodel.dto.{className}RequestBean;
import com.{contextpath}.api.handler.models.enums.OperationEnum;
import com.{contextpath}.api.handler.models.enums.ResponseCodes;
import com.{contextpath}.api.handler.outputmodel.dto.{className}ResponseBean;
import com.{contextpath}.api.handler.outputmodel.handlers.ResultHandler;
import {daoPkName}.dao.{className}DAO;
import {daoPkName}.logger.handler.LoggingHandler;
import {daoPkName}.main.MetaData;
import {daoPkName}.model.{className};

public class {className}ManagerProcessor implements Processor {

	ResultHandler resultHandler = new ResultHandler();

	public void process(Exchange exchange) throws Exception {
		List<{className}> output = null;
		{className} inputParam;
		{className}ResponseBean {classNamelow}ResponseBean = new {className}ResponseBean();
		StringBuilder whereClause = new StringBuilder("");
		{className}RequestBean requestBean = exchange.getIn().getBody(
				{className}RequestBean.class);
		OperationEnum operationEnum = null;
		if (requestBean != null && requestBean.getRequestHeader() != null) {
			operationEnum = requestBean.getRequestHeader().getOperationEnum();
		}
		int rows = 0;
		inputParam = requestBean.get{className}();
		if (inputParam == null || operationEnum == null) {
			{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
					ResponseCodes.INVALIDREQUEST);
		} else {
			if (operationEnum.equals(OperationEnum.SEARCH)
					|| operationEnum.equals(OperationEnum.LIST)
					|| operationEnum.equals(OperationEnum.DELETE)) {
						{whereClauseByColumns}
				
			}
			if (operationEnum.equals(OperationEnum.SEARCH)
					|| operationEnum.equals(OperationEnum.LIST)) {
						if(whereClause == null || whereClause.toString().isEmpty()){
							LoggingHandler.logInfo(MetaData.LOGGER_NAME, "search by Empty where");
							if(operationEnum.equals(OperationEnum.LIST)){
									output = {className}DAO.selectAll();									
							}else{
								{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
											ResponseCodes.INVALIDPARAMETERS);
							}
						}else{
							LoggingHandler.logInfo(MetaData.LOGGER_NAME, "search by ",
									whereClause.toString());
							output = {className}DAO.search{className}(whereClause.toString());
							if (output == null || output.isEmpty()) {
								{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
										ResponseCodes.NODATAFOUND);
							}
						}
						if (output != null && !output.isEmpty()) {
								{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
										ResponseCodes.SUCCESS);
								{classNamelow}ResponseBean.setPagingModel(requestBean
										.getPagingModel());
								{classNamelow}ResponseBean.setSortingModel(requestBean
										.getSortingModel());
								{classNamelow}ResponseBean.getPagingModel().setTotalSize(
										output.size());
								output = resultHandler.handleResult(output, requestBean);
								{classNamelow}ResponseBean.set{className}s(output);
							}
				
			} else if (operationEnum.equals(OperationEnum.ADD)) {
				rows = {className}DAO.insertSelective(null, inputParam);
				if (rows > 0) {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.SUCCESS);
				} else {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.FAILED);
				}
			} else if (operationEnum.equals(OperationEnum.UPDATE)) {
				rows = {className}DAO.updateSelective(null, inputParam);
				if (rows > 0) {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.SUCCESS);
				} else {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.FAILED);
				}
			} else if (operationEnum.equals(OperationEnum.DELETE)) {
				LoggingHandler.logInfo(MetaData.LOGGER_NAME, "Delete by ",
						whereClause.toString());
				rows = {className}DAO.deleteCustom(whereClause.toString());
				if (rows > 0) {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.SUCCESS);
				} else {
					{classNamelow}ResponseBean.getResponseHeader().setResponseCodeEnum(
							ResponseCodes.FAILED);
				}
			}
		}
		/*
		 * {className}s {classNamelow} = new {className}s(); {classNamelow}.setCity("Test");
		 * {classNamelow}.setCountry("TestCountry"); if (param != null && param.getId()
		 * != null) { {classNamelow}.setId(param.getId()); } else { {classNamelow}.setId("2"); }
		 * if (param != null && param.getName() != null) {
		 * {classNamelow}.setName(param.getName()); } else { {classNamelow}.setName("Metwally"); }
		 */
		exchange.getIn().setBody({classNamelow}ResponseBean);
		exchange.getOut().setBody(exchange.getIn().getBody());
	}
}
