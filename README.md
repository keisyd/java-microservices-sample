#

# Samples on Java With Spring

This is a walk through how to apply the best practices on micro-services using Java and it's power.

# Getting started

We're simply starting as a Helo World Http route to then keep going with TDD always creating test cases before and then the application.

# The project

The application is supposed to manage Wallet Operations such as credit and debit using Event Sourcing approach for the ACID.

# Hexagonal Architecture

The aim of this Design Patter derived from DDD is to decouple the business logic from the data sources and the presentation layer. We're going to try it for the best of the micro-service isolation enabling us to expose the logic virtually in any kind of service.

# First Steps

As taught from Martin Fowler in his book [Patterns of Enterprise Application Architecture](http://martinfowler.com/eaaCatalog/money.html) we should not work with money the same way computers work with their floating point values.

That's why, the first thing to do is to import the [Joda-Money](https://www.joda.org/joda-money/) that will handle the calculation for us.

Our main concern is how to keep the instances consistent and the values immutability across different interactions. This is going to be done using some level of immutability on the class, only allowing the client to interact with it over the public operations on the interface.

For this, we create a interface that define how the interaction would be and allow us to use **Dependency Inversion **

```java
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

```

# Dependency Inversion

We are going to provide the class logic to the adapters from in injection on the interface using Spring Boot
