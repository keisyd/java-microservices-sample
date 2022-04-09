package com.example.domain;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.example.domain.interfaces.IOperation;

import org.joda.money.Money;

public class Operation implements IOperation {
    private UUID id;
    private UUID walletId;
    private String description;
    private String operationTime;
    private String walletCreation;
    private String CurrencyUnit;
    private OperationType operationType;
    private Money walletAmount;
    private Money previousAmount;
    private Money netOperationAmount;
    private Money grossOperationAmount;
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

    public static Operation Create(String currencyUnit) {
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

    public String getOperationTime() {
        return this.operationTime;
    }

    public String getWalletCreation() {
        return this.walletCreation;
    }

    public void setWalletCreation(String walletCreation) {
        this.walletCreation = walletCreation;
    }

    public String getCurrencyUnit() {
        return this.CurrencyUnit;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public UUID getWalletId() {
        return this.walletId;
    }

    public String getDescription() {
        return this.description;
    }

    public Money getWalletAmount() {
        return this.walletAmount;
    }

    public Money getPreviousAmount() {
        return this.previousAmount;
    }

    public Money getNetOperationAmount() {
        return this.netOperationAmount;
    }

    public Money getGrossOperationAmount() {
        return this.grossOperationAmount;
    }

    public ArrayList<Fee> getFees() {
        return this.fees;
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
