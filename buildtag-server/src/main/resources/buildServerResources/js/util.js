var ProjectConfig = {};
ProjectConfig.deleteRegex = function (element, url, project) {
    jQuery.ajax({
        url: url + '?project=' + project + '&id=' + element.name,
        type: 'DELETE',
        success: function (response) {
            document.location.reload()
        },
        error: function (response) {
            alert(response)
        }
    });
};
ProjectConfig.addRegex = function (url, project) {
    body = {
        tag: jQuery("#tag").val(),
        regex: jQuery("#regex").val()
    };

    jQuery.ajax({
        url: url + '?project=' + project,
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