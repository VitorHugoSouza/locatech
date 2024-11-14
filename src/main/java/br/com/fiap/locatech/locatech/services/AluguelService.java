package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dto.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundExceptions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;

    private final VeiculoRepository veiculoRepository;

    public AluguelService(AluguelRepository aluguelRepository, VeiculoRepository veiculoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offset = (page - 1) * size;
        return aluguelRepository.findAll(size, offset);
    }

    public Optional<Aluguel> findAluguelById(Long id) {

        return Optional.ofNullable(aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Aluguel não encontrado!")));
    }

    public void saveAluguel(AluguelRequestDTO aluguel) {
        var aluguelEntity = calculaAluguel(aluguel);
        var save = aluguelRepository.save(aluguelEntity);

        Assert.state(save == 1, "Erro ao salvar pessoa" + aluguel.pessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id) {
        var update = aluguelRepository.update(aluguel, id);

        if (update == 0) {
            throw new RuntimeException("Pessoa não encontrado");
        }
    }

    public void deleteAluguel(Long id) {
        var delete = aluguelRepository.delete(id);

        if (delete == 0) {
            throw new RuntimeException("Pessoa não encontrado");
        }
    }

    private Aluguel calculaAluguel(AluguelRequestDTO aluguelDTO) {
        var veiculo = veiculoRepository.findById(aluguelDTO.veiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        var qtdDias = BigDecimal.valueOf(aluguelDTO.dataFim().getDayOfYear() - aluguelDTO.dataInicio().getDayOfYear());
        var valor = veiculo.getValorDiaria().multiply(qtdDias);

        return new Aluguel(aluguelDTO, valor);
    }
}
