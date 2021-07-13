package com.scut.library.Service;

import com.scut.library.Entity.Book;
import com.scut.library.Entity.FinedUser;
import com.scut.library.fisco;
import com.scut.library.utils.Response;
import com.scut.library.utils.ResponseFactory;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminService {
    static final long period= 60L *24*60*60*1000;
    static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd EEE");
    /*
    User admin;
    Boolean isLogin=false;

    public Boolean isLogin() {
        return isLogin;
    }


    public Response login(String user_id, String password){
        int result=fisco.client.login(new BigInteger(user_id),password.getBytes(StandardCharsets.UTF_8));
        ResponseFactory rf=new ResponseFactory();
        rf.put(0,"Non-existent user.");
        rf.put(-1,"Wrong password.");
        //rf.put(-2,"Already login.");
        rf.put(-3,"Internal error.");

        //测试用
        //int result=test_login(new BigInteger(user_id),password.getBytes(StandardCharsets.UTF_8));

        if(result==1){
            admin=new adminUser(user_id);
            isLogin=true;
        }
        return rf.getResponse(result,null);
    }

    //测试用
    private int test_login(BigInteger user_id,byte[] password){
        if(user_id.equals(new BigInteger("0000"))&&new String(password).equals("0000")){
            return 1;
        }
        return -1;
    }

    public Response logout(){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        //isLogin=false;
        return rf.getResponse(1,null);
    }

     */

    public Response insertBook(String book_id,String name,String price){
        ResponseFactory rf=new ResponseFactory();
        rf.put(-1,"书籍ID被占用！");
        rf.put(-2,"内部错误！");

        //if(!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.insertBook(new BigInteger(book_id),name.getBytes(StandardCharsets.UTF_8),new BigInteger(price)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response deleteBook(String book_id){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.deleteBook(new BigInteger(book_id)), null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response getUserBorrowedBooks(String user_id){
        ResponseFactory rt =new ResponseFactory();
        //if(!isLogin())return rt.getResponse(-200,null);
        return rt.getResponse(1, load_books(fisco.client.getBooks(new BigInteger(user_id))));

        //测试用
        //return rt.getResponse(1,load_books(null));
    }

    public Response getAllBooks(){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(1,load_books(fisco.client.getAllBooks()));

        //测试用
        //return rf.getResponse(1,load_books(null));
    }

    public Response getBorrowedBooks(){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(1,load_books(fisco.client.getBorrowedBooks()));

        //测试用
        //return rf.getResponse(1,load_books(null));
    }

    private List<Book> load_books(List<Tuple6<BigInteger,byte[], BigInteger, BigInteger, BigInteger, BigInteger>>bs){
        List<Book>books= new ArrayList<>();

        //测试用
        //books.add(new Book("1111","book 1","1","1234", sdf.format(new Date(0)),sdf.format(new Date(period)),"1"));


        for (Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>t:bs){
            books.add(new Book(t.getValue1().toString(),
                    new String(t.getValue2()),
                    t.getValue3().toString(),
                    t.getValue4().toString(),
                    sdf.format(new Date(t.getValue5().longValue())),
                    sdf.format(new Date(t.getValue5().longValue()+period)),
                    t.getValue6().toString()));
        }



        return books;
    }

    public Response insertUser(String user_id,String name){
        ResponseFactory rf=new ResponseFactory();
        rf.put(-1,"用户ID被占用！");
        rf.put(-2,"内部错误！");
        //if(!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.insertUser(new BigInteger(user_id),name.getBytes(StandardCharsets.UTF_8)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response deleteUser(String user_id){
        ResponseFactory rf=new ResponseFactory();
        rf.put(-1,"用户不存在！");
        rf.put(-2,"内部错误！");
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.deleteUser(new BigInteger(user_id)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response clearFine(String user_id){
        ResponseFactory rf=new ResponseFactory();
        rf.put(-1,"用户不存在！");
        rf.put(-2,"内部错误！");
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(fisco.client.clearFine(new BigInteger(user_id)),null);

        //测试用
        //return rf.getResponse(1,null);
    }

    public Response getFinedUsers(){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(1,loadUsers(fisco.client.getFinedUsers()));

        //测试用
        //return rf.getResponse(1,loadUsers(null));
    }

    private List<FinedUser>loadUsers(List<Tuple3<BigInteger,byte[],BigInteger>>fus){
        List<FinedUser>finedUsers=new ArrayList<>();

        //测试用
        //finedUsers.add(new FinedUser("1234","1234","1000"));
        //finedUsers.add(new FinedUser("2345","2345","1000"));




        for(Tuple3<BigInteger,byte[],BigInteger>t:fus){
            finedUsers.add(new FinedUser(
                    t.getValue1().toString(),
                    new String(t.getValue2()),
                    t.getValue3().toString()));
        }



        return finedUsers;
    }

    public Response getAllUsers(){
        ResponseFactory rf=new ResponseFactory();
        //if (!isLogin())return rf.getResponse(-200,null);
        return rf.getResponse(1,loadUsers(fisco.client.getAllUsers()));

        //测试用
        //return rf.getResponse(1,loadUsers(null));
    }
}
