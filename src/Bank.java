import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Bank Class is created to include a static method and a main
 * @author: Yuhang Zhao, student number: 150467199
 *
 */
public class Bank {
	/**
	 * This is a static method to read the file whose name is inputFile. More specifically, every time reading
	 * the whole line, and get every token delimitered by ",". Then utilize them to create every line as a Account
	 * object if the tokens on the line don't violate any rule:
	 * 1. If the file is empty, it will throw a EmptyFileException defined by me
	 * 2. If the file cannot be opened(file not found), it throw a NoSuchFileException
	 * 3. If a row with an incorrect number of fields, it will skip the current line to recover this error
	 * 4. If a filed with incorrect format(a accountType or accountName with a number), it will throw the 
	 *    inputInvalidException "Format error: accountType or/and accountNumber with a numeric".
	 * 5. If a filed with incorrect format(a balance with a string value), it will throw a NumberFormatException
	 *    "Format error: balance with a string value"
	 * 6. If a filed with invalid data according to system rules((no duplicate account numbers are allowed,
	      savings accounts cannot have negative balances.....saving accounts start with 8, chequing accounts 
	      7), it will throw the inputInvalidException
	 * @param inputFile(the name of file)
	 * @return a array: Account[]
	 */
	public static Account[] createAccounts(String inputFile) {
		int i = 0;
		Path path = Paths.get(inputFile);
		File file = new File(inputFile);
		Account[] array = new Account[2000]; 
		try (Scanner s = new Scanner(path)) {
			if(file.length() == 0) throw new EmptyFileException("File is empty");
				while(s.hasNextLine()) {
					try {
					String line = s.nextLine();
					String[] lineArray = line.split(",");
					
					//check if the number of fields is valid 
					if (lineArray.length < 3) {
					}
					else if(lineArray.length == 3) {
						String type = lineArray[0];
						if( !(type.equals("C") || type.equals("S"))) throw new inputInvalidException("Invalid account type value");
						String name = lineArray[1];
						Pattern p = Pattern.compile(".*\\d+.*");
						Matcher mType = p.matcher(type);
						Matcher mName = p.matcher(name);
						if(mType.matches() || mName.matches()) {
							throw new InputMismatchException("Format error: accountType or/and accountNumber with a numeric");
						}
						else {
							int number = Integer.parseInt(lineArray[2]);
							//test if there is duplicate account number
							if(i>0) {
								for(int j=0;j<i;j++) {
									if(array[j].getNumber() == number) throw new inputInvalidException("Duplicate accountNumber");
								}
							}
							//test if there is any mismatch between account type and number
							if((type.equals("S") && (number<8000 || number>8999)) || (type.equals("C") && (number<7000 || number>7999))){
								throw new inputInvalidException("Account type mismatches the account number");
							}
							
							array[i] = new Account(type, name, number);
							array[i].printAllAttributes();
							
							i++;
						}
						
		
					}
					else if (lineArray.length == 4) {
						String type = lineArray[0];
						if( !(type.equals("C") || type.equals("S"))) throw new InputMismatchException("Invalid account type value");
						String name = lineArray[1];
						Pattern p = Pattern.compile(".*\\d+.*");
						Matcher mType = p.matcher(type);
						Matcher mName = p.matcher(name);
						if(mType.matches() || mName.matches()) {
							throw new InputMismatchException("Format error: accountType or/and accountNumber with a numeric");
						}
						else {
							
							int number = Integer.parseInt(lineArray[2]);
							//test if there is duplicate account number
							if(i>0) {
								for(int j=0;j<i;j++) {
									if(array[j].getNumber() == number) throw new inputInvalidException("Duplicate accountNumber");
								}
							}
							//test if there is any mismatch between account type and number
							if((type.equals("S") && (number<8000 || number>8999)) || (type.equals("C") && (number<7000 || number>7999))){
								throw new inputInvalidException("Account type mismatches the account number");
							}
							
							double balance = Double.parseDouble(lineArray[3]);
							
							//rounded the balance to 2 decimal places
							BigDecimal bg = new BigDecimal(balance);
							double balance2= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							if((type.equals("S")) && (balance<0)) throw new inputInvalidException("Saving accounts cannot have negative balances");
							
							array[i] = new Account(type, name, number, balance2);
							array[i].printAllAttributes();
							
							i++;
						}
					
						
					}
				
			}catch(inputInvalidException e) {
				System.err.println(e);
			}catch(NumberFormatException e){
				System.err.println("Format error: balance with a string value");
			}catch(InputMismatchException e) {
				System.err.println(e);
			}
		}
		}catch(NoSuchFileException e) {
			System.err.println(e);
		}catch(EmptyFileException e) {
			System.err.println(e);
		}catch(IOException e) {
			System.err.println("Input and output errors");
		}
		System.out.printf("\nThe number of successfully create accounts is: %d", i);//show the number of successfully create accounts
		return array;
		
	}
	
	/**
	 * 
	 * @param args, String array passed into the program from the command line.  Input file will be passed 
	 * in as the first parameter. args[0] stands for the name of file.
	 * Create a Account type array accountArray to get the return array from Bank.createAccounts(args[0])
	 * 
	 */
	
	public static void main(String[] args) {
		Account[] accountArray = Bank.createAccounts(args[0]); 
	}

}
