public class Fuel extends Product{

    private static final double PROFIT_MARGIN=0.4;
    private static final double MIN_SUGGESTED_PRICE=20;
    public static final double MIN_LITRE=0.5;
    public static final double MAX_LITRE=100;

    public Fuel(String name, double cost) {
        super(name, cost);
    }

    public double sellingProduct(double pricePreLitre,double litre,double freePreLitre){
        if (litre<MIN_LITRE){
            litre=MIN_LITRE;
        }
        if (litre>MAX_LITRE){
            litre=MAX_LITRE;
        }
        super.updateCurrentSales(pricePreLitre*litre,litre+((int)(litre/freePreLitre)));
         return pricePreLitre*litre;
    }
    @Override
    public double suggestedPrice() {
        if ((getCost()*(1+PROFIT_MARGIN))>MIN_SUGGESTED_PRICE){
            return getCost()*(1+PROFIT_MARGIN);
        }else{
            return MIN_SUGGESTED_PRICE;
        }
    }

    @Override
    public double totalProfit() {
        return getTotalSales()-(getCost()*getSoldLitre());
    }

    @Override
    public String showDetails() {
        return getName()+"/t/t"+"Fuel"+"/t"+getCost()+" Per litre/t/t"+suggestedPrice()+"/t"+getSoldLitre()+"/t"+getTotalSales();
    }
}
