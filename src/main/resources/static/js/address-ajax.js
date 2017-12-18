$(document).ready(function () {
    $(document).on('change', '#country', function(event){
        stateAjax();
    });

    $(document).on('change', '#state', function (event) {
        cityAjax();
    });

    function stateAjax() {
        var selectedCountryId = $('#country option:selected').val();
        $.ajax({
            url: '/api/address/country/' + selectedCountryId + '/states',
            type: "GET",
            success: function (response) {
                $('#state').empty();
                for (item in response) {
                    $('#state').append($('<option>', {
                        value: response[item].id,
                        text: response[item].name
                    }, '</option>'));
                }
            },
            error: function (e) {
            }
        });
    }

    function cityAjax() {
        var selectedStateId = $('#state option:selected').val();

        $.ajax({
            url: '/api/address/state/' + selectedStateId + '/cities',
            type: "GET",
            success: function (response) {
                $('#city').empty();
                for (item in response) {
                    $('#city').append($('<option>', {
                        value: response[item].id,
                        text: response[item].name
                    }, '</option>'));
                }
            },
            error: function (e) {
            }
        });
    }
});
