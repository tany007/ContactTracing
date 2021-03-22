import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ContactTracingSystemApp {
	
	
	CustomerLoginCreds customerLoginCreds = new CustomerLoginCreds();
	
	private void roleMenu() throws IOException {
		int choice = 0;
		Scanner input =new Scanner(System.in);
		
		
		
		while(true) {
			System.out.println("Input Selection (1..4): ");
			System.out.println("Select Role ");
			System.out.println("1. Admin ");
			System.out.println("2. Customer ");
			System.out.println("3. Shop");
			System.out.println("4. Exit...");
	    	choice = input.nextInt();
		switch(choice)
		{
		case 1:
				adminMenu();
				break;
		case 2:
				customerMenu();
			break;
		case 3:
			shopMenu();
		case 4:
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Input");
		}
	}
	}
	
	private void shopMenu() throws IOException {
		Scanner input =new Scanner(System.in);
		int choice = 0;
		int sno = 0;
		String serialNo = null;
		
		while(true) {
			System.out.println("Input Selection (1..2): ");
			System.out.println("1. Check Shop Status ");
			System.out.println("2. Go Back to Main Menu ");
			choice = input.nextInt();
			
		switch(choice) {
		
		case 1: 
			displayShopName();
			System.out.println("Select a Serial number to view Shop status... ");
			sno = input.nextInt();
			serialNo = Integer.toString(sno);
			displayShopStatus(serialNo);
			break;
		
	   case 2:
		   roleMenu();
		   break;
		   
	   default:
			System.out.println("Invalid Input");
			
		}
		}
		
		
	}

	private void customerMenu() throws IOException {
		int choice = 0;
		String name = null;
		Scanner input =new Scanner(System.in);
		
		while(true) {
			System.out.println("Input Selection (1..6): ");
			System.out.println(" Customers Options ");
			System.out.println("1. Customer Login ");
			System.out.println("2. Customer Register ");
			System.out.println("3. Check In A Shop ");
			System.out.println("4. History of the Shop Visited ");
			System.out.println("5. Check Status ");
			System.out.println("6. Log out ");
			System.out.println("7. Go Back to Main Menu ");
	    	choice = input.nextInt();
		switch(choice) {
		
		case 1:
				name = customerLogin();
				break;
				
				
		case 2:
				Customer c = new Customer();
				c.Register();
				break;
				
		case 3:
				//System.out.println("Please Login to check In a Shop");
			try {
				customerCheckInAShop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
	   case 4:
		   		name = customerLogin();
		   		if(!name.equals(null)) {
		   			try {
		   				
		   				displayCustomerCheckInData(name);
		   				
		   				} catch (IOException e) {
				// TODO Auto-generated catch block
		   					e.printStackTrace();
		   				}
		   		} else {
		   			System.out.println("Please login to Proceed with Check In a Shop");
		   		}
		   		break;
	   case 5:
		   	if(name!=null) {
			try {
				String resultView = Customer.CustomerStatus(name);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	}else {
		   		System.out.println("Customer logged in is required ");
		   	}
			break;
	
	   case 6:
		   	 if(name!=null){
		   		 name = null;
		   		 System.out.println("Logged out!");
		   		 roleMenu();
		   	 	}
		   	 else {
		   		 System.out.println("Customer is not logged in");
		   	 }
		   	 break;
	   case 7:
		   roleMenu();
		   break;
	   default:
		   	System.out.println("Invalid Entry");
		   	break;
		}
	}
	}
	
	private void displayCustomerCheckInData(String name) throws IOException {
		ArrayList<CheckInShop> checkInShops = CheckInShop.readSCustomerCheckInDataFromFile(name);;
		
		System.out.println("SNo.   "+"  Date  "+"  Time  "+"  Customer "+"  Shop   ");
		 for(int i=0;i < checkInShops.size();i++) {
				System.out.println((i+1) +": "+checkInShops.get(i).toString());
	        }
		}
	
	
	private void adminMenu() throws IOException {
		Scanner input =new Scanner(System.in);
		int choice = 0;
		boolean flag = false;
		
		
		
		while(true) {
			System.out.println("Input Selection (1..6): ");
			System.out.println("1. Admin Login ");
			System.out.println("2. View All Customers ");
			System.out.println("3. View All Shops ");
			System.out.println("4. View Master Visit History ");
			System.out.println("5. Flag a customer ");
			System.out.println("6. Log Out ");
			System.out.println("7. Go Back to Manin Menu ");
	    	choice = input.nextInt();
			
		switch(choice) {
		
		case 1:
				
				flag = checkAdminLogin();
				break;
		case 2:
				if(flag==true) {
					System.out.println("Below are the list of All the Customers ");
					try {
						displayAllCustomers();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					System.out.println("You need to Login as Admin ");
				}
				break;
		case 3:
				if(flag==true) {
					System.out.println("Below are the list of All Shops ");
					try {
						displayAllShops();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("You need to Login as Admin ");
				}
				break;
		
		case 4:
				if(flag==true) {
					System.out.println("Master Visit History ");
					
					try {
						displayMasterViewData();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					System.out.println("You need to Login as Admin ");
				}
				break;
				
		case 5:
			if(flag==true) {
				try {
					AdminFlagCustomerAndUpdateinFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("You need to Login as Admin ");
			}
			 break;
			 
		case 6:
				if(flag==true) {
					flag=false;
					System.out.println("Logged out !");
					roleMenu();
				}
				else {
					System.out.println("You are not logged In");
				}
				break;
		case 7:
				roleMenu();
		default:
				System.out.println("Invalid Entry ");
				break;
		}
		
	}
	}

	private void displayAllCustomers() throws IOException {
        ArrayList<Customer> customers = CustomerSignIn.readSCustomerFromFile();
        
        System.out.println("SNo.   "+"Name\t"+"Phone"+"  Status ");
        for(int i=0;i < customers.size();i++) {
			System.out.println((i+1) +": "+customers.get(i).toString());
        }
        		
	}
	
	private void displayAllShops() throws IOException {
		ArrayList<Shop> shops = Shop.readShopNameFromFile();
		
		//List<Shop> shops = new ArrayList<>();    
		System.out.println("SNo.     "+"Name     "+"Phone     "+"Manager     "+ "Status     ");
		for(Shop shop: shops){	
			System.out.println(shop.getSerialNo() + "     " + shop.getName()+"     "+shop.getPhone()+"     "+shop.getManager()+"     "+shop.getStatus());
		}
		
	}
	
	private void displayShopName() throws IOException {
		ArrayList<Shop> shops = Shop.readShopNameFromFile();
		
		System.out.println("Below are lists of the Shops");
		System.out.println("SNo.  "+" Name   ");
		for(Shop shop: shops) {
			System.out.println(shop.getSerialNo()+"     "+shop.getName());
		}
		
	}
	
	private void displayShopStatus(String sno) throws IOException {
		int serno = Integer.parseInt(sno);
		ArrayList<Shop> shops = new ArrayList<Shop>();
		List<String> lines = Files.readAllLines(Paths.get("shops.csv"));
		for(int i=0;i<lines.size();i++) {
			String[] items = lines.get(i).split(",");
			String serialNo = items[0];
			String sname = items[1];
			String sphone = items[2];
			String status = items[3];
			String manager = items[4];
			
			shops.add(new Shop(serialNo,sname,sphone,status,manager));
		}
		if(shops.size() >= serno && serno > 0) {
		 for(int j=0;j<shops.size();j++) {
			if(!((shops.get(j).getSerialNo()).equals(sno))) {
		    }
			 else {
				 System.out.println(shops.get(j).getName() + " status is "+shops.get(j).getStatus());
				System.out.println();
				break;
			} 
		 }
		}
		 else {
			 System.out.println("Invalid Serial No. ");
		 }
	}
	
	
	private void displayMasterViewData() throws IOException {
		ArrayList<CheckInShop> checkInShops = CheckInShop.readCustomerCheckInDataForMasterView();
		
		System.out.println("SNo.   "+"  Date  "+"  Time  "+"  Customer "+"  Shop   ");
		 for(int i=0;i < checkInShops.size();i++) {
				System.out.println((i+1) +": "+checkInShops.get(i).toString());
	        }
		}
	
	private void AdminFlagCustomerAndUpdateinFile() throws IOException {
		//saving the updated list in customers.csv file after flagging a customer as a "CASE" by Admin
				ArrayList<Customer> customer = new ArrayList<Customer>();
				Scanner input =new Scanner(System.in);
				
				int sno = 0;
				System.out.println("Below are the customers : ");
				ArrayList<Customer> customers = new ArrayList<>();
				List<String> lines = Files.readAllLines(Paths.get("customers.csv"));
				for (int i = 0; i < lines.size(); i++) {
		            // split a line by comma
		            String[] items = lines.get(i).split(",");
		            // items[0] is id, items[1] is name
		            String name = items[0];
		            String phone = items[1];
		            String status = items[2];
		            
		            customers.add (new Customer(name, phone, status));
		            
		            
				}
				//System.out.printf("%-8s %-10s%n%-8s %-10s%n%-8s %-10d", "Author :", author, "Title :", title, "Pages :", pages);
				
				for(int i=0;i < customers.size();i++) {
					System.out.println((i+1) +": "+customers.get(i).toString());
				}
				
				System.out.println("Select the customer To Flag as Case");
				sno = input.nextInt();
				customer = Admin.FlagCustomer(sno);
				
	}
	
	private String customerLogin() {
			
		String cname =null;
		String cphone = null;
		Scanner input =new Scanner(System.in);
		if(null != customerLoginCreds.getcName()) {
		cname = customerLoginCreds.getcName();
		cphone = customerLoginCreds.getcPhone();
		}
		else {
		
		System.out.println("Enter Customer Name "); 
		cname = input.nextLine();
	 
		System.out.println("Enter Customer Phone Number : "); 
		cphone = input.nextLine();
		}
	 
		try {
			if(CustomerSignIn.CustomerLogin(cname,cphone)!=null) {
				System.out.println("Logged in as "+cname);
				customerLoginCreds.setcName(cname);
				customerLoginCreds.setcPhone(cphone);
				
			}
			else {
				System.out.println(" Customer not available for login ");
				
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cname;
	}
	
	private boolean checkAdminLogin() throws IOException {
		Scanner input =new Scanner(System.in);
		boolean flag = false;
		
		System.out.println("Admin Login ");
		System.out.println("Enter Admin Username "); 
		String name = input.nextLine();
		System.out.println("Enter Admin Password: "); 
		String password = input.nextLine();
	 
		if(Admin.adminLogin(name, password)!=null) {
			System.out.println(" Successfully Logged in as Admin");
			flag = true;
		}
		else {
			System.out.println("Admin Authentication failed ");
			flag = false;
	 }
		return flag;
	}
	
	private void customerCheckInAShop() throws IOException {
		
		
		String name =null;
		String phone = null;
		Scanner input =new Scanner(System.in);
		if(null != customerLoginCreds.getcName()) {
		name = customerLoginCreds.getcName();
		phone = customerLoginCreds.getcPhone();
		}
		else {
			
		System.out.println("Enter Customer Name "); 
		name = input.nextLine();
	 
		System.out.println("Enter Customer Phone Number : "); 
		phone = input.nextLine();
		}
		if(CustomerSignIn.CustomerLogin(name,phone)!=null) {
			System.out.println(name + " Successfully Logged in");
			System.out.println("Check In a Shop ");
			System.out.println("Below shops available for check in :");

			ArrayList<Shop> shops = Shop.readShopNameFromFile();
			
			String shopName = null;
			//List<Shop> shops = new ArrayList<>();    
			for(Shop shop: shops){
			     System.out.println(shop.getSerialNo() +". " + shop.getName());
			}
			
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		    Date date = new Date();  
		    
		    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
		    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

		    String dateAsString = dateFormatter.format(date);
		    String timeAsString = timeFormatter.format(date);
			
			System.out.println("Select a Shop to Check In");
			String shopNo = input.nextLine();
			
			for(Shop shop: shops){
			     //System.out.println(shop.getSerialNo() +". " + shop.getName());
				 if(shopNo.equals(shop.getSerialNo()))
			     shopName = shop.getName();
			}
			
			if(CheckInShop.CheckIn(shopNo) != null) {
				System.out.println("Checked In at "+ dateFormatter.format(date)); 
			
				
				ArrayList<CheckInShop> checkInShop = new ArrayList<CheckInShop>();
				 
				//checkInShop.add(1,dateAsString,timeAsString,shopName);
				checkInShop.add(new CheckInShop(dateAsString,timeAsString,name,shopName));
				CheckInShop.saveCheckedInDataOfCustomer(checkInShop);
				ArrayList<CheckInShop> checkinshop = CheckInShop.readSCustomerCheckInDataFromFile(name);
				//System.out.println("CheckIn shop details "+checkinshop);
			}
			else {
				System.out.println("Shop is closed ");
			}
			
	        //System.out.println (shops);
			//System.out.println(Shop.readShopNameFromFile());
		}
		else {
			System.out.println("Customer Not Available for login ");
		}				
	}

	public static void main(String[] args) throws IOException {
		Scanner input =new Scanner(System.in);
		//CustomerSignIn customerSignIn = new CustomerSignIn();	
		ContactTracingSystemApp contactTracingSystemApp = new ContactTracingSystemApp();
		contactTracingSystemApp.roleMenu();
		
	}
	

	}


