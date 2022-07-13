import java.util.Map;

public interface ShopInterface {
    /**
     * Typ wyliczeniowy reprezentujący zachowanie klientów sklepu.
     */
    public static enum Customer {
        PATIENT, IMPATIENT
    }

    /**
     * Zlecenie zakupu produktu productName w ilości quantity. W przypadku, gdy
     * zakup zgłasza niecierpliwy klient metoda wykonywana jest na podstawie
     * aktualnego stanu magazynu. Jeśli po zakup zgłasza się klient cierpliwy, to w
     * przypadku, gdy towar jest dostępny metoda kończy się natychmiast, lecz, gdy
     * towaru brak, to wątek wykonujący metodę zostaje wstrzymany do chwili
     * pojawienia się w sklepie wystarczającej ilości sztuk produktu. Metoda nigdy
     * nie zakończy się odpowiedzią false dla klienta cierpliwego. Klient może
     * zgłosić chęć zakupu produktu, którego jeszcze nie ma w magazynie sklepie.
     * Ilość sztuk produktu wydana klientowi zmniejsza stan magazynu.
     * Po zakończeniu metody purchase stan magazynu musi uwzględniać wykonaną
     * transakcję.
     *
     * @param productName  nazwa produktu
     * @param quantity     ilość sztuk produktu
     * @param customerType typ klienta
     * @return true - towar został klientowi sprzedany, false - brak towaru
     */
    public boolean purchase(String productName, int quantity, Customer customerType);

    /**
     * Dostawa produktu do sklepu. Do sklepu dostarczany jest towar o podanej nazwie
     * i w podanej ilości. Jeśli na dostarczony właśnie towar oczekują klienci
     * cierpliwi, to dostawa powoduje zakończenie ich oczekiwania (oczywiście, o ile
     * sztuk towaru wystarczy). Po zakończeniu metody stan magazynu musi odzwierciedlać
     * wykonaną dostawę.
     *
     * @param productName nazwa produktu
     * @param quantity    ilość sztuk produktu
     */
    public void deliveryOfGoods(String productName, int quantity);

    /**
     * Aktualny stan magazynu. Stan magazynu udostępniany jest jako mapa, której
     * kluczem jest nazwa produktu. Wartości wskazywane przez klucze to ilość sztuk
     * danego produktu znajdująca się w danej chwili na magazynie.
     *
     * @return stan magazynu w postaci mapy.
     */
    public Map<String, Integer> stockStatus();
}