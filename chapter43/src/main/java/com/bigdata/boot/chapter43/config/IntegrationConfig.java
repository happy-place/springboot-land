package com.bigdata.boot.chapter43.config;

import com.bigdata.boot.chapter43.web.SampleEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;
import java.util.function.Consumer;

/**
 * @ClassName Config
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/28 15:15
 * @Version 1.0
 **/

@Configuration
public class IntegrationConfig {

    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("data/input"));
        return reader;
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler writer = new FileWritingMessageHandler(
                new File("data/output"));
        writer.setExpectReply(false);
        return writer;
    }

    @Bean
    public IntegrationFlow integrationFlow(SampleEndpoint endpoint) {
        //  flow:  fileReader (by fixed rate)> inputChannel > endpoint > outputChannel > fileWriter
        return IntegrationFlows.from(fileReader(), new FixedRatePoller())
                .channel(inputChannel()).handle(endpoint).channel(outputChannel())
                .handle(fileWriter()).get();
    }

    private static class FixedRatePoller
            implements Consumer<SourcePollingChannelAdapterSpec> {
        @Override
        public void accept(SourcePollingChannelAdapterSpec spec) {
            spec.poller(Pollers.fixedRate(500));
        }

    }

}

