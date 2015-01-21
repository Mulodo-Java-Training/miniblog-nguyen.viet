<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
</head>
<body>

	<h3>Users</h3>
	<a>${userid.lastname},${userid.firstname}</td>
			<table class="data" border=1>
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Birthday</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.lastname},${user.firstname}</td>
						<td>${user.email}</td>
						<td>${user.birthday}</td>
						<td><a href="delete/${user.id}">delete</a></td>
					</tr>
				</c:forEach>
			</table>
	</body>
</html>