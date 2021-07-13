package cn.edu.scut.medicalresourceflow.controller;


import cn.edu.scut.medicalresourceflow.annoation.GodToken;
import cn.edu.scut.medicalresourceflow.annoation.PassToken;
import cn.edu.scut.medicalresourceflow.annoation.UserLoginToken;
import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.bo.ChangeRoleBO;
import cn.edu.scut.medicalresourceflow.entity.bo.DeleteResourceBO;
import cn.edu.scut.medicalresourceflow.entity.bo.ResourceBO;
import cn.edu.scut.medicalresourceflow.entity.bo.TransferBO;
import cn.edu.scut.medicalresourceflow.entity.dto.ResourceDTO;
import cn.edu.scut.medicalresourceflow.entity.dto.TransactionDTO;
import cn.edu.scut.medicalresourceflow.entity.vo.ProvinceCntBO;
import cn.edu.scut.medicalresourceflow.service.*;
import cn.edu.scut.medicalresourceflow.util.RedisKeyUtil;
import cn.edu.scut.medicalresourceflow.util.RedisUtil;
import cn.edu.scut.medicalresourceflow.util.Result;
import cn.edu.scut.medicalresourceflow.util.RoleUtil;
import cn.edu.scut.medicalresourceflow.validate.Insert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@RestController
@Validated
@RequestMapping("/chain")
public class MedicalChainController {

    @Resource
    MedicalChainService medicalChainService;

    @Resource
    AccountService accountService;

    @Resource
    RoleService roleService;

    @Resource
    TokenService tokenService;

    @Resource
    UserService userService;

    @Resource
    RedisUtil redisUtil;

    @PutMapping("/role")
    @GodToken
    public Result changeRole(@RequestBody @Validated ChangeRoleBO roleBO){
        RoleUtil role = roleService.checkRole(roleBO.getRole());
        User user = new User();
        user.setUserId(roleBO.getUserId());
        user.setRole(role.getRole());
        Account account = accountService.selectByUserId(roleBO.getUserId());
        medicalChainService.changeRole(account,role);
        userService.updateByPrimaryKeySelective(user);
        redisUtil.del(RedisKeyUtil.RESOURCE_ALL.getKey());
        redisUtil.del(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey());
        Set<Object> resources = redisUtil.sget(RedisKeyUtil.USER_RESOURCE_TRANSACTION.getKey()
                + "::" + roleBO.getUserId().toString());
        for (Object r:resources){
            redisUtil.hdel(RedisKeyUtil.RESOURCE_TRANSACTION.getKey(), (String) r);
        }
        return Result.OK().build();
    }

    @PostMapping("/resource")
    @UserLoginToken
    public Result registerResource(@RequestBody @Validated(Insert.class) ResourceBO resourceBO,
                                 @RequestHeader("token")String token){
        Integer userId=tokenService.getUserId(token);
        Account account = accountService.selectByUserId(
                userId
        );
        medicalChainService.registerResource(account,resourceBO);
        redisUtil.del(RedisKeyUtil.RESOURCE_ALL.getKey());
        redisUtil.del(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_USER.getKey(), userId.toString());
        return Result.OK().build();
    }

    @PutMapping("/transfter")
    @UserLoginToken
    public Result transferResource(@RequestBody @Validated TransferBO transferBO,
                                   @RequestHeader("token")String token){
        Integer fromUserId = tokenService.getUserId(token);
        Account account = accountService.selectByUserId(
                fromUserId
        );
        Account toAccount = accountService.selectByUserId(transferBO.getToUserId());
        medicalChainService.transferResource(account,toAccount,transferBO.getResourceId(),transferBO.getInfo());
        redisUtil.del(RedisKeyUtil.RESOURCE_ALL.getKey());
        redisUtil.del(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_USER.getKey(), fromUserId.toString());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_USER.getKey(), transferBO.getToUserId().toString());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_TRANSACTION.getKey(), transferBO.getResourceId().toString());
        return Result.OK().build();
    }

    @DeleteMapping("/resource")
    @UserLoginToken
    public Result deleteResource(@RequestBody @Validated DeleteResourceBO deleteResourceBO,
                                 @RequestHeader("token")String token){
        Integer userId = tokenService.getUserId(token);
        Account account = accountService.selectByUserId(
                userId
        );
        medicalChainService.deleteResource(account,deleteResourceBO.getResourceId(),deleteResourceBO.getInfo());
        redisUtil.del(RedisKeyUtil.RESOURCE_ALL.getKey());
        redisUtil.del(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_USER.getKey(), userId.toString());
        redisUtil.hdel(RedisKeyUtil.RESOURCE_TRANSACTION.getKey(), deleteResourceBO.getResourceId().toString());
        return Result.OK().build();
    }

    @GetMapping("/transactions")
    @PassToken
    public Result getTransactionList(@RequestParam("resourceId")BigInteger resourceId){
        List<TransactionDTO> transactions;
        transactions = (List<TransactionDTO>)
                redisUtil.hget(RedisKeyUtil.RESOURCE_TRANSACTION.getKey(), resourceId.toString());
        if (transactions != null){
            return Result.OK().data(transactions).build();
        }
        transactions = medicalChainService.getTransactionListByResourceId(resourceId);
        redisUtil.hset(RedisKeyUtil.RESOURCE_TRANSACTION.getKey(), resourceId.toString(),transactions);
        for (TransactionDTO t:transactions){
            redisUtil.sadd(RedisKeyUtil.USER_RESOURCE_TRANSACTION.getKey()
                    + "::" + t.getFrom().getUserId().toString(),resourceId.toString());
            redisUtil.sadd(RedisKeyUtil.USER_RESOURCE_TRANSACTION.getKey()
                    + "::" + t.getTo().getUserId().toString(),resourceId.toString());
        }
        return Result.OK()
                .data(transactions)
                .build();
    }

    @GetMapping("/myResources")
    @UserLoginToken
    public Result getMyResources(@RequestHeader("token")String token){
        List<ResourceBO> resources;
        Integer userId = tokenService.getUserId(token);
        resources = (List<ResourceBO>) redisUtil.hget(RedisKeyUtil.RESOURCE_USER.getKey(),userId.toString());
        if (resources != null){
            return Result.OK().data(resources).build();
        }
        Account account = accountService.selectByUserId(
                userId
        );
        resources = medicalChainService.getMyResources(account);
        redisUtil.hset(RedisKeyUtil.RESOURCE_USER.getKey(),userId.toString(),resources);
        return Result.OK()
                .data(resources)
                .build();
    }


    @GetMapping("/resources/category")
    @PassToken
    public Result getAllResourceInfoByCategory(@RequestParam("category")String category){
        return Result.OK()
                .data(medicalChainService.getAllResourceInfoByCategory(category))
                .build();
    }

    @GetMapping("/resources/province")
    @PassToken
    public Result getAllResourceInfoByProvince(@RequestParam("province")String province){
        return Result.OK()
                .data(medicalChainService.getAllResourceInfoByProvince(province))
                .build();
    }

    @GetMapping("/resources/provinceCnt")
    @PassToken
    public Result getAllResourceInfoCntByProvince(){
        List<ProvinceCntBO> result;
        result =(List<ProvinceCntBO>) redisUtil.get(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey());
        if (result !=null){
            return Result.OK().data(result).build();
        }
        List<ResourceDTO> resources;
        resources = (List<ResourceDTO>) redisUtil.get(RedisKeyUtil.RESOURCE_ALL.getKey());
        if (resources == null){
            resources = medicalChainService.getAllResourceInfo();
            redisUtil.set(RedisKeyUtil.RESOURCE_ALL.getKey(),resources);
        }
        HashMap<String,HashMap<String,Integer>> cnt = new HashMap<>();
        for(ResourceDTO r:resources){
            if (r.getIsUsed()) continue;
            if (!cnt.containsKey(r.getProvince()))
                cnt.put(r.getProvince(),new HashMap<>());
            HashMap<String,Integer> pCnt = cnt.get(r.getProvince());
            if (!pCnt.containsKey(r.getCategory())){
                pCnt.put(r.getCategory(),r.getResourceNum().intValue());
            }else{
                pCnt.put(r.getCategory(),
                           pCnt.get(r.getCategory()) + r.getResourceNum().intValue()
                        );
            }
        }
        result = new ArrayList<>();
        for (Map.Entry<String,HashMap<String,Integer>> entry: cnt.entrySet()){
            result.add(
                    new ProvinceCntBO(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }
        redisUtil.set(RedisKeyUtil.RESOURCE_PROVINCE_CNT.getKey(),result);
        return Result.OK().data(result).build();
    }

    @GetMapping("/resources")
    @PassToken
    public Result getAllResourceInfo(){
        List<ResourceDTO> resources;
        resources = (List<ResourceDTO>) redisUtil.get(RedisKeyUtil.RESOURCE_ALL.getKey());
        if (resources != null){
            return Result.OK().data(resources).build();
        }
        resources = medicalChainService.getAllResourceInfo();
        redisUtil.set(RedisKeyUtil.RESOURCE_ALL.getKey(),resources);
        return Result.OK()
                .data(resources)
                .build();
    }

}
