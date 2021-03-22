import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomerSignIn {
	
	//Customer customer = new Customer();
	
	public static String CustomerLogin(String cname, String cphone) throws IOException {
				
		String resultRow = null;
	    BufferedReader br = new BufferedReader(new FileReader("customers.csv"));
	    String line;
	    while ( (line = br.readLine()) != null ) {
	        String[] values = line.split(",");
	        if(values[0].equals(cname) && values[1].equals(cphone)) {
	            resultRow = line;
	            break;
	        }
	    }
	    br.close();
	    //System.out.println(resultRow);
	    return resultRow;

	}
	
	public static ArrayList<Customer> readSCustomerFromFile() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();

        // read customers.csv into a list of lines.
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
        return customers;
    }

}
