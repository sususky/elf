layui.define(function (exports) {

    var config = {
        base_server: 'http://localhost:8080/', // 接口地址
        autoRender: false,  // 窗口大小改变后是否自动重新渲染表格，解决layui数据表格非响应式的问题

        // 导航菜单
        menus: [{
            name: '主页',
            icon: 'layui-icon-home',
            subMenus: [{
                name: 'home',
                url: 'home',
                path: 'home.html',
                noAuth: true
            }]
        }, {
            name: '系统管理',
            icon: 'layui-icon-set',
            subMenus: [{
                name: '用户管理',
                url: 'user',  // 这里url不能带斜杠，因为是用递归循环进行关键字注册，带斜杠会被q.js理解为其他注册模式
                path: 'system/user.html',

            }, {
                name: '角色管理',
                url: 'role',
                path: 'system/role.html'
            }, {
                name: '权限管理',
                url: 'privilege',
                path: 'system/privilege.html'
            }, {
                name: '系统日志',
                url: 'log',
                path: 'system/log.html'
            }]
        }]


    };
    exports('config', config);
});
