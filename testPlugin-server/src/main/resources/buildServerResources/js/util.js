var ProjectConfig = {};
ProjectConfig.deleteRegex = function (element, url) {
    jQuery.ajax({
        url: url + '&id=' + element.name,
        type: 'DELETE',
        success: function (response) {
            document.location.reload()
        },
        error: function (response) {
            alert(response)
        }
    });
};
ProjectConfig.addRegex = function (url) {
    body = {
        tag: jQuery("#tag").val(),
        regex: jQuery("#regex").val()
    };

    jQuery.ajax({
        url: url,
        type: 'POST',
        data: body,
        success: function (response) {
            document.location.reload()
        },
        error: function (response) {
            alert(response)
        }
    });
};