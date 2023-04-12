public abstract class Product {

    private String name;
    private double cost;
    private double totalSales;
    private int soldLitre;
    public static int totalProducts=0;

    public Product(String name, double cost) {
        this.name = name;
        this.cost = cost;
        totalProducts++;
    }

    public double updateCurrentSales(double totalPrice,double litre){
        this.totalSales+=totalPrice;
        this.soldLitre+=litre;
        return totalSales;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public int getSoldLitre() {
        return soldLitre;
    }

    public double getCost() {
        return cost;
    }
    public String getName() {
        return name;
    }
    public abstract double suggestedPrice();
    public abstract double totalProfit();
    public abstract String showDetails();
}
