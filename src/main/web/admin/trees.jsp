<%@page import="utils.TreeUtil"%>
<%@page import="utils.CustomerUtil"%>
<%@ page import="utils.Util" %>
<%@ page import="models.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="models.Tree" %>
<%
int sortBy = 0;
if (request.getSession().getAttribute("sortByAdmin") != null) sortBy = (int) request.getSession().getAttribute("sortByAdmin");

int itemsPerPage = 5;
if (request.getSession().getAttribute("itemsPerPageAdmin") != null) itemsPerPage = (int) request.getSession().getAttribute("itemsPerPageAdmin");

ArrayList<Tree> results = new ArrayList<Tree>();
if (request.getAttribute("results") != null) results = (ArrayList<Tree>) request.getAttribute("results");
%>
<%@ include file="/templates/header.jsp" %>
	<body style="height: 100%">
		<%@ include file="/admin/templates/navigation.jsp" %>
		<div class="jumbotron jumbotron-fluid py-4 d-print-none"> 
		  <div class="container">
		    <h1 class="display-4">Trees</h1>
		    <p class="lead">Manage all trees available to customers</p>
		  </div>
		</div>
		<div class="main container container-fluid pb-5 px-1">
			<div id="options" class="col-12 bg-light text-right" style="padding: 10px; margin-bottom: 15px">
					<button class="btn btn-sm btn-success" onclick="addTree()">Add Tree</button>
				</div>
			<div class="container container-fluid">
				<form method="post" action="" id="treesForm">
					<div class="row">
						<div class="col-md-2" style="padding-left: 0px">
							<label for="itemsPerPage">Items Per Page:</label>
							<select name="itemsPerPage" id="itemsPerPage" class="form-control">
								<% for (int i = 5; i <= 30; i+= 5) { %>
									<option value=<%= i %> <% if (i == itemsPerPage) { %>selected<% } %>><%= i %></option>
								<% } %>
							</select>
						</div>
						<div class="col-md-4">
							<label for="sortBy">Sort By:</label>
							<select name="sortBy" id="sortBy" class="form-control">
								<option <% if (sortBy == 0) { %>selected<% } %> value="0">Price (Highest - Lowest)</option>
						        <option <% if (sortBy == 1) { %>selected<% } %> value="1">Price (Lowest - Highest)</option>
						        <option <% if (sortBy == 2) { %>selected<% } %> value="2">Height (Tallest - Shortest)</option>
						        <option <% if (sortBy == 3) { %>selected<% } %> value="3">Height (Shortest - Tallest)</option>
							</select>
						</div>
					</div>
				</form>
				<div class="row">
					<div class="table-responsive" style="margin-top: 25px">
						<table id="orders" class="table table-hover" width="100%">
							<thead class="thead-light mb-2">
								<th class="align-middle" style="text-align: center">Tree</th>
								<th class="align-middle" style="text-align: center">Supplier</th>
								<th class="align-middle" style="text-align: center">Daily Price</th>
								<th class="align-middle" style="text-align: center">Deposit Percentage</th>
								<th class="align-middle" style="text-align: center">Stock Level</th>
								<th class="align-middle" style="text-align: center">Options</th>
							</thead>
							<tbody>
								<% for (Tree tree : results) { %>
								<tr>
									<td class="align-middle" style="text-align: center"><%= tree.getFormattedName() %></td>
									<td class="align-middle" style="text-align: center"><%= tree.getSupplier() %></td>
									<td class="align-middle" style="text-align: center"><%= new Util().getFormattedPrice(tree.getPricePerDay()) %></td>
									<td class="align-middle" style="text-align: center"><%= new DecimalFormat("0.#").format(tree.getDepositPercentage()) %>%</td>
									<td class="align-middle" style="text-align: center"><%= tree.getStockLevel() %></td>
									<td class="align-middle" style="text-align: center">
										<button class="btn btn-sm btn-success" onclick="editTree(<%= tree.getId() %>)"><i class="fas fa-edit"></i></button>
										<form method="post" style="display: inline">
											<button class="btn btn-sm btn-danger" name="delete" value="<%= tree.getId() %>"><i class="far fa-trash-alt"></i></button>
										</form>
									</td>
								</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/templates/footer.jsp" %>