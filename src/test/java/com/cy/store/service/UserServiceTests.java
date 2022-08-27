package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void reg(){
        try {
            User user=new User();
            user.setUsername("abcd");
            user.setPassword("1234");
            userService.reg(user);
            System.out.println("Ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("test01", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(14,"abc","1234","123456");
    }

    @Test
    public void getByUid(){
        System.err.println(userService.getByUid(14));
    }

    @Test
    public void changeInfo(){
        User user=new User();
        user.setPhone("00000");
        user.setPassword("123");
        user.setEmail("jafoao@qq.com");
        user.setGender(0);
        userService.changeInfo(13,"管理员",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(4,"/woshizhangsan","张三");
    }
}
