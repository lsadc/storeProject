package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

//控制层类的基类
public class BaseController {
    //操作成功的状态码
    public static final int OK=200;

    //请求处理方法，这个方法的返回值是需要传递给全端的数据
    @ExceptionHandler({ServiceException.class,FileUploadException.class}) //处理统一抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result=new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用的异常");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        }else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户名的密码错误的异常");
        }else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限的异常");
        }else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        }else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生的未知的异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    protected final Integer getuidFromSession(HttpSession session){
       return Integer.valueOf(session.getAttribute("uid").toString()) ;
    }

    protected final String getUsernameFormSession(HttpSession session){
       return session.getAttribute("username").toString();
    }

}
