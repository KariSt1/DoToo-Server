// Sets date fields in the add event form to the current date
var title;
document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date();
    let time = now.toISOString().substring(0,16);
    start.setAttribute("value", time);
    end.setAttribute("value", time);
});

function eventDeleted(event) {
    jQuery(function($) {
        var url = window.location.origin + '/deleteEvent';
        var data = {};
        data.id = event.id;
        $.post(url, data);
    });
    let card = event.parentNode;
    card.parentNode.removeChild(card);
}


function invalidEventInput() {
    jQuery(function($) {
        debugger;
        var startDate = document.getElementById("start-date").value;
        var endDate = document.getElementById("end-date").value;
        var title = document.getElementById("event-title").value;

        if (title.length > 50) {
            alert("Title cannot be longer than 50 characters");
            return;
        }
        if (Date.parse(endDate) <= Date.parse(startDate)) {
            alert("End date should be greater than start date");
            return;
        }
       if( $("#myForm")[0].checkValidity()) {
           $("#myForm").submit();
       }
       else {
           return;
       }
    });

}

function sortEvents() {
    jQuery(function($) {
        var x = document.getElementsByClassName("event");
        for(var i=0;i<x.length;i++){
            x[i] = x[i].getElementsByTagName("p")[0].innerText;
            console.log(x[i]);
        }

           return;
    });
}
