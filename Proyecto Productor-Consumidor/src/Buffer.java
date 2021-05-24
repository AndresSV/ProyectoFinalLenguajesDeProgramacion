import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

public class Buffer {
    
    static int count = 1;

    private Queue<String> buffer;
    private int elements;
    private int consumeCount;
    DefaultTableModel productions;
    JProgressBar progressbar;
    JSpinner jSpinner4;
    private static int mSize;
    
    Buffer(int size, DefaultTableModel productions, JProgressBar progressbar,JSpinner jSpinner4) {
        this.buffer = new LinkedList<String>();
        this.mSize = size;
        this.productions = productions;
        this.progressbar = progressbar;
        this.count = 0;
        this.consumeCount = 0;
        this.jSpinner4 = jSpinner4;
    }
    
    public void updateTable(){
        this.progressbar.setValue(this.elements);
        productions.setRowCount(0);
        for(String s : buffer) { 
            productions.addRow(new Object[]{s.charAt(1), s.charAt(3), s.charAt(5)});
             
        }
    }
    
    synchronized String consume() {
        String product = "";
        this.elements--;
        while(this.buffer.isEmpty()) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        product = this.buffer.poll();
        notify();
        updateTable();
        this.jSpinner4.setValue(++this.consumeCount);
        return product;
    }
    
    synchronized void produce(String product) {
        this.elements++;
        while(this.buffer.size()>=mSize) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buffer.add(product);
        updateTable();
        notify();
    }
    
    synchronized static void print(String string) {
        if(count>mSize){
            System.out.print("Reached Buffer Limit");
            return;
        }
            
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
}
