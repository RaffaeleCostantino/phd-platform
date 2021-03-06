<%--
    Document   : RegistroPresenze
    Created on : 23-dic-2015, 1.24.09
    Author     : Rembor
--%>
 
<%@page import="it.unisa.dottorato.phdCourse.Course"%>
<%@page import="it.unisa.dottorato.phdCourse.CalendarManager"%>
<%@page import="it.unisa.dottorato.phdCourse.Lesson"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="it.unisa.dottorato.presence.PresenceManager"%>
<%@page import="it.unisa.dottorato.account.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.account == null}">
        <c:redirect url="login.jsp" />
    </c:when>
</c:choose>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
 
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="Xenon Boostrap Admin Panel" />
        <meta name="author" content="" />
        <title>Registro Presenze</title>
 
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Arimo:400,700,400italic">
        <link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
        <link rel="stylesheet" href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap.css">
        <link rel="stylesheet" href="assets/css/xenon-core.css">
        <link rel="stylesheet" href="assets/css/xenon-forms.css">
        <link rel="stylesheet" href="assets/css/xenon-components.css">
        <link rel="stylesheet" href="assets/css/xenon-skins.css">
        <link rel="stylesheet" href="assets/css/custom.css">
 
        <script src="assets/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="script/registro.js"></script>
 
 
    </head>
 
    <c:choose>
                <c:when test="${sessionScope.account != null}">
                    <% Account loggedPerson = ((Account) session.getAttribute("account"));
                        if (loggedPerson.getTypeAccount().equals("phdstudent")) {
                    %> 
    <body class="page-body">

        <div class="page-body">
            <jsp:include page="barraMenu.jsp"/>
 
            <div class="page-container">
                <div class="main-content">
                    <div class="row">
 
                        <div class="col-sm-1"></div>
 
                        <div class="col-sm-10">
                           
                            <div class="form-group">
                                
              
     <FORM ACTION="Submit" METHOD="POST"> 
                                <label  > Seleziona un corso</label>
                                
                                <select name="Corsoprofessore" class="form-control" id="Corsoprofessore"  onchange="selectedItemDot()" >
                       
                                    <option value="default"  >  - selezionate il vostro  corso  -  </option>
                                    
               
           
                                </select>
                             
           
     </form>
                                 
                            </div>
                        </div>
 
                        <div class="col-sm-1" ></div>
 
                    </div>
 
                    <div class="row">
 
                        <div class="col-sm-1"></div>
 
                        <div class="col-sm-10">
                            <div class="panel panel-default" id="panelDiv" hidden>
 
                                <div id="results" >
                              
            
                                    <table id="resultst" class="table">
                                       
                                        <thead id="resulthead">
                                    
                                             

                                        </thead>
                                        <tbody id="resultbody">
                                            <tr> </tr>
                                        </tbody>
                                    </table>
 
                                </div>
                            </div>
                        </div>
 
                        <div class="col-sm-1"></div>
                    </div>
                </div>
 
 
            </div>
 
        </div>
 
        <!-- Bottom Scripts -->
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/TweenMax.min.js"></script>
        <script src="assets/js/resizeable.js"></script>
        <script src="assets/js/joinable.js"></script>
        <script src="assets/js/xenon-api.js"></script>
        <script src="assets/js/xenon-toggles.js"></script>
 
        <!-- JavaScripts initializations and stuff -->
        <script src="assets/js/xenon-custom.js"></script>    
    </body>
    <%}else{%>
                <c:redirect url="index.jsp" />
          <%  }
    %>
            </c:when>
        </c:choose>
</html>
