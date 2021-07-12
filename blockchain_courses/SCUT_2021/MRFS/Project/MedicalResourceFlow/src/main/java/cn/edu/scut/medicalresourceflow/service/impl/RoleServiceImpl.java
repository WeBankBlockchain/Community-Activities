package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.exception.BusinessException;
import cn.edu.scut.medicalresourceflow.service.RoleService;
import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import cn.edu.scut.medicalresourceflow.util.RoleUtil;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public RoleUtil checkRole(String role) {
        switch (role){
            case "user":
                return RoleUtil.USER;
            case "hospital":
                return RoleUtil.HOSPITAL;
            case "factory":
                return RoleUtil.FACTORY;
            default:
                throw new BusinessException(ErrorCode.CUSTOMIZE_PARAM_VALIDATION_ERROR);
        }

    }
}
