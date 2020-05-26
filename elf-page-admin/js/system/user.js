layui.use(['form', 'table', 'util', 'laydate', 'config', 'index', 'base'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    var index = layui.index;
    var base = layui.base;
    var laydate = layui.laydate;

    //时间范围
    laydate.render({
        elem: '#user-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });

    // 渲染表格
    table.render({
        elem: '#user-table',
        url: config.base_server + 'user',
        method: 'get',
        where: {
            token: base.getToken()
        },
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'account', sort: false, title: '账号'},
            {field: 'nickName', width:120, sort: false, title: '昵称'},
            {field: 'phone', width:120, sort: false, title: '手机号'},
            // {field: 'sex', sort: true, title: '性别'},
            {
                field: 'sex', width: 80, align: 'center', templet: function (d) {
                    if (d.sex == 1) {
                        return '男';
                    }else {
                        return '女';
                    }
                }, title: '性别'
            },
            {
                sort: true, templet: function (d) {
                    return util.toDateString(d.createTime);
                }, title: '创建时间'
            },
            // {field: 'state', sort: true, templet: '#user-tpl-state', title: '状态'},
            {align: 'center', toolbar: '#user-table-bar', title: '操作'}
        ]]
    });

    // 添加按钮点击事件
    $('#user-btn-add').click(function () {
        showEditModel();
    });
    // 搜索按钮点击事件
    $('#user-btn-search').click(function () {
        var key = $('#user-search-key').val();
        var value = $('#user-search-value').val();
        var searchDate = $('#user-date').val().split(' - ');
        var data = {'startTime':searchDate[0], 'endTime':searchDate[1]};
        if(key=='account'){
            data.name = value;
            data.phone = '';
        }else{
            data.phone = value;
            data.name = '';
        }
        table.reload('user-table', {where: data});

    });
    // 添加按钮点击事件
    $('#user-cancel').click(function () {
        index.closePopupCenter();
        table.reload('user-table');
    });

    // 工具条点击事件
    table.on('tool(user-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') { // 修改
            showEditModel(data);
        } else if (obj.event === 'del') { //删除
            doDelete(obj);
        } else if (layEvent === 'reset') { // 重置密码
            reset(data);
        }
    });

    // 表单提交事件
    form.on('submit(user-form-submit)', function (data) {
        layer.load(2);
        data.field.password = $.md5(data.field.password);
        base.jsonReq('user', data.field, function (data) {
            layer.closeAll('loading');
            if (data.code == 0) {
                layer.msg(data.msg, {icon: 1});
                //index.finishPopupCenter();
                //table.reload('user-table');
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }, $('#user-form').attr('method'));
        return false;
    });

    //显示表单弹窗
    var showEditModel = function (data) {
        var title = data ? '修改用户' : '添加用户';
        base.getReq('role', {limit: 0}, function (result) {
            var list = result.data;
            var selectContent = '<option value="0">--请选择--</option>';
            if(list){
                for(var i = 0;i<list.length; i++){
                    if(data && data.roleId == list[i].id){
                        selectContent = selectContent + '<option value="' + list[i].id + '" selected="selected">' + list[i].roleName + '</option>';
                    }else{
                        selectContent = selectContent + '<option value="' + list[i].id + '">' + list[i].roleName + '</option>';
                    }
                }
            }
            $("select[name='roleId']").html(selectContent);
        });


        index.popupCenter({
            title: title,
            content: $('#user-model').html(),
            success: function () {
                form.render('select');
                $('#user-form')[0].reset();
                $('#user-form').attr('method', 'POST');
                if (data) {
                    form.val('user-form', data);
                    $('#user-form').attr('method', 'PUT');
                }
                $('#user-form .close').click(function () {
                    layer.closeAll('page');
                    table.reload('user-table');
                });
                $('.layui-layer-close.layui-layer-close1').click(function () {
                    layer.closeAll('page');
                    table.reload('user-table');
                });

            }
        });
        /*
        layer.open({
            type: 1,
            title: data ? '修改用户' : '添加用户',
            area: '450px',
            offset: '120px',
            content: $('#user-model').html(),
            success: function () {
                $('#user-form')[0].reset();
                $('#user-form').attr('method', 'POST');
                if (data) {
                    form.val('user-form', data);
                    $('#user-form').attr('method', 'PUT');
                }
                $('#user-form .close').click(function () {
                    layer.closeAll('page');
                });
            }
        });

        base.putTempData('t_user', data);
        var title = data ? '修改用户' : '添加用户';
        index.popupCenter({
            title: title,
            path: 'template/system/user_form.html',
            finish: function () {
                table.reload('user-table', {});
            }
        });
        */
    };
    // 删除
    var doDelete = function (obj) {
        layer.confirm('确定要删除此用户吗？', function () {
            layer.load(2);
            base.jsonReq('user/' + obj.data.id, {}, function (data) {
                layer.closeAll('loading');
                if (data.code == 0) {
                    layer.msg(data.msg, {icon: 1});
                    //obj.del();
                    table.reload('user-table');
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }, 'DELETE');
        });
    };
    var reset = function(field){
        layer.confirm('确定重置此用户的密码吗？', function (i) {
            layer.close(i);
            layer.load(2);
            base.jsonReq('user/psw/' + field.userId, {}, function (data) {
                layer.closeAll('loading');
                if (data.code == 0) {
                    layer.msg(data.msg, {icon: 1});
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }, 'PUT');
        });
    }

    /*
    // 修改user状态
    form.on('switch(user-tpl-state)', function (obj) {
        layer.load(2);
        config.req('user/state', {
            userId: obj.elem.value,
            state: obj.elem.checked ? 0 : 1
        }, function (data) {
            layer.closeAll('loading');
            if (data.code == 200) {
                layer.msg(data.msg, {icon: 1});
                //table.reload('table-user', {});
            } else {
                layer.msg(data.msg, {icon: 2});
                $(obj.elem).prop('checked', !obj.elem.checked);
                form.render('checkbox');
            }
        }, 'PUT');
    });
    */

});
