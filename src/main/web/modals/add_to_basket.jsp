<%@ page import="utils.TreeUtil" %>
<%@ page import="models.Tree" %>
<%
int id = Integer.valueOf(request.getParameter("id"));

request.getSession().setAttribute("currentTreeID", id);

Tree tree = new TreeUtil().getTree(id);
%>
<form id="addToBasketForm" method="post" action="/browse">
	<div class="form-row">
		<div class="col-12 col-md-8">
			<h3><%= tree.getFormattedName() %></h3>
			<p><%= tree.getDescription() %></p>
		</div>
		<div class="col-12 col-md-4 form-group">
			<label for="quantity">Quantity:</label>
			<% if (tree.getStockLevel() >= 3) { %>
			<input type="number" min="1" max="3" value="1" name="quantity" class="form-control" required>
			<small>Maximum: 3</small>
			<% } else { %>
			<input type="number" min="1" max="<%= tree.getStockLevel() %>" value="1" name="quantity" class="form-control" required>
			<small>Maximum: <%= tree.getStockLevel() %></small>
			<% } %>
			<br>
			<small>Stock Level: <%= tree.getStockLevel() %></small>
		</div>
	</div>
	<hr/>
	<div class="form-row">
		<div class="col form-group">
			<label for="rentalStartDate">Rental Start Date:</label>
			<input id="rentalStartDate" type="date" name="rentalStartDate" min="2021-12-01" max="2022-01-14" class="form-control" required>
		</div>
		<div class="col form-group">
			<label for="rentalEndDate">Rental End Date:</label>
			<input id="rentalEndDate" type="date" name="rentalEndDate" min="2021-12-01" max="2022-01-14" class="form-control" required>
		</div>
	</div>
	<hr class="mt-0" />
	<button class="btn btn-block btn-success" type="submit">Add to Basket</button>
</form>
<script>
$("#rentalStartDate").on("change", function() {
	let value = $("#rentalStartDate").val();
	$("#rentalEndDate").attr("min", value);
});
</script>