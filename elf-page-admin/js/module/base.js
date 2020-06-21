layui.define(['config'], function (exports) {
    var config = layui.config;

    var base = {
        tableName: 'local',  // 存储表名
        // 获取缓存的token
        getToken: function () {
            var t = layui.data(base.tableName).token;
            if (t) {
                return t;
            }
        },
        // 清除user
        removeToken: function () {
            layui.data(base.tableName, {
                key: 'token',
                remove: true
            });
        },
        // 缓存token
        putToken: function (token) {
            layui.data(base.tableName, {
                key: 'token',
                value: token
            });
        },

        // 当前登录的用户
        getUser: function () {
            var u = layui.data(base.tableName).loginUser;
            if (u) {
                return JSON.parse(u);
            }
        },
        // 缓存user
        putUser: function (user) {
            layui.data(base.tableName, {
                key: 'loginUser',
                value: JSON.stringify(user)
            });
        },
        // 缓存临时数据
        putTempData: function (key, value) {
            if (value) {
                layui.sessionData('tempData', {key: key, value: value});
            } else {
                layui.sessionData('tempData', {key: key, remove: true});
            }
        },
        // 获取缓存临时数据
        getTempData: function (key) {
            return layui.sessionData('tempData')[key];
        },
        // 判断是否有权限
        hasPerm: function (auth) {
            var user = base.getUser();
            if(user.isSuper==1){
                return true;
            }
            if (user.privileges) {
                for (var i = 0; i < user.privileges.length; i++) {
                    if (auth == user.privileges[i]) {
                        return true;
                    }
                }
            }
            return false;

        },
        // 封装ajax请求
        jsonReq: function (url, data, success, method) {
            //var token = base.getToken();
            $.ajax({
                url: config.base_server + url,
                data: JSON.stringify(data),
                type: method,
                contentType : "application/json",
                dataType: 'JSON',
                success: function (data) {
                    if(data.code==201){
                        base.removeToken();
                        layer.msg('登录过期', {icon: 2}, function () {
                            // location.href = 'login.html';
                        });
                    }else if(data.code==0){
                        success(data);
                    }else{
                        layer.msg(data.msg, {icon: 2});
                    }

                },
                error: function (xhr) {
                    console.log(xhr.status + ' - ' + xhr.statusText);
                    layer.msg('服务器开小差了', {icon: 2});
                },
                beforeSend: function (xhr) {
                    var token = base.getToken();
                    if (token) {
                        xhr.setRequestHeader('token', token);
                    }
                }
            });
        },
        getReq: function (url, data, success) {
            var token = base.getToken();
            $.ajax({
                url: config.base_server + url + "?token=" + token,
                data: data,
                type: 'get',
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                    if(data.code==201){
                        base.removeToken();
                        layer.msg('登录过期', {icon: 2}, function () {
                            // location.href = 'login.html';
                        });
                    }else if(data.code==0){
                        success(data);
                    }else{
                        layer.msg(data.msg, {icon: 2});
                    }

                },
                error: function (xhr) {
                    console.log(xhr.status + ' - ' + xhr.statusText);
                    layer.msg('服务器开小差了', {icon: 2});
                    // location.href = '500.html';
                }
            });
        }

    }

    exports('base', base);
});
