package com.scut.library.Controller;

import com.scut.library.Service.AdminService;
import com.scut.library.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//管理员用户前端接口
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService ac;

    /*
    //登录
    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody RequestLogin rl){
        return ac.login(rl.getUser_id(),rl.getPassword());
    }

    //登出
    @GetMapping("/logout")
    @ResponseBody
    public Response logout(){
        return ac.logout();
    }

     */

    //上架书籍
    @PostMapping ("/insertBook")
    @ResponseBody
    public Response insertBook(@RequestBody RequestInsertBook rib){
        return ac.insertBook(rib.getId(),rib.getName(),rib.getPrice());
    }

    //下架书籍
    @PostMapping("/deleteBook")
    @ResponseBody
    public Response deleteBook(@RequestBody RequestID rbid){
        return ac.deleteBook(rbid.getId());
    }

    //获取某个用户的借书信息
    @PostMapping("/getUserBorrowedBooks")
    @ResponseBody
    public Response getUserBorrowedBooks(@RequestBody RequestID ruid){
        return ac.getUserBorrowedBooks(ruid.getId());
    }

    //获取所有书的信息
    @GetMapping("/getAllBooks")
    @ResponseBody
    public Response getAllBooks(){
        return ac.getAllBooks();
    }

    //获取所有借出的书籍的信息
    @GetMapping("/getBorrowedBooks")
    @ResponseBody
    public Response getBorrowedBooks(){
        return ac.getBorrowedBooks();
    }

    //添加用户
    @PostMapping("/insertUser")
    @ResponseBody
    public Response insertUser(@RequestBody RequestInsertUser riu){
        return ac.insertUser(riu.getId(),riu.getName());
    }

    //删除用户
    @PostMapping("/deleteUser")
    @ResponseBody
    public Response deleteUser(@RequestBody RequestID ruid){
        return ac.deleteUser(ruid.getId());
    }

    //清除某个用户的罚款
    @PostMapping("/clearFine")
    @ResponseBody
    public Response clearFine(@RequestBody RequestID ruid){
        return ac.clearFine(ruid.getId());
    }

    //获得有罚款的用户列表
    @GetMapping("/getFinedUsers")
    @ResponseBody
    public Response getFinedUsers(){
        return ac.getFinedUsers();
    }

    //获得所有用户
    @GetMapping("/getAllUsers")
    @ResponseBody
    public Response getAllUsers(){
        return ac.getAllUsers();
    }
}


