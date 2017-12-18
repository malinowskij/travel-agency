$(function () {
    $("#birthDate").datepicker({
        changeMonth: true, changeYear: true,
        dateFormat: 'dd-mm-yy',
        yearRange: '1900:+0'
    });
});