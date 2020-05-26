layui.use(['laydate', 'table', 'util', 'config', 'base'], function () {
    var laydate = layui.laydate;
    var table = layui.table;
    var config = layui.config;
    var util = layui.util;
    var base = layui.base;

    //渲染表格
    table.render({
        elem: '#log-table',
        url: config.base_server + 'log',
        where: {
            token: base.getToken()
        },
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'level', sort: true, title: '等级'},
            {field: 'module', sort: true, title: '模块'},
            {field: 'browser', sort: false, title: '浏览器'},
            {field: 'clientIp', sort: false, title: 'IP'},
            {field: 'os', sort: false, title: '设备类型'},
            {field: 'content', sort: false, title: '内容'},
            {
                field: 'createTime', sort: true, templet: function (d) {
                    return util.toDateString(d.createTime);
                }, title: '操作时间'
            }
        ]]
    });

    //时间范围
    laydate.render({
        elem: '#log-date',
        type: 'date',
        range: true,
        theme: 'molv'
    });

    //搜索按钮点击事件
    $('#log-btn-search').click(function () {
        var searchDate = $('#log-date').val().split(' - ');
        var searchModule = $('#log-module').val();
        table.reload('log-table', {
            where: {
                startTime: searchDate[0],
                endTime: searchDate[1],
                name: searchModule
            }
        });
    });
});
