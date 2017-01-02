package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.model.AssetsRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public interface IAssetsService {
    /**
     * 根据id查找资产
     *
     * @return 资产实体
     */
    Assets findAssets(int id);
    
    /**
     * 获取所有资产
     *
     * @return 资产列表
     */
    List<Assets> getAssets();
    
    /**
     * 获取某员工领用的所有资产
     *
     * @param phone 员工手机号
     * @return 资产列表
     */
    List<Assets> getAssetsByEmployeeReceive(String phone);
    
    /**
     * 获取某资产的当前状态
     *
     * @param id 资产id
     * @return 资产状态
     */
    AssetsRecord getAssetsStatus(int id);
    
    /**
     * 购入资产
     *
     * @param assets 资产实体
     * @return 购入是否成功
     */
    Boolean buyAssets(Assets assets);
    
    /**
     * 领用资产
     *
     * @param assets 资产实体
     * @return 领用是否成功
     */
    Boolean receiveAssets(Assets assets);
    
    /**
     * 归还资产
     *
     * @param assets 资产实体
     * @return 归还是否成功
     */
    Boolean returnAssets(Assets assets);
    
    /**
     * 报废资产
     *
     * @param assets 资产实体
     * @return 报废是否成功
     */
    Boolean discardAssets(Assets assets);
}
