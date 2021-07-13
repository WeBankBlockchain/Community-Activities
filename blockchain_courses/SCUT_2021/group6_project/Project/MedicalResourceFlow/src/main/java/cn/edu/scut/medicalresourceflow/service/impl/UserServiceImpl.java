package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.dao.UserDao;
import cn.edu.scut.medicalresourceflow.entity.User;
import cn.edu.scut.medicalresourceflow.entity.dto.UserDTO;
import cn.edu.scut.medicalresourceflow.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    @Override
    public int insertSelective(User record) {
        return userDao.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public User selectByMail(String mail) {
        return userDao.selectByMail(mail);
    }

    @Override
    public List<UserDTO> selectByRoleNameProvince(String role, String name, String province) {
        return userDao.selectByRoleNameProvince(role,name,province);
    }

    @Override
    public List<UserDTO> selectByProvince(String province) {
        return userDao.selectByProvince(province);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int getCountByRole(String role) {
        return userDao.getCountByRole(role);
    }
}
