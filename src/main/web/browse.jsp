<%@page import="java.text.DecimalFormat"%>
<%@page import="models.Tree"%>
<%
int sortBy = 0;
if (request.getSession().getAttribute("sortBy") != null) sortBy = (int) request.getSession().getAttribute("sortBy");

ArrayList<Tree> results = new ArrayList<Tree>();
if (request.getAttribute("results") != null) results = (ArrayList<Tree>) request.getAttribute("results");
%>
<%@ include file="templates/header.jsp" %>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<div class="container container-fluid p-5">
			<div class="row">
				<div class="col-12 col-md-3 pt-3 mb-4">
					<h6>Filters</h6>
					<hr/>
					<form id="sortByForm" method="post" action="" class="mt-3">
						<div class="form-group">
							<label for="sortBy" class="mb-2">Sort By:</label>
							<select name="sortBy" id="sortBy" class="form-control">
						        <option <% if (sortBy == 0) { %>selected<% } %> value="0">Price (Highest - Lowest)</option>
						        <option <% if (sortBy == 1) { %>selected<% } %> value="1">Price (Lowest - Highest)</option>
						        <option <% if (sortBy == 2) { %>selected<% } %> value="2">Height (Tallest - Shortest)</option>
						        <option <% if (sortBy == 3) { %>selected<% } %> value="3">Height (Shortest - Tallest)</option>
							</select>
						</div>
					</form>
				</div>
				<div class="col-12 col-md-9">
					<div class="container container-fluid" id="productItems">
						<% if (!(searchQuery.equalsIgnoreCase(""))) { %>
						<div class="row">
							<h4>Showing <%= results.size() %> results for "<%= searchQuery %>"...</h4>
						</div>
						<% } %>
						<div class="row">
							<% for (Tree tree : results) { %>
							<div class="col-6 col-md-4 my-2">
								<div class="card card-sm">
									<img src="assets/tree3.jpg" class="card-img-top" alt="...">
									<div class="card-body">
										<h5 class="card-title"><%= tree.getFormattedName() %></h5>
										<p class="card-text">From £<%= new DecimalFormat("0.00").format(tree.getPricePerDay()) %></p> 
										<hr/>
										<form method="post" action="">
											<% if (tree.getStockLevel() != 0) { %>
											<button class="btn btn-block btn-success" type="button" onclick="addToBasket(<%= tree.getId() %>)">Add to Basket</button>
											<% } else { %>
											<button class="btn btn-block btn-success" type="button" disabled>Out of Stock</button>
											<% } %>
										</form>
									</div>
								</div>
							</div>
							<% } %>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="templates/footer.jsp" %>