<%-- 
    Document   : {className}Table
    Created on : Apr 10, 2014, 9:02:33 PM
    Author     : mmetwally
--%>

<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="{daoPkName}.model.*,{daoPkName}.*,{daoPkName}.dao.*" %>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/tlds/roleaction.tld" prefix="roleaction" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    #example {
        width: 100% !Important;
    }
    table.display {
        margin: 0 auto;
        width: 100%;
        clear: both;
        border-collapse: collapse;
        /*table-layout: fixed;*/          

    }    
</style>
<script type="text/javascript">
    $(document).ready(function() {
        var oTable = $("#example").dataTable({
            "sPaginationType": "full_numbers",
            "bJQueryUI": true,
            "sScrollY": "600px",
            "sScrollX": "100%",
            "sScrollXInner": "140%",
            "bScrollCollapse": true,
			"aoColumns": [
				{jquerycolumns}null,null
			],		
            "sDom": '<"H"lTfr>t<"F"ip>'
        });
        var tt = new $.fn.dataTable.TableTools(oTable);
        $(tt.fnContainer()).insertAfter('div.info');

    });

</script>
<%
    List<{className}> all{className}s = {className}DAO.selectAll();
%>

<div id="demo">
    <table id="example" cellpadding="0" cellspacing="0" border="0" class="display stripe row-border order-column" >
        <thead>
		<tr>
		{tableheader}
		<th><u>Edit</u></th>
        <th><u>Delete</u></th>
		</tr>          
        </thead>
        <tbody>
            <%for ({className} record: all{className}s) {
            %>
            <tr class="">   
			{tablebody}                
                <td>
					<a href="/vermilion/Pages/vermilion/{classNamelow}.jsp?code=<%=record.getId()%>&action=edit">Edit</a>        
        </td>
        <td> 
			<a href="javascript: doDelete(<%=record.getId()%>,'<%=record.get{fstringpropNameC}()%>')">Delete</a>        

        </td>

        </tr>
        <%}%>
        </tbody>
    </table>
</div>              
