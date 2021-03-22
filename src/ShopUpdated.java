import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ShopUpdated {
	
	public static void saveUpdatedShop(ArrayList<Shop> shops ) throws IOException {
		
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shops.size(); i++) {
            sb.append (shops.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("tempShop.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);
        File f = new File("shops.csv"); 
        if(f.delete()) {
        	//System.out.println("Successfully deleted customers.csv file");
        }
        else
        {
        	//System.out.println("Failed to delete customers.csv");
        }
        
        File oldName = new File("tempShop.csv"); 
        File newName = new File("shops.csv"); 
        if(oldName.renameTo(newName)) {
        	//System.out.println("Successfully remaned temp.csv to customers.csv file");
        }
        else {
        	//System.out.println("Failed to rename temp.csv");
        }
        
        File myObj = new File("tempShop.csv");
        if (myObj.createNewFile()) {
         // System.out.println("File created: " + myObj.getName());
        } else {
          //System.out.println("File already exists.");
        }
    }

}
