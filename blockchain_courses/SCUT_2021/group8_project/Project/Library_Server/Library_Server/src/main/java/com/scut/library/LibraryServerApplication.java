package com.scut.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryServerApplication {
    public static void main(String[]args) throws Exception
    {
        try {
            fisco.client.initialize();
            fisco.client.loadBackAddr();
        }
        catch (Exception e)
        {
            fisco.client.initialize();
            fisco.client.deployBackAndRecordAddr();
        }
        SpringApplication.run(LibraryServerApplication.class,args);
    }
}
