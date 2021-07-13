package org.fisco.bcos.trace;

import org.fisco.bcos.trace.controller.NewController;
//import com.library.library.dao.MessageDao;
//import org.mybatis.spring.annotation.MapperScan;
import org.fisco.bcos.trace.server.TraceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@SpringBootApplication(scanBasePackages = "org.fisco.bcos.trace")
///@MapperScan(basePackages = "com.library.library.dao", annotationClass = Repository.class)
public class ItemTrace {

    //private static MessageDao mesdao;
    public static void main(String[] args) throws Exception {
        TraceServer.initialize();
        //TraceServer.deployAssetAndRecordAddr();
        SpringApplication.run(ItemTrace.class, args);
        System.out.print("hello");
    }
}
