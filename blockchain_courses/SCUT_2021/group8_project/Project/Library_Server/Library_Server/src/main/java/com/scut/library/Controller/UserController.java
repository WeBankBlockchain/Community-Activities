package com.scut.library.Controller;

import com.scut.library.Service.UserService;
import com.scut.library.utils.RequestBookID;
import com.scut.library.utils.RequestChangePassword;
import com.scut.library.utils.RequestLogin;
import com.scut.library.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//普通用户前端接口
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService us;

    //登录
    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody RequestLogin rl){
        return us.login(rl.getUser_id(), rl.getPassword());
    }

    //登出
    @GetMapping("/logout")
    @ResponseBody
    public Response logout(){
        return us.logout();
    }

    //获取已借书籍
    @GetMapping("/getBooks")
    @ResponseBody
    public Response getBooks(){
        return us.getBooks();
    }

    //借书
    @PostMapping("/borrowBook")
    @ResponseBody
    public Response borrowBook(@RequestBody RequestBookID rbid){
        return us.borrowBook(rbid.getBook_id());
    }

    //还书
    @PostMapping("/returnBook")
    @ResponseBody
    public Response returnBook(@RequestBody RequestBookID rbid){
        return us.returnBook(rbid.getBook_id());
    }

    //改密码
    @PostMapping("/changePassword")
    @ResponseBody
    public Response changePassword(@RequestBody RequestChangePassword rcp){
        return us.changePassword(rcp.getUser_id(),rcp.getOld_password(), rcp.getNew_password());
    }
}


