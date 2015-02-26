<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Register</title>
<script src="<c:url value='/resources/js/common.js'/>"></script>
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
      <h3 style="color:dimgray; text-align: center">Register</h3>
      <hr class="">
      <form method="POST" name="user" action="register" action="" accept-charset="UTF-8" role="form" class="form-signin">
        <fieldset>
          <div class="panel panel-info">
            <div class="panel-heading">Username</div>
            <input class=" panel-body" placeholder="Username & 6 character minimun" name="username" type="text" required pattern=".{6,}" required title="6 characters minimum">
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Password</div>
            <input class="panel-body" placeholder="Password & 6 character minimun" name="password" type="password" required id="pass1" pattern=".{6,}" required title="6 characters minimum">
            <input class="panel-body" placeholder="Confirm password" name="password2" type="password" required id="pass2" onkeyup="checkPass(); return false;">
          <span id="confirmMessage" class="confirmMessage"></span>
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Firstname</div>
            <input class="panel-body" placeholder="Firstname" name="firstname" type="text" required>
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Lastname</div>
            <input class="panel-body" placeholder="Lastname" name="lastname" type="text" required>
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Birthday</div>
            <div class="panel-body ">
              <select id="dobday" class=" panel-body panel-body-dob" name="day">
              <option value="null">Day</option>
              </select>
              <select id="dobmonth" class=" panel-body panel-body-dob" name="month">
              <option value="null">Month</option>
              </select>
              <select id="dobyear" class=" panel-body panel-body-dob" name="year">
              <option value="null">Year</option>
              </select>
            </div>
            <script>
				$(document).ready(function(){
				  $.dobPicker({
					daySelector: '#dobday', /* Required */
					monthSelector: '#dobmonth', /* Required */
					yearSelector: '#dobyear', /* Required */
					dayDefault: 'Day', /* Optional */
					monthDefault: 'Month', /* Optional */
					yearDefault: 'Year', /* Optional */
					minimumAge: 10, /* Optional */
					maximumAge: 100 /* Optional */
				  });
				});
			</script> 
          </div>
          <div class="panel panel-info">
            <div class="panel-heading">Email</div>
            <input class="panel-body" placeholder="Email" name="email" type="text" required>
          </div>
          <input class="btn btn-lg  btn-primary btn-block " type="submit" value="Register" name="register"/>
          <br>
          <a href="welcome">
          <input class="btn btn-lg btn-warning btn-block" value="Cancel" name="cancel"/>
          </a> <br>
          <p class="text-center"><a href="login">Already have an account?</a></p>
        </fieldset>
      </form>
    </div>
  </div>
</div>
</body>
</html>