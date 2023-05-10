package com.springbatch.enviopromocoesclientes.processor;

import com.springbatch.enviopromocoesclientes.dominio.InteresseProdutoCliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class ProcessarEmailProdutoClienteProcessor implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {
    @Override
    public SimpleMailMessage process(InteresseProdutoCliente interesseProdutoCliente) throws Exception {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("xpto@no-reply.com");
        email.setTo(interesseProdutoCliente.getCliente().getEmail());
        email.setSubject("Promoção Impedível!!!!");
        email.setText(gerarTextoPromocao(interesseProdutoCliente));
        return email;
    }

    private String gerarTextoPromocao(InteresseProdutoCliente interesseProdutoCliente) {
        StringBuilder writer = new StringBuilder();
        writer.append(String.format("Olá, %s!\n\n", interesseProdutoCliente.getCliente().getNome()));
        writer.append("Essa promoção pode ser do seu interesse:\n\n");
        writer.append(String.format("%s - %\n\n", interesseProdutoCliente.getProduto().getNome(), interesseProdutoCliente.getProduto().getDescricao()));
        writer.append(String.format("Por apenas: %s",
                NumberFormat.getCurrencyInstance().format(interesseProdutoCliente.getProduto().getPreco())));
        return writer.toString();
    }
}