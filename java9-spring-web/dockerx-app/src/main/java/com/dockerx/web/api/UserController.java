package com.dockerx.web.api;


import com.dockerx.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 20:14.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String id){
        System.out.println(String.format("从request请求中通过id获取user",id));
        return new User(id,"DockerX","Refresh your java skills");
    }
}
