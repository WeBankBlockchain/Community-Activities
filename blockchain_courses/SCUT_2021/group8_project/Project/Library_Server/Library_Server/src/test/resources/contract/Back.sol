pragma solidity >=0.4.24 <0.6.11;
pragma experimental ABIEncoderV2;


contract Back
{
    // event
    event createEvent(address creater, bytes tableName);
    event insertEvent(address owner, bytes tableName, uint id);
    event borrowEvent(uint borrower_id, uint book_id);
    event returnEvent(uint borrower_id, uint book_id);
    event updateEvent(bytes tableName, uint id, bytes value_name, uint new_value);
    event deleteEvent(bytes tableName, uint id);

    struct Book
    {
        bytes Name;
        uint Price;
        uint BorrowerId;
        uint LastBorrowTime;
        uint State;
        bool isUsed;
    }

    struct User
    {
        bytes Name;
        bytes32 Password;
        uint Fine;
        bool isUsed;
    }

    struct Admin
    {
        bytes32 Name;
        bytes32 Password;
    }

    mapping (uint=>Book) private Books;
    mapping (uint=>User) private Users;
    Admin admin;
    uint[] Books_index;
    uint[] Users_index;


    function stringToBytes32(string memory source) constant internal returns(bytes32 result)
    {
        assembly
        {
            result := mload(add(source,32))
        }
    }

    /*
        入库
        返回值：
        1：成功
        -1：id已被占用
    */
    function insertBook(uint book_id, bytes name, uint256 price) public returns(int8)
    {

        Book memory new_book;

        new_book.Name = name;
        new_book.Price = price;
        new_book.State = 0;
        new_book.isUsed = true;
        if(Books[book_id].isUsed)
        {
            return -1;
        }

        Books[book_id] = new_book;
        Books_index.push(book_id);
        emit insertEvent(msg.sender, "Book", book_id);
        return 1;
    }


    /*
        删除书本
        返回值：
        1：成功
        -1：书本不存在
    */
    function deleteBook(uint book_id) public returns(int8)
    {
        if(!Books[book_id].isUsed)
        {
            return -1;
        }

        delete Books[book_id];
        for(uint i; i< Books_index.length;i++)
        {
            if(Books_index[i] == book_id)
            {
                delete Books_index[i];
                return(1);
            }
        }
    }


    /*
        更新书本信息
        返回值：
        1：成功
        0：还书超时
        -1：状态不变，非法更新
        -2：查无此书
        -3：未知错误
    */
    function updateBook(uint book_id, uint borrower_id, uint new_state) internal returns(int8)
    {
        Book memory book = Books[book_id];

        if(!book.isUsed)
        {
            return -2;
        }

        if(book.State == new_state)
        {
            return -1;
        }

        book.State = new_state;

        //归还
        if(new_state == 0)
        {
            uint borrow_length = book.LastBorrowTime - (now/1 days)*1 days;
            if(borrow_length > 60 days)
            {
                //超时罚款一天5元
                uint fine = (borrow_length - 60 days) * 5;
                int temp = updateUserFine(borrower_id,fine);
                if(temp != 1)
                {
                    return -3;
                }
                delete book.BorrowerId;
                delete book.LastBorrowTime;
                Books[book_id] = book;
                emit updateEvent("Book", book_id, "State", 0);
                return 0;
            }
            delete book.BorrowerId;
            delete book.LastBorrowTime;
            Books[book_id] = book;
            emit updateEvent("Book", book_id, "State", 0);
            return 1;
        }

        //借出
        if(new_state == 1)
        {
            book.BorrowerId = borrower_id;
            book.LastBorrowTime = (now/1 days)*1 days;//借出时间精确到日
            emit updateEvent("Book", book_id, "BorrowerId", borrower_id);
            emit updateEvent("Book", book_id, "LastBorrowTime", book.LastBorrowTime);
            emit updateEvent("Book", book_id, "State", 1);
            return 1;
        }
    }


    /*
        获取某个用户借的书
    */
    function getBooks(uint user_id) public returns(uint[], bytes[], uint[], uint[], uint[], uint[])
    {
        uint[] book_ids;
        bytes[] Names;
        uint[] Prices;
        uint[] BorrowerIds;
        uint[] LastBorrowTimes;
        uint[] States;
        book_ids.length = 0;
        Names.length = 0;
        Prices.length = 0;
        BorrowerIds.length = 0;
        LastBorrowTimes.length = 0;
        States.length = 0;
        for(uint i; i < Books_index.length; i++)
        {
            if(Books[Books_index[i]].BorrowerId == user_id)
            {
                book_ids.push(Books_index[i]);
                Names.push(Books[Books_index[i]].Name);
                Prices.push(Books[Books_index[i]].Price);
                BorrowerIds.push(Books[Books_index[i]].BorrowerId);
                LastBorrowTimes.push(Books[Books_index[i]].LastBorrowTime);
                States.push(Books[Books_index[i]].State);
            }
        }

        return(book_ids,Names,Prices,BorrowerIds,LastBorrowTimes,States);
    }


    /*
        获取全部书
    */
    function getAllBooks() public returns(uint[], bytes[], uint[], uint[], uint[], uint[])
    {
        uint[] book_ids;
        bytes[] Names;
        uint[] Prices;
        uint[] BorrowerIds;
        uint[] LastBorrowTimes;
        uint[] States;
        book_ids.length = 0;
        Names.length = 0;
        Prices.length = 0;
        BorrowerIds.length = 0;
        LastBorrowTimes.length = 0;
        States.length = 0;
        for(uint i=0;i < Books_index.length;i++)
        {
            book_ids.push(Books_index[i]);
            Names.push(Books[Books_index[i]].Name);
            Prices.push(Books[Books_index[i]].Price);
            BorrowerIds.push(Books[Books_index[i]].BorrowerId);
            LastBorrowTimes.push(Books[Books_index[i]].LastBorrowTime);
            States.push(Books[Books_index[i]].State);
        }
        return(book_ids,Names,Prices,BorrowerIds,LastBorrowTimes,States);
    }


    /*
        获取借出的书
    */
    function getBorrowedBooks() public returns(uint[], bytes[], uint[], uint[], uint[], uint[])
    {
        uint[] book_ids;
        bytes[] Names;
        uint[] Prices;
        uint[] BorrowerIds;
        uint[] LastBorrowTimes;
        uint[] States;
        book_ids.length = 0;
        Names.length = 0;
        Prices.length = 0;
        BorrowerIds.length = 0;
        LastBorrowTimes.length = 0;
        States.length = 0;
        for(uint i; i < Books_index.length; i++)
        {
            if(Books_index[i] != 0 && Books[Books_index[i]].State == 1)
            {
                book_ids.push(Books_index[i]);
                Names.push(Books[Books_index[i]].Name);
                Prices.push(Books[Books_index[i]].Price);
                BorrowerIds.push(Books[Books_index[i]].BorrowerId);
                LastBorrowTimes.push(Books[Books_index[i]].LastBorrowTime);
                States.push(Books[Books_index[i]].State);
            }
        }
        return(book_ids,Names,Prices,BorrowerIds,LastBorrowTimes,States);
    }

    /*
        添加用户
        返回值：
        1：成功
        -1：id已被占用
    */
    function insertUser(uint user_id, bytes name) public returns(int8)
    {
        User memory new_user;

        new_user.Name = name;
        new_user.Password = "123456";
        new_user.isUsed = true;

        if(Users[user_id].isUsed)
        {
            return -1;
        }

        Users[user_id] = new_user;
        Users_index.push(user_id);

        emit insertEvent(msg.sender, "User", user_id);
        return 1;
    }


    /*
        对用户添加罚款/罚款清零
        返回值：
        1：成功
        -1：查无此人
    */
    function updateUserFine(uint user_id, uint fine) internal returns(int8)
    {
        User user = Users[user_id];

        if(!user.isUsed)
        {
            return -1;//查无此人
        }

        user.Fine = fine;
        Users[user_id] = user;
        emit updateEvent("User", user_id, "Fine", fine);
        return 1;
    }


    /*
        删除用户
        返回值：
        1：成功
        -1：用户不存在
    */
    function deleteUser(uint user_id) public returns(int8)
    {
        if(!Users[user_id].isUsed)
        {
            return -1;
        }

        delete Users[user_id];
        for(uint i; i< Users_index.length;i++)
        {
            if(Users_index[i] == user_id)
            {
                delete Users_index[i];
                return(1);
            }
        }
    }


    /*
        设置管理员
        返回值：
        1：成功
    */
    function setAdmin(string name, string password) public returns(int8)
    {

        Admin new_admin;

        new_admin.Name = stringToBytes32(name);
        new_admin.Password = stringToBytes32(password);

        admin = new_admin;
        return 1;
    }


    /*
        借书
        返回值：
        1：成功
        0：罚款未缴清
        -1：查无此人
        -2：查无此书
        -3：书已借出
        -4：未知错误
    */
    function borrowBook(uint borrower_id, uint book_id) public returns(int8)
    {
        User user = Users[borrower_id];
        Book book = Books[book_id];
        if(!user.isUsed)
        {
            return -1;//查无此人
        }

        if(user.Fine > 0)
        {
            return 0;//罚款未缴清
        }

        if(!book.isUsed)
        {
            return -2;//查无此书
        }

        if(book.State == 1)
        {
            return -3 ;//书已借出
        }

        if(updateBook(book_id, borrower_id, 1) == 1)
        {
            emit borrowEvent(borrower_id, book_id);
            return 1;
        }
        return -4;
    }


    /*
        还书
        返回值：
        1：成功
        0：超时
        -1：查无此人
        -2：查无此书
        -3：书已在库
        -4：未知错误
    */
    function returnBook(uint borrower_id, uint book_id) public returns(int8)
    {
        User user = Users[borrower_id];
        Book book = Books[book_id];

        if(!user.isUsed)
        {
            return -1;//查无此人
        }

        if(!book.isUsed)
        {
            return -2;//查无此书
        }

        if(book.State == 0)
        {
            return -3 ;//书已在库
        }

        int8 temp = updateBook(borrower_id,book_id,0);
        if(temp == 0 || temp == 1)
        {
            return temp;
        }
        return -4;
    }


    /*
        登陆验证
        返回值：
        1：成功
        0：查无此人
        -1：密码错误
    */
    function verifyPassword(uint id, bytes32 password) internal returns(int8)
    {
        if(!Users[id].isUsed)
        {
            return 0;
        }

        if(Users[id].Password == password)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }


    /*
        用户登陆
        返回值：
        1：成功
        0：查无此人
        -1：密码错误
    */
    function userLogin(uint user_id, string password) public returns(int8)
    {
        bytes32 _password = stringToBytes32(password);

        int8 verify = verifyPassword(user_id, _password);
        return verify;
    }


    /*
        管理员登陆
        返回值：
        1：成功
        -1：密码错误
    */
    function adminLogin(string name, string password) public returns(int8)
    {
        bytes32 _name = stringToBytes32(name);
        bytes32 _password = stringToBytes32(password);

        if(admin.Name == _name && admin.Password == _password )
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }


    /*
        管理员将用户罚款清零
    */
    function clearFine(uint user_id) public returns(int8)
    {
        return updateUserFine(user_id,0);
    }


    /*
        获取全部用户
    */
    function getAllUsers() public returns(uint[], bytes[], uint[])
    {
        uint[] user_ids;
        bytes[] Names;
        uint[] Fines;
        user_ids.length = 0;
        Names.length = 0;
        Fines.length = 0;
        for(uint i; i < Users_index.length; i++)
        {
            user_ids.push(Users_index[i]);
            Names.push(Users[Users_index[i]].Name);
            Fines.push(Users[Users_index[i]].Fine);
        }

        return(user_ids,Names,Fines);
    }


    /*
        查看有罚款在身的用户
    */
    function getFinedUsers() public returns(uint[], bytes[], uint[])
    {
        uint[] user_ids;
        bytes[] Names;
        uint[] Fines;
        user_ids.length = 0;
        Names.length = 0;
        Fines.length = 0;
        for(uint i; i < Users_index.length; i++)
        {
            if(Users_index[i] != 0 && Users[Users_index[i]].Fine > 0)
            {
                user_ids.push(Users_index[i]);
                Names.push(Users[Users_index[i]].Name);
                Fines.push(Users[Users_index[i]].Fine);
            }
        }

        return(user_ids,Names,Fines);
    }


    /*
        用户修改密码
        返回值：
        1：成功
        0：查无此人
        -1：密码错误
        -2：新旧密码一样
    */
    function changePassword(uint user_id, string old_password, string new_password) public returns(int8)
    {
        bytes32 _old_password = stringToBytes32(old_password);
        bytes32 _new_password = stringToBytes32(new_password);

        if(_old_password == _new_password)
        {
            return -2;
        }


        int8 verify = verifyPassword(user_id, _old_password);

        if(verify == 1)
        {
            Users[user_id].Password = _new_password;
            return 1;
        }
        else
        {
            return verify;
        }
    }
}