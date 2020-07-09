package com.bigdata.boot.chapter27.sftp;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Sftp configuration.
 *
 * @Autor Song H.J.
 * @Date 2019-01-18
 */
@Configuration
@DependsOn("sftpProperty")
public class SftpConfig {

    @Resource(name ="sftpProperty" )
    private SftpProperty sftpProperty;

    private static Logger log = LoggerFactory.getLogger(SftpConfig.class);


    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port:23}")
    private int sftpPort;

    @Value("${sftp.user}")
    private String sftpUser;

    @Value("${sftp.privateKey:#{null}}")
    private org.springframework.core.io.Resource sftpPrivateKey;

    @Value("${sftp.privateKeyPassphrase:}")
    private String sftpPrivateKeyPassphrase;

    @Value("${sftp.password}")
    private String sftpPassword;

    private ExpressionParser expressionParser = new SpelExpressionParser();

   /* @Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(false);
        factory.setHost(sftpProperty.getHost());
        factory.setPort(sftpProperty.getPort());
        factory.setUser(sftpProperty.getUser());
        Properties jschProps = new Properties();
        //!important 必须配置PreferredAuthentications，否则程序控制台会询问user name 和 password。
        jschProps.put("StrictHostKeyChecking", "no");
        jschProps.put("PreferredAuthentications",
                "password,gssapi-with-mic,publickey,keyboard-interactive");
        factory.setSessionConfig(jschProps);
      //  if (sftpPassword != null) {
            factory.setPassword(sftpProperty.getPassword());
//        } else {
//            factory.setPrivateKey(sftpPrivateKey);
//            factory.setPrivateKeyPassphrase(sftpPrivateKeyPassphrase);
//        }
        factory.setAllowUnknownKeys(true);
        //        //设置缓存的属性，缓存的size(), waitTimeout().
        CachingSessionFactory<LsEntry> cachingSessionFactory =
                new CachingSessionFactory<LsEntry>(factory);
        cachingSessionFactory.setPoolSize(10);
//        cachingSessionFactory.setSessionWaitTimeout(1000);
        return cachingSessionFactory;
//        return new CachingSessionFactory<LsEntry>(factory);
    }*/

    /**
     * 创建 spring-integration-sftp session
     * 避免使用jsch原生的创建session的方式
     *
     * @return SessionFactory<LsEntry>
     */
    @Bean
    public SessionFactory<LsEntry> sftpSessionFactory(){
        System.out.println("######################################################");
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setUser(sftpProperty.getUser());
        factory.setHost(sftpProperty.getHost());
        factory.setPort(sftpProperty.getPort());
        factory.setPassword(sftpProperty.getPassword());

        Properties jschProps = new Properties();
        //!important 必须配置PreferredAuthentications，否则程序控制台会询问user name 和 password。
        jschProps.put("StrictHostKeyChecking", "no");
        jschProps.put("PreferredAuthentications",
                "password,gssapi-with-mic,publickey,keyboard-interactive");

        factory.setSessionConfig(jschProps);
        factory.setAllowUnknownKeys(true);

        //设置缓存的属性，缓存的size(), waitTimeout().
        CachingSessionFactory<LsEntry> cachingSessionFactory =
                new CachingSessionFactory<LsEntry>(factory);
//        cachingSessionFactory.setPoolSize(2000);


        return  cachingSessionFactory;
    }

    /**
     * 配置Outbound Channel Adapter.
     *
     * 实质上就是一个MessageHandler，接收Message Channel发送的信息流.
     * @return MessageHandler
     */
    @ServiceActivator(inputChannel = "fileInChannel")
    @Bean
    public SftpMessageHandler sftpMessageHandler(){
        SftpMessageHandler sftpMsgHandler = new SftpMessageHandler(sftpSessionFactory());
        sftpMsgHandler.setRemoteDirectoryExpression(
                new LiteralExpression(sftpProperty.getSftpAchievePath()));
        sftpMsgHandler.setAutoCreateDirectory(true);
        sftpMsgHandler.setCharset("UTF-8");
        return sftpMsgHandler;
    }


    /**
     * 配置 Inbound Channel Adapter
     *
     * 监控sftp服务器文件的状态。一旦由符合条件的文件生成，就将其同步到本地服务器。
     * 需要条件：inboundFileChannel的bean；轮询的机制；文件同步bean,SftpInboundFileSynchronizer；
     */
    @Bean
    @InboundChannelAdapter(value = "inboundFileChannel",
            poller = @Poller(cron = "0 1/10 * * * *", maxMessagesPerPoll = "1"))
    public MessageSource<File> fileMessageSource() {
        System.out.println("=========================================================");

        //创建sftpInboundFileSynchronizer，并绑定到message source.
        SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(sftpInboundFileSynchronizer());

        //自动创建本地文件夹
        source.setAutoCreateLocalDirectory(true);
        source.setLocalDirectory(new File(sftpProperty.getLocalDir()));

        //设置文件过滤器
        source.setLocalFilter(new AcceptOnceFileListFilter<File>());

        return source;

    }

    /**
     * 为Inbound-channel-adapter提供bean
     */
    @Primary
    @Bean
    public DirectChannel inboundFileChannel() {
        return new DirectChannel();
    }

    /**
     * SftpInboundFileSynchronizer,
     *
     *  同步sftp文件至本地服务器.
     *      <1> 可以放在service中获取bean使用.toLocal方法；
     *      <2> 也可以使用inbound-channel-adapter中，做监控文件服务器的动态。
     *
     * @return SftpInboundFileSynchronizer
     */
    @Bean(name = "synFileChannel")
    public SftpInboundFileSynchronizer sftpInboundFileSynchronizer (){

        SftpInboundFileSynchronizer fileSynchronize =
                new SftpInboundFileSynchronizer(sftpSessionFactory());
        fileSynchronize.setDeleteRemoteFiles(true);
        fileSynchronize.setPreserveTimestamp(true);

        //!important
        fileSynchronize.setRemoteDirectory(sftpProperty.getSftpRemotePath());
        fileSynchronize.setFilter(new SftpSimplePatternFileListFilter("*.*"));
        //fileSynchronize.setLocalFilenameGeneratorExpression( );
        fileSynchronize.setPreserveTimestamp(true);
        return fileSynchronize;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 配置 SFTP Outbound Gateway
     *
     * @return MessageHandler
     * 获取一组远程文件的文件名；默认是获取一组FileInfo对象；
     */
    @Bean
    @ServiceActivator(inputChannel = "listFileChannel")
    public MessageHandler handler() {
        System.out.println("=========================         listFileChannel         ================================");
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(),"ls","payload");
//        MessageChannel message = sftpOutboundGateway.getOutputChannel();
        sftpOutboundGateway.setLocalDirectory(new File(sftpProperty.getLocalDir()));
        sftpOutboundGateway.setAutoCreateLocalDirectory(true);  // TODO dynanic path
        return sftpOutboundGateway;
    }

    /**
     * 获取一组远程文件的文件名,包括文件夹，默认是包括的
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "listDirChannel")
    public MessageHandler handler2() {
        System.out.println("=========================         listDirChannel         ================================");
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(),"ls","payload");
        sftpOutboundGateway.setOptions("-dirs");
        sftpOutboundGateway.setLocalDirectory(new File(sftpProperty.getLocalDir()));
        sftpOutboundGateway.setAutoCreateLocalDirectory(true);  // TODO dynanic path
        return sftpOutboundGateway;
    }

    /**
     * 基于特定的文件模式过滤器获取多个文件
     * 递归下载所有符合的文件
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mgetChannel")
    public MessageHandler handler3() {
        System.out.println("=========================         mgetChannel        ================================");

        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(),"mget","payload");
        sftpOutboundGateway.setOptions("-R");
        sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
        System.out.println(sftpProperty.getLocalDir());
        sftpOutboundGateway.setLocalDirectory(new File(sftpProperty.getLocalDir()));
        sftpOutboundGateway.setAutoCreateLocalDirectory(true);  // TODO dynanic path
        return sftpOutboundGateway;
    }

    @Autowired
    private BeanFactory beanFactory;

    /**
     * 发送单个文件到远程服务器；
     * outbound gateway，put命令需要借助与sftpRemoteFileTemplate。
     * 看源码，可以发现outbound gateway 有多种构造函数；
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "uploadFileChannel")
    public MessageHandler handler4(){
        System.out.println("=========================         uploadFileChannel        ================================"+sftpProperty.getSftpRemoteShort());
        SftpRemoteFileTemplate  sftpRemoteFileTemplate = new SftpRemoteFileTemplate(sftpSessionFactory());
        sftpRemoteFileTemplate.setRemoteDirectoryExpression(new LiteralExpression(sftpProperty.getSftpRemoteShort()));
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpRemoteFileTemplate,"put","payload");
//        sftpOutboundGateway.setLocalDirectoryExpressionString("/get/");
        sftpOutboundGateway.setBeanFactory(beanFactory);
        return sftpOutboundGateway;
    }

    /**
     * 该命令是发送多个文件到服务器
     * 递归发送文件和子文件夹下的所有文件；
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "multiUploadChannel")
    public MessageHandler handler5(){
        System.out.println("=========================         multiUploadChannel         ================================");
        SftpRemoteFileTemplate  sftpRemoteFileTemplate = new SftpRemoteFileTemplate(sftpSessionFactory());
        sftpRemoteFileTemplate.setRemoteDirectoryExpression(new LiteralExpression(sftpProperty.getSftpRemoteShort()));


        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpRemoteFileTemplate,"mput","payload");
//        sftpOutboundGateway.setLocalDirectoryExpressionString("/get/");
//        sftpOutboundGateway.setOptions("-R");
        sftpOutboundGateway.setMputFilter(new FileListFilter<File>() {
            @Override
            public List<File> filterFiles(File[] files) {
                List<File> filted = Arrays.asList(files);
                return filted;
            }
        });
        sftpOutboundGateway.setBeanFactory(beanFactory);
        return sftpOutboundGateway;
    }

    /**
     * 删除远程文件。
     * 如果删除成功，message payload的返回值是Boolean.TRUE；否则是Boolean.FALSE。
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "removeFileChannel")
    public MessageHandler handler6(){
        System.out.println("=========================         removeFileChannel        ================================");

        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(),"rm","payload");
        sftpOutboundGateway.setBeanFactory(beanFactory);
        return sftpOutboundGateway;
    }

    /**
     * 该命令是移动文件在远程服务器上的位置。
     * 返回值：转移成功，返回true，否则是false；
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "moveFileChannel")
    public MessageHandler handler7(){
        System.out.println("=========================         moveFileChannel         ================================");
//        SftpRemoteFileTemplate  sftpRemoteFileTemplate = new SftpRemoteFileTemplate(sftpSessionFactory());
//        sftpRemoteFileTemplate.setRemoteDirectoryExpression(new LiteralExpression("/remote"));

        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(),"mv", String.format("'%s/test1'",sftpProperty.getSftpRemoteShort()));
//        sftpOutboundGateway.setRenameExpression(new LiteralExpression("/remote1"));
//        sftpOutboundGateway.setChmod(777);
//        sftpOutboundGateway.setRenameExpressionString("remote1");
        sftpOutboundGateway.setRenameExpression(new LiteralExpression("upload/test2"));

//        sftpOutboundGateway.setAutoCreateLocalDirectory(true);
        sftpOutboundGateway.setBeanFactory(beanFactory);
        return sftpOutboundGateway;
    }


    @Bean
    @ServiceActivator(inputChannel = "dynamicMoveFileChannel")
    public MessageHandler handler8(){
        return new MessageHandler(){
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {

            }
        };
    }

    @MessagingGateway
    public interface UploadGateway {

        @Gateway(requestChannel = "listFileChannel")
        List<FileInfo> listFileInfo(String dir);

        @Gateway(requestChannel = "listDirChannel")
        List<FileInfo> listFileName(String dir);

        @Gateway(requestChannel = "mgetChannel")
        List<File> mgetFile(String dir);

        @Gateway(requestChannel = "uploadFileChannel")
        String putFile(File source);

        @Gateway(requestChannel = "multiUploadChannel")
        List<String> mputFile(File directory);

        @Gateway(requestChannel = "removeFileChannel")
        boolean removeFile(String file);

        @Gateway(requestChannel = "moveFileChannel")
        boolean moveFile(String file);

        @Gateway(requestChannel = "dynamicMoveFileChannel")
        boolean dynamicMoveFile(String file);

    }

}