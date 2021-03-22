import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
	
	CustomerRegister customerRegister = new CustomerRegister();
	
	Scanner input =new Scanner(System.in);
	
	private  String name;
	private  String phone;
	private  String status;
	
	public Customer() {}
	
	public Customer(String name, String phone, String status) {

		this.name = name;
		this.phone = phone;
		this.status = status;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void Register() {
		
		//CustomerRegister cr = new CustomerRegister();
		ArrayList<Customer> customer = new ArrayList<Customer>();
		System.out.println("Enter Customer Name : ");
		name = input.nextLine();
		
		System.out.println("Enter Customer Phone Number : ");
		phone = input.nextLine();
		
		customer.add(new Customer(name, phone, "Normal"));
		
		try {
			customerRegister.saveCustomerToFile (customer);
			System.out.println ("Cutomer Registrered in the System...");
		} catch (IOException e) {
			
			System.out.println("Exception thrown! ");
			e.printStackTrace();	
		}
	}
	
	public static String CustomerStatus(String name) throws IOException {
		 
		String resultRow = null;
		String cstatus = null;
	    BufferedReader br = new BufferedReader(new FileReader("customers.csv"));
	    String line;
	    while ( (line = br.readLine()) != null ) {
	        String[] values = line.split(",");
	        if(values[0].equals(name)) {
	            resultRow = line;
	            break;
	        }
	    }
	    br.close();
	    System.out.println(resultRow);
	    return resultRow;
		
	}
	

	 public String toString() {
         return "       "+name + "     " + phone+"       "+status;
     }
     public String toCSVString() {
    	 return name + "," + phone + "," + status;
    }

}
