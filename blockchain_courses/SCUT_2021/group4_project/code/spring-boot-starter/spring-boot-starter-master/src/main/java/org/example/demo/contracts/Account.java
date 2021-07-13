package org.example.demo.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Account extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526110066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005257600080fd5b50610d25806100626000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630f4ae692146100bf57806326b85ee11461010c57806330e0789e1461016357806334a18dda146101c3578063647574a71461023657806370a082311461024d57806379fa913f146102a4578063a9059cbb1461030d578063ae0369cb1461035a578063bca926af146103a7578063c9116b69146103be578063d39f70bc146103e9575b600080fd5b3480156100cb57600080fd5b5061010a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610400565b005b34801561011857600080fd5b50610121610489565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101c1600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506104b3565b005b3480156101cf57600080fd5b50610234600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610679565b005b34801561024257600080fd5b5061024b6107ea565b005b34801561025957600080fd5b5061028e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061082d565b6040518082815260200191505060405180910390f35b3480156102b057600080fd5b5061030b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610876565b005b34801561031957600080fd5b50610358600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506109de565b005b34801561036657600080fd5b506103a5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610a7c565b005b3480156103b357600080fd5b506103bc610ac4565b005b3480156103ca57600080fd5b506103d3610bad565b6040518082815260200191505060405180910390f35b3480156103f557600080fd5b506103fe610c16565b005b80600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b80600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015151561056a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f6e6f7420656e6f756768206d6f6e65792100000000000000000000000000000081525060200191505060405180910390fd5b80600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205403600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555080600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16630553904e3084846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561075c578082015181840152602081019050610741565b50505050905090810190601f1680156107895780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156107aa57600080fd5b505af11580156107be573d6000803e3d6000fd5b505050506040513d60208110156107d457600080fd5b8101908080519060200190929190505050505050565b33600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166311e3f2af30836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610952578082015181840152602081019050610937565b50505050905090810190601f16801561097f5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561099f57600080fd5b505af11580156109b3573d6000803e3d6000fd5b505050506040513d60208110156109c957600080fd5b81019080805190602001909291905050505050565b60008273ffffffffffffffffffffffffffffffffffffffff1614151515610a6d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f706c656173652070726f7669646520726563696576657220616464726573732181525060200191505060405180910390fd5b610a783383836104b3565b5050565b80600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b610b2b606060405190810160405280602a81526020017f5f7472616e7366657228616464726573732c616464726573732c61646472657381526020017f732c75696e7432353629000000000000000000000000000000000000000000008152506003610679565b610b6b6040805190810160405280601681526020017f626f6e757328616464726573732c75696e7432353629000000000000000000008152506001610679565b610bab6040805190810160405280601c81526020017f6e65774163636f756e7428616464726573732c2075696e7432353629000000008152506001610679565b565b600060036000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905090565b610c7b606060405190810160405280602a81526020017f5f7472616e7366657228616464726573732c616464726573732c61646472657381526020017f732c75696e743235362900000000000000000000000000000000000000000000815250610876565b610cb96040805190810160405280601681526020017f626f6e757328616464726573732c75696e743235362900000000000000000000815250610876565b610cf76040805190810160405280601c81526020017f6e65774163636f756e7428616464726573732c2075696e743235362900000000815250610876565b5600a165627a7a72305820e1df021dceae28dd29a3f1829dda63689566775cc2afd795a7b38abd68e89d5a0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526110066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005257600080fd5b50610d25806100626000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630f4ae692146100bf57806326b85ee11461010c57806330e0789e1461016357806334a18dda146101c3578063647574a71461023657806370a082311461024d57806379fa913f146102a4578063a9059cbb1461030d578063ae0369cb1461035a578063bca926af146103a7578063c9116b69146103be578063d39f70bc146103e9575b600080fd5b3480156100cb57600080fd5b5061010a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610400565b005b34801561011857600080fd5b50610121610489565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101c1600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506104b3565b005b3480156101cf57600080fd5b50610234600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610679565b005b34801561024257600080fd5b5061024b6107ea565b005b34801561025957600080fd5b5061028e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061082d565b6040518082815260200191505060405180910390f35b3480156102b057600080fd5b5061030b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610876565b005b34801561031957600080fd5b50610358600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506109de565b005b34801561036657600080fd5b506103a5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610a7c565b005b3480156103b357600080fd5b506103bc610ac4565b005b3480156103ca57600080fd5b506103d3610bad565b6040518082815260200191505060405180910390f35b3480156103f557600080fd5b506103fe610c16565b005b80600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b80600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015151561056a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f6e6f7420656e6f756768206d6f6e65792100000000000000000000000000000081525060200191505060405180910390fd5b80600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205403600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555080600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16630553904e3084846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561075c578082015181840152602081019050610741565b50505050905090810190601f1680156107895780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156107aa57600080fd5b505af11580156107be573d6000803e3d6000fd5b505050506040513d60208110156107d457600080fd5b8101908080519060200190929190505050505050565b33600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166311e3f2af30836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610952578082015181840152602081019050610937565b50505050905090810190601f16801561097f5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561099f57600080fd5b505af11580156109b3573d6000803e3d6000fd5b505050506040513d60208110156109c957600080fd5b81019080805190602001909291905050505050565b60008273ffffffffffffffffffffffffffffffffffffffff1614151515610a6d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f706c656173652070726f7669646520726563696576657220616464726573732181525060200191505060405180910390fd5b610a783383836104b3565b5050565b80600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b610b2b606060405190810160405280602a81526020017f5f7472616e7366657228616464726573732c616464726573732c61646472657381526020017f732c75696e7432353629000000000000000000000000000000000000000000008152506003610679565b610b6b6040805190810160405280601681526020017f626f6e757328616464726573732c75696e7432353629000000000000000000008152506001610679565b610bab6040805190810160405280601c81526020017f6e65774163636f756e7428616464726573732c2075696e7432353629000000008152506001610679565b565b600060036000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905090565b610c7b606060405190810160405280602a81526020017f5f7472616e7366657228616464726573732c616464726573732c61646472657381526020017f732c75696e743235362900000000000000000000000000000000000000000000815250610876565b610cb96040805190810160405280601681526020017f626f6e757328616464726573732c75696e743235362900000000000000000000815250610876565b610cf76040805190810160405280601c81526020017f6e65774163636f756e7428616464726573732c2075696e743235362900000000815250610876565b5600a165627a7a72305820e1df021dceae28dd29a3f1829dda63689566775cc2afd795a7b38abd68e89d5a0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"name\":\"bonus\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[{\"name\":\"_address\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"},{\"name\":\"_money\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"outputs\":[],\"methodSignatureAsString\":\"bonus(address,uint256)\"},{\"name\":\"myAddress\",\"type\":\"function\",\"constant\":true,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"view\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"}],\"methodSignatureAsString\":\"myAddress()\"},{\"name\":\"_transfer\",\"type\":\"function\",\"constant\":false,\"payable\":true,\"anonymous\":false,\"stateMutability\":\"payable\",\"inputs\":[{\"name\":\"_from\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"},{\"name\":\"_to\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"},{\"name\":\"_money\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"outputs\":[],\"methodSignatureAsString\":\"_transfer(address,address,uint256)\"},{\"name\":\"registerParallelFunction\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\",\"indexed\":false,\"components\":null,\"typeAsString\":\"string\"},{\"name\":\"criticalSize\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"outputs\":[],\"methodSignatureAsString\":\"registerParallelFunction(string,uint256)\"},{\"name\":\"setsender\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[],\"outputs\":[],\"methodSignatureAsString\":\"setsender()\"},{\"name\":\"balanceOf\",\"type\":\"function\",\"constant\":true,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"view\",\"inputs\":[{\"name\":\"_address\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"methodSignatureAsString\":\"balanceOf(address)\"},{\"name\":\"unregisterParallelFunction\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\",\"indexed\":false,\"components\":null,\"typeAsString\":\"string\"}],\"outputs\":[],\"methodSignatureAsString\":\"unregisterParallelFunction(string)\"},{\"name\":\"transfer\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[{\"name\":\"to\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"},{\"name\":\"money\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"outputs\":[],\"methodSignatureAsString\":\"transfer(address,uint256)\"},{\"name\":\"newAccount\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[{\"name\":\"_address\",\"type\":\"address\",\"indexed\":false,\"components\":null,\"typeAsString\":\"address\"},{\"name\":\"_money\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"outputs\":[],\"methodSignatureAsString\":\"newAccount(address,uint256)\"},{\"name\":\"enableParallel\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[],\"outputs\":[],\"methodSignatureAsString\":\"enableParallel()\"},{\"name\":\"myBalance\",\"type\":\"function\",\"constant\":true,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"view\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"indexed\":false,\"components\":null,\"typeAsString\":\"uint256\"}],\"methodSignatureAsString\":\"myBalance()\"},{\"name\":\"disableParallel\",\"type\":\"function\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[],\"outputs\":[],\"methodSignatureAsString\":\"disableParallel()\"},{\"name\":null,\"type\":\"constructor\",\"constant\":false,\"payable\":false,\"anonymous\":false,\"stateMutability\":\"nonpayable\",\"inputs\":[],\"outputs\":null,\"methodSignatureAsString\":\"null()\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BONUS = "bonus";

    public static final String FUNC_MYADDRESS = "myAddress";

    public static final String FUNC__TRANSFER = "_transfer";

    public static final String FUNC_REGISTERPARALLELFUNCTION = "registerParallelFunction";

    public static final String FUNC_SETSENDER = "setsender";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_UNREGISTERPARALLELFUNCTION = "unregisterParallelFunction";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_NEWACCOUNT = "newAccount";

    public static final String FUNC_ENABLEPARALLEL = "enableParallel";

    public static final String FUNC_MYBALANCE = "myBalance";

    public static final String FUNC_DISABLEPARALLEL = "disableParallel";

    protected Account(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt bonus(String _address, BigInteger _money) {
        final Function function = new Function(
                FUNC_BONUS,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void bonus(String _address, BigInteger _money, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_BONUS,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForBonus(String _address, BigInteger _money) {
        final Function function = new Function(
                FUNC_BONUS,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getBonusInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_BONUS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
                );
    }

    public String myAddress() throws ContractException {
        final Function function = new Function(FUNC_MYADDRESS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt _transfer(String _from, String _to, BigInteger _money) {
        final Function function = new Function(
                FUNC__TRANSFER,
                Arrays.<Type>asList(new Address(_from),
                new Address(_to),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void _transfer(String _from, String _to, BigInteger _money, TransactionCallback callback) {
        final Function function = new Function(
                FUNC__TRANSFER,
                Arrays.<Type>asList(new Address(_from),
                new Address(_to),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionFor_transfer(String _from, String _to, BigInteger _money) {
        final Function function = new Function(
                FUNC__TRANSFER,
                Arrays.<Type>asList(new Address(_from),
                new Address(_to),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, BigInteger> get_transferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC__TRANSFER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue()
                );
    }

    public TransactionReceipt registerParallelFunction(String functionName, BigInteger criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName),
                new Uint256(criticalSize)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void registerParallelFunction(String functionName, BigInteger criticalSize, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName),
                new Uint256(criticalSize)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterParallelFunction(String functionName, BigInteger criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName),
                new Uint256(criticalSize)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getRegisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
                );
    }

    public TransactionReceipt setsender() {
        final Function function = new Function(
                FUNC_SETSENDER,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void setsender(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETSENDER,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetsender() {
        final Function function = new Function(
                FUNC_SETSENDER,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public BigInteger balanceOf(String _address) throws ContractException {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(_address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt unregisterParallelFunction(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void unregisterParallelFunction(String functionName, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUnregisterParallelFunction(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new Utf8String(functionName)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getUnregisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt transfer(String to, BigInteger money) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(to),
                new Uint256(money)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void transfer(String to, BigInteger money, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(to),
                new Uint256(money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransfer(String to, BigInteger money) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(to),
                new Uint256(money)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getTransferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
                );
    }

    public TransactionReceipt newAccount(String _address, BigInteger _money) {
        final Function function = new Function(
                FUNC_NEWACCOUNT,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void newAccount(String _address, BigInteger _money, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_NEWACCOUNT,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForNewAccount(String _address, BigInteger _money) {
        final Function function = new Function(
                FUNC_NEWACCOUNT,
                Arrays.<Type>asList(new Address(_address),
                new Uint256(_money)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getNewAccountInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_NEWACCOUNT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue()
                );
    }

    public TransactionReceipt enableParallel() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void enableParallel(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnableParallel() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public BigInteger myBalance() throws ContractException {
        final Function function = new Function(FUNC_MYBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt disableParallel() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void disableParallel(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDisableParallel() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public static Account load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Account(contractAddress, client, credential);
    }

    public static Account deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Account.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
