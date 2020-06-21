layui.use(['form', 'table', 'util', 'laydate', 'config', 'index', 'base'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    //var index = layui.index;
    var base = layui.base;
    var laydate = layui.laydate;

    //时间范围
    laydate.render({
        elem: '#privilege-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });

    // 渲染表格
    var renderTable = function (){
        table.render({
            elem: '#auth-table',
            url: config.base_server + 'privilege',
            where: {
                token: base.getToken()
            },
            page: true,
            cols: [[
                {type: 'numbers'},
                {field: 'privilegeName', sort: false, title: '权限'},
                {field: 'parentName', sort: false, title: '上级'},
                {field: 'link', sort: false, title: 'url'},
                {field: 'seq', sort: true, title: '排序'},
                {
                    field: 'category', width: 80, align: 'center', templet: function (d) {
                        if (d.category == 3) {
                            return '<span class="layui-badge layui-bg-gray">按钮</span>';
                        }else if (d.category == 1) {
                            return '<span class="layui-badge layui-bg-blue">目录</span>';
                        } else {
                            return '<span class="layui-badge-rim">菜单</span>';
                        }
                    }, title: '类型'
                },
                {
                    field: 'createTime', sort: true, templet: function (d) {
                        return util.toDateString(d.createTime);
                    }, title: '创建时间'
                },
                {templet: '#auth-opt', width: 120, align: 'center', title: '操作'}
            ]]
        });
    }

    renderTable();

    // 同步按钮点击事件
    $('#privilege-btn-add').click(function () {
        showEditModel();
    });
    //搜索按钮点击事件
    $('#privilege-btn-search').click(function () {
        var searchDate = $('#privilege-date').val().split(' - ');
        var searchName = $('#privilege-search').val();
        table.reload('auth-table', {
            where: {
                startTime: searchDate[0],
                endTime: searchDate[1],
                name: searchName
            }
        });
    });

    // 工具条点击事件
    table.on('tool(auth-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        console.log(data);
        if (layEvent === 'edit') { // 修改
            showEditModel(data);
        } else if (layEvent === 'del') { // 重置密码
            doDelete(obj);
        }
    });

    form.on('submit(privilege-form-submit)', function (data) {
        layer.load(2);
        console.log(data.field);
        base.req('privilege', data.field, function (data) {
            layer.closeAll('loading');
            if (data.code == 0) {
                layer.msg(data.msg, {icon: 1});
                table.reload('auth-table');
                //renderTable();
                layer.closeAll('page');
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }, $('#privilege-form').attr('method'));
        return false;
    });

    // 显示表单弹窗
    var showEditModel = function (data) {

        layer.open({
            type: 1,
            title: data ? '修改权限' : '添加权限',
            area: '450px',
            offset: '120px',
            content: $('#privilege-model').html(),
            success: function () {
                form.render('select');
                $('#privilege-form')[0].reset();
                $('#privilege-form').attr('method', 'POST');
                if (data) {
                    form.val('privilege-form', data);
                    $('#privilege-form').attr('method', 'PUT');
                }
                $('#privilege-form .close').click(function () {
                    layer.closeAll('page');
                });
            }
        });

    };
    // 删除
    var doDelete = function (obj) {
        layer.confirm('确定删除此权限吗？', function (i) {
            //layer.close(i);
            layer.load(2);
            base.jsonReq('privilege/' + obj.data.id, {}, function (data) {
                layer.closeAll('loading');
                if (data.code == 0) {
                    layer.msg(data.msg, {icon: 1});
                    //obj.del();
                    //renderTable();
                    table.reload('auth-table');
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }, 'DELETE');
        });
    };

    /*
    // 角色切换事件
    form.on('select(auth-slt-role)', function (data) {
        table.reload('auth-table', {where: {roleId: data.value}});
    });
    // 监听状态开关操作
    form.on('switch(auth-state)', function (obj) {
        var roleId = $('#auth-slt-role').val();
        if (!roleId) {
            layer.msg('请选择角色再进行授权操作', {icon: 2});
            $(obj.elem).prop('checked', !obj.elem.checked);
            form.render('checkbox');
            return;
        }
        layer.load(2);
        config.req('authorities/role', {
            roleId: roleId,
            authId: obj.value
        }, function (data) {
            layer.closeAll('loading');
            if (data.code == 200) {
                layer.msg(data.msg, {icon: 1});
            } else {
                layer.msg(data.msg, {icon: 2});
                $(obj.elem).prop('checked', !obj.elem.checked);
                form.render('checkbox');
            }
        }, obj.elem.checked ? 'POST' : 'DELETE');
    });
    */

});
