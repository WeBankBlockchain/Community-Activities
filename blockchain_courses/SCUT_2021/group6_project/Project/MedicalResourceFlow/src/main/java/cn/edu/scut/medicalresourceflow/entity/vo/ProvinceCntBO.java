package cn.edu.scut.medicalresourceflow.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceCntBO {
    String province;

    HashMap<String,Integer> cnt;
}
