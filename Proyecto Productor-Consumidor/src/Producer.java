import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Producer extends Thread {
    Buffer buffer;
    private int wait_MS;
    int cantidad, valMin, valMax;
    String[] operadores;
    private volatile boolean isRunning = true;
    DefaultTableModel produced;
    
    Producer(Buffer buffer, int cantidad, int valMin, int valMax, int wait_MS, String operadores, DefaultTableModel produced) {
        this.buffer = buffer;
        this.cantidad = cantidad;
        this.valMin = valMin;
        this.valMax =  valMax;
        if(wait_MS < 0){
            wait_MS=0;
        }
        if(wait_MS > 10000){
            wait_MS=10000;
        }
        if(wait_MS < 0){
            wait_MS=0;
        }
        if(wait_MS > 10000){
            wait_MS=10000;
        }
        this.wait_MS = wait_MS;
        this.operadores = operadores.split("");
        this.produced=produced;
    }
    
    private String scheme_operation(){
        Random r = new Random(System.currentTimeMillis());
        char operator = operadores[((int) (Math.random()*(this.operadores.length)))].charAt(0);
        int Value1 = (int)(Math.random() *(this.valMax - this.valMin))+ this.valMin;
        int Value2 = (int)(Math.random() *(this.valMax - this.valMin))+ this.valMin;
        return "("+operator+" "+ Value1 +" " +Value2+ ")";
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        Random r = new Random(System.currentTimeMillis());
        
        while(isRunning) {
            String product = scheme_operation();
            char operador = product.charAt(1);
            int valor1 = Character.getNumericValue(product.charAt(3));
            int valor2 = Character.getNumericValue(product.charAt(5));
            Object[]rowData={operador,valor1,valor2,this.buffer.count};
            this.produced.addRow(rowData);
            this.buffer.produce(product);
        }   
        try {
            Thread.sleep(this.wait_MS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            log("Stopped Thread");
        }
    }
    
    public void terminate(){
        log("Stopping producer...");
        isRunning = false;
    }
    
    private void log (Object obj){
        System.out.println(obj);
    }
}