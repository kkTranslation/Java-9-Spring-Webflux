package com.dockerx.web.api;

import com.dockerx.admin.model.Admin;
import com.dockerx.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 21:33.
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("{id}")
    public String adminLevel(@PathVariable("id") int id){
        Admin admin = new Admin(id);
        return adminService.adminLevel(admin);
    }

}
