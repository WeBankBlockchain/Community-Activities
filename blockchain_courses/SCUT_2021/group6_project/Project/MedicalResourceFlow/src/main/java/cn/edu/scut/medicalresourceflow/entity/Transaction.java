package cn.edu.scut.medicalresourceflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    String from;
    String to;
    BigInteger resourceId;
    String time;
    String info;
}
