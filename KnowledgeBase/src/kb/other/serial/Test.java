package kb.other.serial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test {
	
	static{
		System.setProperty("sun.io.serialization.extendedDebugInfo", "true");
	}
	
	public static void main(String[] args) throws Exception {
		write();
		read();
	}
	
	private static void read() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/kb/other/serial/person.serial"));
		Person person = (Person) ois.readObject();
		System.out.println(person.toString());
		ois.close();
	}
	
	private static void write() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("src/kb/other/serial/person.serial"));
			Address address = new Address();
			address.setAddress("beijing");
			Person person = new Person(25, "zuokai");
			person.setAddress(address);
			oos.writeObject(person);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}