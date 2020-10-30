function changeView(view) {
    jQuery(function($) {
        var url = window.location.origin + '/itemchecked';
        var data = {};
        data.id = item.id
        data.checked = item.checked;
        $.post(url, data);
    });
    let description = item.parentNode.nextElementSibling;
    description.classList.toggle("linethrough");
}

document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date();
    let time = now.toISOString().substring(0,16);
    start.setAttribute("value", time);
    end.setAttribute("value", time);
});