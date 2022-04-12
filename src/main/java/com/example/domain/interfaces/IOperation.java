package com.example.domain.interfaces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.example.domain.Entity;
import com.example.domain.Fee;

import org.joda.money.Money;

public interface IOperation extends Entity {
    public IOperation Debit(Money grossAmount, ArrayList<Fee> fees);

    public IOperation Credit(Money grossAmount, ArrayList<Fee> fees);
}
