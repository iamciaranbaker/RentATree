<%@page import="utils.TreeUtil"%>
<%@page import="utils.CustomerUtil"%>
<%@ page import="utils.Util" %>
<%@ page import="models.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="models.Tree" %>
<%@ page import="models.Order" %>
<%@ page import="models.OrderItem" %>
<%
int sortBy = 0;
if (request.getSession().getAttribute("sortByAdmin") != null) sortBy = (int) request.getSession().getAttribute("sortByAdmin");

int itemsPerPage = 5;
if (request.getSession().getAttribute("itemsPerPageAdmin") != null) itemsPerPage = (int) request.getSession().getAttribute("itemsPerPageAdmin");

ArrayList<Order> results = new ArrayList<Order>();
if (request.getAttribute("results") != null) results = (ArrayList<Order>) request.getAttribute("results");
%>
<%@ include file="/templates/header.jsp" %>
	<body style="height: 100%">
		<%@ include file="/admin/templates/navigation.jsp" %>
		<div class="jumbotron jumbotron-fluid py-4 d-print-none"> 
		  <div class="container">
		    <h1 class="display-4">Orders</h1>
		    <p class="lead">Manage all orders on your platform</p>
		  </div>
		</div>
		<form method="post" action="" id="ordersForm">
			<div class="main container container-fluid pb-5 px-1">
				<div id="options" class="col-12 bg-light text-right" style="padding: 10px; margin-bottom: 15px">
					<button class="btn btn-sm btn-info" name="markAs" value="delivered">Mark As Delivered</button>
					<button class="btn btn-sm btn-success" name="markAs" value="returned">Mark As Returned</button>
					<button class="btn btn-sm btn-danger" name="markAs" value="missed">Mark As Missed</button>
				</div>
				<div class="container container-fluid">
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
								<option <% if (sortBy == 0) { %>selected<% } %> value="0">Date Placed (Newest - Oldest)</option>
								<option <% if (sortBy == 1) { %>selected<% } %> value="1">Date Placed (Oldest - Newest)</option>
						        <option <% if (sortBy == 2) { %>selected<% } %> value="2">Status (Start - Finish)</option>
						        <option <% if (sortBy == 3) { %>selected<% } %> value="3">Status (Finish - Start)</option>
						        <option <% if (sortBy == 4) { %>selected<% } %> value="4">Customer (A - Z)</option>
						        <option <% if (sortBy == 5) { %>selected<% } %> value="5">Customer (Z - A)</option>
						        <option <% if (sortBy == 6) { %>selected<% } %> value="6">Total (Highest - Lowest)</option>
						        <option <% if (sortBy == 7) { %>selected<% } %> value="7">Total (Lowest - Highest)</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="table-responsive" style="margin-top: 25px">
							<table id="orders" class="table table-hover" width="100%">
								<thead class="thead-light mb-2">
									<th class="align-middle" style="text-align: center"><input class="align-middle" type="checkbox" id="checkAll"></th>
									<th class="align-middle" style="text-align: center">#</th>
									<th class="align-middle" style="text-align: center">Customer</th>
									<th class="align-middle" style="text-align: center">Items</th>
									<th class="align-middle" style="text-align: center">Status</th>
									<th class="align-middle" style="text-align: center">Total</th>
									<th class="align-middle" style="text-align: center">Options</th>
								</thead>
								<tbody>
									<% for (Order order : results) { %>
									<tr>
										<td class="align-middle" style="text-align: center">
											<input class="align-middle" type="checkbox" name="order-<%= order.getId() %>">
										</td>
										<td class="align-middle" style="text-align: center">#<%= order.getId() %></td>
										<td class="align-middle" style="text-align: center"><%= new CustomerUtil().getCustomer(order.getCustomerd()).getFirstName() + " " + new CustomerUtil().getCustomer(order.getCustomerd()).getLastName() %></td>
										<td class="align-middle" style="text-align: center">
											<% int count = 1; %>
											<% for (OrderItem orderItem : order.getItems()) { %>
											<%= orderItem.getQuantity() + "x " + new TreeUtil().getTree(orderItem.getTreeId()).getFormattedName() %>
											<% if (count != order.getItems().size()) { %>
											<br>
											<% } %>
											<% } %>
										</td>
										<td class="align-middle" style="text-align: center">
											<% if (order.getStatus().equalsIgnoreCase("delivered")) { %>
											<span class="badge badge-info p-2">Delivered</span>
											<% } else if (order.getStatus().equalsIgnoreCase("returned")) { %>
											<span class="badge badge-success p-2">Returned</span>
											<% } else if (order.getStatus().equalsIgnoreCase("missed")) { %>
											<span class="badge badge-danger p-2">Missed</span>
											<% } else { %>
											<span class="badge badge-secondary p-2">Processing</span>
											<% } %>
										</td>
										<td class="align-middle" style="text-align: center"><%= new Util().getFormattedPrice(order.getTotalPrice()) %></td>
										<td class="align-middle" style="text-align: center">
											<button class="btn btn-sm btn-danger" name="delete" value="<%= order.getId() %>"><i class="far fa-trash-alt"></i></button>
										</td>
									</tr>
									<% } %>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</form>
		<%@ include file="/templates/footer.jsp" %>