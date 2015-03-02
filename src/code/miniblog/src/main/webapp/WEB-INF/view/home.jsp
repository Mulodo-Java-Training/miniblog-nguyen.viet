<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Home</title>
</head>

<body>
	<!-- Header -->
	<%@include file="header.jsp"%>
	<!--End header-->
	<!-- Container -->
	<div class="container">
		<div class="row">
			<!--Sort column to show-->
			<!--col 1 is emty-->
			<div class="col-lg-3 col-md-3 col-sm-0 col-xs-0"></div>
			<!--end col 1-->
			<!--col 2 is show post-->
			<div class="col-lg-6 col-md-6 col-sm-9 col-xs-9" >
				${messager}
				<c:forEach var="u" items="${userList}">
					<c:set var="birthday" value="${u.birthday}" />
					<c:set var="dobday" value="${fn:substring(birthday, 0, 10)}" />
					<table class="table table-bordered">
						<thead class="panel panel-info ">
							<tr class="panel panel-primary panel-heading">
								<th colspan="3">User infor</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 60px"><strong>Username</strong></td>
								<td colspan="2"><a href="post.user?id=${u.id}">${u.username}</a></td>
							</tr>
							<tr>
								<td><strong>Fullname</strong></td>
								<td>${u.firstname}${u.lastname}</td>
							</tr>
							<tr>
								<td><strong>Birthday</strong></td>
								<td>${dobday}</td>
							</tr>
						</tbody>
					</table>
				</c:forEach>
				<br>
				<!-- /.blog-post -->
				<c:forEach var="p" items="${postList}">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h5 class="blog-post-title" align="justify">
								<a href="post.detail?id=${p.id}">${p.title}</a>
							</h5>
							<p class="blog-post-meta detail-post">
								<fmt:timeZone value="GMT-0">
								Date create: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${p.date_create}"/> 
								by <a href="post.user?id=${p.users.id}">${p.users.username}</a>
								</fmt:timeZone>
							</p>
						</div>
						<div class="panel-body">
							<p class="box-description">${p.description}</p>
						</div>
					</div>
					<br>
				</c:forEach>
				<!-- /.blog-post -->
			</div>
			<!--end col 2 -->
			<!--col 3-->
			<%@include file="leftbar.jsp"%>
			<!--end col 3 -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /container -->
</body>
</html>
