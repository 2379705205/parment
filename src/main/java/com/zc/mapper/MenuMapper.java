package com.zc.mapper;

import com.zc.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 16:34
 */
@Mapper
public interface MenuMapper {

    List<Menu> findByUserId(String id);


}
