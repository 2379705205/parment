package com.zc.service.Impl;

import com.zc.common.ResponseResultList;
import com.zc.mapper.MenuMapper;
import com.zc.pojo.Menu;
import com.zc.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 14:44
 */
@Service
public class MenuServiceImpl implements MenuService {
    Logger log = Logger.getLogger(MenuServiceImpl.class);


    @Autowired
    MenuMapper menuMapper;

    public ResponseResultList findByUserId(String userId) {
        List<Menu> menuList = menuMapper.findByUserId(userId);
        log.info("查询菜单信息->>"+menuList);
        if (menuList.size()==0) {
            return new ResponseResultList(false,100,"当前用户没有菜单",menuList);
        }
        return new ResponseResultList(true,200,"查询成功",menuList);
    }
}
