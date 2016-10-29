package com.{contextpath}.api.handler;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.{contextpath}.api.handler.inputmodel.dto.{className}RequestBean;
import com.{contextpath}.api.handler.outputmodel.dto.{className}ResponseBean;
import {daoPkName}.model.{className};

@Path("/{classNamelow}")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class {className}Managment {

	@POST
	@Path("/mgr")
	@Produces("application/json")
	// @Consumes("application/json")
	public {className}ResponseBean handle{className}({className}RequestBean {classNamelow}RequestBean) {
	
		return new {className}ResponseBean();

	}

}
