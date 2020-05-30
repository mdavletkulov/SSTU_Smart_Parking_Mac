$(function () {
    $('#datetimepicker1').datetimepicker({
        locale: 'ru'
    });
    $('#datetimepicker2').datetimepicker({
        locale: 'ru',
        useCurrent: false
    });
    $("#datetimepicker1").on("change.datetimepicker", function (e) {
        $('#datetimepicker2').datetimepicker('minDate', e.date);
    });
    $("#datetimepicker2").on("change.datetimepicker", function (e) {
        $('#datetimepicker1').datetimepicker('maxDate', e.date);
    });
});