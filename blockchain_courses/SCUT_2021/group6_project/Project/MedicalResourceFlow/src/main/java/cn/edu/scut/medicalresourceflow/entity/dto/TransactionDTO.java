package cn.edu.scut.medicalresourceflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    AccountDTO from;
    AccountDTO to;
    BigInteger resourceId;
    String time;
    String info;
}
