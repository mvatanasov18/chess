

$(document).ready(function() {
    $("body").append("<p>WORKKS JS SHITT</p>");
    console.log("HERE");
    var information = {"id": "testingabcd"};

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "chess/ajax/makeNewChess",
        data : JSON.stringify(information),
        dataType : "json",
        timeout : 10000,
        success : function(data) {
            console.log("bachka.");
        },
        error : function(data) {
            console.log("ni bachka.");
        },
        done : function(e) {
            console.log("krai");
        }
    });
});
