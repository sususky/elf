layui.use(['form', 'table', 'util', 'laydate', 'config', 'base'], function () {
    var form = layui.form;
    var table = layui.table;
    var config = layui.config;
    var layer = layui.layer;
    var util = layui.util;
    var base = layui.base;
    var laydate = layui.laydate;

    //时间范围
    laydate.render({
        elem: '#role-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });
    var renderTable = function(){
        //渲染表格
        table.render({
            elem: '#role-table',
            url: config.base_server + 'role',
            where: {
                token: base.getToken()
            },
            page: false,
            cols: [[
                {type: 'numbers'},
                {field: 'roleName', sort: false, title: '角色名'},
                {field: 'comments', sort: false, title: '备注'},
                {
                    field: 'createTime', sort: true, templet: function (d) {
                        return util.toDateString(d.createTime);
                    }, title: '创建时间'
                },
                {align: 'center', toolbar: '#role-table-bar', title: '操作'}
            ]]
        });
    }
    renderTable();

    // 添加按钮点击事件
    $('#role-btn-add').click(function () {
        showEditModel();
    });
    // 搜索按钮点击事件
    $('#role-btn-search').click(function () {
        var keyword = $('#role-edit-search').val();
        var searchDate = $('#role-date').val().split(' - ');

        table.reload('role-table', {
            where: {
                startTime: searchDate[0],
                endTime: searchDate[1],
                name: keyword
            }
        });

    });

    // 工具条点击事件
    table.on('tool(role-table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') { //修改
            showEditModel(data);
        } else if (obj.event === 'del') { //删除
            doDelete(obj);
        } else if (obj.event === 'auth') {  // 权限管理
            showPermDialog(obj.data.id);
        }
    });

    // 表单提交事件
    form.on('submit(role-form-submit)', function (data) {
        layer.load(2);
        base.jsonReq('role', data.field, function (data) {
            layer.closeAll('loading');
            if (data.code == 0) {
                layer.msg(data.msg, {icon: 1});
                table.reload('role-table');
                layer.closeAll('page');
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }, $('#role-form').attr('method'));
        return false;
    });

    // 显示编辑弹窗
    var showEditModel = function (data) {
        layer.open({
            type: 1,
            title: data ? '修改角色' : '添加角色',
            area: '450px',
            offset: '120px',
            content: $('#role-model').html(),
            success: function () {
                $('#role-form')[0].reset();
                $('#role-form').attr('method', 'POST');
                if (data) {
                    form.val('role-form', data);
                    $('#role-form').attr('method', 'PUT');
                }
                $('#role-form .close').click(function () {
                    layer.closeAll('page');
                });
            }
        });
    };

    // 删除
    var doDelete = function (obj) {
        layer.confirm('确定要删除吗？', function (i) {
            layer.close(i);
            layer.load(2);
            base.jsonReq('role/' + obj.data.id, {}, function (data) {
                layer.closeAll('loading');
                if (data.code == 0) {
                    layer.msg(data.msg, {icon: 1});
                    //obj.del();
                    renderTable();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }, 'DELETE');
        });
    };

    // 权限管理
    var showPermDialog = function (roleId) {
        layer.open({
            type: 1,
            title: '权限管理',
            area: ['450px', '380px'],
            offset: '120px',
            content: '<ul id="treeAuth" class="ztree" style="padding: 25px 0px 20px 60px;"></ul>',
            btn: ['保存', '关闭'],
            btnAlign: 'c',
            success: function (layero, index) {
                $(layero).children('.layui-layer-content').css('overflow-y', 'auto');
                layer.load(2);
                var setting = {
                    check: {enable: true},
                    data: {
                        simpleData: {enable: true}
                    }
                };
                base.getReq('privilege/role/' + roleId, {}, function (data) {
                    $.fn.zTree.init($('#treeAuth'), setting, data.data);
                    layer.closeAll('loading');
                });
            },
            yes: function (index) {
                layer.load(1);
                var treeObj = $.fn.zTree.getZTreeObj('treeAuth');
                var nodes = treeObj.getCheckedNodes(true);
                var ids = new Array();
                for (var i = 0; i < nodes.length; i++) {
                    ids[i] = nodes[i].id;
                }

                base.jsonReq('role/' + roleId + '/privilege', ids, function (data) {
                    layer.closeAll('loading');
                    if (0 == data.code) {
                        layer.msg(data.msg, {icon: 1});
                        layer.close(index);
                    } else {
                        layer.msg(data.msg, {icon: 2});
                    }
                }, 'POST');
                /*
                $.ajax({
                    url:config.base_server + 'role/' + roleId + '/privilege',
                    type:"post",
                    contentType:"application/json",
                    data: JSON.stringify(ids),
                    dataType:"json",
                    success:function(data){
                        layer.closeAll('loading');
                        if (0 == data.code) {
                            layer.msg(data.msg, {icon: 1});
                            layer.close(index);
                        } else {
                            layer.msg(data.msg, {icon: 2});
                        }
                    },
                    beforeSend: function (xhr) {
                        var token = base.getToken();
                        if (token) {
                            xhr.setRequestHeader('token', token);
                        }
                    }
                });
                */

            }
        });
    }

});
