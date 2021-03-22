import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CheckInShop {
	
	//private int serialNo;
	private String date;
	private String time;
	private String customerName;
	private String shopName;
	
	
	CheckInShop(){}
		
	public CheckInShop(String date, String time, String customerName, String shopName) {

		//this.serialNo = serialNo;
		this.date = date;
		this.time = time;
		this.customerName = customerName;
		this.shopName = shopName;
	}
	
	
	@Override
	public String toString() {
		return date +"    "+time+"    "+customerName+"    "+shopName;
				
	}

	public String toCSVString() {
   	 return  date + "," + time + "," + customerName + "," + shopName;
   }


	public static String CheckIn(String serialNo) throws IOException {
		
		String resultRow = null;
	    BufferedReader br = new BufferedReader(new FileReader("shops.csv"));
	    String line;
	    while ( (line = br.readLine()) != null ) {
	        String[] values = line.split(",");
	        if(values[0].equals(serialNo) && values[3].equals("Normal")) {
	            resultRow = line;
	            break;
	        }
	    }
	    br.close();
	    //System.out.println(resultRow);
	    return resultRow;
	}
	
	public static void saveCheckedInDataOfCustomer(ArrayList<CheckInShop> checkInshop) throws IOException {
		
		 // read customers.csv into a list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < checkInshop.size(); i++) {
            sb.append (checkInshop.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("customersCheckIn.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);
    }
	
	public static ArrayList<CheckInShop> readSCustomerCheckInDataFromFile(String customerName) throws IOException {
        ArrayList<CheckInShop> checkInShop = new ArrayList<>();
        
        List<String> lines = Files.readAllLines(Paths.get("customersCheckIn.csv"));
        
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
          
            //items[0] is date, items[1] is time, items[2] is customer name items[3] is shop name
            String date = items[0];
            String time = items[1];
            String cname = items[2];
            String sname = items[3];
        
            if(customerName.equals(cname)) {
            	checkInShop.add(new CheckInShop(date,time,cname,sname) );
            }
           
	}
        return checkInShop;
        
	}
	
	//Read data from customersCheckIn and return the ArrayList object
	public static ArrayList<CheckInShop> readCustomerCheckInDataForMasterView() throws IOException{
		ArrayList<CheckInShop> checkInShop = new ArrayList<>();
		
		List<String> lines = Files.readAllLines(Paths.get("customersCheckIn.csv"));
		
		for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            //items[0] is date, items[1] is time, items[2] is customer name items[3] is shop name
            
            String date = items[0];
            String time = items[1];
            String cname = items[2];
            String sname = items[3];
            
            checkInShop.add(new CheckInShop(date,time,cname,sname));
		
	}
		return checkInShop;
}
}

