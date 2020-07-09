package com.bigdata.boot.chapter27.sftp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component("sftpProperty")
@ConfigurationProperties(prefix = "sftp")
public class SftpProperty {


    private String host;

    private Integer port;

    private String user;

    private String password;

    private Map<String,String> filePath;


    ////////////////////////////////////////////////////
    public String getSftpRemotePath(){
        return filePath.get("remote");
    }

    public String getSftpAchievePath(){
        return filePath.get("achieve");
    }

    public String getLocalDir(){
        return filePath.get("local");
    }

    public String getSftpRemoteShort(){
        return new File(filePath.get("remote")).getName();
    }

    ///////////////////////////////////////////////////
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getFilePath() {
        return filePath;
    }

    public void setFilePath(Map<String, String> filePath) {
        this.filePath = filePath;
    }
}