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
        let url = window.location.origin + '/deleteEvent';
        let data = {};
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
        let startDate = document.getElementById("start-date").value;
        let endDate = document.getElementById("end-date").value;
        let title = document.getElementById("event-title").value;


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
        let url = window.location.origin + '/changeview';
        let data = {};
        data.viewDate = date;
        data.view = null;
        data.nav = null;
        $.post(url, data);
    });
    let viewButtons = document.querySelector(".btn-group").children;
    let activeBtn;
    for(let i = 0; i < viewButtons.length; i++) {
        if(viewButtons[i].classList.contains("active")) activeBtn = viewButtons[i];
    }
    $(activeBtn).click();
}

function sortEvents() {
    jQuery(function ($) {
        let x = document.getElementsByClassName("event");
        for (let i = 0; i < x.length; i++) {
            x[i] = x[i].getElementsByTagName("p")[0].innerText;
            console.log(x[i]);
        }

        return;
    });
}

function selectCategory(){
    let selected_category = document.querySelector("#viewByCategory").selectedIndex;
    jQuery(function($) {
        let url = window.location.origin + '/changeview';
        let data = {};
        data.viewDate = null;
        data.view = null;
        data.nav = null;
        data.category = document.getElementsByTagName("option")[selected_category].innerText;
        $.post(url, data);
    });
    let viewButtons = document.querySelector(".btn-group").children;
    let activeBtn;
    for(let i = 0; i < viewButtons.length; i++) {
        if(viewButtons[i].classList.contains("active")) activeBtn = viewButtons[i];
    }
    $(activeBtn).click();
}

