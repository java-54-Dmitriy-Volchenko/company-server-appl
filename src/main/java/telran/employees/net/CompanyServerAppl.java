package telran.employees.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import telran.employees.*;
import telran.io.Persistable;
import telran.net.Protocol;
import telran.net.TcpServer;

public class CompanyServerAppl {

	private static final String FILE_NAME = "employeesTest.data";
	private static final int PORT = 5001;

	public static void main(String[] args) {
		
		Company company = new CompanyMapsImpl();
		try {
			((Persistable)company).restore(FILE_NAME);
		} catch (Exception e) {
			
		}
		
		Protocol protocol = new CompanyProtocol(company);
		TcpServer tcpServer = new TcpServer(protocol, PORT);
		new Thread(() -> tcpServer.run()).start();
		 try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
	            while (true) {
	                System.out.println("Enter 'shutdown' to stop the server:");
	                String command = reader.readLine();
	                if ("shutdown".equalsIgnoreCase(command)) {
	                    tcpServer.shutdown();
	                    
	                    break;
	                }
	            }
	        } catch (IOException e) {
	         
	        }

	
	try {
        ((Persistable)company).save(FILE_NAME);
		} catch (Exception e) {
       
    }
  }
}