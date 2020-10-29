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