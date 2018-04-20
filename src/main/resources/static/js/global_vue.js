/**
 * Created by xuqipei on 17-7-18.
 */
var nav = new Vue({
    el: '.index-container',
    data: {
        userObj: null,
        dynamicData: {},
        modalData: {},
        merchantData: [],
        menuList: [],
        total: 0,
        loginValid: false,
        registerValid: false,
        totalPrice: 0,
        currentView: 'showmerchanttable',
        currentMerchantName: '未选择',
        currentTableId: -1
    },
    created: function () {
        this.loadMerchantName();
        this.showMerchant();
        this.getLoginUser();
        this.loadTableId();
    },
    methods: {
        loadMerchantName: function () {
            var merchantName = localStorage.getItem("current_merchant_name");
            this.currentMerchantName = merchantName;
        },
        loadTableId: function () {
            var result = merchantTableController.getCurrentSelected();
            if (result.status == 0) {
                this.currentTableId = result.data.tableId;
                localStorage.setItem("current_merchant_table_id", result.data);
                console.log("选中餐桌" + result.data);
            } else {
                this.currentTableId = -1;
                localStorage.removeItem("current_merchant_table_id");
            }
        },
        getLoginUser: function () {
            var userResult = userController.getUserInfo();
            console.log(userResult);
            if (userResult.status == 0) {
                this.userObj = userResult.data;
                this.loadTableId();
            } else {
                this.userObj = null;
            }
        },
        unpayToRun: function () {
            merchantTableController.unpayToRun();
        },
        initModalData: function () {
            this.modalData = {
                title: '请输入人数',
                num: 1
            }
        },
        initTableData: function () {
            this.initModalData();
            this.loadTableId();
            this.dynamicData = this.getMerchantTable();
        },
        getMerchantTable: function () {
            var merchantId = localStorage.getItem("current_merchant_id");
            var merchantName = localStorage.getItem("current_merchant_name");
            if (merchantId == null) {
                alert("未选择商家!");
                this.showMerchant();
                return;
            }

            this.currentMerchantName = merchantName;
            var dataResult = merchantTableController.getAll(merchantId);
            if (dataResult.status != 0) {
                alert("获取餐桌出错啦～～" + dataResult.msg);
                return [];
            }
            return dataResult.data.merchantTableList;
        },
        getMerchant: function () {
            this.merchantData = merchantController.getALl().data;
        },
        getMenu: function (merchantId) {
            var categoryList = menuController.getAllCategory(merchantId).data;
            this.dynamicData.categoryList = categoryList;
            var categoryIds = '';
            for (var i = 0; i < categoryList.length; i++) {
                categoryIds += categoryList[i].id + ",";
            }

            this.dynamicData.menuList = menuController.getAllMenu(categoryIds).data;
        },
        showMerchant: function () {
            this.currentView = 'showmerchant';
            this.getMerchant();
        },
        showMerchantTable: function () {
            console.log("显示餐桌");
            this.currentView = 'showmerchanttable';
            this.initTableData();
        },
        showCartDetail: function () {
            $('#cartShow').modal('show');
        },
        logout: function () {
            userController.logout();
        },
        updateTotal: function () {
            var shopCart = localStorage.getItem("shop_cart");
            if (shopCart == null) {
                this.total = 0;
                this.totalPrice = 0;
                return;
            }
            shopCart = JSON.parse(shopCart);
            var count = 0;
            var totalPrice = 0;
            for (var key in shopCart) {
                count += shopCart[key].num;
                console.log();
                totalPrice += shopCart[key].price * shopCart[key].num;
            }
            this.total = count;
            this.totalPrice = totalPrice;
            this.menuList = shopCart;
        },
        clearCart: function () {
            localStorage.clear();
            this.updateTotal();
        },
        showMenu: function () {
            var merchantId = localStorage.getItem("current_merchant_id");
            if (merchantId == null) {
                alert("未选择商家!");
                this.showMerchant();
                return;
            }
            this.getMenu(merchantId);
            this.updateTotal();
            this.currentView = 'showmenu';
        },
        showPay: function () {
            var userResult = userController.getUserInfo();
            if (userResult.status != 0) {
                alert("用户未登录,请登录...");
                this.showLogin();
                return;
            }
            this.dynamicData = orderController.getUserOrder().data;
            this.currentView = 'showpay';
        },
        showLogin: function () {
            $('#loginModal').modal('show');
            this.loginValidator();
        },
        loginValidator: function () {
            var $form = $('#loginForm');
            //添加表单验证
            $form.bootstrapValidator({
                message: '非法输入',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    username: {
                        message: '非法输入',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z]\w{4,17}$/,
                                message: '非法用户名，肯定是登录不了的'
                            }
                        }
                    },
                    password: {
                        message: '非法输入',
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z]\w{4,17}$/,
                                message: '非法密码，肯定是没用的'
                            }
                        }
                    },
                }
            }).on('error.field.bv', function () {
                nav.loginValid = nav.loginValid && false;
                console.log('发现错误～' + nav.loginValid);
            }).on('success.field.bv', function () {
                nav.loginValid = nav.loginValid && true;
                console.log('一切正常～' + nav.loginValid);
            });
        },
        registerValidator: function () {
            var $form = $('#registerForm');
            //添加表单验证
            $form.bootstrapValidator({
                message: '非法输入',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    username: {
                        message: '非法输入',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z]\w{4,17}$/,
                                message: '用户名需包含字母及数字，不得超过16个字符'
                            },
                            remote: {
                                type: 'POST',
                                url: '/user/valid',
                                message: '用户名已存在',
                                delay: 2000
                            }
                        }
                    },
                    nickname: {
                        message: '非法输入',
                        validators: {
                            notEmpty: {
                                message: '昵称不能为空'
                            },
                            stringLength: {
                                min: 1,
                                max: 16,
                                message: '长度在1到16位之间'
                            }
                        }
                    },
                    password: {
                        message: '非法输入',
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z]\w{4,17}$/,
                                message: '密码需包含字母及数字，不得超过16个字符'
                            },
                            identical: {
                                field: 'confirmPassword',
                                message: '两次密码不一致'
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            identical: {
                                field: 'password',
                                message: '两次密码不一致'
                            }
                        }
                    },
                    age: {
                        validators: {
                            notEmpty: {
                                message: '年龄不能为空'
                            },
                            lessThan: {
                                value: 100,
                                inclusive: true,
                                message: '年龄不能大于100'
                            },
                            greaterThan: {
                                value: 10,
                                inclusive: false,
                                message: '年龄不能小于10'
                            },
                            regexp: {
                                regexp: /^[0-9]+$/,
                                message: '年龄不符合要求'
                            }
                        }
                    },
                    mobilephone: {
                        validators: {
                            notEmpty: {
                                message: '手机不能为空'
                            },
                            regexp: {
                                regexp: /^1[0-9]{10}$/,
                                message: '手机不符合要求'
                            }
                        }
                    }
                }
            }).on('error.field.bv', function () {
                nav.loginValid = nav.loginValid && false;
                console.log('发现错误～' + nav.loginValid);
            }).on('success.field.bv', function () {
                nav.loginValid = nav.loginValid && true;
                console.log('一切正常～' + nav.loginValid);
            });
        }
    },
    components: {
        showmerchant: merchantParent,
        showmerchanttable: merchantTableParent,
        showmenu: menuParent,
        showpay: showPay
    }
});