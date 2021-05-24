
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.*;

public class Consumer extends Thread {
    Buffer buffer;
    private int waitMillis;
    private volatile boolean Running = true;
    DefaultTableModel consumed;

    Consumer(Buffer buffer, int ms, DefaultTableModel consumed) {
        this.buffer = buffer;
        this.waitMillis = ms;
        this.consumed = consumed;
    }

    @Override
    public void run() {
        log("Running Consumer...");
        String product;
        while(Running) {
            product = this.buffer.consume();
            int resultado_scheme = schemesolver(product);
            Buffer.print("Consumer "+ this.getId()  + " consumed: " + product+" result scheme: "+resultado_scheme);
            char operador = product.charAt(1);
            int valor1 = Character.getNumericValue(product.charAt(3));
            int valor2 = Character.getNumericValue(product.charAt(5));
            Object[]rowData={operador,valor1,valor2,resultado_scheme};
            if(valor2 == 0 && operador == '/'){
                rowData[3]="INDT";
            }
            this.consumed.addRow(rowData);
            try {
                Thread.sleep(this.waitMillis);
            } catch (InterruptedException ex) {
                log("Stopped");
            }
        }
    }

    public int schemesolver(String operacion){
        char operador = operacion.charAt(1);
        int valor1 = Character.getNumericValue(operacion.charAt(3));
        int valor2 = Character.getNumericValue(operacion.charAt(5));
        int result= 0;
        switch(operador){
            case '+':
                result= valor1 + valor2;
                break;
            case '-':
                result= valor1 - valor2;
                break;
            case '*':
                result= valor1 * valor2;
                break;
            case '/':
                if(valor2 == 0) {
                    result = 0;
                    log("indeterminado");
                } else {
                    result= valor1 / valor2;
                }
                break;
            case '^':
                result = (int) Math.pow(valor1, valor2);
                break;
            default:
                result = 0;
        }
        return  result;
    }

    public void terminate(){
        Running = false;
    }

    private void log (Object obj){
        System.out.println(obj);
    }
}
