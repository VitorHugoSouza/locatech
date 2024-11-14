package br.com.fiap.locatech.locatech.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AluguelRequestDTO(
        @Schema( description = "Id da pessoa que está alugando o veículo")
        @NotNull(message = "O id pessoa não pode ser nulo")
        Long pessoaId,
        @NotNull(message = "O id veículo não pode ser nulo")
        Long veiculoId,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
