package com.springbatch.enviopromocoesclientes.step;

import com.springbatch.enviopromocoesclientes.dominio.InteresseProdutoCliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EnvioEmailclientesStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step envioEmailClientesStep(
            ItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader,
            ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> processarEmailProdutoCliente,
            ItemWriter<SimpleMailMessage> enviarEmailProdutoClienteWriter
    ) {
        return stepBuilderFactory
                .get("envioEmailClientesStep")
                .<InteresseProdutoCliente, SimpleMailMessage>chunk(1)
                .reader(lerInteresseProdutoClienteReader)
                .processor(processarEmailProdutoCliente)
                .writer(enviarEmailProdutoClienteWriter)
                .build();
    }

}
