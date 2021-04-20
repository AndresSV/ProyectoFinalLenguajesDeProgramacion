
package producerconsumer;

public class ProducerConsumer {

    public static void main(String[] args) {
        
        Buffer buffer = new Buffer();
        
        Producer producer = new Producer(buffer);
        producer.start();
        
        Consumer consumer = new Consumer(buffer);
        consumer.start();
    }
    
}
