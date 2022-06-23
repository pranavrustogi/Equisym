$(document).ready(function(){
	$.ajax({
		url:'CourseServlet.java',
		cache: false,
		dataType: 'json',
		error: function (request, error) {
        console.log(arguments);
        alert(" Can't do because: " + error);
    },
		success : function(result){
			$.each(result,function(key,value){
				$('<option>').val(key).text(value).appendTo("#slotd");
			});
		}
	});
});
  