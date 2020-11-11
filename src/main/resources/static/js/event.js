// Sets date fields in the add event form to the current date
document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date();
    let time = now.toISOString().substring(0,16);
    start.setAttribute("value", time);
    end.setAttribute("value", time);
});

function invalidDateRange() {
    jQuery(function($) {
        var startDate = document.getElementById("start-date").value;
        var endDate = document.getElementById("end-date").value;

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