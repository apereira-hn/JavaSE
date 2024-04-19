// 1.3.3 Creation of the Transfer, Credit, Debit classes
package org.components;

import java.time.LocalDate;

public class Debit extends Flow {
    public Debit(String comment, String identifier, double amount, int targetAccountNumber, boolean effect, LocalDate dateOfFlow) {
        super(comment, identifier, amount, targetAccountNumber, effect, dateOfFlow);
    }

    public Debit() {
        // Default constructor
    }
}
