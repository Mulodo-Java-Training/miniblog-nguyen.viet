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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
	function myFunction(id) {
		$("#myComment").modal('show');
		var textContain = document.getElementById("comment-description-"+id).textContent;
	    document.getElementById("description-show").innerHTML = textContain;
	    document.getElementById("comment-id").value = id;
	};
</script>
<title>Post Detail</title>
</head>
<body>
	<!-- Header -->
	<%@include file="header.jsp"%>
	<!--End header-->
	<!-- Container -->

	<div class="container">
		<div class="row">
			<h3>Post detail</h3>
			<hr>
			<!--Sort column to show-->
			<!--col 1 is emty-->
			<div class="col-md-2 blog-main"></div>
			<!--end col 1-->
			<!--col 2 is show post-->
			<div class="col-md-6 blog-main ">
				${messager}
				<div class="blog-post">
					<div role="form" class="form-signin">
						<fieldset>
							<div class="panel panel-info">
								<div class="panel-heading">
									<h3>${post.title}</h3>
									<p class="blog-post-meta detail-post">
										<fmt:timeZone value="GMT-0">
										Date create: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss"
												value="${p.date_create}" />
										</fmt:timeZone>
										&nbsp;&nbsp;by&nbsp;&nbsp;<a
											href="post.user?id=${post.users.id}">${post.users.username}</a>
									</p>
									<c:choose>
										<c:when test="${post.date_modify != null}">
											<p class="blog-post-meta detail-post">
											<fmt:timeZone value="GMT-0">
											Date modify: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss"
												value="${post.date_modify}" />
											</fmt:timeZone>
											</p>
										</c:when>
									</c:choose>
									<c:set var="userLogin" scope="session" value="${post.users.id}" />
									<c:set var="postUserId" scope="session"
										value='<%=session.getAttribute("user_id")%>' />
									<c:choose>
										<c:when test="${userLogin == postUserId}">
											<a href="post.edit?id=${post.id}"
												class="glyphicon glyphicon-pencil"></a>&nbsp;&nbsp;
											<a href="post.delete?id=${post.id}"
												class="glyphicon glyphicon-trash"
												onclick="return confirm('Are you sure?')"></a>
										</c:when>
									</c:choose>
								</div>
								<div class="panel-body">
									<p>${post.description}</p>
								</div>
							</div>
							<h4>Comments</h4>
							<hr>

							<!--Comment list -->
							<c:forEach var="c" items="${commentList}">
								<div class="panel panel-primary">
									<div class="panel-body">
										<p id="comment-description-${c.id}">${c.description}</p>
										<p class="blog-post-meta detail-post">
										<fmt:timeZone value="GMT-0">
											Date create: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss"
												value="${c.date_create}" />
											</fmt:timeZone>&nbsp;&nbsp;by&nbsp;&nbsp;<a
												href="post.user?id=${c.users.id}">${c.users.username}</a>
										</p>
										<c:choose>
											<c:when test="${c.date_modify != null}">
												<p class="blog-post-meta detail-post">
												<fmt:timeZone value="GMT-0">
												Date modify: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss"
												value="${c.date_modify}" />
											</fmt:timeZone></p>
											</c:when>
										</c:choose>
										<c:set var="userLogin" scope="session" value="${c.users.id}" />
										<c:set var="postUserId" scope="session"
											value='<%=session.getAttribute("user_id")%>' />
										<c:choose>
											<c:when test="${userLogin == postUserId}">
												<a class="glyphicon glyphicon-pencil"
													onclick="myFunction(${c.id})"></a>&nbsp;&nbsp;
											<a href="comment.delete?id=${c.id}"
													class="glyphicon glyphicon-trash"
													onclick="return confirm('Are you sure?')"></a>
											</c:when>
										</c:choose>
									</div>
								</div>
							</c:forEach>
							<!--End comment list -->
							<form method="POST" action="comment.add" accept-charset="UTF-8"
								role="form" class="form-signin" name="comment">
								<input name="articles_id" type="text" value="${post.id}"
									required hidden="true">
								<textarea name="description" class="panel-body" rows="auto"
									cols="50" placeholder="Leave your comment....!"></textarea>
								<input class="btn btn-primary " type="submit" value="Add"
									name="add" style="float: right" />
							</form>
						</fieldset>
					</div>
				</div>
				<br>
			</div>
			<!--end col 2 -->
			<!--col 3-->
			<%@include file="leftbar.jsp"%>
			<!--end col 3 -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /container -->
	<form method="POST" action="comment.edit" accept-charset="UTF-8"
		name="comment">
		<div id="myComment" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">Edit Comment</h4>
					</div>
					<div class="modal-body">
						<input name="id" type="text" id="comment-id" required
							hidden="true">
						<textarea name="description" class="panel-body"
							placeholder="Leave your comment....!" id="description-show"></textarea>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>