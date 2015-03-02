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

<title>Add Post</title>
</head>
<body>
	<!-- Header -->
	<%@include file="header.jsp"%>
	<!--End header-->
	<!-- Container -->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#post-description").cleditor();
		});
	</script>
	<div class="container">
		<div class="row">
			<h4>New Post</h4>
			<hr>
			<!--Sort column to show-->
			<!--col 1 is emty-->
			<div class="col-md-2 blog-main"></div>
			<!--end col 1-->
			<!--col 2 is show post-->
			<div class="col-md-6 blog-main ">
				${messager}
				<div class="blog-post">
					<form method="POST" action="postadd" accept-charset="UTF-8"
						role="form" class="form-signin" name="article">
						<fieldset>
							<div class="panel panel-info">
								<div class="panel-heading">Title</div>
								<input class=" panel-body" placeholder="Post title" name="title"
									type="text" required>
							</div>
							<div class="panel panel-info">
								<div class="panel-heading">Description</div>
								<textarea id="post-description" name="description"
									placeholder="Type description here..."></textarea>
							</div>
							<input class="btn btn-lg  btn-primary btn-block " type="submit"
								value="Post" name="post" /> <br> 
								<a href="home"> 
								<input	class="btn btn-lg btn-warning btn-block" value="Cancel" name="cancel" />
								</a>
						</fieldset>
					</form>
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
</body>
</html>