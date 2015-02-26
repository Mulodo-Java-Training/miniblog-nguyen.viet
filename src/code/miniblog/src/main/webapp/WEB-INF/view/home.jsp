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
			<hr>
			<!--Sort column to show-->
			<!--col 1 is emty-->
			<div class="col-md-2 blog-main"></div>
			<!--end col 1-->
			<!--col 2 is show post-->
			<div class="col-md-6 blog-main ">
			${messager}
				<c:forEach var="u" items="${userList}">
				<c:set var="birthday" value="${u.birthday}"/>
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
								<td colspan="2">${u.username}</td>
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
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 1</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Mark</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 2</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Tom</a>
					</p>
					<p>This blog post shows a few different At mobile device sizes,
						tablets and down, these column...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 3</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Nguyen.Viet</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 4</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Mark</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 5</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Mark</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 6</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Mark</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
				<div class="blog-post">
					<h4 class="blog-post-title">
						<a href="postdetail.html">Post 7</a>
					</h4>
					<p class="blog-post-meta detail-post">
						Date: 01/02/2015 11:12:32 by <a href="#">Mark</a>
					</p>
					<p>At mobile device sizes, tablets and down, these columns and
						their nested columns will stack...</p>
				</div>
				<br>
				<!-- /.blog-post -->
			</div>
			<!--end col 2 -->
			<!--col 3-->
			<div class="col-md-3 col-sm-offset-8 blog-sidebar affix">
				<ul class="nav nav-list bs-docs-sidenav fix-size">
					<li><a href="postadd.html" class="font-nav-sile">New Post
							<span class="pull-right glyphicon glyphicon-plus"></span>
					</a></li>
					<li><a href="#" class="font-nav-sile">My Post <span
							class="pull-right glyphicon glyphicon-th-list"></span></a></li>
					<li><a href="#" class="font-nav-sile">Top Post <span
							class="pull-right glyphicon glyphicon-list-alt"></span></a></li>
					<li><a href="#" class="font-nav-sile">All Post <span
							class="pull-right glyphicon glyphicon-th"></span></a></li>
				</ul>
			</div>
			<!--end col 3 -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /container -->
</body>
</html>
