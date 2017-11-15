package com.dockerx.admin.service;

import com.dockerx.admin.model.Admin;
import org.springframework.stereotype.Service;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 21:23.
 */
@Service
public class AdminService {
    public String adminLevel(Admin admin) {
        switch (admin.getLevel()) {
            case 0:
                return "管理员";
            case 1:
                return "副管理";
            case 2:
                return "底层管理";
            default:
                return "谁让你进来的？";
        }
    }
}
