<%@page import="utils.Util"%>
<%@page import="models.Tree"%>
<%@page import="utils.TreeUtil.SortType"%>
<%@page import="utils.TreeUtil"%>
<%@page import="models.Order"%>
<%@page import="utils.OrderUtil"%>
<%@page import="utils.BasketUtil"%>
<%
request.getSession().setAttribute("currentPage", "/");

// run basket setup as there is no servlet
new BasketUtil().setupBasket(request.getSession());
%>
<%@ include file="templates/header.jsp" %>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<div id="carousel" class="carousel slide" data-ride="carousel" onclick="location.href='/browse'">
			<ol class="carousel-indicators">
				<li data-target="#carousel" data-slide-to="0" class="active"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="assets/bg.jpg" class="d-block w-100" style="opacity: 0.55"> 
					<div class="carousel-caption d-block">
						<h5><span class="badge badge-danger">OFFER</span> Buy One Get One Half Price</h5>
						<p>To celebrate the festive season, why not buy two for the price of 1 and 1/2?!
						<br><br>
						<span class="badge badge-success">AUTO-APPLIED AT CHECKOUT</span></p>
					</div>
				</div>
			</div>
		</div>
		<div class="container container-fluid my-5">
			<div class="container"> 
				<h3 class="text-center" style="margin-bottom: 20px; text-decoration: underline">Our Favourite Trees</h3>
				<div class="row">
					<% for (Tree tree : new TreeUtil().getTrees(SortType.PRICE_HIGHEST_TO_LOWEST, 3)) { %>
					<div class="col-6 col-md-4 my-2">
						<div class="card card-sm">
							<img src="assets/tree3.jpg" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title"><%= tree.getFormattedName() %></h5>
								<p class="card-text">From <%= new Util().getFormattedPrice(tree.getPricePerDay()) %></p> 
								<hr/>
								<form method="post" action="/basket">
									<button class="btn btn-block btn-success" type="button" onclick="addToBasket(<%= tree.getId() %>)">Add to Basket</button>
								</form>
							</div>
						</div>
					</div>
					<% } %>
				</div>
			</div>
		</div>
		<%@ include file="templates/footer.jsp" %>