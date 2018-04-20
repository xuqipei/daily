package com.daily.service.impl;

import com.daily.common.BigDecimalUtil;
import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyMenu;
import com.daily.entity.DailyMerchantTable;
import com.daily.entity.DailyOrder;
import com.daily.entity.DailyOrderItem;
import com.daily.repository.MenuRepository;
import com.daily.repository.MerchantTableRepository;
import com.daily.repository.OrderItemRepository;
import com.daily.repository.OrderRepository;
import com.daily.service.IOrderService;
import com.daily.vo.OrderItemVo;
import com.daily.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by xuqipei on 17-7-19.
 */
@Service("iOrderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MerchantTableRepository merchantTableRepository;

    /**
     * 创建订单
     */
    @Transactional
    public ServerResponse createOrder(String menuId, String count, Integer merchantId, Integer userID, Integer tableId) {
        Map<Integer, Integer> menuMap = this.getMenus(menuId, count);
        DailyOrder dailyOrder = new DailyOrder();
        //创建订单号
        long orderNO = this.createOrderNO();
        dailyOrder.setOrderNo(orderNO);
        //获取座位号和就餐人数
        DailyMerchantTable dailyMerchantTable = merchantTableRepository.findOne(tableId);
        if (dailyMerchantTable.getMerchantId() != merchantId) {
            return ServerResponse.createByErrorMessage("食屎啦你");
        }
        dailyOrder.setTableId(tableId);
        dailyOrder.setTableNo(dailyMerchantTable.getTableNo());
        dailyOrder.setSitReal(dailyMerchantTable.getSitReal());
        //设置用户
        dailyOrder.setUserId(userID);
        //设置商家
        dailyOrder.setMerchantId(merchantId);
        //设置默认支付状态,支付时间,订单时间
        /**支付状态 0-已取消，10-未付款，50-交易成功*/
        dailyOrder.setStatus(Const.WAIT_BUYER_PAY);
        dailyOrder.setPayTime(null);
        dailyOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //创建订单详情
        this.createOrderItem(menuMap, userID, orderNO);
        //计算订单总价
        List dailyItemList = this.getOrderItemList(orderNO);
        dailyOrder.setTotalPrice(this.getTotalPrice(dailyItemList));
        orderRepository.saveAndFlush(dailyOrder);
        return this.getOrderVo(dailyOrder.getOrderNo());
    }

    /**
     * 获取菜品列表
     */
    private Map<Integer, Integer> getMenus(String menuId, String count) {
        Map<Integer, Integer> menus;
        String[] menuIdList = menuId.split(",");
        String[] CountList = count.split(",");
        menus = new HashMap<>();
        for (int i = 0; i < menuIdList.length; i++) {
            menus.put(Integer.valueOf(menuIdList[i]), Integer.valueOf(CountList[i]));
        }
        return menus;
    }


    /**
     * 获取用户所有订单
     */
    public ServerResponse getUserOrderList(Integer user_Id) {
        List<DailyOrder> orders = orderRepository.getAllByUserId(user_Id);
        return ServerResponse.createBySuccess(orders);
    }


    /**
     * 获取商家所有订单
     */
    public ServerResponse getMerchantOrderList(Integer merchanr_id) {
        List<DailyOrder> orders = orderRepository.getAllByMerchantId(merchanr_id);
        return ServerResponse.createBySuccess(orders);
    }

    public ServerResponse deleteOrderById(Integer orderId) {
        orderRepository.delete(orderId);
        return ServerResponse.createBySuccessMessage("删除成功!");
    }

    /**
     * 获取订单视图
     *
     * @return
     */
    public ServerResponse getOrderVo(long order_No) {
        DailyOrder dailyOrder = orderRepository.getByOrderNo(order_No);
        if (dailyOrder == null) {
            return null;
        }
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(dailyOrder.getOrderNo());//订单号
        orderVo.setTableNo(dailyOrder.getTableNo());//座位号
        orderVo.setSitReal(dailyOrder.getSitReal());//就餐人数
        orderVo.setOrderItemVo(this.getOrderItemVo(order_No));//订单详情
        orderVo.setTotalPrice(dailyOrder.getTotalPrice());//总价钱


        /**支付状态 0-已取消，10-未付款，50-交易成功*/
        switch (dailyOrder.getStatus()) {
            case Const.REFUND:
                orderVo.setStatus("已取消");
                break;
            case Const.WAIT_BUYER_PAY:
                orderVo.setStatus("未付款");
                break;
            case Const.TRADE_SUCCESS:
                orderVo.setStatus("交易成功");
                break;

        }
        orderVo.setCreateTime(dailyOrder.getCreateTime().toString());//订单创建时间
        Timestamp payTime = dailyOrder.getPayTime();
        if (payTime != null) {
            orderVo.setPayTime(payTime.toString());//支付时间

        }

        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 获取订单详情列表
     *
     * @param order_no
     * @return
     */

    public List<OrderItemVo> getOrderItemVo(long order_no) {
        List<OrderItemVo> orderItemVoList = new ArrayList<>();

        List<DailyOrderItem> dailyOrderItemList = orderItemRepository.findAllByOrderNo(order_no);
        if (dailyOrderItemList != null) {
            for (DailyOrderItem dailyOrderItem : dailyOrderItemList) {
                OrderItemVo orderItemVo = new OrderItemVo();
                orderItemVo.setMenuId(dailyOrderItem.getMenuId());
                orderItemVo.setMenuName(dailyOrderItem.getMenuName());
                orderItemVo.setPrice(dailyOrderItem.getUnitPrice());
                orderItemVo.setTotalPrice(dailyOrderItem.getTotalPrice());
                orderItemVoList.add(orderItemVo);
            }
        }

        return orderItemVoList;
    }


    //创建订单详情
    public List<DailyOrderItem> createOrderItem(Map menuMap, Integer userID, long orderNO) {

        List<DailyOrderItem> dailyOrderItemList = new ArrayList<>();
        int itemId = (int) orderItemRepository.count() + 1;
        Iterator iterator = menuMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            DailyMenu dailyMenu = menuRepository.findOne((Integer) entry.getKey());//获取菜品
            Integer count = (Integer) entry.getValue();//获取数量

            if (dailyMenu == null) {
                return dailyOrderItemList;
            }


            DailyOrderItem dailyOrderItem = new DailyOrderItem();

            BigDecimal bigDecimal = BigDecimalUtil.mul(count.doubleValue(), dailyMenu.getPrice().doubleValue());


            dailyOrderItem.setId(itemId);
            dailyOrderItem.setMenuId(dailyMenu.getId());//menu_id
            dailyOrderItem.setMenuName(dailyMenu.getName());//menu_name
            dailyOrderItem.setQuantity(count);//数量
            dailyOrderItem.setUnitPrice(dailyMenu.getPrice());//单价
            dailyOrderItem.setTotalPrice(bigDecimal);//合计
            dailyOrderItem.setOrderNo(orderNO);//订单号
            dailyOrderItem.setUserId(userID);//用户
            dailyOrderItemList.add(dailyOrderItem);
            itemId++;
        }
        orderItemRepository.save(dailyOrderItemList);
        return dailyOrderItemList;

    }


    //获取订单详情列表
    public List<DailyOrderItem> getOrderItemList(long orderNO) {

        return orderItemRepository.findAllByOrderNo(orderNO);
    }

    //创建生成订单号
    public long createOrderNO() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(10);
    }


    //计算获取订单总价
    public BigDecimal getTotalPrice(List<DailyOrderItem> orderItemList) {
        BigDecimal total = new BigDecimal("0");
        for (DailyOrderItem dailyOrderItem : orderItemList) {
            total = BigDecimalUtil.add(total.doubleValue(), dailyOrderItem.getTotalPrice().doubleValue());
        }
        return total;
    }




}
