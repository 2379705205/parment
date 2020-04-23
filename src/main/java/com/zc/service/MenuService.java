package com.zc.service;

import com.zc.common.ResponseResultList;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 14:43
 */
public interface MenuService {
    /**
     * 根据用户id查询当前菜单
     * @param userId
     * @return
     */
    ResponseResultList findByUserId(String userId);

}
