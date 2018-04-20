# 餐饮平台后端接口

## 用户端

 - 已完成
    - 获取指定商家的所有餐桌
        - URL:
            - http://localhost:8080/table/get_all/{merchantId}
        - METHOD:
            - GET
        - RETURN    
            {
            
              "status": 0,
              "data": {
                "merchantTableList": [
                  {
                    "id": 1,
                    "tableNo": "一号桌",
                    "merchantId": 1,
                    "bookStatus": 0,
                    "sitReal": 0,
                    "capacity": 4
                  },
                  {
                    "id": 2,
                    "tableNo": "二号桌",
                    "merchantId": 1,
                    "bookStatus": 0,
                    "sitReal": 3,
                    "capacity": 10
                  }
                ],
                "freeTableNums": 3,
                "totalTableNums": 3
              }
            }
    - 使用该餐桌
        - URL:
            - http://localhost:8080/table/use/{merchantId}/{tableId}/{sitNum}
        - METHOD:
            - PUT
        - RETURN
        
        {
        
          "status": 0,
          "msg": "成功选择餐桌"
        }
    - 添加用户
        - URL:
            - http://localhost:8080/user/addUser
        - METHOD:
            - POST 
        - PARAM: 
         
            - username(用户名)
            - password(密码)
            - mobilephone(手机)
            - nickname(昵称)
            - age(年龄)
            - gender(性别)                
        - RETURN    
             {
             
               "status": 0,
               "data": {
                 "id": 0,
                 "username": "培培",
                 "password": "8888",
                 "mobilephone": "13676089509",
                 "nickname": "老培培",
                 "age": 21,
                 "brithday": 1500881245790,
                 "gender": "男",
                 "createTime": 1500881245790
               }
             }
    - 获取用户（用户名）  
        - URL: 
            - http://localhost:8080/user/findByUsername       
        - METHOD:         
            -  POST 
        - PARAM: 
            - username(用户名)
        - RETURN
            
            {
            
              "status": 0,
              "data": {
                "id": 1,
                "username": "培培",
                "mobilephone": "13676089509",
                "nickname": "老培培",
                "gender": "男",
                "age": 21,
                "brithday": "2017-07-24"
              }
            }
    - 获取用户（用户Id）
        - URL: 
            - http://localhost:8080/user//findOne/{userId}   
        - METHOD:         
            -  POST 
        - PARAM: 
            - userId(用户Id)
        - RETURN 
            
            {
            
              "status": 0,
              "data": {
                "id": 3,
                "username": "大培培",
                "mobilephone": "13676089509",
                "nickname": "大培培",
                "gender": "男",
                "age": 21,
                "brithday": "2017-07-24"
              }
            }
    - 获取用户列表
        - URL:
            - http://localhost:8080/user/userList
        - METHOD:
            - POST
        - RETURN
        
            {
            
              "status": 0,
              "data": [
                {
                  "id": 1,
                  "username": "培培",
                  "mobilephone": "13676089509",
                  "nickname": "培培",
                  "gender": "男",
                  "age": 21,
                  "brithday": "2017-07-24"
                },
                {
                  "id": 3,
                  "username": "培培",
                  "mobilephone": "13676089509",
                  "nickname": "培培",
                  "gender": "男",
                  "age": 21,
                  "brithday": "2017-07-24"
                }
              ]
            }
    - 删除用户
        - URL:
            - http://localhost:8080/user//deleteUser/{userId}
        - METHOD:
            - DELETE
        - PARAM: 
            - userId(用户ID)
        - RETURN
            
            {
            
              "status": 0,
              "data": "成功删除！"
            }
    - 登录用户
        - URL:
            - http://localhost:8080/user/login
        - METHOD:
            - POST
        - PARAM: 
            - username(用户名)
            - password(密码)
        - RETURN
        
            (用户名和密码不匹配)
            
            {
            
              "status": 1,
              "msg": "密码或用户名错误！"
            }
            
            （登录成功）
            
            {
            
              "status": 0,
              "data": {
                "id": 1,
                "username": "培培",
                "password": "8888",
                "mobilephone": "13676089509",
                "nickname": "培培",
                "age": 21,
                "brithday": 1500881246000,
                "gender": "男",
                "createTime": 1500881246000
              }
            }
    - 获取所有菜系(Integer merchantId)
        - URL
            - http://localhost:8080/category/get_all/{merchantId}
        - METHOD
            - GET
        - RETURN   
            {
            
              "status": 0,
              "data": [
                {
                  "id": 1,
                  "name": "粤菜",
                  "merchantId": 1
                },
                {
                  "id": 2,
                  "name": "川菜",
                  "merchantId": 1
                }
              ]
            }
            
    - 获取所有菜品(Integer categoryId)
        - URL
            - http://localhost:8080/menu/get_all/{categoryId}
        - METHOD
            - GET
        - RETURN
            {  
            
                 "status": 0,
                 "data": [
                   {
                     "id": 6,
                     "name": "尖椒炒牛肉",
                     "bannerUrl": "#",
                     "categoryId": 2,
                     "option": "未点",
                     "price": 39,
                     "describe": "尖椒炒牛肉是由尖椒，牛肉等制作的食物，工艺是炒。",
                     "discount": 70
                   }
                 ]
               }

 - 订单模块
     - 创建新订单
         - URL
             - http://localhost:8080/order/createOrder
         - METHOD
             - POST
         - PARAM
             - merchantId(商家ID)
             - tableId(餐桌ID)
             - menuId（菜品ID）
             - count（菜品数量）
         - RETURN
         
            {
            
                  "status": 0,
                  
                  "data": {
                  
                    "orderNo": 1500881951572,
                    "tableNo": "二号桌",
                    "sitReal": 3,
                    "orderItemVo": [
                      {
                        "menuId": 1,
                        "menuName": "清蒸鱼",
                        "price": 22,
                        "totalPrice": 66
                      }
                    ],
                    "totalPrice": 66,
                    "payTime": null,
                    "status": "未付款",
                    "createTime": "2017-07-24 15:39:12.0"
                  }
              
            }
     - 查询订单
         - URL
             - http://localhost:8080/order/getOrder
         - METHOD
             - POST
         - PARAM
             - order_no(订单号)
         - RETURN 
             
             {
             
               "status": 0,
               "data": {
                 "orderNo": 1500881951572,
                 "tableNo": "二号桌",
                 "sitReal": 3,
                 "orderItemVo": [
                   {
                     "menuId": 1,
                     "menuName": "清蒸鱼",
                     "price": 22,
                     "totalPrice": 66
                   }
                 ],
                 "totalPrice": 66,
                 "payTime": null,
                 "status": "未付款",
                 "createTime": "2017-07-24 15:39:12.0"
               }
             }
## 商家端
 - 已完成
    - 新增餐桌
        - URL:
            - http://localhost:8080/manage/table/add/{merchantId}
        - METHOD:
            - POST
        - PARAM: 
            - tableNo(餐桌名)
            - capacity(餐桌可坐人数)
        - RETURN
        
            {
            
              "status": 0,
              "msg": "添加成功"
            }
    - 删除餐桌
        - URL:
            - http://localhost:8080/manage/table/delete/{tableId}
        - METHOD:
            - DELETE
        - RETURN
        
            {
            
              "status": 0,
              "msg": "删除成功"
            }
    - 获取指定商家的所有餐桌
        - URL:
            - http://localhost:8080/manage/table/get_all/{merchantId}
        - METHOD:
            - GET
        - RETURN
        
            {
            
              "status": 0,
              
              "data": {
              
                "merchantTableList": [
                
                  {
                    "id": 1,
                    "tableNo": "一号桌",
                    "merchantId": 1,
                    "bookStatus": 0,
                    "sitReal": 0,
                    "capacity": 4
                  },
                  {
                    "id": 2,
                    "tableNo": "二号桌",
                    "merchantId": 1,
                    "bookStatus": 0,
                    "sitReal": 0,
                    "capacity": 10
                  },
                  {
                    "id": 3,
                    "tableNo": "三号桌",
                    "merchantId": 1,
                    "bookStatus": 0,
                    "sitReal": 0,
                    "capacity": 8
                  }
                ],
                
                "freeTableNums": 3,
                
                "totalTableNums": 3
                
              }
              
            }
    - 修改餐桌基本信息
        - URL:
            - http://localhost:8080/manage/table/update/{tableId}
        - METHOD:
            - PUT
        - PARAM: 
            - MerchantTable(MerchantTable中的成员变量名作为参数名)
        - RETURN
        
           {      
           
                 "status": 0,            
                 "msg": "修改餐桌信息成功"
             
           }
    - 修改餐桌状态为空闲
        - URL:
            - http://localhost:8080/manage/table/free/{tableId}
        - METHOD:
            - PUT
        - RETURN
        
            {
            
              "status": 0,
              "msg": "已空闲"
            }    
    - 修改餐桌状态为已预订
        - URL:
            - http://localhost:8080/manage/table/reserve/{tableId}
        - METHOD:
            - PUT
    - 修改餐桌状态为使用中
        - URL:
            - http://localhost:8080/manage/table/use/{merchantId}/{tableId}/{sitNum}
        - METHOD:
            - PUT

    - 获取单个菜系(Iteger id)
         - URL:
             - http://localhost:8080/manage/category/get_one/{id}
         - METHOD:
             - GET
         - RETURN: 
             {
             
               "status": 0,
               "data": {
                 "id": 1,
                 "name": "粤菜",
                 "merchantId": 1
               }
             }
    - 获取商家全部菜系(Integer merchantId)  
         - URL:
             - http://localhost:8080/manage/category/get_all/{merchantId}
         - METHOD:
             - GET
        - RETURN
            {
            
                 "status": 0,
                 "data": [
                   {
                     "id": 6,
                     "name": "尖椒炒牛肉",
                     "bannerUrl": "#",
                     "categoryId": 2,
                     "option": "未点",
                     "price": 39,
                     "describe": "尖椒炒牛肉是由尖椒，牛肉等制作的食物，工艺是炒。",
                     "discount": 70
                   }
                 ]
                 
             }
    - 新增菜系   
         - URL:
                - http://localhost:8080/manage/category/add
         - METHOD:
                - POST
         - PARAM: 
                - DailyCategory(dailyCategory)
         - RETURN  
            {
            
               "status": 0,
              
               "msg": "添加成功"
              
            }                
    - 修改菜系(Iteger id)     
         - URL:
            - http://localhost:8080/manage/category/update/{id}
         - METHOD:
            - PUT
         - PARAM: 
            - dailyCategory(DailyCategory)
         - RETURN  
            {
            
               "status": 0,
               
               "msg": "修改菜系信息成功"
              
            }
    - 删除菜系(Iteger id)    
         - URL:
             - http://localhost:8080/manage/category/delete/{id}
         - METHOD:
             - DELETE
         - RETURN  
            {
            
               "status": 0,
              
               "msg": "删除成功"
              
            }
    - 获取单个菜品(Integer id)
         - URL:
            - http://localhost:8080/manage/menu/get_one/{id}
         - METHOD:
             - GET
    - 获取所有菜品(Integer categoryId)
         - URL:
              - http://localhost:8080/manage/menu/get_all/{categoryId}
         - METHOD:
              - GET
    - 增加菜品
         - URL:
              - http://localhost:8080/manage/menu/add
         - METHOD:
              - POST
          - PARAM: 
               - DailyMenu(dailyMenu)             
    - 修改菜品根据(Integer id)
         - URL:
              - http://localhost:8080/manage/menu/update/{id}
         - METHOD:
              - PUT
         - PARAM: 
              - DailyMenu(dailyMenu)
    - 上传图片
         - URL:
              - http://localhost:8080/manage/menu/upload
         - METHOD:
              - POST   
         - PARAM: 
              - MultipartFile(multipartFile)        
        - RETURN
        
            {
            
              "status": 0,
              "msg": "http://ac-ud7op3rB.clouddn.com/2O0Nw6thOVQ6dSLxdwji2QS1sXzjjcD4nZbJmvdN.png"
            
            }                   
    - 删除菜品根据菜品ID
    
         - URL:
              - http://localhost:8080/manage/menu/delete/{id}
         - METHOD:
              - DELETE

