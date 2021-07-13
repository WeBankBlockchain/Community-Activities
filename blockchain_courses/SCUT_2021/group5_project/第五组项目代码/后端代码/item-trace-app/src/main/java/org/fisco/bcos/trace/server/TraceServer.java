package org.fisco.bcos.trace.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import org.fisco.bcos.trace.contract.ItemTraceBack;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
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

import javax.print.DocFlavor;

public class  TraceServer {
    static Logger logger = LoggerFactory.getLogger(TraceServer.class);
    private static BcosSDK bcosSDK;
    private static Client client;
    private static CryptoKeyPair cryptoKeyPair;

    public static void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    public static void deployAssetAndRecordAddr() {
        try {
            ItemTraceBack itemtraceback = ItemTraceBack.deploy(client, cryptoKeyPair);
            System.out.println(
                    " deploy ItemTraceBack success, contract address is " + itemtraceback.getContractAddress());

            recordItemTraceBackAddr(itemtraceback.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy ItemTraceBack contract failed, error message is  " + e.getMessage());
        }
    }

    public static void recordItemTraceBackAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    public static String loadItemTraceBackAddr() throws Exception {
        // load ItemTraceBack contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load ItemTraceBack contract address failed, please deploy it first. ");
        }
        logger.info(" load ItemTraceBack address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }

    public static Tuple2<String,String> getUserMessage(String mp_openid) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            Tuple2<String,String> result = itemtraceback.get_user_message(mp_openid);
            System.out.printf("账户 %s, 昵称为 %s \n", mp_openid ,  result.getValue1());
            System.out.printf("账户 %s, 头像url为 %s \n", mp_openid ,  result.getValue2());
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getUserMessage exception, error message is {}", e.getMessage());

            System.out.printf(" getUserMessage failed, error message is %s\n", e.getMessage());
            return new Tuple2<String, String>("-1", "-1");
        }
    }

    public static BigInteger getUserLevel(String mp_openid) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            BigInteger result = itemtraceback.get_user_level(mp_openid);
            System.out.printf("账户 %s, 等级为 %s \n", mp_openid ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getUserLevel exception, error message is {}", e.getMessage());

            System.out.printf(" getUserLevel failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-1));
        }
    }

    public static String getUserCompany(String mp_openid) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_user_company(mp_openid);
            System.out.printf("账户 %s, 公司为 %s \n", mp_openid ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getUserCompany exception, error message is {}", e.getMessage());

            System.out.printf(" getUserCompany failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static String getItemProcess(String item_id) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_item_process(item_id);
            System.out.printf("商品 %s, 商品信息为 %s \n", item_id ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getItemProcess exception, error message is {}", e.getMessage());

            System.out.printf(" getItemProcess failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static String getReceiverRequest(String mp_openid) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_receiver_request(mp_openid);
            System.out.printf("接收者 %s, 待处理请求为 %s \n", mp_openid ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getReceiverRequest exception, error message is {}", e.getMessage());

            System.out.printf(" getReceiverRequest failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static String getCompanyEmployee(String company) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_company_employee(company);
            System.out.printf("公司名 %s, 员工信息为 %s \n", company ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getCompanyEmployee exception, error message is {}", e.getMessage());

            System.out.printf(" getCompanyEmployee failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static String getUserSend(String mp_openid) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_user_send(mp_openid);
            System.out.printf("员工 %s, 近五次成功交易记录为 %s \n", mp_openid ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getUserSend exception, error message is {}", e.getMessage());

            System.out.printf(" getUserSend failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static String getCompanyProduce(String company) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            String result = itemtraceback.get_company_produce(company);
            System.out.printf("制造公司 %s, 制造的商品列表为 %s \n", company ,  result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" getCompanyProduce exception, error message is {}", e.getMessage());

            System.out.printf(" getCompanyProduce failed, error message is %s\n", e.getMessage());
            return "-1";
        }
    }

    public static BigInteger grantLevel(String leader_id, String user_id, BigInteger operation) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = itemtraceback.grant_level(leader_id,user_id,operation);
            List<ItemTraceBack.GrantLevelEventEventResponse> response = itemtraceback.getGrantLevelEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("1")) == 0) {
                    System.out.printf(
                            " grantLevel success => leader: %s, user: %s \n", leader_id, user_id);
                } else {
                    System.out.printf(
                            " grantLevel failed, ret code is %s \n", response.get(0).ret.toString());
                }
                return response.get(0).ret;
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return new BigInteger(String.valueOf(-100));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" grantLevel exception, error message is {}", e.getMessage());

            System.out.printf(" grantLevel failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-100));
        }
    }

    public static BigInteger sendTransferRequest(String sender_id,String item_list,String receiver_id,String send_from,String send_to) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = itemtraceback.send_transfer_request(sender_id,item_list,receiver_id,send_from,send_to);
            List<ItemTraceBack.SendRequestEventEventResponse> response = itemtraceback.getSendRequestEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("1")) == 0) {
                    System.out.printf(
                            " sendRequest success => sender: %s, item_list: %s, receiver: %s, send_from: %s, send_to: %s\n",
                            sender_id, item_list, receiver_id, send_from, send_to);
                } else {
                    System.out.printf(
                            " sendRequest failed, ret code is %s \n", response.get(0).ret.toString());
                }
                return response.get(0).ret;
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return new BigInteger(String.valueOf(-100));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" sendTransferRequest exception, error message is {}", e.getMessage());

            System.out.printf(" sendTransferRequest failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-100));
        }
    }

    public static BigInteger acceptTransferRequest(String sender_id,String receiver_id,String time,String acc) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = itemtraceback.accept_transfer_request(sender_id,receiver_id,time,acc);
            List<ItemTraceBack.AcceptRequestEventEventResponse> response = itemtraceback.getAcceptRequestEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("1")) == 0) {
                    System.out.printf(
                            " acceptRequest success => sender_id: %s, receiver_id: %s \n", sender_id, receiver_id);
                } else {
                    System.out.printf(
                            " acceptRequest failed, ret code is %s \n", response.get(0).ret.toString());
                }
                return response.get(0).ret;
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return new BigInteger(String.valueOf(-100));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" acceptRequest exception, error message is {}", e.getMessage());

            System.out.printf(" acceptRequest failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-100));
        }
    }

    public static BigInteger insertUser(String mp_openid,String nickname,String avatar) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = itemtraceback.insert_User(mp_openid,nickname,avatar);
            List<ItemTraceBack.InsertUserEventEventResponse> response = itemtraceback.getInsertUserEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("1")) == 0) {
                    System.out.printf(
                            " insertUser success => mp_openid: %s, nickname: %s, avatar: %s \n", mp_openid, nickname, avatar);
                } else {
                    System.out.printf(
                            " insertUser failed, ret code is %s \n", response.get(0).ret.toString());
                }
                return response.get(0).ret;
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return new BigInteger(String.valueOf(-100));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" insertUser exception, error message is {}", e.getMessage());

            System.out.printf(" insertUser failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-100));
        }
    }

    public static BigInteger Produce(String item_id,String item_name,String producer_id,String produce_time,String produce_place) {
        try {
            String contractAddress = loadItemTraceBackAddr();
            ItemTraceBack itemtraceback = ItemTraceBack.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = itemtraceback.produce(item_id,item_name,producer_id,produce_time,produce_place);
            List<ItemTraceBack.ProduceEventEventResponse> response = itemtraceback.getProduceEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("1")) == 0) {
                    System.out.printf(
                            " Produce success => item_id: %s, item_name: %s, producer_id: %s , produce_time: %s , produce_place: %s \n",
                            item_id, item_name, producer_id,produce_time,produce_place);
                } else {
                    System.out.printf(
                            " Produce failed, ret code is %s \n", response.get(0).ret.toString());
                }
                return response.get(0).ret;
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return new BigInteger(String.valueOf(-100));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" Produce exception, error message is {}", e.getMessage());

            System.out.printf(" Produce failed, error message is %s\n", e.getMessage());
            return new BigInteger(String.valueOf(-100));
        }
    }

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.trace.server.TraceServer deploy");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.trace.server.TraceServer getUserMessage mp_openid");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.trace.server.TraceServer getUserLevel mp_openid");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.trace.server.TraceServer getUserCompany mp_openid");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            Usage();
        }

        TraceServer server = new TraceServer();
        server.initialize();

        switch (args[0]) {
            case "deploy":
                server.deployAssetAndRecordAddr();
                break;
            case "getUserMessage":
                if (args.length < 2) {
                    Usage();
                }
                server.getUserMessage(args[1]);
                break;
            case "getUserLevel":
                if (args.length < 2) {
                    Usage();
                }
                server.getUserLevel(args[1]);
                break;
            case "getUserCompany":
                if (args.length < 2) {
                    Usage();
                }
                server.getUserCompany(args[1]);
                break;
            default:
            {
                Usage();
            }
        }
        System.exit(0);
    }
}
