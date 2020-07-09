package com.bigdata.boot.chapter16.service.impl;

import com.bigdata.boot.chapter16.model.Customer;
import com.bigdata.boot.chapter16.repo.CustomerRepository;
import com.bigdata.boot.chapter16.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CustomerServiceImpl
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/23 11:37
 * @Version 1.0
 **/

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> nameLike(String name) {
        return customerRepository.nameLike(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Customer saveCustomer(Customer customer) {
        Long id = customerRepository.save(customer).getId();
        return customerRepository.findById(id).get();
    }


}
