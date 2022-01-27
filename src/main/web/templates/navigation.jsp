<%@page import="models.Basket"%>
<%@page import="models.BasketItem"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Customer"%>
<%
Customer customer = null;
if (request.getSession().getAttribute("customer") != null) customer = (Customer) request.getSession().getAttribute("customer");

Basket basket = (Basket) request.getSession().getAttribute("basket");

String searchQuery = "";
if (request.getSession().getAttribute("searchQuery") != null) searchQuery = (String) request.getSession().getAttribute("searchQuery");

String currentPage = "";
if (request.getSession().getAttribute("currentPage") != null) currentPage = (String) request.getSession().getAttribute("currentPage");
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#">
		<img src="assets/logo.png" width="auto" height="25px">
		Rent A Tree
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<form class="form-inline my-2 my-lg-0 ml-auto mr-2" method="post" action="/browse">
			<input class="form-control mr-sm-2" name="searchQuery" type="search" placeholder="Search..." aria-label="Search" value="<%= searchQuery %>">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</form>
		<ul class="navbar-nav text-white">
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li class="nav-item <% if (currentPage.equals("/")) { %>active<% } %>">
				<a class="nav-link" href="/">Home</a> 
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li class="nav-item <% if (currentPage.equals("/browse")) { %>active<% } %>">
				<a class="nav-link" href="/browse">Browse</a>
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li>
				<a class="nav-link <% if (currentPage.equals("/basket") || currentPage.equals("/checkout") || currentPage.equals("/order-confirmation")) { %>active<% } %>" href="/basket">
					<i class="fas fa-shopping-cart"></i>
					<% if (basket.getSize() != 0) { %>
					<sup class="basket-badge"><%= basket.getSize() %></sup>
					<% } %>
				</a>
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<% if (customer != null) { %>
			<li class="nav-item dropdown <% if (currentPage.equals("/orders")) { %>active<% } %>">
				<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
				<%= customer.getFirstName() %>
				</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown" style="min-width: 5rem;">
					<a class="dropdown-item" href="/orders">Orders</a>
					<% if (customer.getIsAdmin() == 1) { %>
					<a class="dropdown-item" href="/admin/trees">Admin Portal</a>
					<% } %>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="/logout">Logout</a>
				</div>
			</li>
			<% } else { %>
			<li>
				<a class="nav-link" href="/login">Login</a>
			</li>
			<li class="align-middle">
				<a class="nav-link text-muted">|</a>
			</li>
			<li>
				<a class="nav-link" href="/register">Register</a>
			</li>
			<% } %>
		</ul>
	</div>
</nav>