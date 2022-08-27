package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public interface AddressService {

    void addNewAddress(Integer uid,String username, Address address);
}
