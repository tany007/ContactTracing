import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Shop {

	private String serialNo;
	private String name;
	private String phone;
	private String status;
	private String manager;
	
	public Shop() {}
	
	public Shop(String serialNo,String name, String phone, String status, String manager) {
		this.serialNo = serialNo;
		this.name = name;
		this.phone = phone;
		this.status = status;
		this.manager = manager;
	}
	
	
	 public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	 @Override
	public String toString() {
		return serialNo+"  "+name+"  "+phone+"  "+status+"  "+manager;
	}

	public String toCSVString() {
		 return serialNo +","+name + "," + phone+","+status+","+manager;
    }
	 
	public static ArrayList<Shop> readShopNameFromFile() throws IOException {
        ArrayList<Shop> shops = new ArrayList<>();

        // read customers.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("shops.csv"));
        
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            String serialNo = items[0];
            String name = items[1];
            String phone = items[2];
            String status = items[3];
            String manager = items[4];
            shops.add (new Shop(serialNo,name, phone, status,manager));
            
        }
        //System.out.println(shops);
        return shops;
    }
}
