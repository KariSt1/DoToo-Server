function itemChecked(item) {
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

function itemDeleted(item) {
    jQuery(function($) {
        var url = window.location.origin + '/deleteitem';
        var data = {};
        data.id = item.id
        $.post(url, data);
    });
    let row = item.parentNode.parentNode;
    row.parentNode.removeChild(row);

}

function listDeleted(list) {
    jQuery(function($) {
        var url = window.location.origin + '/deletelist';
        var data = {};
        data.id = list.id
        $.post(url, data);
    });
    let card = list.parentNode.parentNode.parentNode;
    card.parentNode.removeChild(card);

}