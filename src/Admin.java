import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Admin {
	
	
	private String name;
	private String password;

	public Admin() {
	}

	public Admin(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public static String adminLogin(String name, String password) throws IOException {
		String resultRow = null;
		BufferedReader br = new BufferedReader(new FileReader("admin.csv"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			if (values[0].equals(name) && values[1].equals(password)) {
				resultRow = line;
				break;
			}
		}
		br.close();
		// System.out.println(resultRow);
		return resultRow;
	}

	/*
	 * ArrayList<Customer> customers = new ArrayList<>();
	 * 
	 * // read customers.csv into a list of lines. List<String> lines =
	 * Files.readAllLines(Paths.get("customers.csv"));
	 * 
	 * for (int i = 0; i < lines.size(); i++) { // split a line by comma String[]
	 * items = lines.get(i).split(","); // items[0] is id, items[1] is name String
	 * name = items[0]; String phone = items[1]; String status = items[2];
	 * customers.add (new Customer(name, phone, status)); } return customers;
	 */

	public static ArrayList<Customer> FlagCustomer(int sno) throws IOException {

		// System.out.println("Below are the customers : ");

		ArrayList<Customer> customers = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get("customers.csv"));
		for (int i = 0; i < lines.size(); i++) {
			// split a line by comma
			String[] items = lines.get(i).split(",");
			// items[0] is id, items[1] is name
			String name = items[0];
			String phone = items[1];
			String status = items[2];

			customers.add(new Customer(name, phone, status));

		}
		// System.out.println("Inside customer "+customers);
		for (int k = 0; k < customers.size(); k++) {
			if ((customers.get((sno - 1)).getStatus().equals("Normal"))) {
				// System.out.println("Read if statement ");
				customers.get((sno - 1)).setStatus("case");
				System.out.println(customers.get((sno - 1)).getName() + " is flagged ");

				try {
					CustomerUpdated.saveUpdatedCustomer(customers);
					System.out.println("Cutomer Updated as CASE in the System...");
				} catch (IOException e) {

					System.out.println("Exception thrown! ");
					e.printStackTrace();
				}
				try {
					UpdateStatusInCustomerFile(customers.get((sno - 1)).getName());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("After flagging ");
				for (int j = 0; j < customers.size(); j++) {
					System.out.println((j + 1) + ": " + customers.get(j).toString());
				}

			} else
				System.out.println("Customer not avilable to mark as CASE!");
			break;
		}

		return customers;
	}

	public static void UpdateStatusInCustomerFile(String name) throws IOException, ParseException {
		ArrayList<CheckInShop> checkIn = new ArrayList<CheckInShop>();
		ArrayList<String> da = new ArrayList<String>(); // for storing the dates
		ArrayList<LocalTime> t = new ArrayList<LocalTime>(); // for storing the time
		ArrayList<Integer> in = new ArrayList<Integer>();// for storing the indexes of matched time
		ArrayList<String> customersName = new ArrayList<String>();// for storing the customers name to be mark as Close
		ArrayList<String> shopsName = new ArrayList<String>();// for storing the shops name to be mark as Case

		int counter = 0;
		int ind = 0;
		List<String> lines = Files.readAllLines(Paths.get("customersCheckIn.csv"));

		/* storing dates in the arraylist da */
		for (int j = 0; j < lines.size(); j++) {
			String[] items = lines.get(j).split(",");
			String d = items[0];
			da.add(d);
		}

		
		 // storing times in the arraylist t
		  
		  for(int j = 0;j <lines.size();j++) { 
		  String[] items =
		  lines.get(j).split(",");
		  
		  String t1 = items[1];
		  
		  //parsing time from string type to date type 
		  LocalTime localTime = LocalTime.parse(t1,DateTimeFormatter.ofPattern("HH:mm:ss"));
		  t.add(localTime); 
		  }
		  //System.out.println("Displaying time from arraylist "+t);
		 
		for (int i = 0; i < lines.size(); i++) {
			// split a line by comma
			String[] items = lines.get(i).split(",");
			String date = items[0];
			String time = items[1];
			String cname = items[2];
			String sname = items[3];

			LocalTime localTime1 = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));

			LocalTime localTime2, localTime3;
			localTime2 = localTime1.plusHours(1);
			localTime3 = localTime1.minusHours(1);

			if (name.equals(cname)) {
				for (int j = 0; j < da.size(); j++) {
					if (date.equals(da.get(j))&&((t.get(j)).isAfter(localTime3) && t.get(j).isBefore(localTime2))) {
						in.add(j);
					}
					
				}
			}
		}
		

		for (int m = 0; m < lines.size(); m++) {
			String[] items = lines.get(m).split(",");
			String cn = items[2];
			String sn = items[3];
			for (int n = 0; n < in.size(); n++) {
				if (in.get(n) == m) {
					// in[n]
					customersName.add(cn);
					shopsName.add(sn);
				}
			}

		}
		ArrayList<String> newCustomersNameList = new ArrayList<String>();
		newCustomersNameList = removeDuplicates(customersName);
		newCustomersNameList.remove(name);

		ArrayList<String> newShopsNameList = new ArrayList<String>();
		newShopsNameList = removeDuplicates(shopsName);
		
		ArrayList<Customer> customers = new ArrayList<>();
		List<String> newline = Files.readAllLines(Paths.get("customers.csv"));
		for (int r = 0; r < newline.size(); r++) {
			String[] items = newline.get(r).split(",");
			String cname = items[0];
			String phone = items[1];
			String status = items[2];

			customers.add(new Customer(cname, phone, status));
		}
		// System.out.println("After adding customers "+customers);
		for (int r = 0; r < customers.size(); r++) {
			// System.out.println("Outside For loop ");
			for (int x = 0; x < newCustomersNameList.size(); x++)
				if ((customers.get(r).getName()).equals(newCustomersNameList.get(x))) {
					customers.get(r).setStatus("Close");
					break;
				}

		}
				
		CustomerUpdated.saveUpdatedCustomer(customers);
		System.out.println("Updated the Close customers in the file ");
		
		/* setting Shop as case */
		ArrayList<Shop> shops =  new ArrayList<Shop>();
		List<String> newline1 = Files.readAllLines(Paths.get("shops.csv")); 
		for (int s = 0; s < newline1.size(); s++) {
			String[] items = newline1.get(s).split(",");
			
		String serialNo = items[0];
		String shopname = items[1];
		String shopphone =  items[2];
		String shopstatus = items[3];
		String smanager = items[4];
		
		shops.add(new Shop(serialNo,shopname,shopphone, shopstatus,smanager ));
	   }	
		for(int a = 0;a<shops.size();a++) {
			for(int u = 0; u < newShopsNameList.size();u++) {
				if((shops.get(a).getName()).equals((newShopsNameList.get(u))))
				{
				shops.get(a).setStatus("case");
				//System.out.println("Shop status is set");
				}
			}
				
		}
		/* Updated shop.csv file */
		ShopUpdated.saveUpdatedShop(shops);
	}

	// Remove duplicates from a list
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
		// Create a new ArrayList
		ArrayList<T> newList = new ArrayList<T>();

		// Traverse through the first list
		for (T element : list) {

			// If this element is not present in newList
			// then add it
			if (!newList.contains(element)) {

				newList.add(element);
			}
		}

		// return the new list
		return newList;
	}

}
