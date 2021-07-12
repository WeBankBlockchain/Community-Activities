package org.fisco.bcos.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import org.bouncycastle.pqc.math.linearalgebra.BigEndianConversions;
import org.fisco.bcos.contract.Back;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class BackClient
{
    static Logger logger = LoggerFactory.getLogger(BackClient.class);

    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;


    public void initialize() throws Exception
    {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());

    }


    public void deployBackAndRecordAddr()
    {

        try
        {
            Back back = Back.deploy(client, cryptoKeyPair);
            System.out.println(
                    " deploy Back success, contract address is " + back.getContractAddress());

            recordBackAddr(back.getContractAddress());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Back contract failed, error message is  " + e.getMessage());
        }
    }


    public void recordBackAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }


    public String loadBackAddr() throws Exception
    {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals(""))
        {
            throw new Exception(" load Back contract address failed, please deploy it first. ");
        }
        logger.info(" load Back address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }


    public int insertBook(BigInteger book_id, byte[] name, BigInteger price)
    {
        int ret_code = -2;
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getInsertBookOutput(back.insertBook(book_id, name, price)).getValue1().intValue();
            logger.info("insertBook id:{}, name:{}, price:{}",book_id ,new String(name) ,price);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("insertBook exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int deleteBook(BigInteger book_id)
    {
        int ret_code = -2;
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getDeleteBookOutput(back.deleteBook(book_id)).getValue1().intValue();
            logger.info("deleteBook id:{}",book_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("deleteBook exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> getBooks(BigInteger user_id)
    {
        List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> out = new ArrayList<>();
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            Tuple6<List<BigInteger>,List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> books
                    = back.getGetBooksOutput(back.getBooks(user_id));
            for (int i = 0;i < books.getValue1().size();i += books.getSize())
            {
                Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger> book;
                book = new Tuple6<>(
                        books.getValue1().get(i),
                        books.getValue2().get(i+1),
                        books.getValue3().get(i+2),
                        books.getValue4().get(i+3),
                        books.getValue5().get(i+4),
                        books.getValue6().get(i+5)
                );
                out.add(book);
            }
            logger.info("getBooks user_id:{}",user_id);
        }
        catch (Exception e)
        {
            logger.error("getBooks exception, user_id: {}, error message is {}", user_id,e.getMessage());
        }
        return out;
    }


    public List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> getAllBooks()
    {
        List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> out = new ArrayList<>();
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            Tuple6<List<BigInteger>,List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> books
                    = back.getGetAllBooksOutput(back.getAllBooks());
            for (int i = 0;i < books.getValue1().size();i += books.getSize())
            {
                Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger> book;
                book = new Tuple6<>(
                        books.getValue1().get(i),
                        books.getValue2().get(i+1),
                        books.getValue3().get(i+2),
                        books.getValue4().get(i+3),
                        books.getValue5().get(i+4),
                        books.getValue6().get(i+5)
                );
                out.add(book);
            }
            logger.info("getAllBooks");
        }
        catch (Exception e)
        {
            logger.error("getAllBooks exception, error message is {}",e.getMessage());
        }
        return out;
    }


    public List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> getBorrowedBooks()
    {
        List<Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger>> out = new ArrayList<>();
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            Tuple6<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> books
                    = back.getGetBorrowedBooksOutput(back.getBorrowedBooks());
            for (int i = 0;i < books.getValue1().size();i += books.getSize())
            {
                Tuple6<BigInteger,byte[],BigInteger,BigInteger,BigInteger,BigInteger> book;
                book = new Tuple6<>(
                        books.getValue1().get(i),
                        books.getValue2().get(i+1),
                        books.getValue3().get(i+2),
                        books.getValue4().get(i+3),
                        books.getValue5().get(i+4),
                        books.getValue6().get(i+5)
                );
                out.add(book);
            }
            logger.info("getBorrowedBooks");
        }
        catch (Exception e)
        {
            logger.error("getBorrowedBooks exception, error message is {}",e.getMessage());
        }
        return out;
    }


    public int insertUser(BigInteger user_id, byte[] name)
    {
        int ret_code = -2;
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getInsertUserOutput(back.insertUser(user_id, name)).getValue1().intValue();
            logger.info("insertUser id:{}, name:{}",user_id,new String(name));
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("insertUser exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int deleteUser(BigInteger user_id)
    {
        int ret_code = -2;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getDeleteUserOutput(back.deleteUser(user_id)).getValue1().intValue();
            logger.info("deleteUser id:{}",user_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("deleteUser exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int borrowBook(BigInteger borrower_id, BigInteger book_id)
    {
        int ret_code = -5;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getBorrowBookOutput(back.borrowBook(borrower_id,book_id)).getValue1().intValue();
            logger.info("borrowBook, borrower id:{}, book id:{}", borrower_id, book_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("borrowBook exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int returnBook(BigInteger borrower_id, BigInteger book_id)
    {
        int ret_code = -5;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getReturnBookOutput(back.returnBook(borrower_id,book_id)).getValue1().intValue();
            logger.info("returnBook, borrower id:{}, book id:{}", borrower_id, book_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("returnBook exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int userLogin(BigInteger user_id, String password)
    {
        int ret_code = -3;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getUserLoginOutput(back.userLogin(user_id,password)).getValue1().intValue();
            logger.info("user login, user id:{}",user_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("user login exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int setAdmin(String admin_name, String password) throws Exception
    {
        int ret_code = -1;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getAdminLoginOutput(back.adminLogin(admin_name,password)).getValue1().intValue();
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("admin login exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int adminLogin(String admin_name, String password)
    {
        int ret_code = -3;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getAdminLoginOutput(back.adminLogin(admin_name,password)).getValue1().intValue();
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("admin login exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public int clearFine(BigInteger user_id)
    {
        int ret_code = -2;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getClearFineOutput(back.clearFine(user_id)).getValue1().intValue();
            logger.info("clearFine user id:{}",user_id);
            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("clearFine exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }


    public List<Tuple3<BigInteger,byte[],BigInteger>> getAllUsers()
    {
        List<Tuple3<BigInteger,byte[],BigInteger>> out = new ArrayList<>();
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            Tuple3<List<BigInteger>,List<byte[]>, List<BigInteger>> users
                    = back.getGetAllUsersOutput(back.getAllUsers());
            for (int i = 0;i < users.getValue1().size();i += users.getSize())
            {
                Tuple3<BigInteger,byte[],BigInteger> user;
                user = new Tuple3<>(
                        users.getValue1().get(i),
                        users.getValue2().get(i+1),
                        users.getValue3().get(i+2)
                );
                out.add(user);
            }
            logger.info("getAllUsers");
        }
        catch (Exception e)
        {
            logger.error("getFinedUsers exception, error message is {}",e.getMessage());
        }
        return out;
    }


    public List<Tuple3<BigInteger,byte[],BigInteger>> getFinedUsers()
    {
        List<Tuple3<BigInteger,byte[],BigInteger>> out = new ArrayList<>();
        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            Tuple3<List<BigInteger>,List<byte[]>, List<BigInteger>> users
                    = back.getGetFinedUsersOutput(back.getFinedUsers());
            for (int i = 0;i < users.getValue1().size();i += users.getSize())
            {
                Tuple3<BigInteger,byte[],BigInteger> user;
                user = new Tuple3<>(
                        users.getValue1().get(i),
                        users.getValue2().get(i+1),
                        users.getValue3().get(i+2)
                );
                out.add(user);
            }
            logger.info("getFinedUser");
        }
        catch (Exception e)
        {
            logger.error("getFinedUsers exception, error message is {}",e.getMessage());
        }
        return out;
    }


    public int changePassword(BigInteger user_id, String old_password, String new_password)
    {
        int ret_code = -3;

        try
        {
            String contractAddress = loadBackAddr();
            Back back = Back.load(contractAddress, client, cryptoKeyPair);
            ret_code = back.getChangePasswordOutput(back.changePassword(user_id, old_password, new_password)).getValue1().intValue();

            return ret_code;
        }
        catch (Exception e)
        {
            logger.error("changePassword exception, error message is {}", e.getMessage());
        }
        return ret_code;
    }
}


