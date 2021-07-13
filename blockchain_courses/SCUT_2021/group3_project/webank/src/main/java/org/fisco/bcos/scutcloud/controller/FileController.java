package org.fisco.bcos.scutcloud.controller;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.fisco.bcos.scutcloud.pojo.Data;
import org.fisco.bcos.scutcloud.pojo.FileProperty;
import org.fisco.bcos.scutcloud.pojo.UploadFileResponse;
import org.fisco.bcos.scutcloud.result.Result;
import org.fisco.bcos.scutcloud.result.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/api/file")
public class FileController {
    // 设置固定的日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 将 properties 中的自定义配置注入到这里
    @Value("${file.upload.path}")
    private String upLoadFilePath;

    @Value("${file.download.path}")
    private String downLoadFilePath;

    // 默认字符集
    private static final String DEFAULT_CHARSET = "utf-8";

    @Autowired
    DataController dataController;


    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("ownerId") BigInteger ownerId, @RequestParam("credit") BigInteger credit, @RequestParam("not") String not,
                             @RequestParam("dataName") String dataName, @RequestParam("dataDescription") String dataDescription, @RequestParam("address") String address,@RequestParam("type") String type, HttpServletRequest request){

        System.out.println(ownerId);
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = format + "_" + fileName;
        // 获得绝对路径
        String absolutePath = upLoadFilePath + newFileName;
        // 得到文件保存的位置以及新文件名
        File dest = new File(absolutePath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            logger.info("上传成功，当前上传的文件保存在 {}",absolutePath);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            Data data = new Data(ownerId, null, credit, dataName, dataDescription, absolutePath,type);
            Result result = dataController.createData(data);
            if(result.getCode() == 200)
            {
                return ResultFactory.buildSuccessResult("上传成功，当前上传的文件保存在 "+absolutePath);

            }
            else
            {
                return ResultFactory.buildFailResult("upload failed");
            }
        } catch (IOException e) {
            logger.error(e.toString());
            return ResultFactory.buildFailResult("上传错误");
        }
        
    }

    @GetMapping("/download")
    public void download(@RequestParam("path") String path, final HttpServletResponse response) {
        File file = new File(path);
        // 文件名称
        String filename = file.getName();
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            //判断文件父目录是否存在
            if (file.exists()) {
                //设置返回文件信息
                response.setContentType("application/octet-stream;charset=UTF-8");
                // 将响应头中的Content-Disposition暴露出来 ， 不然前端获取不到
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                // 在响应头中的Content-Disposition里设置文件名称
                response.setHeader("Content-Disposition","attachment;filename="+filename);
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while(bis.read(buffer) != -1){
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
