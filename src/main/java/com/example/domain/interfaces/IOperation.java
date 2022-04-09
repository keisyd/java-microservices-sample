package com.example.domain.interfaces;

import java.util.ArrayList;
import java.util.UUID;

import com.example.domain.Entity;
import com.example.domain.Fee;
import com.example.domain.OperationType;

import org.joda.money.Money;

public interface IOperation extends Entity {

    public UUID getWalletId();

    public String getDescription();

    public String getOperationTime();

    public String getWalletCreation();

    public String getCurrencyUnit();

    public OperationType getOperationType();

    public Money getWalletAmount();

    public Money getPreviousAmount();

    public Money getNetOperationAmount();

    public Money getGrossOperationAmount();

    public ArrayList<Fee> getFees();

    public IOperation Debit(Money grossAmount, ArrayList<Fee> fees);

    public IOperation Credit(Money grossAmount, ArrayList<Fee> fees);

}
