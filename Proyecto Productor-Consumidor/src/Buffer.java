
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buffer {
    
    private String buffer;
    
    Buffer() {
        this.buffer = "";
    }
    
    synchronized String consume() {
        String product = "";
        
        if(this.buffer.equals("")) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        product = this.buffer;
        this.buffer = "";
        notify();
        
        return product;
    }
    
    synchronized void produce(String product) {
        if(!this.buffer.equals("")) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buffer = product;
        
        notify();
    }
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
}
