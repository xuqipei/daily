/**
 * Created by xuqipei on 17-7-25.
 */

var userHeader = Vue.component('user-header', {
    template: '<header>\
    <!-- logo -->\
    <div class="am-fl tpl-header-logo">\
        <a href="javascript:;"><img src="assets/img/logo.png" alt=""></a>\
        </div>\
        <!-- 右侧内容 -->\
        <div class="tpl-header-fluid">\
        <!-- 侧边切换 -->\
        <div class="am-fl tpl-header-switch-button am-icon-list">\
        <span>\
        </span>\
        </div>\
        <!-- 其它功能-->\
        <div class="am-fr tpl-header-navbar">\
        <ul>\
        <!-- 欢迎语 -->\
        <li class="am-text-sm tpl-header-navbar-welcome">\
        <a href="javascript:;">欢迎你, <span>{{merchantName}}</span> </a>\
</li>\
<!-- 退出 -->\
<li class="am-text-sm" @click="loginOut">\
    <a href="javascript:;">\
    <span class="am-icon-sign-out"></span> 退出\
    </a>\
    </li>\
    </ul>\
    </div>\
    </div>\
    </header>',
    props: ['merchantName'],
    methods: {
        loginOut: function () {
            userController.logoutAdmin();
        }
    }
});
var LeftNav = Vue.component(
    'left-nav',
    {
        props: ['navObject', 'currentParentNav', 'currentChildNav'],
        template: '<ul class="sidebar-nav">\
        <li class="sidebar-nav-link">\
    <a href="javascript:;" class="sidebar-nav-sub-title" :class="{active:isParent()}">\
    <i class="am-icon-table sidebar-nav-link-logo"></i> {{navObject.parentName}}\
    <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico" \
    :class="{\'sidebar-nav-sub-ico-rotate\':isParent()}"></span>\
    </a>\
    <ul class="sidebar-nav sidebar-nav-sub" :style="displayStyle">\
        <li v-for="child in navObject.childList" class="sidebar-nav-link">\
        <a :href="child.forward" :class="{\'sub-active\':isChild(child.childName)}">\
            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> {{child.childName}}\
        </a>\
        </li>\
    </ul>\
    </li>\
    </ul>',
        methods: {
            isParent: function () {
                return this.navObject.parentName == this.currentParentNav;
            },
            isChild: function (childName) {
                return childName == this.currentChildNav;
            }
        },
        computed: {
            displayStyle: function () {
                return {
                    display: this.isParent() ? 'block' : 'none'
                };
            }
        }
    });

var leftSidebar = new Vue({
    el: '.left-sidebar',
    data: {
        navMap: {parent: "餐桌管理", child: "餐桌列表"},
        navList: [
            {
                parentName: '餐桌管理',
                childList: [
                    {childName: '餐桌列表', forward: 'table_list.html'},
                    {childName: '添加餐桌', forward: 'add_table.html'}
                ]
            },
            {
                parentName: '菜品管理',
                childList: [
                    {childName: '菜品列表', forward: 'menu_list.html'},
                    {childName: '添加菜品', forward: 'add_menu.html'}
                ]
            },
            {
                parentName: '分类管理',
                childList: [
                    {childName: '分类列表', forward: 'category_list.html'},
                    {childName: '添加分类', forward: 'add_category.html'}
                ]
            },
            {
                parentName: '订单管理',
                childList: [
                    {childName: '订单列表', forward: 'order_list.html'}
                ]
            },
            {
                parentName: '资料管理',
                childList: [
                    {childName: '编辑信息', forward: 'edit_self.html'}
                ]
            }
        ]
    },
    methods: {
        mapMenu: function (url) {
            var reg = /\w+.html/;
            var forward = url.match(reg)[0];

            var list = this.navList;
            for (var p in list) {
                var parent = list[p].parentName;
                var childList = list[p].childList;
                for (var c in childList) {
                    if (childList[c].forward == forward) {
                        var child = childList[c].childName;
                        this.navMap.parent = parent;
                        this.navMap.child = child;
                        return;
                    }
                }
            }
            this.navMap = {parent: '餐桌管理', child: '使用情况'};
        }
    }
});

var userInfo = new Vue({
    el: '.vue-user-info',
    data: {
        id: -1,
        username: '',
        merchantName: '',
        address: '',
        contact: '',
        bannerUrl: ''
    },
    created: function () {
        var result = userController.getInfo();
        this.assembleData(result);
    },
    methods: {
        assembleData: function (result) {
            if (result.status == 0) {
                this.id = result.data.id;
                this.username = result.data.loginName;
                this.merchantName = result.data.merchantName;
                this.address = result.data.address;
                this.contact = result.data.contact;
                this.bannerUrl = result.data.bannerUrl;
            } else {
                alert("用户未登录");
                window.location.href = "index.html";
            }
        }
    }
});

