		<%@page import="models.Basket"%>
<footer class="pt-4 bg-dark text-white d-print-none">
			<div class="container text-center text-md-left">
		    	<div class="row">
					<div class="col-md-4 col-lg-3 mr-auto my-md-4 my-0 mt-4 mb-1">
						<h5 class="font-weight-bold text-uppercase mb-4">Rent A Tree</h5>
						<p>A cost-efficient way of renting a tree of any size for your event/occasion.</p>
					</div>
					<hr class="clearfix w-100 d-md-none">
					<div class="col-md-2 col-lg-2 mx-auto my-md-4 my-0 mt-4 mb-1">
		        		<h5 class="font-weight-bold text-uppercase mb-4">Useful links</h5>
						<ul class="list-unstyled">
							<li>
								<p>
									<a href="#!">About Us</a>
								</p>
							</li>
							<li>
								<p>
									<a href="#!">Blog</a>
								</p>
							</li>
							<li>
								<p>
									<a href="#!">Awards</a>
								</p>
							</li>
						</ul>
					</div>
					<hr class="clearfix w-100 d-md-none">
					<div class="col-md-4 col-lg-3 mx-auto my-md-4 my-0 mt-4 mb-1">
						<h5 class="font-weight-bold text-uppercase mb-4">Contact Us</h5>
						<ul class="list-unstyled">
							<li>
								<p>
									<i class="fas fa-home mr-3"></i> Rent A Tree Limited
								</p>
							</li>
							<li>
								<p>
									<i class="fas fa-envelope mr-3"></i> info@rentatree.com
								</p>
							</li>
							<li>
								<p>
									<i class="fas fa-phone mr-3"></i> 01438 741114
								</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</footer>
		<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
		<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
		<script src="/assets/main.js"></script>
		<script>
		<% for (int i = 0; i < ((Basket) request.getSession().getAttribute("basket")).getSize(); i++) { %>
		$("#rentalStartDate-<%= i %>").on("change", function() {
			let value = $("#rentalStartDate-<%= i %>").val();
			$("#rentalEndDate-<%= i %>").attr("min", value);
			console.log(value);
		});
		<% } %>
		</script>
	</body>
</html>