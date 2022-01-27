<%@page import="java.text.DecimalFormat"%>
<%@page import="utils.TreeUtil"%>
<%@page import="models.Tree"%>
<%
int id = Integer.valueOf(request.getParameter("id"));

request.getSession().setAttribute("currentTreeID", id);

Tree tree = new TreeUtil().getTree(id);
%>
<form id="editTreeForm" method="post" action="/admin/trees">
	<div class="form-row">
		<div class="col-12 col-md-6">
			<label for="type">Type:</label>
			<input class="form-control" type="text" id="type" name="type" value="<%= tree.getType() %>" required>
		</div>
		<div class="col-12 col-md-6">
			<label for="type">Material:</label>
			<input class="form-control" type="text" id="material" name="material" value="<%= tree.getMaterial() %>" required>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12">
			<label for="type">Description:</label>
			<textarea class="form-control" rows="3" type="text" id="description" name="description" value="<%= tree.getDescription() %>" required></textarea>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12">
			<label for="type">Supplier Name:</label>
			<input class="form-control" type="text" id="supplier" name="supplier" value="<%= tree.getSupplier() %>" required>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12 col-md-6">
			<label for="height">Height:</label>
			<div class="input-group">
				<input class="form-control" type="number" id="height" name="height" value="<%= tree.getHeight() %>" required>
				<div class="input-group-append">
					<div class="input-group-text">cm</div>
				</div>
			</div>
		</div>
		<div class="col-12 col-md-6">
			<label for="productDeposit">Deposit Percentage:</label>
			<div class="input-group">
				<input class="form-control" type="number" id="depositPercentage" name="depositPercentage" value="<%= new DecimalFormat("0.#").format(tree.getDepositPercentage()) %>" min="0" max="100" required>
				<div class="input-group-append">
					<div class="input-group-text">%</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12 col-md-6">
			<label for="pricePerDay">Daily Price:</label>
			<div class="input-group">
				<div class="input-group-append">
					<div class="input-group-text">£</div>
				</div>
				<input class="form-control" type="number" id="pricePerDay" name="pricePerDay" value="<%= tree.getPricePerDay() %>" required>
			</div>
		</div>
		<div class="col-12 col-md-6">
			<label for="stockLevel">Stock Level:</label>
			<div class="form-group">
				<input class="form-control" type="number" min="0" name="stockLevel" id="stockLevel" value="<%= tree.getStockLevel() %>" required>
			</div>
		</div>
	</div>
	<hr class="mt-0">
	<button class="btn btn-block btn-success" type="submit">Save Tree</button>
</form>