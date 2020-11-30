/**
 * The account class is for creating the Account type object
 * @author Yuhang Zhao, student number: 150467199
 *
 */
public class Account {
	private String type;
	private String customerName;
	private int number;
	private double balance;
	
	//two constructors
	public Account(String type, String customerName, int number, double balance)
	{
		this.type = type;
		this.customerName = customerName;
		this.number = number;
		this.balance = balance;
		
	}
	public Account(String type, String customerName, int number)
	{
		this(type,customerName,number,0);
	}
	
	//get methods
	public String getName() {
		return this.customerName;
	}
	public int getNumber() {
		return this.number;
	}
	public String getType() {
		return this.type;
	}
	public double getBalance() {
		return this.balance;
	}
	
	//print all attributes of one Account object
	public void printAllAttributes() {
		System.out.printf("%s %s %d %.2f\n", type, customerName, number, balance);
	}
}
