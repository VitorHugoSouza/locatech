package br.com.fiap.locatech.locatech.repositories;

import br.com.fiap.locatech.locatech.entities.Aluguel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AluguelRepositoryImpl implements AluguelRepository {

    private final JdbcClient jdbcClient;

    public AluguelRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<Aluguel> findById(Long id) {
        return this.jdbcClient
                .sql("SELECT a.id, a.pessoa_id, a.veiculo_id, a.data_inicio, a.data_fim, a.valor_total, " +
                        "p.nome as pessoa_nome, p.cpf as pessoa_cpf, " +
                        "v.modelo as veiculo_modelo, v.placa as veiculo_palca " +
                        "FROM alugueis a " +
                        "INNER JOIN pessoas p on p.id = a.pessoa_id " +
                        "INNER JOIN veiculos v on v.id = a.veiculo_id " +
                        "WHERE a.id = :id")
                .param("id", id)
                .query(Aluguel.class)
                .optional();
    }

    @Override
    public List<Aluguel> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT a.id, a.pessoa_id, a.veiculo_id, a.data_inicio, a.data_fim, a.valor_total, " +
                        "p.nome as pessoa_nome, p.cpf as pessoa_cpf, " +
                        "v.modelo as veiculo_modelo, v.placa as veiculo_palca " +
                        "FROM alugueis a " +
                        "INNER JOIN pessoas p on p.id = a.pessoa_id " +
                        "INNER JOIN veiculos v on v.id = a.veiculo_id " +
                        "LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Aluguel.class)
                .list();
    }

    @Override
    public Integer save(Aluguel alugueis) {
        return this.jdbcClient
                .sql("INSERT INTO alugueis (pessoa_id, veiculo_id, data_inicio, data_fim, valor_total) " +
                        "VALUES (:pessoa_id, :veiculo_id, :data_inicio, :data_fim, :valor_total)")
                .param("pessoa_id", alugueis.getPessoaId())
                .param("veiculo_id", alugueis.getVeiculoId())
                .param("data_inicio", alugueis.getDataInicio())
                .param("data_fim", alugueis.getDataFim())
                .param("valor_total", alugueis.getValorTotal())
                .update();

    }

    @Override
    public Integer update(Aluguel alugueis, Long id) {
        return this.jdbcClient
                .sql("UPDATE alugueis SET pessoa_id = :pessoa_id, " +
                        "veiculo_id = :veiculo_id, " +
                        "data_inicio = :data_inicio, " +
                        "data_fim = :data_fim, " +
                        "valor_total = :valor_total " +
                        "WHERE id = :id")
                .param("id", id)
                .param("pessoa_id", alugueis.getPessoaId())
                .param("veiculo_id", alugueis.getVeiculoId())
                .param("data_inicio", alugueis.getDataInicio())
                .param("data_fim", alugueis.getDataFim())
                .param("valor_total", alugueis.getValorTotal())
                .update();
    }

    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM alugueis WHERE id = :id")
                .param("id", id)
                .update();
    }
}
