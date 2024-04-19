// 1.2.2 Creation of the CurrentAccount and SavingsAccount
package org.components;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CurrentAccount")
public class CurrentAccount extends Account {
    public CurrentAccount(String label, Client client) {
        super(label, client);
    }

    public CurrentAccount(){
        // Default constructor
    }
}

