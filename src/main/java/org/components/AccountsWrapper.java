// 2.2 XML file of account
package org.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "accounts")
public class AccountsWrapper {
    private List<Account> accounts;

    @XmlElements({@XmlElement(name = "account", type = CurrentAccount.class), @XmlElement(name = "account", type = SavingsAccount.class)})
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
