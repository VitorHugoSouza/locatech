package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dto.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.entities.Pessoa;
import br.com.fiap.locatech.locatech.services.AluguelService;
import br.com.fiap.locatech.locatech.services.PessoaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private static final Logger logger = LoggerFactory.getLogger(AluguelController.class);

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping
    public ResponseEntity<List<Aluguel>> finAllAlugueis(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("Acesso endpoint aluguel");
        var alugueis = aluguelService.findAllAlugueis(page, size);
        return ResponseEntity.ok(alugueis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluguel>> findAluguelById(
            @PathVariable("id") Long id) {
        logger.info("aluguel " + id);
        var aluguel = aluguelService.findAluguelById(id);
        return ResponseEntity.ok(aluguel);
    }

    @PostMapping
    public ResponseEntity<Void> saveAluguel(
            @Valid @RequestBody AluguelRequestDTO aluguel) {
        logger.info("/aluguel " + aluguel.pessoaId());
        aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> upddateAlguel(
            @PathVariable("id") Long id,
            @RequestBody Aluguel aluguel
    ) {
        logger.info("update aluguel " + aluguel.getId());
        aluguelService.updateAluguel(aluguel, id);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("delete aluguel " + id);
        aluguelService.deleteAluguel(id);
        return ResponseEntity.ok().build();
    }
}
