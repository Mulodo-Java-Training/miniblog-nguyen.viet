<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<%
		int userID;
		if (session.getAttribute("user_id") != null)
			userID = Integer.valueOf(session.getAttribute("user_id")
					.toString());
		else
			userID = 0;

		if (userID == 0)
	%>
	<div class="col-md-3 col-sm-offset-8 blog-sidebar affix">
		<ul class="nav nav-list bs-docs-sidenav fix-size">
			<li><a href="post.add" class="font-nav-sile">New Post <span
					class="pull-right glyphicon glyphicon-plus"></span>
			</a></li>
			<li><a href="post.user?id=<%=userID%>" class="font-nav-sile">My
					Post <span class="pull-right glyphicon glyphicon-th-list"></span>
			</a></li>
			<li><a href="post.top" class="font-nav-sile">Top Post <span
					class="pull-right glyphicon glyphicon-list-alt"></span></a></li>
			<li><a href="post.list" class="font-nav-sile">All Post <span
					class="pull-right glyphicon glyphicon-th"></span></a></li>
		</ul>
	</div>
</body>
</html>