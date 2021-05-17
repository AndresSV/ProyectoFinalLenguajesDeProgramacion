
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    Buffer buffer;
    int cantidad;
    
    Consumer(Buffer buffer, int cantidad) {
        this.buffer = buffer;
        this.cantidad = cantidad;
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        char product;
        
        for(int i=0 ; i<cantidad ; i++) {
            product = this.buffer.consume();
            //System.out.println("Consumer consumed: " + product);
            Buffer.print("Consumer consumed: " + product);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
