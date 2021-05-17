

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer extends Thread {
    Buffer buffer;
    int cantidad, valMin, valMax;
    
    Producer(Buffer buffer, int cantidad, int valMin, int valMax) {
        this.buffer = buffer;
        this.cantidad = cantidad;
        this.valMin = valMin;
        this.valMax =  valMax;
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        String products = "+-*/";
        Random r = new Random(System.currentTimeMillis());
        char product;
        
        for(int i=valMin; i<=valMax; i++){
            products += i;
        }
        
        System.out.println(products);
        
        for(int i=0 ; i<cantidad ; i++) {
            product = products.charAt(r.nextInt(products.length()));
            this.buffer.produce(product);
            //System.out.println("Producer produced: " + product);
            Buffer.print("Producer produced: " + product);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
