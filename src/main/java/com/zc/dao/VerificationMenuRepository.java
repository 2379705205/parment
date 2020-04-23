package com.zc.dao;

import com.zc.pojo.Menu;
import com.zc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 14:33
 */

public interface VerificationMenuRepository extends JpaRepository<Menu,String> {


}
