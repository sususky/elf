layui.define(['config', 'layer', 'element', 'base'], function (exports) {
    var config = layui.config;
    var layer = layui.layer;
    var element = layui.element;
    var base = layui.base;

    var popupRightIndex, popupCenterIndex; //, popupCenterParam;

    var index = {
        openPageTabs: true,  // 是否开启多标签
        // 检查多标签功能是否开启
        checkPageTabs: function () {
            if (index.openPageTabs) {
                $('.layui-layout-admin').addClass('open-tab');
                // 如果开启多标签先加载主页
                element.tabAdd('index-pagetabs', {
                    id: 'home',
                    title: '<i class="layui-icon layui-icon-home"></i>',
                    content: '<div id="home"></div>'
                });
                $('#home').load('template/home');
            } else {
                $('.layui-layout-admin').removeClass('open-tab');
            }
        },
        // 渲染左侧导航栏
        initLeftNav: function () {
            var authMenus = new Array();
            for (var i = 0; i < config.menus.length; i++) {
                var tempMenu = config.menus[i];
                var tempSubMenus = new Array();
                for (var j = 0; j < tempMenu.subMenus.length; j++) {
                    var tempSubMenu = tempMenu.subMenus[j];
                    if(tempSubMenu.noAuth){
                        tempSubMenus.push(tempSubMenu);
                    }else if (base.hasPerm(tempSubMenu.url)) {
                        tempSubMenus.push(tempSubMenu);
                    }
                }
                if (tempSubMenus.length > 0) {
                    tempMenu.subMenus = tempSubMenus;
                    authMenus.push(tempMenu);
                }
            }

            index.initRouter(authMenus);

            index.activeNav(Q.lash);
        },
        // 路由注册
        initRouter: function (menus) {
            index.regAndDraw(menus);
            Q.init({
                index: 'home'
            });
            // tab选项卡切换监听
            element.on('tab(index-pagetabs)', function () {
                var layId = $(this).attr('lay-id');
                Q.go(layId);
            });
        },
        // 使用递归循环注册
        regAndDraw: function (menus) {
            var liContent = '';
            $.each(menus, function (i, data) {
                liContent = liContent + '<li class="layui-nav-item" p-for="menus">';
                liContent = liContent + '<a href="javascript:;"><i class="layui-icon ' + data.icon;
                liContent = liContent + '"></i>&emsp;<cite>' + data.name + '</cite></a> ';

                if (data.subMenus) {
                    liContent = liContent + '<dl class="layui-nav-child" p-each="subMenus">';
                    $.each(data.subMenus, function(j, subData){
                        liContent = liContent + '<dd><a href="#!';
                        liContent = liContent + subData.url + '">' + subData.name + '</a></dd> ';

                        Q.reg(subData.url, function () {
                            // index.loadView('template/' + subData.path);
                            index.loadView({
                                menuId: subData.url,
                                menuPath: 'template/' + subData.path,
                                menuName: subData.name
                            });
                        });
                    } );
                    liContent = liContent + '</dl> ';
                }
                liContent = liContent + '</li> ';

            });
            $("#menu-view").html(liContent);
        },
        // 路由加载组件
        loadView: function (param) {

            var menuId = param.menuId;
            var menuPath = param.menuPath;
            var menuName = param.menuName;
            var flag;  // 选项卡是否已添加
            var contentBody = '.layui-layout-admin .layui-body';
            // 判断是否开启了选项卡功能
            if (index.openPageTabs) {
                // $('.layui-layout-admin').addClass('open-tab');
                $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title>li').each(function () {
                    if ($(this).attr('lay-id') === menuId) {
                        flag = true;
                        return false;
                    }
                });
                if (!flag) {
                    element.tabAdd('index-pagetabs', {
                        title: menuName,
                        id: menuId,
                        content: '<div id="' + menuId + '"></div>'
                    });
                }
                contentBody = '#' + menuId;
                element.tabChange('index-pagetabs', menuId);
                index.rollPage('auto');

                // 切换tab关闭表格内浮窗
                $('.layui-table-tips-c').trigger('click');
                //index.removeLoading('.layui-layout-admin .layui-body');
                // 解决切换tab滚动条时而消失的问题
                var $iframe = $('.layui-layout-admin .layui-body .layui-tab-content .layui-tab-item.layui-show .admin-iframe')[0];
                if ($iframe) {
                    $iframe.style.height = "99%";
                    $iframe.scrollWidth;
                    $iframe.style.height = "100%";
                }
            } else {
                // $('.layui-layout-admin').removeClass('open-tab');
                $('.layui-body.admin-iframe-body').removeClass('admin-iframe-body');
            }


            index.showLoading('.layui-layout-admin .layui-body');

            $(contentBody).load(menuPath, function () {
                element.render('breadcrumb');
                //form.render('select');
                index.removeLoading('.layui-layout-admin .layui-body');
            });
            index.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (document.body.clientWidth <= 750) {
                index.flexible(true);
            }
        },
        // 多标签功能
        /*
        // 打开新页面
        openNewTab: function (param) {
            var menuId = param.menuId;
            var url = param.url;
            var title = param.title;
            index.loadView({
                menuId: menuId,
                menuPath: url,
                menuName: title
            });
        },
        // 关闭选项卡
        closeTab: function (menuId) {
            element.tabDelete('index-pagetabs', menuId);
        },
        */
        // 滑动选项卡
        rollPage: function (d) {
            var $tabTitle = $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title');
            var left = $tabTitle.scrollLeft();
            if ('left' === d) {
                $tabTitle.scrollLeft(left - 120);
            } else if ('auto' === d) {
                var autoLeft = 0;
                $tabTitle.children("li").each(function () {
                    if ($(this).hasClass('layui-this')) {
                        return false;
                    } else {
                        autoLeft += $(this).outerWidth();
                    }
                });
                $tabTitle.scrollLeft(autoLeft - 47);
            } else {
                $tabTitle.scrollLeft(left + 120);
            }
        },

        // 从服务器获取登录用户的信息
        initUserInfo: function () {
            // 获取当前用户信息
            base.getReq("user/" + base.getUser().id, {}, function (data) {

            });


        },
        // 页面元素绑定事件监听
        bindEvent: function () {
            // 退出登录
            $('#btnLogout').click(function () {
                layer.confirm('确定退出登录？', function (i) {
                    layer.close(i);
                    config.removeToken();
                    location.replace('login.html');
                });
            });
            // 修改密码
            $('#setPsw').click(function () {
                index.popupRight('template/password.html');
            });
            // 个人信息
            $('#setInfo').click(function () {

            });
            // 消息
            $('#btnMessage').click(function () {
                index.popupRight('template/message.html');
            });
        },


        // 设置侧栏折叠
        flexible: function (expand) {
            var isExapnd = $('.layui-layout-admin').hasClass('admin-nav-mini');
            if (isExapnd == !expand) {
                return;
            }
            if (expand) {
                $('.layui-layout-admin').removeClass('admin-nav-mini');
            } else {
                $('.layui-layout-admin').addClass('admin-nav-mini');
            }
            index.onResize();
        },
        // 设置导航栏选中
        activeNav: function (url) {
            $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item .layui-nav-child dd').removeClass('layui-this');
            if (url && url != '') {
                $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item').removeClass('layui-nav-itemed');
                var $a = $('.layui-layout-admin .layui-side .layui-nav>.layui-nav-item>.layui-nav-child>dd>a[href="#!' + url + '"]');
                $a.parent('dd').addClass('layui-this');
                $a.parent('dd').parent('.layui-nav-child').parent('.layui-nav-item').addClass('layui-nav-itemed');
            }
        },
        // 右侧弹出
        popupRight: function (path) {
            popupRightIndex = layer.open({
                type: 1,
                id: 'adminPopupR',
                anim: 2,
                isOutAnim: false,
                title: false,
                closeBtn: false,
                offset: 'r',
                shade: .2,
                shadeClose: true,
                resize: false,
                area: '336px',
                skin: 'layui-layer-adminRight',
                success: function () {
                    //admin.showLoading('#adminPopupR');
                    $('#adminPopupR').load(path, function () {
                        //admin.removeLoading('#adminPopupR');
                    });
                },
                end: function () {
                    layer.closeAll('tips');
                }
            });
        },
        // 关闭右侧弹出
        closePopupRight: function () {
            layer.close(popupRightIndex);
        },
        // 中间弹出
        popupCenter: function (param) {
            //popupCenterParam = param;
            popupCenterIndex = layer.open({
                type: 1,
                id: 'adminPopupC',
                title: param.title ? param.title : false,
                shade: .2,
                offset: '120px',
                area: param.area ? param.area : '450px',
                resize: false,
                skin: 'layui-layer-adminCenter',
                content: param.content,
                success: function () {
                    param.success ? param.success() : '';
                },
                end: function () {
                    layer.closeAll('tips');
                    //param.end ? param.end() : '';
                }
            });
        },
        // 关闭中间弹出并且触发finish回调
        /*
        finishPopupCenter: function () {
            layer.close(popupCenterIndex);
            popupCenterParam.finish ? popupCenterParam.finish() : '';
        },*/
        // 关闭中间弹出
        closePopupCenter: function () {
            layer.close(popupCenterIndex);
        },

        // 窗口大小改变监听
        onResize: function () {
            if (config.autoRender) {
                if ($('.layui-table-view').length > 0) {
                    setTimeout(function () {
                        admin.events.refresh();
                    }, 800);
                }
            }
        },
        // 显示加载动画
        showLoading: function (element) {
            $(element).append('<i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop admin-loading"></i>');
        },
        // 移除加载动画
        removeLoading: function (element) {
            $(element + '>.admin-loading').remove();
        }

    };

    // ewAdmin提供的事件
    index.events = {
        flexible: function (e) {  // 折叠侧导航
            var expand = $('.layui-layout-admin').hasClass('admin-nav-mini');
            index.flexible(expand);
        },
        refresh: function () {  // 刷新主体部分
            Q.refresh();
        },
        back: function () {  //后退
            history.back();
        },
        theme: function () {  // 设置主题
            index.popupRight('components/tpl/theme.html');
        },
        fullScreen: function (e) {  // 全屏
            var ac = 'layui-icon-screen-full', ic = 'layui-icon-screen-restore';
            var ti = $(this).find('i');

            var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ac).removeClass(ic);
            } else {
                var el = document.documentElement;
                var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ic).removeClass(ac);
            }
        },
        // 关闭当前选项卡
        closeThisTabs: function () {
            var $title = $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title');
            if ($title.find('li').first().hasClass('layui-this')) {
                return;
            }
            $title.find('li.layui-this').find(".layui-tab-close").trigger("click");
        },
        // 关闭其他选项卡
        closeOtherTabs: function () {
            $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title li:gt(0):not(.layui-this)').find('.layui-tab-close').trigger('click');
        },
        // 关闭所有选项卡
        closeAllTabs: function () {
            $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title li:gt(0)').find('.layui-tab-close').trigger('click');
        }
    };

    // 所有ew-event
    $('body').on('click', '*[ew-event]', function () {
        var event = $(this).attr('ew-event');
        var te = index.events[event];
        te && te.call(this, $(this));
    });

    // 移动设备遮罩层点击事件
    $('.site-mobile-shade').click(function () {
        index.flexible(true);
    });

    // 侧导航折叠状态下鼠标经过显示提示
    $('body').on('mouseenter', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        var tipText = $(this).find('cite').text();
        if (document.body.clientWidth > 750) {
            layer.tips(tipText, this);
        }
    }).on('mouseleave', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        layer.closeAll('tips');
    });

    // 侧导航折叠状态下点击展开
    $('body').on('click', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        if (document.body.clientWidth > 750) {
            layer.closeAll('tips');
            index.flexible(true);
        }
    });

    // 所有lay-tips处理
    $('body').on('mouseenter', '*[lay-tips]', function () {
        var tipText = $(this).attr('lay-tips');
        var dt = $(this).attr('lay-direction');
        layer.tips(tipText, this, {tips: dt || 1, time: -1});
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips');
    });

    exports('index', index);
});
