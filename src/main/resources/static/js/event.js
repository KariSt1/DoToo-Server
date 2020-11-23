// Sets date fields in the add event form to the current date
var title;
document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date(document.getElementById("view-data").value);
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
    card.classList.add("fade-out");
    setTimeout(() => {
        card.parentNode.removeChild(card)
    }, 1000);

}

function invalidEventInput() {
    jQuery(function($) {
        debugger;
        var startDate = document.getElementById("start-date").value;
        var endDate = document.getElementById("end-date").value;
        var title = document.getElementById("event-title").value;


        if (title.length > 50) {
           // alert("Title cannot be longer than 50 characters");
            swal("Uh oh...", "Title cannot be longer than 50 characters", "error");
            return;
        }
        if (Date.parse(endDate) <= Date.parse(startDate)) {
            //alert("End date should be greater than start date");
            swal("Uh oh...", "End date should be greater than start date", "error");
            return;
        }


        if (!$("#myForm")[0].checkValidity()) {
            $("#myForm")[0].reportValidity();
        } else {
            $("#myForm").submit();
        }

    });


}

function postViewDate(date) {
    jQuery(function($) {
        var url = window.location.origin + '/changeview';
        var data = {};
        data.viewDate = date;
        data.view = null;
        data.nav = null;
        $.post(url, data);
    });
    var viewButtons = document.querySelector(".btn-group").children;
    var activeBtn;
    for(let i = 0; i < viewButtons.length; i++) {
        if(viewButtons[i].classList.contains("active")) activeBtn = viewButtons[i];
    }
    $(activeBtn).click();
}

function sortEvents() {
    jQuery(function ($) {
        var x = document.getElementsByClassName("event");
        for (var i = 0; i < x.length; i++) {
            x[i] = x[i].getElementsByTagName("p")[0].innerText;
            console.log(x[i]);
        }

        return;
    });
}

function submitSelected(val){
    jQuery(function($) {
        $("#viewByCategory").submit();
    })

}

