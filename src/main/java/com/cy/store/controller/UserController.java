package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    /* @RequestMapping("reg")
       public JsonResult<Void> reg(User user){
           //创建响应结果对象
           JsonResult<Void> result=new JsonResult<>();
           try {
               userService.reg(user);
               result.setState(200);
               result.setMessage("用户名注册成功");
           } catch (UsernameDuplicatedException e) {
               result.setState(4000);
               result.setMessage("用户名被占用");
           }catch (InsertException e) {
               result.setState(5000);
               result.setMessage("注册是产生未知的异常");
           }
           return result;
       }*/
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
       userService.reg(user);
        return new JsonResult<>(OK);
    }


    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = userService.login(username, password);
        //向session对象中完成数据的绑定
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        //获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFormSession(session));
        return new JsonResult<User>(OK,user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid=getuidFromSession(session);
        String username=getUsernameFormSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data=userService.getByUid(getuidFromSession(session));

        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){

         Integer uid=getuidFromSession(session);
         String username=getUsernameFormSession(session);
         userService.changeInfo(uid,username,user);
         return new JsonResult<>(OK);
    }
    /**设置文件上传的最大值*/
    public static final int  AVATAR_MAX_SIZE=10*1024*1024;

    /**限制文件上传的类型*/
    public static final List<String> AVATAR_TYPE=new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }


    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("类型不支持");
        }
        String parent = session.getServletContext().getRealPath("upload");
//        String parent="C:/Users/xiaos/Desktop/upload";
        //File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {//检测目录是否存在
            dir.mkdirs();//创建当前目录
        }

        System.out.println(parent);
        //获取到这个文件名称，UUID工具来将生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);//是一个空文件
        //参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getuidFromSession(session);
        String username = getUsernameFormSession(session);
        //返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK, avatar);
    }
}
