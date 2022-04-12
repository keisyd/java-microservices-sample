package com.example.domain;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.example.domain.interfaces.IOperation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.joda.money.Money;

/**
 * An operation on the walletId.
 * 
 * Can be any operation, but credit has the destinationId equals walletId
 * and debit has originId equals walletId
 */
@Value
@RequiredArgsConstructor
public class Operation implements IOperation {
    @Getter
    @NonNull
    private UUID id;
    @Getter
    @NonNull
    private UUID walletId;
    @Getter
    @NonNull
    private UUID originId;
    @Getter
    @NonNull
    private UUID destinationId;
    @Getter
    @NonNull
    private String description;
    @Getter
    @NonNull
    private String operationTime;
    @Getter
    @NonNull
    private String walletCreation;
    @Getter
    @NonNull
    private String CurrencyUnit;
    @Getter
    @NonNull
    private OperationType operationType;
    @Getter
    @NonNull
    private Money walletAmount;
    @Getter
    @NonNull
    private Money previousAmount;
    @Getter
    @NonNull
    private Money netOperationAmount;
    @Getter
    @NonNull
    private Money grossOperationAmount;
    @Getter
    @NonNull
    private ArrayList<Fee> fees;

    private Operation(UUID id, UUID walletId, String description, String operationTime, String walletCreation,
            OperationType operationType, Money walletAmount, Money previousAmount, Money netOperationAmount,
            Money grossOperationAmount, ArrayList<Fee> fees) {
        this.id = id;
        this.walletId = walletId;
        this.description = description;
        this.operationTime = operationTime;
        this.walletCreation = walletCreation;
        this.operationType = operationType;
        this.walletAmount = walletAmount;
        this.previousAmount = previousAmount;
        this.netOperationAmount = netOperationAmount;
        this.grossOperationAmount = grossOperationAmount;
        this.fees = fees;
    }

    private Operation() {
    }

    public Operation Create(String currencyUnit) {
        var creationTime = LocalDate.now().toString();

        /// TODO validate currency unit

        var moneyString = currencyUnit + " 0.0";

        Money operationAmount = Money.parse(moneyString); // the amount for all money values on creation

        return new Operation(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Created Wallet",
                creationTime,
                creationTime,
                OperationType.CREATE,
                operationAmount,
                operationAmount,
                operationAmount,
                operationAmount, new ArrayList<Fee>());
    }

    private Money CalculateNetAmount(Money amount, ArrayList<Fee> opeationFees) {
        var netAmount = amount;

        for (Fee fee : opeationFees) {
            Double spFee = fee.getValue();

            netAmount = netAmount.minus(amount.multipliedBy(spFee, RoundingMode.CEILING));
        }

        return netAmount;
    }

    public Operation Debit(Money grossAmount, ArrayList<Fee> fees) {
        var operationMoment = LocalDate.now().toString();

        var netAmount = CalculateNetAmount(grossAmount, this.fees);

        var newWalletAmount = this.walletAmount.minus(netAmount);

        return new Operation(
                UUID.randomUUID(),
                this.walletId,
                "Credit",
                operationMoment,
                this.walletCreation,
                OperationType.DEBIT,
                newWalletAmount,
                this.walletAmount,
                netAmount,
                grossAmount,
                new ArrayList<Fee>());
    }

    public Operation Credit(Money grossAmount, ArrayList<Fee> fees) {
        var operationMoment = LocalDate.now().toString();

        var netAmount = CalculateNetAmount(grossAmount, this.fees);

        var newWalletAmount = this.walletAmount.plus(netAmount);

        return new Operation(
                UUID.randomUUID(),
                this.walletId,
                "Credit",
                operationMoment,
                this.walletCreation,
                OperationType.CREDIT,
                newWalletAmount,
                this.walletAmount,
                netAmount,
                grossAmount,
                new ArrayList<Fee>());
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

}
