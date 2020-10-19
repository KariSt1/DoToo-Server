function itemChecked(clicked_id) {
    var url = window.location.origin + '/itemchecked';
    $.post(url, {id: clicked_id});
}