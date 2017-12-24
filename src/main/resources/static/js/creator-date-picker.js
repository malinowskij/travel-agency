$(function () {
    $.datepicker.regional['pl']
    var dateToday = new Date();
    //dateToday.setDate(dateToday.getDate() + 7);
    var dateToday2 = new Date();
    dateToday2.setDate(dateToday.getDate() + 1);
    var dateFormat = "dd-mm-yy",
        from = $("#startDate")
            .datepicker({
                changeMonth: true,
                dateFormat: 'dd-mm-yy',
                minDate: dateToday
            })
            .on("change", function () {
                to.datepicker("option", "minDate", getDate(this));
            }),
        to = $("#endDate").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: 'dd-mm-yy',
            minDate: dateToday2
        })
            .on("change", function () {
                from.datepicker("option", "maxDate", getDate(this));
            });

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(dateFormat, element.value);
        } catch (error) {
            date = null;
        }

        return date;
    }

});