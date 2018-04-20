var merchantParent = Vue.component('merchant-parent', {
    props: ['merchantData'],
    template: '<div class="row">\
        <merchant-child v-for="merchant in merchantData.content" :key="merchant.id" :merchant="merchant"></merchant-child>\
        </div>'
});
var merchant = Vue.component('merchant-child', {
    props: ['merchant'],
    template: '<div @click="selectMerchant(merchant.id, merchant.merchantName)" class="col-md-10 col-sm-10 col-xs-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 merchant">\
    <div class="media">\
    <a class="media-left" href="#">\
    <img class="media-object img-response" :src="merchant.bannerUrl" alt="媒体对象">\
    </a>\
    <div class="media-body">\
    <h4 class="media-heading">{{merchant.merchantName}}</h4>\
订餐电话: {{merchant.contact}}<br>\
联系地址: {{merchant.address}}<br>\
</div>\
</div>\
</div>',
    methods: {
        selectMerchant: function (merchantId, merchantName) {
            console.log("选中商家: " + merchantId);
            nav.clearCart();
            localStorage.setItem("current_merchant_id", merchantId);
            localStorage.setItem("current_merchant_name", merchantName);
            nav.showMerchantTable();
        }
    }
});

var merchantTableParent = Vue.component(
    'merchant-table-parent',
    {
        props: ['dynamicData', 'currentTableId'],
        template: '<div class="row">\
        <merchant-table-child v-for="merchantTable in dynamicData" :current-table-id="currentTableId" :key="merchantTable.id" :merchant-table="merchantTable"></merchant-table-child>\
        </div>'
    }
);
var merchantTable = Vue.component(
    'merchant-table-child',
    {
        data: function () {
            return {
                tableStatus: [
                    '空闲',
                    '使用中',
                    '已预订'
                ],
                isValid: false
            }
        },
        props: ['merchantTable', 'currentTableId'],
        template: '\
        <div class="col-xs-6 col-sm-3" \
        v-bind:class="{\'selected-table\':isCurrentTable(merchantTable.id), \'merchant-table\':!isCurrentTable(merchantTable.id)}">\
            <div>\
                桌号: {{merchantTable.tableNo}}<br>\
                状态: {{tableStatus[merchantTable.bookStatus]}}<br>\
                已坐人数: {{merchantTable.sitReal}}<br>\
                可坐人数: {{merchantTable.capacity}}<br>\
                <div v-if="merchantTable.sitReal == merchantTable.capacity" align="right">\
                    <button type="button" class="btn btn-warning disabled">预订</button>\
                    <button type="button" class="btn btn-danger disabled">满座</button>\
                </div>\
                <div v-else-if="merchantTable.sitReal > 0" align="right">\
                    <button type="button" class="btn btn-warning" @click="cancel(merchantTable.merchantId,merchantTable.id)">退座</button>\
                    <button type="button" class="btn btn-success" @click="addPeople(merchantTable.merchantId,merchantTable.id)">加座</button>\
                </div>\
                <div v-else-if="merchantTable.bookStatus == 0" align="right">\
                    <button type="button" class="btn btn-warning" @click="reverse(merchantTable.id)">预订</button>\
                    <button type="button" class="btn btn-primary" @click="sitIt(merchantTable.merchantId,merchantTable.id)">坐下</button>\
                </div>\
                <div v-else align="right">\
                    <button type="button" class="btn btn-warning disabled">预订</button>\
                    <button type="button" class="btn btn-primary" @click="sitIt(merchantTable.merchantId,merchantTable.id)">到达</button>\
                </div></div></div>',
        methods: {
            cancel: function (merchantId, tableId) {
                merchantTableController.unUse(merchantId, tableId);
            },
            sitIt: function (merchantId, tableId) {
                var action = merchantTableController.URL.use(merchantId, tableId);
                var method = 'post';
                localStorage.setItem("current_merchant_table_id", tableId);
                this.useOrAddPeople(action, method);
            },
            isCurrentTable: function (tableId) {
                return tableId == this.currentTableId && this.merchantTable.bookStatus != 0;
            },
            reverse: function (tableId) {
                var result = merchantTableController.reserve(tableId);
                alert(result.msg);
                if (result.status == 0) {
                    nav.initTableData(1);
                } else {
                    if (result.msg.indexOf('登录') != -1) {
                        nav.showLogin();
                    }
                }
            },
            addPeople: function (merchantId, tableId) {
                var action = merchantTableController.URL.addPeople(merchantId, tableId);
                var method = 'put';
                this.useOrAddPeople(action, method);
            },
            useOrAddPeople: function (action, method) {
                $('#myModal').modal("show");
                var $form = $('#defaultForm');
                this.validatorInput($form);
                $form.attr("action", action);
                $form.attr("method", method);
            },
            validatorInput: function ($form) {
                //添加表单验证
                $form.bootstrapValidator({
                    message: '非法输入',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        peopleNumber: {
                            message: '非法输入',
                            validators: {
                                notEmpty: {
                                    message: '人数不能为空'
                                },
                                regexp: {
                                    regexp: /^[1-9][0-9]*$/,
                                    message: '请输入大于0的人数'
                                }
                            }
                        }
                    }
                }).on('error.field.bv', function () {
                    this.isValid = this.isValid && false;
                }).on('success.field.bv', function () {
                    this.isValid = this.isValid && true;
                });
            }
        }
    }
);
var Myinput = Vue.component(
    'my-input',
    {
        props: ['labelText', 'holderText', 'inputValue'],
        template: '<form id="defaultForm">\
        <fieldset>\
        <div class="form-group">\
        <label for="name">{{labelText}}</label>\
        <input type="text" class="form-control" v-model="inputValue" name="peopleNumber" :placeholder="holderText">\
        </div>\
        </fieldset>\
        </form>'
    }
);


var numberModal = Vue.component('number-modal', {
        props: ['modalData'],
        template: '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">\
    <div class="modal-dialog">\
    <div class="modal-content">\
    <div class="modal-header">\
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\
<h4 class="modal-title" id="myModalLabel">{{modalData.title}}</h4>\
    </div>\
    <div class="modal-body">\
    <my-input labelText="人数" holderText="请输入人数" :input-value="modalData.num"></my-input>\
    </div>\
    <div class="modal-footer">\
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>\
    <button id="toSubmit" type="button" class="btn btn-primary" @click="submitForm">提交</button>\
    </div>\
    </div><!-- /.modal-content -->\
</div><!-- /.modal -->\
</div>',
        data: function () {
            return {
                isValid: false
            };
        },
        methods: {
            submitForm: function () {
                var $form = $('#defaultForm');
                this.isValid = true;
                $form.submit();
                if (this.isValid) {
                    var result = merchantTableController.sitOrAdd($form);
                    alert(result.msg);
                    $('#myModal').modal("hide");
                    if (result.status == 0) {
                        nav.initTableData();
                        nav.showMenu();
                    } else if (result.msg.indexOf("登录") != -1) {
                        nav.showLogin();
                    }
                }
            }
        }
    })
;

var menuCounter = Vue.component('menu-counter',
    {
        props: ['menu'],
        data: function () {
            return {
                menuId: 0,
                menuName: '',
                menuPrice: 0,
                counter: 0
            };
        },
        template: '<div class="row">\
        <div class="col-lg-6">\
        <div class="input-group">\
        <span class="input-group-btn">\
    <button class="btn btn-default" type="button" @click="counter--">\
    -\
</button>\
</span>\
<input type="text" v-model="counter" readonly class="form-control" style="min-width: 50px;">\
    <span class="input-group-btn">\
    <button class="btn btn-default" type="button" @click="counter++">\
    +\
</button>\
</span>\
</div>\
        </div></div>',
        created: function () {
            this.menuId = this.menu.id;
            this.menuName = this.menu.name;
            this.menuPrice = this.menu.price;
            this.getNumberIfInCart();
        },
        methods: {
            getNumberIfInCart: function () {
                var orderList = localStorage.getItem("shop_cart");
                if (orderList == null) {
                    return;
                }
                orderList = JSON.parse(orderList);
                for (var key in orderList) {
                    if (key == 'menu_' + this.menuId) {
                        this.counter = orderList['menu_' + this.menuId].num;
                    }
                }
            }
        },
        watch: {
            counter: function (newValue) {
                if (newValue < 0) {
                    this.counter = 0;
                } else if (newValue > 999) {
                    this.counter = 999;
                } else {
                    var shopCart = localStorage.getItem("shop_cart");
                    if (shopCart == null) {
                        shopCart = new Object();
                    } else {
                        shopCart = JSON.parse(shopCart);
                    }


                    if (newValue > 0) {
                        shopCart['menu_' + this.menuId] = {
                            id: this.menuId,
                            name: this.menuName,
                            price: this.menuPrice,
                            num: this.counter
                        };
                    } else {
                        delete shopCart['menu_' + this.menuId];
                    }
                    shopCart = JSON.stringify(shopCart);
                    localStorage.setItem("shop_cart", shopCart);
                    nav.updateTotal();
                }
            }
        }
    }
);

var menuParent = Vue.component('menu-parent', {
    props: ['dynamicData'],
    created: function () {
        $(document).ready(function () {
            $("#myNav").affix({
                offset: {
                    top: 100
                }
            });
        });
    },
    template: '<div class="row">\
    <div class="col-xs-3 col-md-2" id="myScrollspy">\
    <ul class="nav nav-tabs nav-stacked" id="myNav">\
        <li :class="{active : index == 0}" v-for="(category, index) in dynamicData.categoryList">\
    <a :href="\'#\'+category.id">{{category.name}}</a>\
</li>\
</ul>\
</div>\
<div class="col-xs-9 col-md-10 bg-white">\
    <menu-selection v-for="(category, index) in dynamicData.categoryList" :category="category"\
:menu-list="dynamicData.menuList" :key="category.id"></menu-selection>\
    </div>\
    <div class="shop-cart">\
</div>\
</div>'
});

var menuSelection = Vue.component('menu-selection', {
    props: ['category', 'menuList'],
    template: '<section>\
    <h3 :id="category.id">{{category.name}}</h3>\
<hr>\
<div class="row">\
    <menu-util v-for="menu in getMenu(category.id)" :key="menu.id" :menu="menu" :category-id="category.id"></menu-util>\
    </div>\
    </section>',
    methods: {
        getMenu: function (categoryId) {
            var arr = [];
            for (var i = 0; i < this.menuList.length; i++) {
                if (this.menuList[i].categoryId == categoryId) {
                    arr.push(this.menuList[i]);
                }
            }
            return arr;
        }
    }
});

var menuUtil = Vue.component('menu-util', {
    props: ['menu', 'categoryId'],
    template: '\
    <div class="col-md-10 col-sm-10 col-xs-10 menu-util">\
    <div class="media">\
    <a class="media-left" href="#">\
    <img class="media-object img-response" :src="menu.bannerUrl" :alt="menu.name">\
    </a>\
    <div class="media-body">\
    <h4 class="media-heading">{{menu.name}}</h4>\
<div class="row">\
    <div class="col-md-12 col-sm-12 col-xs-12">描述：{{menu.description}}</div>\
<div class="col-md-5 col-sm-5 col-xs-5">\
    <br><div class="menu-price">￥{{menu.price}}</div>\
<menu-counter :menu="menu"></menu-counter>\
    </div>\
    </div>\
    </div>\
    </div>\
    <br>\
    </div>',
    methods: {
        getMenu: function (categoryId) {
            var arr = [];
            for (var i = 0; i < this.menuList.length; i++) {
                if (this.menuList[i].categoryId == categoryId) {
                    arr.push(this.menuList[i]);
                }
            }
            return arr;
        }
    }
});


var detailModal = Vue.component('detail-modal', {
    props: ['total', 'totalPrice', 'menuList'],
    template: '<div class="modal fade" id="cartShow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"\
    aria-hidden="true">\
        <div class="modal-dialog">\
    <div class="modal-content">\
    <div class="modal-header">\
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
<h4 class="modal-title" id="myModalLabel">清单</h4>\
    </div>\
    <div class="modal-body">\
    <table class="table">\
    <caption v-if="total > 0">数量:<span class="menu-price">{{total}}</span>, 总价:<span class="menu-price">￥{{totalPrice.toFixed(2)}}</span></caption>\
    <caption v-if="total == 0">总价:<span class="menu-price">￥{{totalPrice.toFixed(2)}}</span></caption>\
<thead>\
<tr>\
<th>菜名</th>\
<th>价格</th>\
<th>数量</th>\
</tr>\
</thead>\
<tbody v-if="total != 0">\
    <tr v-for="menu in menuList">\
    <td>{{menu.name}}</td>\
    <td>{{menu.price}}</td>\
    <td>{{menu.num}}</td>\
    </tr>\
</tbody>\
<tbody v-else>\
<tr v-for="menu in menuList">\
<td>{{menu.menuName}}</td>\
<td>{{menu.price}}</td>\
<td>{{Math.ceil(menu.totalPrice / menu.price)}}</td>\
</tr>\
</tbody>\
</table>\
</div>\
<div class="modal-footer">\
    <button v-if="total > 0" type="button" class="btn btn-primary" @click="toPay">下单</button>\
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>\
    </div>\
    </div>\
    </div>\
    </div>\
    </div>',
    methods: {
        toPay: function () {
            $('#cartShow').modal('hide');
            var merchantId = localStorage.getItem("current_merchant_id");
            var result = merchantTableController.getCurrentSelected();
            if (result.status != 0) {
                alert("尚未选择餐桌");
                nav.currentView = "showmerchanttable";
                return;
            }
            var tableId = result.data.tableId;
            var ids = '';
            var counts = '';
            for (var index in this.menuList) {
                ids += this.menuList[index].id + ',';
                counts += this.menuList[index].num + ',';
            }

            var payResult = orderController.createOrder(merchantId, tableId, ids, counts);
            if (payResult.status != 0) {
                alert(payResult.msg);
                return;
            } else {
                nav.showPay();
                localStorage.removeItem('shop_cart');
                nav.updateTotal();
            }
        }
    }
});

var loginModal = Vue.component('login-modal', {
    template: '<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"\
    aria-hidden="true">\
        <div class="modal-dialog">\
    <div class="modal-content">\
    <div class="modal-header">\
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
<h4 class="modal-title" id="myModalLabel">登录</h4>\
    </div>\
    <div class="modal-body">\
    <form id="loginForm">\
    <fieldset>\
        <div class="form-group">\
            <label for="username" class="col-sm-2 control-label">用户名</label>\
            <div class="col-sm-10">\
            <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">\
            </div>\
        </div>\
    <br>\
    <div class="form-group">\
        <label for="password" class="col-sm-2 control-label">密码</label>\
        <div class="col-sm-10">\
        <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">\
        </div>\
    </div>\
    <br>\
    <br>\
    </fieldset>\
    </form>\
    </div>\
    <div class="modal-footer">\
    <button type="button" class="btn btn-default" @click="toLogin">登录</button>\
    <button type="button" class="btn btn-primary" @click="toRegisterModal">注册</button>\
    </div>\
    </div>\
    </div>\
    </div>',
    methods: {
        toLogin: function () {
            nav.loginValid = true;
            $('#loginForm').submit();
            if (nav.loginValid) {
                var username = $('#username').val();
                var password = $('#password').val();
                var loginResult = userController.login(username, password);
                if (loginResult.status == 0) {
                    nav.userObj = loginResult.data;
                    alert("登录成功~");
                    $('#loginModal').modal('hide');
                    nav.loadTableId();
                } else {
                    nav.userObj = null;
                    alert(loginResult.msg);
                }
            }
        },
        toRegisterModal: function () {
            console.log("注册");
            $('#loginModal').modal('hide');
            $('#registerModal').modal('show');
            nav.registerValidator();
        }
    }
});
var registerModal = Vue.component('register-modal', {
    template: '<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"\
    aria-hidden="true">\
        <div class="modal-dialog">\
    <div class="modal-content">\
    <div class="modal-header">\
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
<h4 class="modal-title" id="myModalLabel">注册</h4>\
    </div>\
    <div class="modal-body" style="overflow-y:scroll;">\
    <form id="registerForm">\
    <fieldset>\
        <div class="form-group">\
            <label for="username" class="col-sm-2 control-label">用户名</label>\
            <div class="col-sm-10">\
            <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="nickname" class="col-sm-2 control-label">昵称</label>\
            <div class="col-sm-10">\
            <input type="text" class="form-control" name="nickname" id="nickname" placeholder="请输入昵称">\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="password" class="col-sm-2 control-label">密码</label>\
            <div class="col-sm-10">\
            <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="confirmPassword" class="col-sm-2 control-label">确认密码</label>\
            <div class="col-sm-10">\
            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="请确认密码">\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="gender" class="col-sm-2 control-label">性别</label>\
            <div class="col-sm-10">\
                <div id="gender" class="radio">\
                    <label><input type="radio" name="gender" id="gender1" value="男" checked>男</label>\
                    <label><input type="radio" name="gender" id="gender2" value="女" checked>女</label>\
                </div>\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="mobilephone" class="col-sm-2 control-label">手机号码</label>\
            <div class="col-sm-10">\
            <input type="text" class="form-control" name="mobilephone" id="mobilephone" placeholder="请输入手机号">\
            </div>\
        </div>\
        <div class="form-group">\
            <label for="age" class="col-sm-2 control-label">年龄</label>\
            <div class="col-sm-10">\
            <input type="text" class="form-control" name="age" id="age" placeholder="请输入年龄">\
            </div>\
        </div>\
    </fieldset>\
    </form>\
    </div>\
    <div class="modal-footer">\
    <button type="button" class="btn btn-default" @click="toLoginModal">登录</button>\
    <button type="button" class="btn btn-primary" @click="toRegister">注册</button>\
    </div>\
    </div>\
    </div>\
    </div>',
    methods: {
        toLoginModal: function () {
            $('#registerModal').modal('hide');
            $('#loginModal').modal('show');
        },
        toRegister: function () {
            nav.loginValid = true;
            var $from = $('#registerForm');
            $from.submit();
            if (nav.loginValid) {
                var result = userController.addUser($from);

                console.log(result);
                if (result.status == 0) {
                    alert("注册成功~");
                    this.toLoginModal();
                } else {
                    alert("注册失败了~");
                }

            }
        }
    }
});

var showPay = Vue.component(
    'show-pay',
    {
        props: ['dynamicData'],
        template: '<div class="table-responsive">\
        <table class="table">\
    <caption>订单列表</caption>\
    <thead>\
    <tr>\
    <th>订单号</th>\
    <th>支付状态</th>\
    <th>金额</th>\
    <th>操作</th>\
    </tr>\
    </thead>\
    <tbody>\
    <tr v-for="order in dynamicData">\
    <td>{{order.orderNo}}</td>\
<td>{{order.status == 50 ? \'已支付\' : \'未支付\'}}</td>\
<td>{{order.totalPrice}}</td>\
<td>\
<button class="btn btn-primary" @click="detail(order.orderNo)">订单详情</button><br>\
<button class="btn btn-danger" v-if="order.status != 50" :class="{disabled:order.status == 50}" @click="alipay(order.orderNo, order.status)">一键支付</button>\
    </td>\
    </tr>\
    </tbody>\
    </table>\
    </div>',
        methods: {
            alipay: function (orderNo, status) {
                if (status == 50) return;
                window.location.href = orderController.URL.pay_order(orderNo);
            },
            detail: function (orderNo) {
                console.log(orderNo);
                var result = orderController.getOrderDeatil(orderNo);
                if (result.status == 0) {
                    nav.total = 0;
                    nav.menuList = result.data.orderItemVo;
                    nav.totalPrice = result.data.totalPrice;
                    nav.showCartDetail();
                }
                console.log(result);
            }
        }
    }
);

var showLogin = Vue.component(
    'show-login',
    {
        template: '<div>显示登录页面</div>'
    }
);