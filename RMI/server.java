package RMI;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class server {
    public static void main(String[] args){
        try{
            try {
                LocateRegistry.createRegistry(1099);
            } catch (Exception ignored) {}
            calculatorImpl obj = new calculatorImpl();
            Naming.rebind("rmi://localhost/calculator", obj);
            System.out.println("RMI server started...");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
