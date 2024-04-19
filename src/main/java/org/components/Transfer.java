// 1.3.3 Creation of the Transfer, Credit, Debit classes
package org.components;

import java.time.LocalDate;

public class Transfer extends Flow {
    private int issuingAccountNumber;

    public Transfer(String comment, String identifier, double amount, int targetAccountNumber, boolean effect, LocalDate dateOfFlow, int issuingAccountNumber) {
        super(comment, identifier, amount, targetAccountNumber, effect, dateOfFlow);
        this.issuingAccountNumber = issuingAccountNumber;
    }

    public Transfer() {
        // Default constructor
    }

    public int getIssuingAccountNumber() {
        return issuingAccountNumber;
    }

    public void setIssuingAccountNumber(int issuingAccountNumber) {
        this.issuingAccountNumber = issuingAccountNumber;
    }
}
