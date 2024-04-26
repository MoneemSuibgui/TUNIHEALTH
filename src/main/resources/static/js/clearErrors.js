$(document).ready(function() {
    $("#userName, #email, #password, #confirm, #courseName, #studentName,#price,#description,#time").on("input", function() {
        var errorsId = $(this).attr("id") + "Errors";
        $("#" + errorsId).html(""); // Clear the error messages
    });
});