$.fn.exists = function (callback) {
    const args = [].slice.call(arguments, 1);
    if (this.length) {
        callback.call(this, args);
    }
    return this;
};


$(document).ready(function () {
    $.get("/app/title", function (data) {
        $("body").prepend(data)
    })

    // classifier_config
    $('#classifierFullName').exists(function () {
        const setClassifier = function (types, name) {
            $('#classifierName').val(name).attr('title', name);
            $('#classifierParamTypes').val(types).attr('title', types);
        }

        const selected = $('#classifierFullName')
        selected.change(function () {
            const types = $(this).find('option:selected').attr('types')
            const name = $(this).find('option:selected').attr('name')
            setClassifier(types, name)
        });

        const types = selected.find('option:selected').attr('types')
        const name = selected.find('option:selected').attr('name')
        setClassifier(types, name)
    })
});

