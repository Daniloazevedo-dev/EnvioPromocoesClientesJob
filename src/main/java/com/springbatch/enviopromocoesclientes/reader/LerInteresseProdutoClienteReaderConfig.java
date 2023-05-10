package com.springbatch.enviopromocoesclientes.reader;

import com.springbatch.enviopromocoesclientes.dominio.Cliente;
import com.springbatch.enviopromocoesclientes.dominio.InteresseProdutoCliente;
import com.springbatch.enviopromocoesclientes.dominio.Produto;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class LerInteresseProdutoClienteReaderConfig {

    @Bean
    public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
            @Qualifier("appDataSource")DataSource dataSource
            ) {
        return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
                .name("lerInteresseProdutoClienteReader")
                .dataSource(dataSource)
                .sql("select * from interesse_produto_cliente " +
                        "join cliente on (cliente = cliente.id) " +
                        "join produto on (produto = produto.id)")
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<InteresseProdutoCliente> rowMapper() {
        return new RowMapper<InteresseProdutoCliente>() {
            @Override
            public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));

                Produto produto = new Produto();
                produto.setId(rs.getInt(6));
                produto.setNome(rs.getString(7));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));

                InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente();
                interesseProdutoCliente.setCliente(cliente);
                interesseProdutoCliente.setProduto(produto);

                return interesseProdutoCliente;
            }
        };
    }

}
