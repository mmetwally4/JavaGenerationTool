
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page errorPage="/Pages/errorPage.jsp" %>
<%@ page import="{daoPkName}.model.*,{daoPkName}.*,{daoPkName}.dao.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%
        String error = "";
        String errType = "";
        error = request.getParameter("errmsg");
        errType = request.getParameter("errType");
        String title = "";        
        List {classNamelow}s;
        {className} {classNamelow} = null;
        {className} edit{className} = null;
        Integer selectedId = 0;
        String action = "";
        try {
            selectedId = Integer.parseInt(request.getParameter("code"));
        } catch (Exception ex) {
        }
        action = request.getParameter("action");
        if (selectedId.intValue() > 0) {
            {classNamelow} = {className}DAO.selectById(selectedId);
            if (action.equalsIgnoreCase("edit")) {
                edit{className} = {classNamelow};
            }
        }     

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>{className}</title>
         <!-- CSS -->
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/demo_page.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/cupertino/jquery.ui.all.css" type="text/css">
        <!---->
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/demo_table.css" rel="stylesheet" type="text/css" />      
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/demo_table_jui.css" rel="stylesheet" type="text/css" />
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/jquery.dataTables.css" rel="stylesheet" type="text/css" media="all" />
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/jquery.dataTables_themeroller.css" rel="stylesheet" type="text/css" media="all" />
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/dataTables.fixedColumns.css" rel="stylesheet" type="text/css" media="all" />
        <!-- Scripts -->
        <script src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/js/jquery.js" type="text/javascript"></script>
        <!--<script type="text/javascript" src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/js/jquery-ui-form.js"></script>-->
        <script src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/js/jquery.dataTables.js" type="text/javascript"></script>
        <script src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/js/dataTables.tableTools.js"></script>
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/js/dataTables.fixedColumns.js"></script>
        <link href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/css/tablebuttonsstyle.css" rel="stylesheet" type="text/css" />   
        <script type="text/javascript" src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/jscalendar/calendar.js"></script>
        <link rel="stylesheet" type="text/css" media="all" href="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
        <script type="text/javascript" src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/jscalendar/lang/calendar-en.js"></script>
        <script type="text/javascript" src="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/jscalendar/calendar-setup.js"></script>
    </head>
    <script language="javascript">
        function resetAll(){
            location.href = "/<%=com.vermilion.servlet.PagesFilter.contextPath%>/Pages/<%=com.vermilion.servlet.PagesFilter.contextPath%>/{classNamelow}.jsp";
        }   

        function cancelFrm(){
            location.href = "/<%=com.vermilion.servlet.PagesFilter.contextPath%>/Pages/<%=com.vermilion.servlet.PagesFilter.contextPath%>/{classNamelow}.jsp";
        }
        function uncheckAll()
        {
            var frm = document.forms["delform"];
            frm.allchecked.value ="";

            var field = document.delform.delcon;
            for (i = 0; i < field; i++)
                field[i].checked = false ;
        }
        function checkall(formname,checkname,thestate){
            //var form = document.forms[formname];
            if(thestate == false){
                var frm = document.forms["delform"];
                frm.allchecked.value ="";
            }
            var el_collection = eval("document.forms.delform.delcon")
            for (c=0;c<el_collection.length;c++)
                el_collection[c].checked = thestate
        }

        function checkallResult(formname,checkname,thestate){
            //var form = document.forms[formname];
            var frm = document.forms["delform"];
            frm.allchecked.value ="1";
            var el_collection = eval("document.forms.delform.delcon")
            for (c=0;c<el_collection.length;c++)
                el_collection[c].checked = thestate
        }
        function isEmpty(StrObj){
            if (StrObj=="")
            {
                return true;
            }
            return false;
        }


        function doDelete(brandId,brandName){
            if(confirm('are you sure you want to delete This {className} '+brandName+' ?') == true){
                location.href = "/<%=com.vermilion.servlet.PagesFilter.contextPath%>/{className}Handler?code="+brandId+"&action=del";
            }
        }


    </script>
    <body>
               <div id="container" style="width:100%;">
            <div class="table ui-tabs ui-widget ui-corner-all" style="position:absolute;top:164px;width:99%;height:880px;" > 
                <div class="tr" style="width:99%;">
                    <div class="td" style="width:20%"> 
				<div id="jQueryTabs2" class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="position: absolute; top: 0px;height:880px;">
                <form name="{classNamelow}handlerFrm" action="/<%=com.vermilion.servlet.PagesFilter.contextPath%>/{className}Handler" method="post">
                    <table>
					<%if (edit{className} != null) {%>
					<tr><td><input type="hidden" name="id" value="<%=edit{className}.getId()%>"/></td></tr>
					<%}%>
					{edittbl}
                       
                        <tr>
                            <td >
                                <%String btnText = "Insert";
        if (edit{className} != null) {
            btnText = "Edit";
        }
                                %>
                                <input type="submit" id="Button1" name="" value="<%=btnText%>" style="position:relative;width:96px;height:25px;background-color:#0099FF;color:#FFFFFF;font-family:Arial;font-weight:bold;font-size:13px;"/>
                                <input type="reset" id="Button2" name="" value="Reset" style="position:relative;width:96px;height:25px;background-color:#0099FF;color:#FFFFFF;font-family:Arial;font-weight:bold;font-size:13px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="button" id="Button3" name="" value="Cancel" onclick="cancelFrm()" style="position:relative;width:96px;height:25px;background-color:#0099FF;color:#FFFFFF;font-family:Arial;font-weight:bold;font-size:13px;"/>
                            </td>
                        </tr>

                    </table>
                </form>              
            </div>					
					</div>  
                    <div class="td ui-tabs ui-widget ui-widget-content ui-corner-all" style="width:79%;position: absolute; top: 0px;padding-left:3px">            
             <div id="jQueryTabs1"  class="" style="height:880px;">
                <%

                %>
              
                <%
    if (errType != null && error != null && !error.equals("")) {
        if (errType.equals("1")) {
                %>
                <table style="width:99%">
                    <tr>
                        <td align="center" colspan="3">
                            <font color="#2AD320"> <%=error%></font>
                        </td>
                    </tr>
                </table>
                <%} else {%>
                <table style="width:99%">
                    <tr>
                        <td align="center" colspan="3">
                            <font color="#FF0000"> <%=error%></font>
                        </td>
                    </tr>
                </table>
                <%}
    }%>
				<div id="contentDiv" style="width:100%">
					<jsp:include page="{classNamelow}Table.jsp" flush="true"/>
				</div>           
                <br/>
                
            </div>
			</div>
                </div>
            </div>
            <jsp:include page="../Menu.jsp" />
        </div>   
    </body>
</html>
