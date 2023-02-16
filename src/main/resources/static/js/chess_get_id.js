function getIdChess(event){
    event.preventDefault();
    var information = {"id": $("#idInput").val()};
    console.log(information["id"]);
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "chess/ajax/getChessById",
        data : JSON.stringify(information),
        dataType : "json",
        timeout : 10000,
        success : function(data) {
            console.log("bachka");
        },
        error : function(data) {
            console.log("ni baca.");
        },
        done : function(e) {
            console.log("svurshih");
        }
    });
}

$(document).ready(function(){
    $("#idSubmit").on("click", getIdChess);

});
