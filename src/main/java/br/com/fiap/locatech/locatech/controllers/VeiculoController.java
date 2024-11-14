package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.entities.Veiculo;
import br.com.fiap.locatech.locatech.services.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veiculo", description = "Controller para crud de veículos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @Operation(
            description = "Busca todos os veículos paginados",
            summary = "Busca de veículos",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<Veiculo>> finAllVeiculos(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("Acesso endpoint veiculos");
        var veiculos = veiculoService.findAllVeiculos(page, size);
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Veiculo>> finVeiculoById(
            @PathVariable("id") Long id) {
        logger.info("veiculos " + id);
        var veiculo = veiculoService.findVeiculoById(id);
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping
    public ResponseEntity<Void> saveVeiculo(
            @RequestBody Veiculo veiculo) {
        logger.info("/veiculo " + veiculo.getModelo());
        veiculoService.saveVeiculo(veiculo);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> upddateVeiculo(
            @PathVariable("id") Long id,
            @RequestBody Veiculo veiculo
    ) {
        logger.info("update veiculo " + veiculo.getModelo());
        veiculoService.updateVeiculo(veiculo, id);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(
            @PathVariable("id") Long id
    ) {
        logger.info("delete veiculo " + id);
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.ok().build();
    }
}
