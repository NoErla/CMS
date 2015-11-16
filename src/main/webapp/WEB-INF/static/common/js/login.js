/**
 * Created by youzipi on 15-11-16.
 */
/**
 * 显示表单的错误提示
 * @param id 表单ID
 * @param errors 错误列表
 */
function showErrors(id, errors) {
    id.find('p[class=error]').hide();
    id.find('input,select').removeClass("error");
    for (var name in errors) {
        var e = id.find('p[for=' + name + ']');
        id.find('input[name=' + name + '],select[name=' + name + ']').addClass("error");
        if (e.length == 0) {
            id.find('input[name=' + name + '],select[name=' + name + ']').after('<p for="' + name + '" class="error"></p>');
            e = id.find('p[for=' + name + ']');
        }
        if (errors[name] != "") {
            e.html(errors[name]);
            e.show();
        }
    }
}

function ajaxLogin(selector, base_path) {
    $(selector).ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.result) {
                location.href = base_path + "/manage/article/list.htm";
            } else {
                showErrors($(selector), data.errors);
                if (data.msg == "change_captcha") {
                    $('#captcha').attr("src", base_path + "/captcha.htm?" + Math.random());
                    $(selector).find('input[name="captcha"]').val('');
                }
            }
        }
    });
}