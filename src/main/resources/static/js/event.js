// Sets date fields in the add event form to the current date
document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date();
    let time = now.toISOString().substring(0,16);
    start.setAttribute("value", time);
    end.setAttribute("value", time);
});