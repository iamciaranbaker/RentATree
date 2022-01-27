<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	    <link rel="stylesheet" href="assets/login.css">
	    <title>Rent A Tree</title>
	</head>
	<body class="text-center">
    	<form class="form-signin" method="post" action="/login">
    		<img src="assets/logo.png" style="margin-bottom: 10px" alt="Logo" width="52" height="52">
			<h1 class="h3 font-weight-normal" style="margin-bottom: 25px">Login</h1> 
      		<% if (request.getAttribute("error") != null) { %>
      		<div class="row">
      			<div class="col-12">
					<div class="alert alert-danger text-center">
						<%= request.getAttribute("error") %>
					</div>
				</div>
			</div>
			<% } %>
			<div class="row">
				<div class="col-12" style="text-align: left">
					<label for="emailAddress">Email Address:</label>
					<input type="email" name="emailAddress" class="form-control" placeholder="Email Address" required autofocus>
				</div>
			</div>
			<div class="row" style="margin-top: 15px">
				<div class="col-12" style="text-align: left">
					<label for="password">Password:</label>
					<input type="password" name="password" class="form-control" placeholder="Password" required>
				</div>
			</div>
			<div class="row" style="margin-top: 20px">
				<div class="col-12" style="text-align: left">
					<button class="btn btn-success btn-block" type="submit">Login</button>
				</div>
			</div>
			<p class="text-center" style="padding-top: 15px; margin-bottom: 0px">Don't have an account? <a href="/register">Register</a></p>
		</form>
	</body>
</html>
<% request.getSession().removeAttribute("loginError"); %>