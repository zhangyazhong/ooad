package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public interface IAssetsService {
    /**
     * 获取所有资产
     *
     * @return 资产列表
     */
    List<Assets> getAssets();
    
    List<Assets> getAssetsByEmployeeReceive(String phone);
}
