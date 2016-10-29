
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/tlds/ajaxtags.tld" prefix="ajax"%>
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
        
        List all{className}s = {className}DAO.selectAll();
        if (request.getAttribute("output") != null) {
            all{className}s = (List) request.getAttribute("output");
            if (all{className}s.size() == 0) {
                error = "No result Found";
                errType = "2";
                all{className}s = {className}DAO.selectAll();
            }
        } else {
            all{className}s = {className}DAO.selectAll();
        }
//session.setAttribute("shoppingList",l);
        request.setAttribute("all{className}s", all{className}s);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>{className}</title>
        <link rel="stylesheet" href="/robocon/style/dropdown.css" type="text/css" />
        <script type="text/javascript" src="/robocon/jsmenu/dropdown.js"></script>
        <link type="text/css" rel="stylesheet" href="/robocon/css/ajaxtags.css" ></link>
        <link rel="stylesheet" type="text/css" href="/robocon/css0/site.css" ></link>
        <script type="text/javascript" src="/robocon/ajaxtags/js/prototype.js"></script>
        <script type="text/javascript" src="/robocon/ajaxtags/js/scriptaculous/scriptaculous.js"></script>
        <script type="text/javascript" src="/robocon/ajaxtags/js/overlibmws/overlibmws.js"></script>
        <script type="text/javascript" src="/robocon/ajaxtags/js/ajaxtags.js"></script>
        <link rel="stylesheet" href="/robocon/style/style.css" type="text/css" />
        <link href="/robocon/style/TemplateCss.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="/robocon/jscalendar/calendar.js"></script>
        <link rel="stylesheet" type="text/css" media="all" href="/robocon/jscalendar/calendar-win2k-cold-1.css" title="win2k-cold-1" />
        <script type="text/javascript" src="/robocon/jscalendar/lang/calendar-en.js"></script>
        <script type="text/javascript" src="/robocon/jscalendar/calendar-setup.js"></script>
    </head>
    <script language="javascript">
        function resetAll(){
            location.href = "/robocon/Pages/{dbName}/{classNamelow}.jsp";
        }   

        function cancelFrm(){
            location.href = "/robocon/Pages/{dbName}/{classNamelow}.jsp";
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
                location.href = "/robocon/{className}Handler?code="+brandId+"&action=del";
            }
        }


    </script>
    <body>
        <div id="container">
            <div id="jQueryTabs1" style="position:absolute;left:342px;top:164px;width:905px;z-index:146">
                <%if (all{className}s != null && !all{className}s.isEmpty()) {
            {className} record = null;

                %>

              <form name="searchForm" action="/robocon/SearchData">
                    <table class="graph_blue_rect">
                        <tr>
                            <td class="td" colspan="2">
                                <font color="blue" size="3"><b>Search Form:</b></font>
                            </td>
                        </tr>
                        <tr>
                            <td class="alternativetd" >
                                <b>Name</b>
                            </td>
                            <td class="alternativetd" >
                                <input type="text" name="name" value="" />
                                <input type="hidden" name="type" value="{classNamelow}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="td" colspan="2">
                                <input type="submit" name="Search" value="Search" />
                                <input type="button" name="reset" value="Reset" onclick="resetAll()"/>
                            </td>
                        </tr>
                    </table>
                </form>
                <br/>
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

                <display:table  export="true" name="all{className}s" pagesize="30" id="row" cellpadding="1" cellspacing="1" defaultsort="1" style="width: 900px" >
				{displaytbl}
                    <display:column title="Edit">
                        <%  record = ({className}) pageContext.getAttribute("row");
                        %>
                        <a href="/robocon/Pages/{dbName}/{classNamelow}.jsp?code=<%=record.getId()%>&action=edit">Edit</a>

                    </display:column>
                    <display:column title="Delete">
                        <%  record = ({className}) pageContext.getAttribute("row");
                        %>
                           <a href="javascript: doDelete(<%=record.getId()%>,'<%=record.get{fstringpropNameC}()%>')">Delete</a>

                    </display:column>
                </display:table>
                <br/>
                <%}%>
            </div>
            <div id="jQueryTabs2" style="position:absolute;left:0px;top:164px;width:321px;height:880px;">
                <form name="{classNamelow}handlerFrm" action="/robocon/{className}Handler" method="post">
                    <table>
					<%if (edit{className} != null) {%>
					<tr><td><input type="hidden" name="{classNamelow}Id" value="<%=edit{className}.getId()%>"/></td></tr>
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
            <jsp:include page="../MenuBar.jsp" />
        </div>
    </body>
</html>
