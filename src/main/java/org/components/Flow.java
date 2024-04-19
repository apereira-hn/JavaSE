// 1.3.2 Creation of the Flow class
package org.components;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Debit.class, name = "Debit"),
        @JsonSubTypes.Type(value = Credit.class, name = "Credit"),
        @JsonSubTypes.Type(value = Transfer.class, name = "Transfer")
})

public abstract class Flow {
    private String comment;
    private String identifier;
    private double amount;
    private int targetAccountNumber;
    private boolean effect;
    private LocalDate dateOfFlow;

    protected Flow(String comment, String identifier, double amount, int targetAccountNumber, boolean effect, LocalDate dateOfFlow) {
        this.comment = comment;
        this.identifier = identifier;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        this.dateOfFlow = dateOfFlow;
    }

    protected Flow() {
        // Default constructor
    }

    public String getComment() {
        return comment;
    }

    public String getIdentifier() {
        return identifier;
    }

    public double getAmount() {
        return amount;
    }

    public int getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public LocalDate getDateOfFlow() {
        return dateOfFlow;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTargetAccountNumber(int targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public void setDateOfFlow(LocalDate dateOfFlow) {
        this.dateOfFlow = dateOfFlow;
    }

    public boolean isEffect() {
        return effect;
    }

}
