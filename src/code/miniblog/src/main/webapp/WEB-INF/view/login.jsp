<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Login</title>
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
      <h3 style="color:dimgray; text-align: center">Login</h3>
      <hr class="">
      <form method="POST" action="login" accept-charset="UTF-8" role="form" class="form-signin">
        <fieldset>
          <input class="form-control" placeholder="Username" name="username" type="text">
          <input class="form-control middle" placeholder="Password" name="password" type="password" value="">
          <br>
          <input class="btn btn-lg btn-primary btn-block" type="submit" value="Login"/>
          <br>
          <p class="text-center"><a href="register.html">Already have an account?</a></p>
        </fieldset>
      </form>
    </div>
  </div>
</div>
<!-- /container --> 
</body>
</html>
