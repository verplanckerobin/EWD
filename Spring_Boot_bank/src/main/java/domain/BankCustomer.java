package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCustomer {

    private String id;

    private String firstname, lastname;

    private double balance;

    public BankCustomer(String id) {
	this.id = id;
    }

    public double getBalanceNoSign() {
	return Math.abs(balance);
    }
}