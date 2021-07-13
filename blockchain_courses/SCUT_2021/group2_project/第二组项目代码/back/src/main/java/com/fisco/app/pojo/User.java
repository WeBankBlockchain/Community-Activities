package com.fisco.app.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String pwd;
    private int role;
    private String USCC;
}

