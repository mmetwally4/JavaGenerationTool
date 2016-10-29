package com.{contextpath}.api.handler.inputmodel.dto;

import com.{contextpath}.api.handler.inputmodel.RequestBean;
import {daoPkName}.model.{className};
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class {className}RequestBean extends RequestBean {

	private {className} {classNamelow};

	public {className} get{className}() {
		return {classNamelow};
	}

	public void set{className}({className} {classNamelow}) {
		this.{classNamelow} = {classNamelow};
	}

}