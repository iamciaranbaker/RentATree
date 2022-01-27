<%@page import="utils.BasketUtil"%>
<%
//run basket setup as it may not exist
new BasketUtil().setupBasket(request.getSession());

String currentPage = "";
if (request.getSession().getAttribute("currentPage") != null) currentPage = (String) request.getSession().getAttribute("currentPage");
%>
<script src="https://kit.fontawesome.com/c7faeac04b.js" crossorigin="anonymous"></script>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#">
		<img src="/assets/logo.png" width="auto" height="25px;"/>
		Rent A Tree
		<span class="small text-muted"><small>Admin Portal</small></span>
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav text-white ml-auto">
			<li class="nav-item <% if (currentPage.equals("/admin/trees")) { %>active<% } %>">
				<a class="nav-link" href="/admin/trees">Trees</a> 
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li class="nav-item <% if (currentPage.equals("/admin/orders")) { %>active<% } %>">
				<a class="nav-link" href="/admin/orders">Orders</a>
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li>
				<a class="nav-link" href="/">Exit Admin</a>
			</li>
		</ul>
	</div>
</nav>