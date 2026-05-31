package com.example.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransferRequest {
    @NotNull
    @Schema(description = "Recipient user ID", example = "2")
    private Long toUserId;

    @NotNull
    @Positive
    @Schema(description = "Amount to transfer", example = "50.00")
    private BigDecimal amount;
}