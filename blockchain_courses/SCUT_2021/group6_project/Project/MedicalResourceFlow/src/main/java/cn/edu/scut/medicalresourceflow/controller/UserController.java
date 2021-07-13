package cn.edu.scut.medicalresourceflow.controller;

import cn.edu.scut.medicalresourceflow.annoation.PassToken;
import cn.edu.scut.medicalresourceflow.annoation.UserLoginToken;
import cn.edu.scut.medicalresourceflow.entity.Account;
import cn.edu.scut.medicalresourceflow.entity.Email;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.bo.LoginBO;
import cn.edu.scut.medicalresourceflow.entity.bo.RegisterBO;
import cn.edu.scut.medicalresourceflow.entity.bo.ResetPwdBO;
import cn.edu.scut.medicalresourceflow.entity.bo.UpdatePwdBO;
import cn.edu.scut.medicalresourceflow.entity.dto.RoleAndResourceNumDTO;
import cn.edu.scut.medicalresourceflow.entity.dto.UserDTO;
import cn.edu.scut.medicalresourceflow.entity.vo.UserVO;
import cn.edu.scut.medicalresourceflow.service.*;
import cn.edu.scut.medicalresourceflow.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    UserService userService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    TokenService tokenService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    TemplateEngine templateEngine;

    @Resource
    EmailService emailService;

    @Resource
    AccountService accountService;

    @Resource
    MedicalChainService medicalChainService;

    @Resource
    RoleService roleService;

    @PostMapping("/login")
    @PassToken
    @Transactional
    public Result userLogin(@RequestBody @Validated LoginBO loginBo){
        User user = userService.selectByMail(loginBo.getMail());
        if (user == null){
            return Result.NULL().errorCode(ErrorCode.CUSTOMIZE_USER_NOT_EXISTED).build();
        }

        if (!passwordEncoder.matches(loginBo.getPassword(),user.getPassword())){
            return Result.NULL().errorCode(ErrorCode.CUSTOMIZE_USER_PASSWORD_ERROR).build();
        }

        Integer tokenVersion = tokenService.getUserTokenVerByPrimaryKey(user.getUserId());

        if (tokenService.updateVerByPrimaryKey(user.getUserId()) != 1){
            return Result.BAD().data("Login unsuccessfully").build();
        }

        String token = tokenService.createToken(user,tokenVersion + 1);
        return Result.OK().data(new UserVO(new UserDTO(user),token)).build();
    }

    @GetMapping("/registerCaptcha")
    @PassToken
    @Transactional
    public Result userRegisterCaptcha(@RequestParam(name = "mail") @NotNull
                                          @javax.validation.constraints.Email(message = "Mail is not correct") String mail){
        String captcha = String.format("%06d",(int)(Math.random()*1000000));
        Email serverEmail;

        if (userService.selectByMail(mail) != null){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_USER_EXISTED)
                    .build();
        }

        /**
         * @description: 设置serverEmail相关信息
         */
        Email.EmailBuilder emailBuilder = Email.NULL()
                .captcha(captcha)
                .to(mail)
                .templateEngine(templateEngine);

        emailBuilder = emailBuilder.subject(EmailOperationContextUtil.EMAIL_REGISTER_CN_ZH.getTitle())
                .operation(EmailOperationContextUtil.EMAIL_REGISTER_CN_ZH.getOperation())
                .template(EmailTemplateUtil.CAPTCHA_EMAIL_ZH_CN.getTemplate());
        serverEmail = emailBuilder.build();
        /**
         * @description: Service 服务
         * @date: 2021/2/13 23:28
         */
        emailService.sendMail(serverEmail);

        redisUtil.hset(RedisKeyUtil.CAPTCHA_REGISTER.getKey(), mail,captcha, 600000);
        return Result.OK().build();
    }

    @PostMapping("/register")
    @Transactional
    @PassToken
    public Result userRegister(@RequestBody @Validated RegisterBO registerBo){

        String captcha = (String) redisUtil.hget(RedisKeyUtil.CAPTCHA_REGISTER.getKey(), registerBo.getMail());

        if (captcha == null || !captcha.equals(registerBo.getCaptcha())){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_PARAM_CAPTCHA_ERROR)
                    .build();
        }

        if (userService.selectByMail(registerBo.getMail()) != null){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_USER_EXISTED)
                    .build();
        }

        User insertUser = new User(registerBo);
        insertUser.setRole("user");
        insertUser.setPassword(passwordEncoder.encode(registerBo.getPassword()));
        userService.insertSelective(insertUser);
        insertUser = userService.selectByMail(registerBo.getMail());
        Account account = accountService.createAccount();
        account.setUserId(insertUser.getUserId());
        accountService.insertSelective(account);
        medicalChainService.registerUser(account,insertUser);
        redisUtil.hdel(RedisKeyUtil.CAPTCHA_REGISTER.getKey(), registerBo.getMail());
        return Result.OK().data(new UserDTO(insertUser)).build();
    }

    @PostMapping("/logout")
    @UserLoginToken
    public Result logout(@RequestHeader("token") String token){
        tokenService.updateVerByPrimaryKey(tokenService.getUserId(token));
        return Result.OK().build();
    }

    @GetMapping("/list")
    @PassToken
    public Result getUserList(@RequestParam(value = "role",required = false)String roleName,
                              @RequestParam(value = "name",required = false)String name,
                              @RequestParam(value = "province",required = false)String province){
        if (roleName != null){
            RoleUtil role = roleService.checkRole(roleName);
            roleName = role.getRole();
        }
        return Result.OK()
                .data(userService.selectByRoleNameProvince(roleName,name,province))
                .build();
    }

    @GetMapping("/roleCnt")
    @PassToken
    public Result getUserCountByRole(){
        RoleAndResourceNumDTO num = new RoleAndResourceNumDTO();
        num.setUser(userService.getCountByRole(RoleUtil.USER.getRole()));
        num.setFactory(userService.getCountByRole(RoleUtil.FACTORY.getRole()));
        num.setHospital(userService.getCountByRole(RoleUtil.HOSPITAL.getRole()));
        num.setResource(medicalChainService.getResourceCount());
        return Result.OK().data(num).build();
    }

    @GetMapping("/info")
    @UserLoginToken
    public Result getUserInfo(@RequestHeader("token")String token){
        User user = userService.selectByPrimaryKey(
                tokenService.getUserId(token)
        );

        return Result.OK().data(new UserDTO(user)).build();
    }

    @GetMapping("/resetPwdCaptcha")
    @PassToken
    @Transactional
    public Result resetPwdCaptcha(@javax.validation.constraints.Email(message = "Email is not correct")  @RequestParam(name = "mail")String mail){
        String captcha = String.format("%06d",(int)(Math.random()*1000000));
        Email serverEmail;

        if (userService.selectByMail(mail) == null){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_USER_NOT_EXISTED)
                    .build();
        }

        /**
         * @description: 设置captcha相关参数
         * @date: 2021/2/13 23:10
         */

        Email.EmailBuilder emailBuilder = Email.NULL()
                .captcha(captcha)
                .to(mail)
                .templateEngine(templateEngine);

        emailBuilder = emailBuilder.subject(EmailOperationContextUtil.EMAIL_RESET_PWD_CN_ZH.getTitle())
                .operation(EmailOperationContextUtil.EMAIL_RESET_PWD_CN_ZH.getOperation())
                .template(EmailTemplateUtil.CAPTCHA_EMAIL_ZH_CN.getTemplate());
        serverEmail = emailBuilder.build();
        /**
         * @description: Service 服务
         * @date: 2021/2/13 23:28
         */
        emailService.sendMail(serverEmail);

        redisUtil.hset(RedisKeyUtil.CAPTCHA_RESET_PWD.getKey(), mail,captcha, 600000);

        return Result.OK().build();
    }


    @PostMapping("/resetPassword")
    @PassToken
    @Transactional
    public Result resetPassword(@RequestBody @Validated ResetPwdBO resetPwdBo){
        String captcha = (String) redisUtil.hget(RedisKeyUtil.CAPTCHA_RESET_PWD.getKey(), resetPwdBo.getMail());
        User updateUser = userService.selectByMail(resetPwdBo.getMail());

        if (updateUser == null){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_USER_NOT_EXISTED)
                    .build();
        }

        if (captcha == null || !captcha.equals(resetPwdBo.getCaptcha())){
            return Result.NULL()
                    .errorCode(ErrorCode.CUSTOMIZE_PARAM_CAPTCHA_ERROR)
                    .build();
        }


        updateUser.setPassword(passwordEncoder.encode(resetPwdBo.getPassword()));
        userService.updateByPrimaryKeySelective(updateUser);
        tokenService.updateVerByPrimaryKey(updateUser.getUserId());
        redisUtil.hdel(RedisKeyUtil.CAPTCHA_RESET_PWD.getKey(), resetPwdBo.getMail());
        return Result.OK().build();
    }

    @PostMapping("/updatePassword")
    @UserLoginToken
    @Transactional
    public Result updatePassword(@RequestBody @Validated UpdatePwdBO updatePwdBo,
                                 @RequestHeader("token") String token){
        User user=new User();
        user.setUserId(tokenService.getUserId(token));
        user.setPassword(passwordEncoder.encode(updatePwdBo.getPassword()));
        userService.updateByPrimaryKeySelective(user);
        tokenService.updateVerByPrimaryKey(user.getUserId());
        return Result.OK().build();
    }

}
