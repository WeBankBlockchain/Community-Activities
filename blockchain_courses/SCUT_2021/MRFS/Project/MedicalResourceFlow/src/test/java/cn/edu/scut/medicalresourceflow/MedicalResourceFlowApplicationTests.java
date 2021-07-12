package cn.edu.scut.medicalresourceflow;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedicalResourceFlowApplicationTests {
//    @Resource
//    AccountService accountService;
//
//    @Resource
//    Client client;
//
//    @Value("${static.contract-address}")
//    String contractAddress ;
//
//    @Value("${static.contract-owner-private-key}")
//    String ownerPrivateKey;
//
//    @Test
//    void contextLoads() {
//        System.out.println(contractAddress);
//        System.out.println(ownerPrivateKey);
//    }
//
//    @Test
//    void deployTest() throws Exception{
//        CryptoKeyPair keyPair = accountService.getOwnerKeyPair();
//        Address address =  Address.deploy(
//                client,
//                keyPair
//        );
//        System.out.println(address.getContractAddress());
//    }
//
//    @Test
//    void deployMedicalChainTest() throws Exception{
//        CryptoKeyPair keyPair = accountService.getOwnerKeyPair();
//        MedicalChain medicalChain =  MedicalChain.deploy(
//                client,
//                keyPair
//        );
//        System.out.println(medicalChain.getContractAddress());
//    }
//
//
//    @Test
//    void loadTest() throws Exception{
//        Account account = accountService.createAccount();
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(account.getPrivateKey());
//        Address address = Address.load(
//                contractAddress,
//                client,
//                keyPair
//        );
//        System.out.println(account.getAddress());
//        System.out.println(address.getAddress());
//        System.out.println(address.getOwner());
//    }
//
//    @Test
//    void getOwner(){
//        CryptoKeyPair keyPair = accountService.getOwnerKeyPair();
//        System.out.println(keyPair.getAddress());
//        System.out.println(keyPair.getHexPublicKey());
//        System.out.println(keyPair.getHexPrivateKey());
//    }

}
