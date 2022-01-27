<%@page import="utils.TreeUtil"%>
<%@page import="models.OrderItem"%>
<%@page import="models.Order"%>
<%
ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("results");
%>
<%@ include file="templates/header.jsp" %>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<div class="container container-fluid py-5 px-5 px-md-0">
			<div class="row">
				<div class="col-12 pb-3">  
					<h1>My Orders</h1>
					<p>Below you will see the status of all your orders</p>
				</div>
			</div>
			<div class="row">
				<div class="col-12 order-first order-md-last">
					<div class="row">
						<% for (Order order : orders) { %>
						<div class="col-12 col-md-6">
							<div class="card mb-3">
								<div class="row no-gutters">
									<div class="col-4" style="background: url('assets/tree3.jpg'); background-size: cover; overflow: hidden;"></div>
									<div class="col-8">
										<div class="card-body" style="height: 200px">
											<a class="h5 card-title text-dark" href="">#<%= order.getId() %></a>
												<p class="small">
												<% int count = 1; %>
												<% for (OrderItem orderItem : order.getItems()) { %>
												<%= orderItem.getQuantity() + "x " + new TreeUtil().getTree(orderItem.getTreeId()).getFormattedName() %>
												<% if (count != order.getItems().size()) { %>
												<br>
												<% } %>
												<% } %>
											</p>
											<h6 class="mb-1">
												<% if (order.getStatus().equalsIgnoreCase("delivered")) { %>
												<span class="badge badge-info p-2">Delivered</span>
												<% } else if (order.getStatus().equalsIgnoreCase("returned")) { %>
												<span class="badge badge-success p-2">Returned</span>
												<% } else if (order.getStatus().equalsIgnoreCase("missed")) { %>
												<span class="badge badge-danger p-2">Missed</span>
												<% } else { %>
												<span class="badge badge-secondary p-2">Processing</span>
												<% } %>
											</h6>
										</div>
									</div>
								</div>
							</div> 
						</div>
						<% } %>
						<% if (orders.size() == 0) { %>
						<p style="margin-left: 15px">You have no orders!</p>
						<% } %>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12 text-center">
					<hr/>
					<p class="small my-0 py-1 text-muted font-weight-bold">Any problems?</p>
					<p class="small text-center text-muted">Please contact us via the details at the bottom of this page and we will be happy to help.</p>
				</div>
			</div>
		</div>
		<%@ include file="templates/footer.jsp" %>