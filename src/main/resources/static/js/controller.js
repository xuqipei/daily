/**
 * Created by xuqipei on 17-7-18.
 */
// const BASE_URL = 'http://119.29.118.31:8089';
const BASE_URL = 'http://localhost:8080';
var merchantController = {
    URL: {
        getALl: function () {
            return BASE_URL + "/merchant/get_all";
        },
        updateMerchant: function () {
            return BASE_URL + "/manage/merchant/update";
        },
        deleteMerchant: function (id) {
            return BASE_URL + "/manage/merchant/delete/" + id;
        }
    },
    updateMerchant: function (merchantName, address, contact, bannerUrl) {
        var updateResult;
        $.ajax({
            type: 'PUT',
            async: false,
            data: {
                merchantName: merchantName,
                address: address,
                contact: contact,
                bannerUrl: bannerUrl
            },
            url: this.URL.updateMerchant(),
            success: function (result) {
                updateResult = result;
            },
            error: function () {
                updateResult.status = 1;
                updateResult.msg = "更新失败，可能是哪里写错了~";
            }
        });
        return updateResult;
    },
    deleteMerchant: function (id) {
        $.ajax({
            type: 'DELETE',
            url: this.URL.deleteMerchant(id),
            success: function (result) {
                if (result.status == 0) {
                    alert("删除成功");
                } else {
                    alert("删除失败");
                }
                window.location.reload();
            },
            error: function () {
                alert("删除失败");
                window.location.reload();
            }
        });
    },
    getALl: function () {
        var merchantResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.getALl(),
            success: function (result) {
                merchantResult = result;
            }
        });
        return merchantResult;
    }
};
var merchantTableController = {
    URL: {
        getALl: function (merchantId) {
            return BASE_URL + "/table/get_all/" + merchantId;
        },
        reserve: function (tableId) {
            return BASE_URL + "/table/reserve/" + tableId;
        },
        use: function (merchantId, tableId) {
            return BASE_URL + "/table/use/" + merchantId + "/" + tableId;
        },
        addPeople: function (merchantId, tableId) {
            return BASE_URL + "/table/add_people/" + merchantId + "/" + tableId;
        },
        getCurrent: function () {
            return BASE_URL + "/table/current_selected";
        },
        addTable: function () {
            return BASE_URL + "/manage/table/add"
        },
        deleteTable: function (id) {
            return BASE_URL + "/manage/table/delete/" + id;
        },
        updateTable: function () {
            return BASE_URL + "/manage/table/update"
        },
        run: function () {
            return BASE_URL + "/table/run";
        },
        unUse: function () {
            return BASE_URL + "/table/un_use";
        }
    },
    unUse: function (merchantId, tableId) {
        $.ajax({
            type: 'PUT',
            async: false,
            data: {
                merchantId: merchantId,
                tableId: tableId
            },
            url: this.URL.unUse(),
            success: function (result) {
                alert(result.msg);
                nav.initTableData();
                nav.showMerchantTable();
            },
            error: function () {
                alert("退桌失败");
            }
        });
    },
    unpayToRun: function () {
        $.ajax({
            type: 'GET',
            url: this.URL.run(),
            success: function (result) {
                alert(result.msg);
            },
            error: function () {
                alert("跑路失败");
            }
        });
    },
    updateTbale: function (tableId, tableNo, merchantId, bookStatus, sitReal, capacity) {
        var updateResult;
        $.ajax({
            type: 'PUT',
            async: false,
            data: {
                id: tableId,
                tableNo: tableNo,
                merchantId: merchantId,
                bookStatus: bookStatus,
                sitReal: sitReal,
                capacity: capacity
            },
            url: this.URL.updateTable(),
            success: function (result) {
                updateResult = result;
            },
            error: function () {
                updateResult.status = 1;
                updateResult.msg = "更新失败，可能是哪里写错了~";
            }
        });
        return updateResult;
    },
    deleteTable: function (id) {
        $.ajax({
            type: 'DELETE',
            url: this.URL.deleteTable(id),
            success: function (result) {
                if (result.status == 0) {
                    alert("删除成功");
                } else {
                    alert("删除失败");
                }
                window.location.reload();
            },
            error: function () {
                alert("删除失败");
                window.location.reload();
            }
        });
    },
    addTable: function (merchantId, tableNo, capacity) {
        $.ajax({
            type: 'POST',
            data: {
                tableNo: tableNo, merchantId: merchantId, capacity: capacity
            },
            url: this.URL.addTable(),
            success: function (result) {
                if (result.status == 0) {
                    alert("添加成功");
                    window.location.reload();
                }
            },
            error: function () {
                alert("添加分类失败，可能是哪里写错了~");
                window.location.reload();
            }
        });
    },
    getAll: function (merchantId) {
        //console.log("获取商户: " + merchantId + "的餐桌");
        var tableResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.getALl(merchantId),
            success: function (result) {
                tableResult = result;
            }
        });
        return tableResult;
    },
    getCurrentSelected: function () {
        var tableResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.getCurrent(),
            success: function (result) {
                tableResult = result;
            }
        });
        return tableResult;
    },
    reserve: function (tableId) {
        console.log("预订餐桌id为: " + tableId + "的餐桌");
        var reserveResult;
        $.ajax({
            type: 'PUT',
            async: false,
            url: this.URL.reserve(tableId),
            success: function (result) {
                reserveResult = result;
            }
        });
        return reserveResult;
    },
    sitOrAdd: function ($form) {
        var submitResult;
        $.ajax({
            cache: true,
            type: $form.attr('method'),
            url: $form.attr('action'),//提交的URL
            data: $form.serialize(), // 要提交的表单,必须使用name属性
            async: false,
            success: function (result) {
                submitResult = result;
            },
            error: function (request) {
                alert("Connection error");
            }
        });
        return submitResult;
    }
};


var menuController = {
    URL: {
        add: function () {
            return BASE_URL + "/manage/menu/add";
        },
        addCategory: function () {
            return BASE_URL + "/manage/category/add"
        },
        get_all_category: function (merchantId) {
            return BASE_URL + "/category/get_all/" + merchantId;
        },
        get_all_menu: function () {
            return BASE_URL + "/menu/get_all";
        },
        delete_menu: function (id) {
            return BASE_URL + "/manage/menu/delete/" + id;
        },
        update_menu: function () {
            return BASE_URL + "/manage/menu/update";
        },
        update_category: function () {
            return BASE_URL + "/manage/category/update";
        },
        delete_category: function (id) {
            return BASE_URL + "/manage/category/delete/" + id;
        }
    },
    deleteCategory: function (id) {
        $.ajax({
            type: 'DELETE',
            url: this.URL.delete_category(id),
            success: function (result) {
                if (result.status == 0) {
                    alert("删除成功");
                    window.location.reload();
                }
            },
            error: function () {
                alert("删除失败");
                window.location.reload();
            }
        });
    },
    addMenu: function (name, categoryId, price, mainPic, description) {
        var addResult;
        $.ajax({
            type: 'POST',
            data: {
                name: name, categoryId: categoryId, price: price, bannerUrl: mainPic, description: description
            },
            async: false,
            url: this.URL.add(),
            success: function (result) {
                addResult = result;
            },
            error: function () {
                addResult.status = 1;
                addResult.msg = "添加菜谱失败，可能是哪里写错了~";
            }
        });
        return addResult;
    },
    updateMenu: function (id, name, categoryId, price, mainPic, description) {
        var updateResult;
        $.ajax({
            type: 'PUT',
            data: {
                id: id, name: name, categoryId: categoryId, price: price, bannerUrl: mainPic, description: description
            },
            async: false,
            url: this.URL.update_menu(),
            success: function (result) {
                updateResult = result;
            },
            error: function () {
                updateResult.status = 1;
                updateResult.msg = "更新菜品失败，可能是哪里写错了~";
            }
        });
        return updateResult;
    },
    updateCategory: function (categoryId, name, merchantId) {
        var updateResult;
        $.ajax({
            type: 'PUT',
            data: {
                id: categoryId, name: name, merchantId: merchantId
            },
            async: false,
            url: this.URL.update_category(),
            success: function (result) {
                updateResult = result;
            },
            error: function () {
                updateResult.status = 1;
                updateResult.msg = "更新分类失败，可能是哪里写错了~";
            }
        });
        return updateResult;
    },
    deleteMenu: function (id) {
        $.ajax({
            type: 'DELETE',
            url: this.URL.delete_menu(id),
            success: function (result) {
                if (result.status == 0) {
                    alert("删除成功");
                    window.location.reload();
                }
            },
            error: function () {
                alert("删除失败");
                window.location.reload();
            }
        });
    },
    addCategory: function (name, merchantId) {
        var categoryResult;
        $.ajax({
            type: 'POST',
            data: {
                name: name, merchantId: merchantId
            },
            async: false,
            url: this.URL.addCategory(),
            success: function (result) {
                categoryResult = result;
            },
            error: function () {
                categoryResult.status = 1;
                categoryResult.msg = "添加分类失败，可能是哪里写错了~";
            }
        });
        return categoryResult;
    },
    getAllCategory: function (merchantId) {
        var categoryResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.get_all_category(merchantId),
            success: function (result) {
                categoryResult = result;
            }
        });
        return categoryResult;
    },
    getAllMenu: function (categoryIds) {
        var categoryResult;
        $.ajax({
            type: 'GET',
            async: false,
            data: {categoryIds: categoryIds},
            url: this.URL.get_all_menu(),
            success: function (result) {
                categoryResult = result;
            }
        });
        return categoryResult;
    }
};

var userController = {
    URL: {
        login: function () {
            return BASE_URL + "/user/login";
        },
        loginMerchant: function () {
            return BASE_URL + "/manage/merchant/login";
        },
        getUserInfo: function () {
            return BASE_URL + "/user/get_info";
        },
        valid: function () {
            return BASE_URL + "/user/valid"
        },
        add: function () {
            return BASE_URL + "/user/addUser"
        },
        logout: function () {
            return BASE_URL + "/user/logout";
        },
        logoutAdmin: function () {
            return BASE_URL + "/manage/merchant/logout"
        },
        getInfo: function () {
            return BASE_URL + "/manage/merchant/get_information";
        }
    },
    getInfo: function () {
        var infoResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.getInfo(),
            success: function (result) {
                infoResult = result;
            }
        });
        return infoResult;
    },
    loginMerchant: function (username, password) {
        var loginResult;
        $.ajax({
            type: 'POST',
            async: false,
            data: {username: username, password: password},
            url: this.URL.loginMerchant(),
            success: function (result) {
                loginResult = result;
            }
        });
        return loginResult;
    },
    login: function (username, password) {
        var loginResult;
        $.ajax({
            type: 'POST',
            async: false,
            data: {username: username, password: password},
            url: this.URL.login(),
            success: function (result) {
                loginResult = result;
            }
        });
        return loginResult;
    },
    logoutAdmin: function () {
        $.ajax({
            type: 'GET',
            url: this.URL.logoutAdmin(),
            success: function (result) {
                alert("退出成功");
                window.location.href = "index.html";

            },
            error: function () {
                alert("退出异常！");
            }
        });
    },
    getUserInfo: function () {
        var userResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.getUserInfo(),
            success: function (result) {
                userResult = result;
            }
        });
        return userResult;
    },
    logout: function () {
        $.ajax({
            type: 'GET',
            url: this.URL.logout(),
            success: function (result) {
                alert("退出成功");
                window.location.reload();
            },
            error: function () {
                alert("退出异常");
            }
        });

    },
    addUser: function ($form) {
        var submitResult;
        $.ajax({
            cache: true,
            type: 'POST',
            url: this.URL.add(),
            data: $form.serialize(), // 要提交的表单,必须使用name属性
            async: false,
            success: function (result) {
                submitResult = result;
            }
        });
        return submitResult;
    }
};

var orderController = {
    URL: {
        create_order: function () {
            return BASE_URL + "/order/createOrder"
        },
        get_all_order: function () {
            return BASE_URL + "/manage/order/merchantOrderList"
        },
        delete_order: function (id) {
            return BASE_URL + "/manage/order/delete/" + id;
        },
        pay_order: function (orderNo) {
            return BASE_URL + "/order/pay/" + orderNo;
        },
        get_user_order: function () {
            return BASE_URL + "/order/userOrderList";
        },
        get_order_detail: function () {
            return BASE_URL + "/order/getOrder";
        },
        get_admin_order_detail: function () {
            return BASE_URL + "/manage/order/getOrder";
        }
    },
    getAdminOrderDetail: function (orderNo) {
        var orderResult;
        $.ajax({
            type: 'GET',
            async: false,
            data: {orderNo: orderNo},
            url: this.URL.get_admin_order_detail(),
            success: function (result) {
                orderResult = result;
            },
            error: function () {
                orderResult.status = 0;
                orderResult.msg = "获取详情失败";
            }
        });
        return orderResult;
    },
    getOrderDeatil: function (orderNo) {
        var orderResult;
        $.ajax({
            type: 'GET',
            async: false,
            data: {orderNo: orderNo},
            url: this.URL.get_order_detail(),
            success: function (result) {
                orderResult = result;
            },
            error: function () {
                orderResult.status = 0;
                orderResult.msg = "获取详情失败";
            }
        });
        return orderResult;
    },
    getUserOrder: function () {
        var orderResult;
        $.ajax({
            type: 'GET',
            async: false,
            url: this.URL.get_user_order(),
            success: function (result) {
                orderResult = result;
            },
            error: function () {
                orderResult.status = 0;
                orderResult.msg = "获取订单失败";
            }
        });
        return orderResult;
    },
    payOrder: function (orderNo) {
        $.ajax({
            type: 'GET',
            url: this.URL.pay_order(orderNo),
            success: function (result) {
                console.log(result);
                window.location.reload();
            },
            error: function () {
                alert("失败");
                window.location.reload();
            }
        });
    },
    deleteOrder: function (id) {
        console.log(this.URL.delete_order(id));
        $.ajax({
            type: 'DELETE',
            url: this.URL.delete_order(id),
            success: function (result) {
                if (result.status == 0) {
                    alert("删除成功");
                } else {
                    alert("删除失败");
                }
                window.location.reload();
            },
            error: function () {
                alert("删除失败");
                window.location.reload();
            }
        });
    },
    createOrder: function (merchantId, tableId, menuId, count) {
        var orderResult;
        $.ajax({
            type: 'POST',
            async: false,
            data: {merchantId: merchantId, tableId: tableId, menuId: menuId, count: count},
            url: this.URL.create_order(),
            success: function (result) {
                orderResult = result;
            }
        });
        return orderResult;
    },
    getAllOrder: function (merchantId) {
        var orderResult;
        $.ajax({
            type: 'GET',
            async: false,
            data: {merchantId: merchantId},
            url: this.URL.get_all_order(),
            success: function (result) {
                orderResult = result;
            },
            error: function () {
                console.log("订单获取失败")
            }
        });
        return orderResult;
    }
};

var fileUpload = {
    URL: {
        file_upload: function () {
            return BASE_URL + "/manage/menu/upload";
        }
    },
    upload: function (upload_file, callback) {
        $.ajax({
            type: "POST",
            data: upload_file,
            url: fileUpload.URL.file_upload(),
            cache: false,
            processData: false,
            contentType: false,
            success: function (result) {
                callback(result);
            },
            error: function (result) {
                console.log(result);
                alert("上传图片失败,可能是文件太大了~");
            }
        });
    }
};