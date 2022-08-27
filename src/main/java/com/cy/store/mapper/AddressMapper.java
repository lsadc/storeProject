package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {

    Integer insert(Address address);

    Integer countByUid(Integer uid);
}
