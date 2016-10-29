package com.{contextpath}.api.handler.outputmodel.dto;

import java.util.ArrayList;
import java.util.List;
import com.{contextpath}.api.handler.outputmodel.ResponseBean;
import {daoPkName}.model.{className};
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class {className}ResponseBean extends ResponseBean {

	private List<{className}> {classNamelow}s;

	public List<{className}> get{className}s() {
		if ({classNamelow}s == null) {
			{classNamelow}s = new ArrayList<{className}>();
		}
		return {classNamelow}s;
	}

	public void set{className}s(List<{className}> {classNamelow}s) {
		this.{classNamelow}s = {classNamelow}s;
	}

}
