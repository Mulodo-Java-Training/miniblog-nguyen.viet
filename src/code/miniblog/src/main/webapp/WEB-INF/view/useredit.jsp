<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>User infor</title>
</head>
<body>
<!-- Header -->
<%@include file="header.jsp" %>
<!--End header-->   
<!-- Container -->
<div class="container">
  <div class="row" style="margin-top:40px;">
    <div class="col-md-4 col-md-offset-4">
    ${messager}
      <h3 style="color:dimgray; text-align: center">User Information</h3>
      <hr class="">
      <form method="POST" action="useredit" name="user" accept-charset="UTF-8" role="form" class="form-signin">
        <fieldset>
        	<input class="panel-body"  name="id" type="text" value="${user.id}" required hidden="true">
          <div class="panel panel-info">
            <div class="panel-heading">Username</div>
            <div class="panel-body">${user.username}</div>
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Firstname</div>
            <input class="panel-body" placeholder="Firstname" name="firstname" type="text" value="${user.firstname}" required >
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Lastname</div>
            <input class="panel-body" placeholder="Lastname" name="lastname" type="text" value="${user.lastname}" required>
          </div>
           <c:set var="birthday" value="${user.birthday}"/>
			<c:set var="day" value="${fn:substring(birthday, 8, 10)}" />
			<c:set var="month" value="${fn:substring(birthday, 5, 7)}" />
			<c:set var="year" value="${fn:substring(birthday, 0, 4)}" />
          <div class="panel panel-info">
            <div class="panel-heading">Birthday</div>
            <div class="panel-body ">
            <select id="dobday" class=" panel-body panel-body-dob" name="day">
            <option value="${day}">${day}</option>
            </select>
            <select id="dobmonth" class=" panel-body panel-body-dob" name="month">
            <option value="${month}">${month}</option>
            </select>
            <select id="dobyear" class=" panel-body panel-body-dob" name="year">
            <option value="${year}">${year}</option>
            </select>
            </div>
           
            <script>
				$(document).ready(function(){
				  $.dobPicker({
					daySelector: '#dobday', /* Required */
					monthSelector: '#dobmonth', /* Required */
					yearSelector: '#dobyear', /* Required */
					dayDefault: '', /* Optional */
					monthDefault: '', /* Optional */
					yearDefault: '', /* Optional */
					minimumAge: 10, /* Optional */
					maximumAge: 100 /* Optional */
				  });
				});
			</script> 
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Email</div>
            <input class="panel-body" placeholder="Email" name="email" type="text" value="${user.email}" required>
          </div>
          <input class="btn btn-lg  btn-primary btn-block " type="submit" value="Edit" name="edit"/>
          <br>
          <br>
          <a href="changepass?id=<%=user_id%>">
          <input class="btn btn-lg  btn-primary btn-block " value="Change Password" name="changepass"/>
          </a><br>
          <a href="useredit?id=<%=user_id%>">
          <input class="btn btn-lg btn-warning btn-block"  value="Cancel" name="cancel"/>
          </a> <br>
        </fieldset>
      </form>
    </div>
  </div>
</div>
</body>
</html>