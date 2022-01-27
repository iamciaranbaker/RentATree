<%@page import="utils.BasketUtil.DeliveryIn"%>
<%@page import="utils.BasketUtil.DeliveryOut"%>
<%@ page import="utils.Util" %>
<%@ page import="utils.TreeUtil" %>
<%@ page import="java.text.DecimalFormat" %>
<%
int count = 0;
%>
<%@ include file="templates/header.jsp" %>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<div class="container container-fluid p-4 p-md-5 border-1">
			<div class="row">
				<div class="col-md-8 p-md-5 pt-0">
					<div class="row mb-5">
						<h3>Basket</h3>
					</div>
					<div class="row text-muted small">
						<div class="col-3">Item</div>
						<div class="col-3 text-center">Quantity</div>
						<div class="col d-none d-md-block text-center">Price</div>
						<div class="col-3 text-center">Options</div>
					</div>
					<% for (BasketItem basketItem : basket.getItems()) { %>
					<form class="row main align-items-center border-bottom p-3 product-item" method="post" action="/basket">
						<div class="col-3">
							<div class="row"><%= new TreeUtil().getTree(basketItem.getTreeId()).getFormattedName() %></div>
							<div class="row text-muted small"><%= new TreeUtil().getTree(basketItem.getTreeId()).getHeight() + "cm" %></div>
						</div>
						<div class="col-3 text-center">
							<input type="number" min="1" max="3" name="quantity-<%= count %>" value="<%= basketItem.getQuantity() %>" style="width: 50px; text-align: center;" />
						</div>
						<div class="col d-none d-md-block text-center">
							<%= new Util().getFormattedPrice(new TreeUtil().getTree(basketItem.getTreeId()).getPricePerDay()) %>/day
						</div>
						<div class="col-3 text-center text-nowrap">
							<button class="btn btn-sm btn-success pr-2" name="update" value="<%= count %>">Update</button>
							<button class="btn btn-sm btn-danger" name="remove" value="<%= count %>">Remove</button>
						</div>
						<div class="col-12">
							<p class="text-muted small align-content-center p-1 pl-2 m-0">
							</p>
							<div class="row bg-light text-muted mb-0 align-content-center p-0 mt-2 small">
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6" style="padding-top: 3px">
											<span class="font-weight-bold mr-2">Rental Start:</span>
										</div>
										<div class="col-12 col-md-6">
											<input type="date" name="rentalStartDate-<%= count %>" id="rentalStartDate-<%= count %>" min="2021-12-01" max="2022-01-14" value="<%= basketItem.getRentalStartDate() %>" />
										</div>
									</div>
								</div>
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-12 col-md-6" style="padding-top: 3px">
											<span class="font-weight-bold mr-2">Rental End:</span>
										</div>
										<div class="col-12 col-md-6">
											<input type="date" name="rentalEndDate-<%= count %>" id="rentalEndDate-<%= count %>" min="2021-12-01" max="2022-01-14" value="<%= basketItem.getRentalEndDate() %>" />
										</div>
									</div>
								</div>
							</div>
							<div class="row bg-light text-muted mb-0 align-content-center p-0 mt-2 small">
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-6" style="padding-top: 5px">
											<span class="font-weight-bold mr-2">Delivery Out:</span>
										</div>
										<div class="col-6">
											<select name="deliveryOut-<%= count %>" class="form-control form-control-sm">
												<option <% if (basketItem.getDeliveryOut() == DeliveryOut.STANDARD_DELIVERY) { %>selected<% } %> value="0">Standard Delivery (Free)</option>
												<option <% if (basketItem.getDeliveryOut() == DeliveryOut.COLLECT_FROM_WAREHOUSE) { %>selected<% } %> value="1">I'll Collect From The Warehouse (Free)</option>
												<option <% if (basketItem.getDeliveryOut() == DeliveryOut.AM_DELIVERY) { %>selected<% } %> value="2">AM Delivery (+ £3.99)</option>
												<option <% if (basketItem.getDeliveryOut() == DeliveryOut.PM_DELIVERY) { %>selected<% } %> value="3">PM Delivery (+ £3.99)</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-6 p-2 mb-3 mb-md-0">
									<div class="row">
										<div class="col-6" style="padding-top: 5px">
											<span class="font-weight-bold mr-2">Delivery In:</span>
										</div>
										<div class="col-6">
											<select name="deliveryIn-<%= count %>" class="form-control form-control-sm">
												<option <% if (basketItem.getDeliveryIn() == DeliveryIn.STANDARD_COLLECTION) { %>selected<% } %> value="0">Standard Collection (Free)</option>
												<option <% if (basketItem.getDeliveryIn() == DeliveryIn.RETURN_TO_WAREHOUSE) { %>selected<% } %> value="1">I'll Return To The Warehouse (Free)</option>
												<option <% if (basketItem.getDeliveryIn() == DeliveryIn.AM_COLLECTION) { %>selected<% } %> value="2">AM Collection (+ £3.99)</option>
												<option <% if (basketItem.getDeliveryIn() == DeliveryIn.PM_COLLECTION) { %>selected<% } %> value="3">PM Collection (+ £3.99)</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<% count++; %>
					<% } %>
					<% if (basket.getSize() != 0) { %>
					<div class="row mt-2 text-muted mt-2 mb-3">
						<div class="col-12 text-center small">Wow! You've chosen some great trees!</div>
					</div>
					<div class="row d-none d-md-block mt-2">
						<div class="col-12 text-center">
							<img class="img-responsive  mx-auto" src="assets/pine-tree.png" width="auto" height="50px" alt="" />
						</div>
					</div>
					<% } else { %>
					<div class="col-12 p-2 text-center" style="margin-top: 120px">
						There are no items in your basket.
					</div>
					<% } %>
				</div>
				<div class="col-md-4 bg-light p-4">
					<div class="row">
						<div class="col-12">
							<h4>Summary</h4>
						</div>
						<div class="col-12 border-top border-dark pt-2 mt-2">
							<div class="row mt-2" style="padding-top: 2px">
								<div class="col-6 font-weight-bold">Total</div>
								<div class="col-6 text-right">
									<%= new Util().getFormattedPrice(basket.getTotalPrice()) %></div>
							</div>
							<% if (basket.getTotalDeposit() > 0) { %>
							<div class="row small mt-2">
								<div class="col-6 font-weight-bold">Refundable Deposit</div>
								<div class="col-6 text-right">+ <%= new Util().getFormattedPrice(basket.getTotalDeposit()) %></div>
							</div>
							<% } %>
							<% if (basket.getTotalDelivery() > 0) { %>
							<div class="row small mt-2">
								<div class="col-6 font-weight-bold">Delivery</div>
								<div class="col-6 text-right">+ <%= new Util().getFormattedPrice(basket.getTotalDelivery()) %></div>
							</div>
							<% } %>
							<hr />
							<div class="row">
								<div class="col-6 font-weight-bold">Amount to Pay</div>
								<div class="col-6 text-right">
									<%= new Util().getFormattedPrice(basket.getTotalDeposit() + basket.getTotalPrice() + basket.getTotalDelivery()) %></div>
							</div>
							<hr />
							<div class="row mt-2">
								<div class="col-12 text-center">
									<p id="saveToProceedMessage" class="text-danger small p-0 mb-2 mt-0 mx-0 mx-auto" style="display: none">Please save your delivery changes to proceed.</p>
									<a href="/checkout">
										<button class="btn btn-block btn-success checkoutOption <% if (basket.getSize() == 0) { %>disabled<% } %>" <% if (basket.getSize() == 0) { %>disabled<% } %>>Checkout</button>
									</a>
									<a href="/browse"><button class="btn btn-block btn-info" style="margin-top: 10px">Continue Shopping</button></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="templates/footer.jsp" %>
	