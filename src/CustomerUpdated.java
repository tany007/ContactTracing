import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CustomerUpdated {
	
	//Customer c = new Customer();
	
	public static void saveUpdatedCustomer(ArrayList<Customer> customers ) throws IOException {
		
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++) {
            sb.append (customers.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("temp.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);
        File f = new File("customers.csv"); 
        if(f.delete()) {
        	//System.out.println("Successfully deleted customers.csv file");
        }
        else
        {
        	//System.out.println("Failed to delete customers.csv");
        }
        
        File oldName = new File("temp.csv"); 
        File newName = new File("customers.csv"); 
        if(oldName.renameTo(newName)) {
        	//System.out.println("Successfully remaned temp.csv to customers.csv file");
        }
        else {
        	//System.out.println("Failed to rename temp.csv");
        }
        
        File myObj = new File("temp.csv");
        if (myObj.createNewFile()) {
         // System.out.println("File created: " + myObj.getName());
        } else {
          //System.out.println("File already exists.");
        }
    }
	}

