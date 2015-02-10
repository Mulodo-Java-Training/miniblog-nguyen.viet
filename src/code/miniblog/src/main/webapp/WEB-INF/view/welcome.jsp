<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Welcome</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/theme.css" rel="stylesheet">

<!-- Bootstrap core JavaScript
    ================================================== --> 
<script src="js/jquery.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<!--<script src="js/ie-emulation-modes-warning.js"></script>-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<!--Header-->
<%@include file="header.jsp" %>
<!--End header--> 
<!-- Container -->
<div class="container">
  <div class="row">
    <hr>
    ${messager}
    <!--Sort column to show--> 
    <!--col 1 is emty-->
    <div class="col-md-3 blog-main"> </div>
    <!--end col 1--> 
    <!--col 2 is show post-->
    <div class="col-md-8 blog-main ">
    
      <div class="blog-post">
        <h4 class="blog-post-title">Post 1</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Mark</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 2</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Tom</a></p>
        <p>This blog post shows a few different At mobile device sizes, tablets and down, these column...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 3</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Nguyen.Viet</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 4</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Mark</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 5</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Mark</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 6</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Mark</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post -->
      <div class="blog-post">
        <h4 class="blog-post-title">Post 7</h4>
        <p class="blog-post-meta detail-post">Date: 01/02/2015 11:12:32 by <a href="#">Mark</a></p>
        <p>At mobile device sizes, tablets and down, these columns and their nested columns will stack...</p>
      </div>
      <br>
      <!-- /.blog-post --> 
    </div>
    <!--end col 2 --> 
  </div>
  <!-- /.col --> 
</div>
<!-- /container --> 
</body>
</html>
