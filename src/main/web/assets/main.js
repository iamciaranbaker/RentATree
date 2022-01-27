$("#sortBy").on("change", function() {
	$("#sortByForm").submit();
});

$("#sortBy").on("change", function() {
	$("#ordersForm").submit();
});

$("#sortBy").on("change", function() {
	$("#treesForm").submit();
});

$("#itemsPerPage").on("change", function() {
	$("#ordersForm").submit();		    	
});

$("#itemsPerPage").on("change", function() {
	$("#treesForm").submit();		    	
});

$("#checkAll").click(function(){
    $("input:checkbox").not(this).prop("checked", this.checked);
});

// add to basket modal
function addToBasket(id) {
	$.ajax({
		url: "/add-to-basket",
		data: {id: id},
		method: "post",
		success: function(result) {
			showModal("Add to Basket", result, {"size": "md", "vcenter": true});
		},
		error: function(e) {
			console.log("error", e);
			showModal("Add to Basket", "An error occurred. Please try again later. (" + e.status + ")", {"size": "md", "vcenter": true});
		}
	});
}

// edit tree modal
function editTree(id) {
	$.ajax({
		url: "/admin/edit-tree",
		data: {id: id},
		method: "post",
		success: function(result) {
			showModal("Edit Tree", result, {"size": "md", "vcenter": true});
		},
		error: function(e) {
			console.log("error", e);
			showModal("Edit Tree", "An error occurred. Please try again later. (" + e.status + ")", {"size": "md", "vcenter": true});
		}
	});
}

// add tree modal
function addTree() {
	$.ajax({
		url: "/admin/add-tree",
		method: "post",
		success: function(result) {
			showModal("Add Tree", result, {"size": "md", "vcenter": true});
		},
		error: function(e) {
			console.log("error", e);
			showModal("Add Tree", "An error occurred. Please try again later. (" + e.status + ")", {"size": "md", "vcenter": true});
		}
	});
}

function showModal(heading, content, settings) {
    return new Promise((resolve, reject) => {
    	var showFooter;

	    var config = {
	        "size": "sm",
	        "vcenter": false,
	        "bodyColor": false,
	        "onLoad": function() {}
	    };

    	$.extend(config, settings);

    	var mSize = "modal-" + config.size;
    	var mCentered = (config.vcenter ? "modal-dialog-centered" : "");
    	var modalColor = (config.bodyColor ? config.bodyColor : "#ffffff");

    	html =  '<div id="dynamicModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirm-modal" aria-hidden="true">';

    	html += '<div class="modal-dialog ' + mCentered + ' ' + mSize +'">';

    	html += '<div class="modal-content">';
    	html += '<div class="modal-header">';
    	html += '<h5 class="modal-title">'+ heading +'</h4>';
    	html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
    	html += '<span aria-hidden="true">&times;</span>';
    	html += '</button>';
    	html += '</div>';
    	html += '<div class="modal-body" style="background-color:' + modalColor + '">';
    	html +=  content;
    	html += '</div>';
    	html += '</div>';  // dialog
    	html += '</div>';  // footer
    	html += '</div>';  // modalWindow
    	$("body").append(html);
    	$("#dynamicModal").modal({backdrop: "static", keyboard: false});
    	$("#dynamicModal").modal("show");

    	$("#dynamicModal").on("hidden.bs.modal", function(e) {
        	$(this).remove();
    	});

    	resolve();
    });
}

function updateModal(content) {
    if ($("#dynamicModal").length !== 0) {
        $("#dynamicModal .modal-body").html(content);
    } else {
        alert("Modal doesn't exist!");
    }
}

function modalOnClose(func) {
    $("#dynamicModal").on("hidden.bs.modal", function() {
        $("#dynamicModal").unbind("hidden.bs.modal");
        func();
    });
}

function closeModal() {
	$("#dynamicModal").modal("hide");
}