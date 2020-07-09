package com.bigdata.boot.chapter27.sftp;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;


@RestController
public class SftpController {


    @Autowired
    private SftpService sftpService;

    @Autowired
    private SftpConfig.UploadGateway uploadGateway;

    @GetMapping("/list-file")
    public void testSftpSpringBatch(@RequestParam(name = "dir")String dir) {

        List<FileInfo> fileList = uploadGateway.listFileInfo(dir);

        for (FileInfo file : fileList) {
            String fileName = file.getFilename();
            String filePath = file.getRemoteDirectory();
            ChannelSftp.LsEntry fileInfo = (ChannelSftp.LsEntry) file.getFileInfo();
            boolean isDir = file.isDirectory();
            boolean isLink = file.isLink();
            long modifyTime = file.getModified();
            System.out.println("=============================  " + fileName);
            System.out.println("==================  " + filePath);
            System.out.println("==================  " + fileInfo.getFilename());
            System.out.println("==================  " + isDir);
            System.out.println("==================  " + isLink);
            System.out.println("==================  " + modifyTime);
        }
    }

    @GetMapping("/list-dir")
    public void testSftpSpringBatch2(@RequestParam(name = "dir")String dir) {

        List<FileInfo> fileNameList = uploadGateway.listFileName(dir);

        for (FileInfo fileName : fileNameList) {

            System.out.println("=============================  " + fileName);
        }
    }


    @GetMapping("/mget")
    public void testSftpSpringBatch3(@RequestParam(name = "pattern")String pattern) throws InterruptedException {

        List<File> fileNameList = uploadGateway.mgetFile(pattern);

        for (File fileName : fileNameList) {
            System.out.println("=============================  " + fileName);
        }
    }

    @GetMapping("/upload")
    public void testSftpSpringBatch4(@RequestParam(name = "file")String file) throws InterruptedException {

        String result = uploadGateway.putFile(new File(file));

        System.out.println("=============================  " + result);
    }

    @GetMapping("/multi-upload")
    public void testSftpSpringBatch5(@RequestParam(name = "pattern")String pattern) throws InterruptedException {

        List<String> result = uploadGateway.mputFile(new File(pattern));


        for (String fileName : result) {
            System.out.println("=============================  " + fileName);
        }
    }

    @GetMapping("/remove-file")
    public void testSftpSpringBatch6(@RequestParam(name = "file")String file) throws InterruptedException {

        boolean result = uploadGateway.removeFile(file);

        System.out.println("=============================  " + result);

    }

    @GetMapping("/rename")
    public void testSftpSpringBatch7(@RequestParam(name = "file")String file) throws InterruptedException {

        boolean result = uploadGateway.moveFile(file);

        System.out.println("=============================  " + result);

    }
}