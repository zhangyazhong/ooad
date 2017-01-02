package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.model.Spare;
import com.github.zhangyazhong.model.SpareRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public interface ISpareService {
    /**
     * 获取备件
     *
     * @return 备件实体
     */
    Spare findSpare(int id);
    
    /**
     * 获取所有备件
     *
     * @return 备件列表
     */
    List<Spare> getSpare();
    
    /**
     * 获取某备件的当前状态
     *
     * @param id 备件id
     * @return 备件状态
     */
    SpareRecord getSpareStatus(int id);
    
    /**
     * 获取某备件的历史记录
     *
     * @param id 备件id
     * @return 备件历史记录
     */
    List<SpareRecord> getSpareHistory(int id);
    
    /**
     * 购入备件
     *
     * @param spare 备件实体
     * @return 购入是否成功
     */
    Boolean buySpare(Spare spare);
    
    /**
     * 安装备件
     *
     * @param spare 备件实体
     * @param assets 资产实体
     * @return 安装是否成功
     */
    Boolean installSpare(Spare spare, Assets assets);
    
    /**
     * 归还备件
     *
     * @param spare 备件实体
     * @return 归还是否成功
     */
    Boolean returnSpare(Spare spare);
    
    /**
     * 报废备件
     *
     * @param spare 备件实体
     * @return 报废是否成功
     */
    Boolean discardSpare(Spare spare);
}
