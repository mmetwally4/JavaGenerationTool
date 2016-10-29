/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.{contextpath}.servlets.handlers;

import {daoPkName}.dao.{className}DAO;
import {daoPkName}.model.{className};
import com.{contextpath}.handlers.DataHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mmetwally
 */
public class {className}Handler extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            {className} {classNamelow} = new {className}();
	                       String error = "";
                String errType = "1";
	Enumeration<String> paramsEnum = request.getParameterNames();
            String paramName = "";
            Integer code = 0;
            String action = "";		
            if(paramsEnum != null){
                while(paramsEnum.hasMoreElements()){
					paramName = paramsEnum.nextElement();
                    try {
                        if (paramName.equalsIgnoreCase("code")) {
                            code = Integer.parseInt(request.getParameterValues(paramName)[0]);
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (paramName.equalsIgnoreCase("action")) {
                            action = request.getParameterValues(paramName)[0];
                        }
                    } catch (Exception ex) {
                    }
					{servletparams}
				}
			}
			if (action != null && action.equalsIgnoreCase("del") && code > 0) {
                {classNamelow} = {className}DAO.selectById(code);
                {className}DAO.deleteById(code);
                error = "{className} " + {classNamelow}.get{fstringpropNameC}() + " deleted Successfully";
                errType = "1";
            }else{
				Integer {classNamelow}Id = {classNamelow}.getId();
				if ({classNamelow}Id != null && {classNamelow}Id.intValue() > 0) {
					{classNamelow} = checkUpdateObject({classNamelow},{className}DAO.selectById({classNamelow}Id));
				}
	
				if ({classNamelow}Id != null && {classNamelow}Id.intValue() > 0) {
					int rows = 0;
					rows = {className}DAO.updateByPrimaryKeySelective({classNamelow});
					if(rows > 0){
						error = "{className} "+{classNamelow}.toString()+" Updated Successfully";
						errType = "1";
					}else{
						error = "Error in updating {className}: "+{classNamelow}.toString();
						errType = "2";
					}
				}else{
					int rows = 0;
					rows = {className}DAO.insertSelective({classNamelow});
					if(rows > 0){
						error = "{className} "+{classNamelow}.toString()+" Insert Successfully";
						errType = "1";
					}else{
						error = "Error in inserting {className}: "+{classNamelow}.toString();
						errType = "2";
					}
				}
			}
            response.sendRedirect("Pages/{dbName}/{classNamelow}.jsp?errmsg="+error+"&errType="+errType);            
        } catch (Exception ex) {
            response.sendRedirect("Pages/{dbName}/{classNamelow}.jsp?errmsg=Error%20in%20the%20requested%20Operation&errType=2");
        } finally {
        }
    }

	public {className} checkUpdateObject({className} {classNamelow},{className} checkObj){
			if({classNamelow} != null && checkObj != null){
				{checkclassParams}
			}
			return {classNamelow};
	}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
