
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buffer {
    
    private String buffer;
    private static int mSize;
    
    Buffer() {
        this.buffer = "";
        this.mSize=100;
    }
    Buffer(int maxSize) {
        this.buffer = "";
        if(maxSize<1)
            maxSize=1;
        if(maxSize>100)
            maxSize=100;
        this.mSize=maxSize;
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
        if(count>mSize){
            System.out.print("Reached Buffer Limit");
            return;
        }
            
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
    public String getBuffer(){
        return this.buffer;
    }
    
    
}
