package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offset = (page - 1) * size;
        return aluguelRepository.findAll(size, offset);
    }

    public Optional<Aluguel> findAluguelById(Long id) {
        return aluguelRepository.findById(id);
    }

    public void saveAluguel(Aluguel aluguel) {
        var save = aluguelRepository.save(aluguel);

        Assert.state(save == 1, "Erro ao salvar pessoa" + aluguel.getPessoaNome());
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
}
