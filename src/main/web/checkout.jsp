<%@page import="utils.TreeUtil"%>
<%@page import="utils.Util"%>
<%@ include file="templates/header.jsp" %>
	<body>
		<%@ include file="templates/navigation.jsp" %>
		<form method="post" action="/checkout">
			<div class="container container-fluid p-4 p-md-5 border-1">
				<div class="row">
					<div class="col-md-8 pt-0">
						<div class="bg-light px-5 py-4 mr-0 mr-md-2">
							<h4>Customer Details</h4>
							<div class="border-top border-dark mt-4">
							  <div class="form-row mt-4">
							    <div class="form-group col-md-6">
							      <label for="firstName">First Name <span style="color: red">*</span></label>
							      <input type="text" class="form-control" id="firstName" name="firstName" value="<%= customer.getFirstName() %>" placeholder="First Name" required>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="lastName">Last Name <span style="color: red">*</span></label>
							      <input type="text" class="form-control" id="lastName" name="lastName" value="<%= customer.getLastName() %>" placeholder="Last Name" required>
							    </div>
							  </div>
							  <div class="form-row">
								  <div class="form-group col-md-6">
								    <label for="email">Email Address <span style="color: red">*</span></label>
								    <input type="email" class="form-control" id="emailAddress" name="emailAddress" value="<%= customer.getEmailAddress() %>" placeholder="Email Address" required>
								  </div>
								  <div class="form-group col-md-6">
								    <label for="phoneNumber">Phone Number <span style="color: red">*</span></label>
								    <input type="phone" class="form-control" id="phoneNunber" name="phoneNumber" value="<%= customer.getPhoneNumber() %>" placeholder="Phone Number" required>
								  </div>
							  </div>
							  <div class="form-group">
							    <label for="addressLine1">Address Line 1 <span style="color: red">*</span></label>
							    <input type="text" class="form-control" id="addressLine1" name="addressLine1" placeholder="Address Line 1" value="<%= customer.getAddressLine1() %>" required>
							  </div>
							  <div class="form-group">
							    <label for="addressLine2">Address Line 2</label>
							    <input type="text" class="form-control" id="addressLine2" name="addressLine2" placeholder="Address Line 2" value="<%= customer.getAddressLine2() %>">
							  </div>
							  <div class="form-row">
							    <div class="form-group col-md-6">
							      <label for="city">City <span style="color: red">*</span></label>
							      <input type="text" class="form-control" id="city" name="city" placeholder="City" value="<%= customer.getCity() %>" required>
							    </div>
							    <div class="form-group col-md-6">
							      <label for="postCode">Post Code <span style="color: red">*</span></label>
							      <input type="text" class="form-control" id="postCode" name="postCode" placeholder="Post Code" value="<%= customer.getPostCode() %>" required>
							    </div>
							  </div>
							</div>
						</div>
					</div>
					<div class="col-md-4 bg-light p-4 mt-3 mt-md-0">
						<div class="row">
							<div class="col-12">
								<h4>Payment</h4>
							</div>
							<div class="col-12 border-top border-dark pt-2 mt-2">
								<% for (BasketItem basketItem : basket.getItems()) { %>
								<div class="row">
									<div class="col-9 font-weight-bold"><%= basketItem.getQuantity() + "x " + new TreeUtil().getTree(basketItem.getTreeId()).getFormattedName() %></div>
									<div class="col-3 text-right"><%= new Util().getFormattedPrice(basketItem.getTotalPrice()) %></div>
								</div>
								<% } %>
								<% if (basket.getTotalDeposit() > 0) { %>
								<div class="row">
									<div class="col-9 font-weight-bold">Refundable Deposit</div>
									<div class="col-3 text-right"><%= new Util().getFormattedPrice(basket.getTotalDeposit()) %></div>
								</div>
								<% } %>
								<% if (basket.getTotalDelivery() > 0) { %>
								<div class="row">
									<div class="col-9 font-weight-bold">Delivery</div>
									<div class="col-3 text-right"><%= new Util().getFormattedPrice(basket.getTotalDelivery()) %></div>
								</div>
								<% } %>
								<div class="row">
									<div class="col-6 font-weight-bold">Amount to Pay</div>
									<div class="col-6 text-right"><%= new Util().getFormattedPrice(basket.getTotalDeposit() + basket.getTotalPrice() + basket.getTotalDelivery()) %></div>
								</div>
								<div class="row border-top border-dark mt-2 py-2"></div>
								<div class="row bg-dark text-white font-weight-bold font-italic" style="margin-bottom: 15px">
									<p class="mx-auto my-5">Credit Card Information</p>
								</div>
							</div>
							<div class="col-12 pt-3 mt-1 border-top border-dark">
								<button class="btn btn-block btn-success" type="submit">Pay Now</button>
								<a class="btn btn-block btn-outline-info" href="/basket">Go Back</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<%@ include file="templates/footer.jsp" %>