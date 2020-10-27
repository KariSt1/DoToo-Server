function itemChecked(item) {
    jQuery(function($) {
        console.log('Id: ' + item.id);
        console.log('Value: ' + item.checked);
        console.log(window.location.origin + '/itemchecked');
        var url = window.location.origin + '/itemchecked';
        var data = {};
        data.id = item.id
        data.checked = item.checked;
        console.log(data);
        $.post(url, data);
    });
}