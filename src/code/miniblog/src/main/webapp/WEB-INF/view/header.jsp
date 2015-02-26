<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title></title>
<!-- Bootstrap core CSS -->
<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">

<!-- Bootstrap core JavaScript
    ================================================== -->
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/dobPicker.min.js'/>"></script>
</head>

<body>
<!--Header-->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header"> 
    <%
    	int user_id;
    	if(session.getAttribute("user_id") != null)
    		 user_id = Integer.valueOf(session.getAttribute("user_id").toString());
    	else
    		user_id = 0;
    	
    	if(user_id == 0){
    %>
    <a class="miniblog-icon" href="welcome">Mini Blog</a> 
    <%}else{ %>
    
    <a class="miniblog-icon" href="home">Mini Blog</a> 
    <%} %>
    </div>
    <div class="row">
      <div class="col-sm-5 col-sm-offset-1">
        <div id="imaginary_container">
          <form action="searchUser">
            <div class="input-group stylish-input-group">
              <input type="text" name="nameSearch" class="form-control"  placeholder="Username, Lastname, Firstname" >
              <span class="input-group-addon">
              <button type="submit"> <span class="glyphicon glyphicon-search"></span> </button>
              </span> </div>
          </form>
        </div>
      </div>
      <ul class="nav navbar-nav navbar-right">
      <% 
      if(user_id == 0){
      %>
        <li><a href="register" class="header-font">Register</a></li>
        <li><a href="login" class="header-font">Login</a></li>
        <%}else{ %>
        <li class="dropdown"> <a href="" class="dropdown-toggle animate" data-toggle="dropdown">Hi! <%=session.getAttribute("username")%><span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="useredit?id=<%=user_id%>" class="animate">Profile<span class="pull-right glyphicon glyphicon-user"></span></a></li>
            <li><a href="logout" class="animate">Logout<span class="pull-right glyphicon glyphicon-off"></span></a></li>
          </ul>
        </li>
        <%} %>
      </ul>
    </div>
  </div>
</nav>
</body>
</html>