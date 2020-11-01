function createCalNavigation() {
    var currentView = "week";
    var currentOffset = 0;

    function changeView(view) {
        currentView = view;
        jQuery(function ($) {
            var url = window.location.origin + '/changeview';
            var data = {};
            data.view = view;
            data.nav = currentOffset;
            $.post(url, data);
        });
    }

    function changeOffset(offset) {
        currentOffset += offset;
        jQuery(function ($) {
            var url = window.location.origin + '/changeview';
            var data = {};
            data.view = currentView;
            data.nav = currentOffset;
            $.post(url, data);
        });
    }

    return({
        changeView : changeView,
        changeOffset : changeOffset
    });
}

document.addEventListener('DOMContentLoaded', () => {
    let start = document.getElementById("start-date");
    let end = document.getElementById("end-date");
    let now = new Date();
    let time = now.toISOString().substring(0,16);
    start.setAttribute("value", time);
    end.setAttribute("value", time);
});