package com.zc.dao;

import com.zc.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 14:33
 */

public interface VerificationPaymentRepository extends JpaRepository<Payment,String> {


}
