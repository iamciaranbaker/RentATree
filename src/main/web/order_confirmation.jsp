<%@page import="utils.BasketUtil.DeliveryIn"%>
<%@page import="utils.BasketUtil.DeliveryOut"%>
<%@page import="utils.TreeUtil"%>
<%@page import="models.OrderItem"%>
<%@page import="utils.Util"%>
<%@page import="models.Order"%>
<%@page import="utils.OrderUtil"%>
<%@ include file="templates/header.jsp" %>
<%
Order order = (Order) request.getAttribute("order");
%>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<div class="d-print-block d-none text-right mr-3 mt-3">
			<h2>Rent A Tree Limited</h2>
			<p>info@rentatree.com</p>
			<p>+ 01 234 567 88</p>
		</div>
		<div class="jumbotron jumbotron-fluid pb-5 d-print-none"> 
		  <div class="container">
		    <h1 class="display-4">Order Confirmed!</h1>
		    <p class="lead">We have received your order and our team are preparing your items as we speak.</p>
		    <p style="font-size:20px;">Status: <span class="badge badge-info p-2">Processing</span></p>
		    <small class="text-muted">Last updated: <%= order.getDateLastUpdated() %></small>
		  </div>
		</div>
		<div class="container container-fluid">
			<div class="row">
				<div class="col-6">
					<ol class="breadcrumb bg-white">
					    <li class="breadcrumb-item"><a href="/orders">My Orders</a></li>
					    <li class="breadcrumb-item active" aria-current="page">#<%= order.getId() %></li>
					  </ol>
				</div>
				<div class="col-6">
					<button class="btn btn-outline-info btn-sm  float-right d-print-none" onclick="print()">Print Order</button>
				</div>
			</div>
		</div>
		<div class="container container-fluid px-4 pt-1 pb-4 px-md-5 pt-md-1 pb-md-5 mt-0 border-1">
			<div class="row">
				<div class="col-md-8 px-md-5 pb-md-5 pt-md-3">
					<div class="row mb-5">
						<h3>Order Summary</h3>
					</div>
					<div class="row text-muted small">
						<div class="col-4">Item</div>
						<div class="col-4 text-center">Quantity</div>
						<div class="col d-none d-md-block text-center">Price</div>
					</div>
					<% for (OrderItem orderItem : order.getItems()) { %>
					<div class="row main align-items-center border-bottom p-3 product-item">
						<div class="col-4">							
							<div class="row"><%= new TreeUtil().getTree(orderItem.getTreeId()).getFormattedName() %></div>
							<div class="row text-muted small"><%= new TreeUtil().getTree(orderItem.getTreeId()).getHeight() + "cm" %></div>
						</div>
						<div class="col-4 text-center" > 
							<%= orderItem.getQuantity() %>
						</div>
						<div class="col d-none d-md-block text-center">
							<%= new Util().getFormattedPrice(new TreeUtil().getTree(orderItem.getTreeId()).getPricePerDay()) %>
						</div>
						<div class="col-12 ">
							<p class="text-muted small align-content-center p-1 pl-2 m-0">
							<div class="row bg-light text-muted mb-0 align-content-center p-0 mt-2 small">
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6">
											<span class="font-weight-bold mr-2">Rental Start:</span>
										</div>
										<div class="col-12 col-md-6">
											<%= orderItem.getRentalStartDate() %>
										</div>
									</div> 
								</div>
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6">
											<span class="font-weight-bold mr-2">Rental End:</span>
										</div>
										<div class="col-12 col-md-6">
											<%= orderItem.getRentalEndDate() %>
										</div>
									</div> 
								</div>
							</div>
							<div class="row bg-light text-muted mb-0 align-content-center p-0 mt-2 small">
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6">
											<span class="font-weight-bold mr-2">Delivery Out:</span>
										</div>
										<div class="col-12 col-md-6">
											<% if (orderItem.getDeliveryOut() == DeliveryOut.COLLECT_FROM_WAREHOUSE) { %>
											Collect From Warehouse
											<% } else if (orderItem.getDeliveryOut() == DeliveryOut.AM_DELIVERY) { %>
											AM Delivery
											<% } else if (orderItem.getDeliveryOut() == DeliveryOut.PM_DELIVERY) { %>
											PM Delivery
											<% } else { %>
											Standard Delivery
											<% } %>
										</div>
									</div> 
								</div>
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6">
											<span class="font-weight-bold mr-2">Delivery In:</span>
										</div>
										<div class="col-12 col-md-6">
											<% if (orderItem.getDeliveryIn() == DeliveryIn.RETURN_TO_WAREHOUSE) { %>
											Return To Warehouse
											<% } else if (orderItem.getDeliveryIn() == DeliveryIn.AM_COLLECTION) { %>
											AM Collection
											<% } else if (orderItem.getDeliveryIn() == DeliveryIn.PM_COLLECTION) { %>
											PM Collection
											<% } else { %>
											Standard Collection
											<% } %>
										</div>
									</div> 
								</div>
							</div>
						</div>
					</div>
					<% } %>
					<div class="row mt-3">
						<div class="col-12">
							<h4>Customer Details</h4>
							<form class="border-top border-dark mt-4">
							  <div class="form-row mt-4">
							    <div class="form-group col-md-6">
							      <label for="Name" class="font-weight-bold">Name</label> 
							      <input type="text" class="form-control-plaintext" readonly id="firstName" value="<%= customer.getFirstName() %>" required>
							    </div>
							    <div class="form-group col-md-6">
								    <label for="email" class="font-weight-bold">Email Address</label>
								    <input type="email" class="form-control-plaintext" readonly id="emailAddress" value="<%= customer.getEmailAddress() %>" required>
								  </div>
							  </div>
							  <div class="form-row">
								  <div class="form-group col-md-6">
								    <label for="phone" class="font-weight-bold">Phone Number</label>
								    <input type="phone" class="form-control-plaintext" readonly id="phoneNumber" value="<%= customer.getPhoneNumber() %>" required>
								  </div>
								  <div class="form-group col-md-6">
							    <label for="inputAddress" class="font-weight-bold">Address Line 1</label>
							    <input type="text" class="form-control-plaintext" readonly id="addressLine1" value="<%= customer.getAddressLine1() %>" required>
							  </div>
							  </div>
							  <div class="form-group">
							    <label for="inputAddress2" class="font-weight-bold">Address Line 2</label>
							    <input type="text" class="form-control-plaintext" readonly id="addressLine2" value="<%= customer.getAddressLine2() %>">
							  </div>
							  <div class="form-row">
							    <div class="form-group col-md-6">
							      <label for="inputCity" class="font-weight-bold">City</label>
							      <input type="text" class="form-control-plaintext" readonly id="city" value="<%= customer.getCity() %>" required>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="postCode" class="font-weight-bold">Post Code</label>
							      <input type="text" class="form-control-plaintext" readonly id="postCode" value="<%= customer.getPostCode() %>" required>
							    </div>
							  </div>
						</form>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="row bg-light p-4">
						<div class="col-12">
							<h4>Summary</h4>
						</div>
						<div class="col-12 border-top border-dark pt-2 mt-2">
							<div class="row mt-2" style="padding-top: 2px">
								<div class="col-6 font-weight-bold">Total</div>
								<div class="col-6 text-right">
									<%= new Util().getFormattedPrice(order.getTotalPrice()) %>
								</div>
							</div>
							<% if (order.getTotalDeposit() > 0) { %>
							<div class="row small mt-2">
								<div class="col-6 font-weight-bold">Refundable Deposit</div>
								<div class="col-6 text-right">+ <%= new Util().getFormattedPrice(order.getTotalDeposit()) %></div>
							</div>
							<% } %>
							<% if (order.getTotalDelivery() > 0) { %>
							<div class="row small mt-2">
								<div class="col-6 font-weight-bold">Delivery</div>
								<div class="col-6 text-right">+ <%= new Util().getFormattedPrice(order.getTotalDelivery()) %></div>
							</div>
							<% } %>
							<hr />
							<div class="row">
								<div class="col-6 font-weight-bold">Amount Paid</div>
								<div class="col-6 text-right">
									<%= new Util().getFormattedPrice(order.getTotal()) %></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container container-fluid d-print-block d-none border-top text-center pt-4">
			<h4>Need Help?</h4>
			<p>If you have any problems with your order, please contact a member of our team by contacting us via the details at the top of this page.</p>
		</div>
		<%@ include file="templates/footer.jsp" %>