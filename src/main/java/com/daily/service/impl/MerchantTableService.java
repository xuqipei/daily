package com.daily.service.impl;

import com.daily.common.*;
import com.daily.entity.DailyMerchantTable;
import com.daily.entity.DailyUser;
import com.daily.entity.TempTableStatus;
import com.daily.repository.MerchantTableRepository;
import com.daily.repository.OrderRepository;
import com.daily.repository.TempDableStatusRepository;
import com.daily.service.IMerchantTableService;
import com.daily.vo.MerchantTableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("iMerchantTableService")
public class MerchantTableService implements IMerchantTableService {
    @Autowired
    private MerchantTableRepository merchantTableRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TempDableStatusRepository tempDableStatusRepository;

    public ServerResponse getTableByMerchantId(Integer merchantId) {
        if (objectNullCheck(merchantId)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<DailyMerchantTable> merchantTableList = merchantTableRepository.findByMerchantId(merchantId);
        MerchantTableVo merchantTableVo = assembleMerchantTableVo(merchantTableList);

        return ServerResponse.createBySuccess(merchantTableVo);
    }

    @Transactional
    public ServerResponse useTable(DailyUser dailyUser, Integer merchantId, Integer tableId, Integer sitNum) {
        if (objectNullCheck(dailyUser, merchantId, tableId, sitNum)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        int count = orderRepository.countByUserIdAndStatus(dailyUser.getId(), Const.WAIT_BUYER_PAY);
        if (count > 0) {
            return ServerResponse.createByErrorMessage("选择餐桌失败,您有未付款的订单");
        }

        TempTableStatus tempTableStatus = tempDableStatusRepository.findByUsername(dailyUser.getUsername());

        if (tempTableStatus == null) {
            tempTableStatus = new TempTableStatus();
            tempTableStatus.setUsername(dailyUser.getUsername());
            tempTableStatus.setTableId(tableId);
            tempDableStatusRepository.save(tempTableStatus);
        } else if (tempTableStatus.getTableId() != tableId) {
            return ServerResponse.createByErrorMessage("失败,不能同时使用两张餐桌");
        }
        DailyMerchantTable merchantTable = merchantTableRepository.findByIdAndMerchantId(tableId, merchantId);

        //判断餐桌是否存在或使用人数是否超出限制
        ServerResponse serverResponse = updateNumCheck(merchantTable, sitNum);

        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }

        if (merchantTable.getBookStatus() == MerchantTableCode.USING.getCode()) {
            return ServerResponse.createByErrorMessage("该餐桌正在使用");
        }

        merchantTable.setBookStatus(MerchantTableCode.USING.getCode());
        merchantTable.setSitReal(sitNum);
        merchantTableRepository.saveAndFlush(merchantTable);

        return ServerResponse.createBySuccessMessage("欢迎,您的餐桌号为" + merchantTable.getTableNo());
    }

    @Transactional
    public ServerResponse addPeopleNum(DailyUser dailyUser, Integer merchantId, Integer tableId, Integer addNum) {
        if (objectNullCheck(dailyUser, merchantId, tableId, addNum)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        TempTableStatus tempTableStatus = tempDableStatusRepository.findByUsername(dailyUser.getUsername());

        if (tempTableStatus == null || tempTableStatus.getTableId() != tableId) {
            return ServerResponse.createByErrorMessage("失败了,不是你的锅不背,不是你的桌不加");
        }

        DailyMerchantTable merchantTable = merchantTableRepository.findByIdAndMerchantId(tableId, merchantId);
        ServerResponse serverResponse = updateNumCheck(merchantTable, merchantTable.getSitReal() + addNum);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }

        int newNum = merchantTable.getSitReal() + addNum;

        int affectCount = merchantTableRepository.updateSitRealById(merchantTable.getId(), newNum);

        if (affectCount > 0) {
            return ServerResponse.createBySuccessMessage("成功增加" + addNum + "人");
        } else {
            return ServerResponse.createBySuccessMessage("不知道什么原因,失败了～");
        }
    }

    private boolean objectNullCheck(Object... args) {
        for (Object o : args) {
            if (o == null) return true;
        }
        return false;
    }

    private ServerResponse updateNumCheck(DailyMerchantTable merchantTable, Integer num) {
        if (merchantTable == null) {
            return ServerResponse.createByErrorMessage("该餐桌不存在");
        }

        if (num > merchantTable.getCapacity()) {
            return ServerResponse.createByErrorMessage("人数超出餐桌容量");
        }
        return ServerResponse.createBySuccess(merchantTable);
    }

    public ServerResponse updateTable(DailyMerchantTable merchantTable) {
        if (objectNullCheck(merchantTable)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        DailyMerchantTable dailyMerchantTable = merchantTableRepository.findOne(merchantTable.getId());

        if (merchantTable.getBookStatus() != null) {
            dailyMerchantTable.setBookStatus(merchantTable.getBookStatus());
        }

        if (merchantTable.getCapacity() != null) {
            dailyMerchantTable.setCapacity(merchantTable.getCapacity());
        }

        if (merchantTable.getTableNo() != null) {
            dailyMerchantTable.setTableNo(merchantTable.getTableNo());
        }

        if (merchantTable.getSitReal() != null) {
            dailyMerchantTable.setSitReal(merchantTable.getSitReal());
        }

        try {
            merchantTableRepository.saveAndFlush(dailyMerchantTable);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("修改餐桌信息失败: " + e.getMessage());
        }

        return ServerResponse.createBySuccessMessage("修改餐桌信息成功");
    }


    public ServerResponse deleteTable(Integer tableId) {
        if (objectNullCheck(tableId)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            merchantTableRepository.delete(tableId);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("删除失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    public ServerResponse getCurrentTable(String username) {
        TempTableStatus tempTableStatus = tempDableStatusRepository.findByUsername(username);
        if (tempTableStatus == null) {
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess(tempTableStatus);
    }

    @Transactional
    public ServerResponse deleteCurrentTable(Integer tableId) {
        int affectRow = tempDableStatusRepository.deleteByTableId(tableId);
        return ServerResponse.createBySuccess(affectRow);
    }

    public ServerResponse addTable(Integer merchantId, String tableNo, Integer capacity) {
        if (objectNullCheck(merchantId, tableNo, capacity)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        DailyMerchantTable merchantTable = new DailyMerchantTable();
        merchantTable.setBookStatus(MerchantTableCode.FREE.getCode());
        merchantTable.setCapacity(capacity);
        merchantTable.setSitReal(0);
        merchantTable.setTableNo(tableNo);
        merchantTable.setMerchantId(merchantId);

        try {
            merchantTableRepository.saveAndFlush(merchantTable);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("添加失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("添加成功");
    }

    @Transactional
    public ServerResponse freeTable(Integer tableId) {
        ServerResponse serverResponse = updateTableStatus(tableId, MerchantTableCode.FREE.getCode(), "已空闲");
        deleteCurrentTable(tableId);
        if (serverResponse.isSuccess()) {
            merchantTableRepository.updateSitRealById(tableId, 0);
        }
        return serverResponse;
    }

    public boolean isArrived(Integer tableId) {
        DailyMerchantTable dailyMerchantTable = merchantTableRepository.findOne(tableId);
        return dailyMerchantTable.getBookStatus() == MerchantTableCode.USING.getCode();
    }

    @Transactional
    public ServerResponse unpayToRun() {
        return ServerResponse.createBySuccessMessage("跑路成功, 是不可能的");
    }

    @Transactional
    public ServerResponse unUseTable(DailyUser dailyUser, Integer merchantId, Integer tableId) {
        DailyMerchantTable merchantTable = merchantTableRepository.findOne(tableId);
        if (merchantTable.getMerchantId() != merchantId) {
            return ServerResponse.createByErrorMessage("你是海绵宝宝请来的派大星吧?");
        }

        int unPayCount = orderRepository.countByUserIdAndTableIdAndStatus(dailyUser.getId(), tableId, Const.WAIT_BUYER_PAY);

        if (unPayCount > 0) {
            return ServerResponse.createByErrorMessage("大兄弟, 这是想跑路呢?");
        }

        TempTableStatus tempTableStatus = tempDableStatusRepository.findByUsername(dailyUser.getUsername());

        if (tempTableStatus.getTableId() != tableId) {
            return ServerResponse.createByErrorMessage("这不是你的桌子啊～");
        }

        ServerResponse serverResponse = freeTable(tableId);

        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccessMessage("退桌成功");
        } else {
            return ServerResponse.createByErrorMessage("退桌失败");
        }
    }

    @Transactional
    public ServerResponse reserveTable(DailyUser dailyUser, Integer tableId) {
        DailyMerchantTable merchantTable = merchantTableRepository.findOne(tableId);

        if (merchantTable == null) {
            return ServerResponse.createByErrorMessage("餐桌不存在");
        }

        if (merchantTable.getBookStatus() != MerchantTableCode.FREE.getCode()) {
            return ServerResponse.createByErrorMessage("餐桌使用中");
        }

        TempTableStatus tempTableStatus = tempDableStatusRepository.findByUsername(dailyUser.getUsername());
        if (tempTableStatus != null) {
            return ServerResponse.createByErrorMessage("预订失败, 已经有一张你的桌子啦~");
        }
        tempTableStatus = new TempTableStatus();
        tempTableStatus.setUsername(dailyUser.getUsername());
        tempTableStatus.setTableId(tableId);
        tempDableStatusRepository.save(tempTableStatus);

        long holderSecond = 60;
        FreeTableTimer.startTimeTask(tableId, holderSecond);

        return updateTableStatus(tableId, MerchantTableCode.REVERSED.getCode(), "预订成功,将为你保留" + (holderSecond / 60) + "分钟" + (holderSecond % 60) + "秒");
    }

    @Transactional
    private ServerResponse updateTableStatus(Integer tableId, Integer status, String msg) {
        if (objectNullCheck(tableId, status)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int affectCount = merchantTableRepository.updateStatusById(tableId, status);
        if (affectCount > 0) {
            return ServerResponse.createBySuccessMessage(msg);
        }
        return ServerResponse.createByErrorMessage("餐桌不存在");
    }

    private MerchantTableVo assembleMerchantTableVo(List<DailyMerchantTable> merchantTableList) {
        Integer totalTableNums = merchantTableList.size();
        Integer freeTableNums = 0;

        for (DailyMerchantTable merchantTable : merchantTableList) {
            if (merchantTable.getBookStatus() == MerchantTableCode.FREE.getCode()) {
                freeTableNums++;
            }
        }

        MerchantTableVo merchantTableVo = new MerchantTableVo();
        merchantTableVo.setMerchantTableList(merchantTableList);
        merchantTableVo.setTotalTableNums(totalTableNums);
        merchantTableVo.setFreeTableNums(freeTableNums);

        return merchantTableVo;
    }
}
