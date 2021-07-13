package org.fisco.bcos.scutcloud.config;

//import org.fisco.bcos.scutcloud.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单认证
        http.formLogin().loginProcessingUrl("/login")
    .successForwardUrl("/index")
    .failureForwardUrl("/loginFail")
    .loginPage("/login.html");
        http.authorizeRequests()
                .antMatchers("/login.html").permitAll() //login.html 不需要被认证
                .antMatchers("/loginFail.html").permitAll() //loginFail.html 不需要被认证
                .antMatchers("/User/register").permitAll()
                .anyRequest().permitAll();//所有的请求都必须被认证。必须登录 后才能访问。

        // 关闭 csrf 防护
        http.csrf().disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  		/* 将用户信息存储到内存中
  		   实际上不会这样做，了解下即可*/
  		auth.inMemoryAuthentication()
  				.withUser("admin")
  				.password("123456")
  				.authorities("admin");

        auth.userDetailsService(userDetailsService());
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailServiceImpl();
//    }
}
