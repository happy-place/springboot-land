package com.bigdata.boot.chapter16.service;

import com.bigdata.boot.chapter16.model.Customer;
import java.util.List;

/**
 * @ClassName CustomerService
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/23 11:37
 * @Version 1.0
 **/

public interface CustomerService {
    List<Customer> nameLike(String name);
    Customer findById(long id);
    void deleteById(long id);
    Customer saveCustomer(Customer customer);
}
