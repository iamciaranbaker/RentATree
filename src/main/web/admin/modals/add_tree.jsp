<%@page import="utils.TreeUtil"%>
<%@page import="models.Tree"%>
<form id="addTreeForm" method="post" action="/admin/trees">
	<div class="form-row">
		<div class="col-12 col-md-6">
			<label for="type">Type:</label>
			<input class="form-control" type="text" id="type" name="type" required>
		</div>
		<div class="col-12 col-md-6">
			<label for="type">Material:</label>
			<input class="form-control" type="text" id="material" name="material" required>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12">
			<label for="type">Description:</label>
			<textarea class="form-control" rows="3" type="text" id="description" name="description" required></textarea>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12">
			<label for="type">Supplier Name:</label>
			<input class="form-control" type="text" id="supplier" name="supplier" required>
		</div>
	</div>
	<br>
	<div class="form-row">
		<div class="col-12 col-md-6">
			<label for="height">Height:</label>
			<div class="input-group">
				<input class="form-control" type="number" id="height" name="height" required>
				<div class="input-group-append">
					<div class="input-group-text">cm</div>
				</div>
			</div>
		</div>
		<div class="col-12 col-md-6">
			<label for="productDeposit">Deposit Percentage:</label>
			<div class="input-group">
				<input class="form-control" type="number" id="depositPercentage" name="depositPercentage" min="0" max="100" required>
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
				<input class="form-control" type="number" id="pricePerDay" name="pricePerDay" required>
			</div>
		</div>
		<div class="col-12 col-md-6">
			<label for="stockLevel">Stock Level:</label>
			<div class="form-group">
				<input class="form-control" type="number" min="0" name="stockLevel" id="stockLevel" required>
				<input class="form-control" type="text" id="addTree" name="addTree" hidden>
			</div>
		</div>
	</div>
	<hr class="mt-0">
	<button class="btn btn-block btn-success" type="submit">Add Tree</button>
</form>