import java.util.HashMap;
import java.util.Map;

public class Shop implements ShopInterface{

    private final Map<String, Integer> stock = new HashMap<>();
    private final Object lockDelivery = new Object();

    @Override
    public boolean purchase(String productName, int quantity, Customer customerType) {
        synchronized (lockDelivery) {
            if (stock.getOrDefault(productName, 0) >= quantity) {
                stock.put(productName, stock.getOrDefault(productName, 0) - quantity);
                return true;
            } else if (customerType.equals(Customer.PATIENT)) {
                    while (stock.getOrDefault(productName, 0) < quantity) {
                        try {
                            lockDelivery.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    stock.put(productName, stock.getOrDefault(productName, 0) - quantity);
                    return true;
            }
            return false;
        }
    }

    @Override
    public void deliveryOfGoods(String productName, int quantity) {
        synchronized (lockDelivery){
            if (stock.containsKey(productName)) {
                stock.put(productName, quantity + stock.get(productName));
            } else stock.put(productName, quantity);
            lockDelivery.notifyAll();
        }
    }

    @Override
    public Map<String, Integer> stockStatus() {
        return stock;
    }
}
