package cn.edu.scut.medicalresourceflow.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class RoleAndResourceNumDTO {

    Integer user;

    Integer hospital;

    Integer factory;

    BigInteger resource;
}
