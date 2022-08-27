package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void insert(){
        User user=new User();
        user.setUsername("zhanSan");
        user.setPassword("12313");
        Integer result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void  findByUsername(){
        User user= userMapper.findByUsername("zhanSan");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(6,"123","vv",new Date());
    }

    @Test
    public void findByUid(){
        User user = userMapper.findByUid(5);
        System.out.println(user);
    }

    @Test
    public void updateInfoByUid(){

        User user=new User();
        user.setUid(14);
        user.setPhone("13238421");
        user.setEmail("10840@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(1 ,"/update/touiang","管理员",new Date());
    }

}
