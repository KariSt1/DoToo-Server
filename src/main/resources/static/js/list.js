function itemChecked(item) {
    jQuery(function($) {
        let url = window.location.origin;
        if(window.location.pathname === '/todolist') {
            url = url + '/itemchecked'
        } else {
            url = url + '/homeitemchecked'
        }
        console.log(url);
        //var url = window.location.origin + '/itemchecked';
        let data = {};
        data.id = item.id;
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
        data.id = item.id;
        $.post(url, data);
    });
    let row = item.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function listDeleted(list) {
    if(document.getElementsByClassName("card").length==1){
        document.getElementsByClassName("text")[0].innerText="Organize your life by making todo-lists here!";
    }
    jQuery(function($) {
        var url = window.location.origin + '/deletelist';
        var data = {};
        data.id = list.id
        $.post(url, data);
    });
    let card = list.parentNode.parentNode.parentNode.parentNode;
    card.classList.add("fade-out");
    setTimeout(() => {
        card.parentNode.removeChild(card)
    }, 1000);
}
