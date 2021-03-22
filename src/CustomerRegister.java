import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CustomerRegister {
	

	
	public void saveCustomerToFile(ArrayList<Customer> customers) throws IOException {
        // read customers.csv into a list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++) {
            sb.append (customers.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("customers.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);
    }

}
