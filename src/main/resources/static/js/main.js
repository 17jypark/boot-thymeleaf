$(document).ready(function () {

    $("#regform").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();

    });
    
    $("#updform").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        update_ajax_submit();

    });
    
    $("#delform").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        delete_ajax_submit();

    });

});

function fire_ajax_submit() {

    //var data = $("#regform").serializeArray();
    var data = {
    		"name" : $('#name').val(),
    		"company" : $('#company').val()
    };
	
	var json = JSON.stringify(data);
	console.log(json);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/users",
        data: json,
        dataType: 'text', // json -> text
        cache: false,
        timeout: 600000,
        success: function (data) {
        	console.log("SUCCESS : ", data);
        	window.location.href = "/users";
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}

function update_ajax_submit() {

    //var data = $("#regform").serializeArray();
    var data = {
    		"name" : $('#name').val(),
    		"company" : $('#company').val()
    };
	var user_id = "/users/" + $('#id').val();
	var json = JSON.stringify(data);
	console.log(json);

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: user_id,
        data: json,
        dataType: 'text', // json -> text
        cache: false,
        timeout: 600000,
        success: function (data) {
        	console.log("SUCCESS : ", data);
        	//window.location.href = "/users"; // Get 방식으로 접근
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}

function delete_ajax_submit() {

    //var data = $("#regform").serializeArray();
    
    var url_id = "/users/" + $('#id').val();
	console.log(url_id);
    $.ajax({
        type: "DELETE",
        url: url_id,
        dataType: 'text', // json -> text
        success: function (result) {
        	console.log("SUCCESS : ", result);
        	window.location.href = "/disjoin";
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}