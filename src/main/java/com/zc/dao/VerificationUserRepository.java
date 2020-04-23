package com.zc.dao;

import com.zc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 14:33
 */

public interface VerificationUserRepository extends JpaRepository<User,String> {

     User findByUserNameAndUserPassword(String userName, String userPassword);

}
