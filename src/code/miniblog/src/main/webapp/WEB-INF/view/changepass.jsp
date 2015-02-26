<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<c:url value='/resources/js/common.js'/>"></script>
<title>Change Pass</title>
</head>
<body>
	<!-- Header -->
	<%@include file="header.jsp"%>
	<!--End header-->
	<!-- Container -->
	<div class="container">
		<div class="row" style="margin-top: 40px;">
			<div class="col-md-4 col-md-offset-4">
				${messager}
				<h3 style="color: dimgray; text-align: center">Change Password</h3>
				<hr class="">
				<form method="POST" action="changepass" accept-charset="UTF-8"
					role="form" class="form-signin" name="user">
					<fieldset>
						<input class="panel-body" name="id" type="text" value="${user.id}"
							required hidden="true" />
						<div class="panel panel-info">
							<div class="panel-heading">Username</div>
							<div class="panel-body">${user.username}</div>
							<input class="panel-body" name="username"
								value="${user.username}" required hidden="true" />
						</div>
						<div class="panel panel-info">
							<div class="panel-heading">Password</div>
							<input class="panel-body" placeholder="Old password"
								name="old_password" type="password" required /> <input
								class="panel-body" placeholder="Password & 6 character minimun"
								name="new_password" type="password" required id="pass1"
								pattern=".{6,}" required title="6 characters minimum" /> <input
								class="panel-body" placeholder="Confirm password"
								name="re_password2" type="password" required id="pass2"
								onkeyup="checkPass(); return false;" /> <span
								id="confirmMessage" class="confirmMessage"></span>
						</div>
						<input class="btn btn-lg  btn-primary btn-block " type="submit"
							value="Change" name="change" /> <br> <a
							href="useredit?id=<%=user_id%>""> <input
							class="btn btn-lg btn-warning btn-block" type="cancel"
							value="Cancel" name="cancel" />
						</a>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>