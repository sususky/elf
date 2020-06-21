var uuid='';

layui.config({
    base: 'js/module/'
}).use(['config', 'form', 'base'], function () {
    var form = layui.form;
    var config = layui.config;
    var base = layui.base;

    $('.form-control').on("focus", function() {
        $(this).parents('.layui-form-item').addClass("input-group-focus");
    }).on("blur", function() {
        $(this).parents(".layui-form-item").removeClass("input-group-focus");
    });

    if(!uuid || uuid==''){
        uuid = $.uuid();
    }

    /*
    if (config.getToken()) {
        location.replace('./');
        return;
    }
    */

    // 表单提交
    form.on('submit(login-submit)', function (obj) {
        var field = obj.field;
        field.uuid = uuid;
        field.password = $.md5(field.password);
        layer.load(2);

        $.ajax({
            url: config.base_server + 'login',
            contentType: "application/json",
            data: JSON.stringify(field),
            type: 'POST',
            dataType: 'JSON',
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.code==0) {
                    base.putToken(data.token);
                    base.putUser(data.user);
                    layer.msg('登录成功', {icon: 1}, function () {
                        location.replace('./');
                    });
                } else {
                    layer.closeAll('loading');
                    layer.msg(data.msg, {icon: 5});
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg('登录失败', {icon: 5});
            }
        });

        return false;
    });

    // 图形验证码
    $('.login-captcha').attr("src", config.base_server + 'captcha?uuid=' + uuid);
    $('.login-captcha').click(function () {
        this.src = this.src + '&t=' + (new Date).getTime();
    });

});
