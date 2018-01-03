$(document).ready(function () {
    $('#attractionForm').submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: '/admin/attraction',
            type: 'POST',
            data: $(this).serialize(),
            success: function (response) {
                $('#attrSelect').append($('<option>', {
                    value: response.id,
                    text: response.name
                }, '</option>'));
                $('#attrDesc').val('');
                $('#attrName').val('');
            },
            error: function (err) {
            }
        })
    });

    $('[data-toggle="popover"]').popover();
})