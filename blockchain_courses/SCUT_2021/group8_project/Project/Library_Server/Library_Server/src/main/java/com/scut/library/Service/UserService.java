package com.scut.library.Service;

import com.scut.library.Entity.Book;
import com.scut.library.Entity.User;
import com.scut.library.fisco;
import com.scut.library.utils.Response;
import com.scut.library.utils.ResponseFactory;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserService {
    static final long period= 60L *24*60*60*1000;
    static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd EEE");
    User u;
    Boolean isLogin=false;

    public Response getBooks(){
        ResponseFactory rf=new ResponseFactory();
        if(!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(1,load_books());
    }

    public Boolean isLogin() {
        return isLogin;
    }

    private List<Book> load_books(){
        List<Book>books= new ArrayList<>();

        //测试用
        //books.add(new Book("1111","book 1","1","1234", sdf.format(new Date(0)),sdf.format(new Date(period)),"1"));


        List<Tuple6<BigInteger,byte[], BigInteger, BigInteger, BigInteger, BigInteger>>bs=fisco.client.getBooks(new BigInteger(u.getId()));
        for (Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>t:bs){
            books.add(new Book(t.getValue1().toString(),
                    new String(t.getValue2()),
                    t.getValue3().toString(),
                    t.getValue4().toString(),
                    sdf.format(new Date(t.getValue5().longValue())),
                    sdf.format(new Date(t.getValue5().longValue()+period)),
                    String.valueOf(t.getValue6())));
        }


        return books;
    }

    public Response login(String user_id,String password){
        ResponseFactory rf=new ResponseFactory();
        rf.put(0,"用户不存在！");
        rf.put(-1,"密码错误！");
        //rf.put(-2,"Already login.");
        rf.put(-3,"内部错误！");
        int result=fisco.client.userLogin(new BigInteger(user_id),password);

        //测试用
        //int result=test_login(new BigInteger(user_id),password.getBytes(StandardCharsets.UTF_8));

        if(result==1){
            u=new User(user_id);
            isLogin=true;
        }
        return rf.getResponse(result,null);
    }

    /*
    //测试用
    private int test_login(BigInteger user_id,byte[] password){
        if(user_id.equals(new BigInteger("1234"))&&new String(password).equals("1234")){
            return 1;
        }
        return -1;
    }

     */

    public Response borrowBook(String book_id){
        ResponseFactory rf=new ResponseFactory();
        rf.put(0,"未缴付罚款！");
        rf.put(-1,"用户不存在！");
        rf.put(-2,"书籍不存在！");
        rf.put(-3,"书籍已借出！");
        rf.put(-4,"未知错误！");
        rf.put(-5,"内部错误！");
        if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.borrowBook(new BigInteger(u.getId()),new BigInteger(book_id)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response returnBook(String book_id){
        ResponseFactory rf=new ResponseFactory();
        rf.put(0,"归还超时！");
        rf.put(-1,"用户不存在！");
        rf.put(-2,"书籍不存在！");
        rf.put(-3,"书籍已归还！");
        rf.put(-4,"未知错误！");
        rf.put(-5,"内部错误！");
        if(!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.returnBook(new BigInteger(u.getId()),new BigInteger(book_id)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response changePassword(String user_id,String old_password,String new_password){
        ResponseFactory rf=new ResponseFactory();
        rf.put(0,"用户不存在！");
        rf.put(-1,"密码错误！");
        rf.put(-2,"新密码与旧密码相同！");
        return rf.getResponse(fisco.client.changePassword(new BigInteger(user_id),old_password,new_password),null);

        //测试用
        //return rf.getResponse(test_login(new BigInteger(user_id),old_password.getBytes(StandardCharsets.UTF_8)),null);
    }

    public Response logout(){
        ResponseFactory rf=new ResponseFactory();
        if (!isLogin())return rf.getResponse(-200,null);
        isLogin=false;
        return rf.getResponse(1,null);
    }
}


