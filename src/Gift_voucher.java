public class Gift_voucher extends Product{

    private static final double PROFIT_MARGIN=0.9;
    public static final double MIN_SUGGESTED_PRICE=1;

    public Gift_voucher(double cost) {
        super(Integer.toString((int)cost), cost);
    }

    public double sellingProduct(int soldQuantity,double price){
        updateCurrentSales(price*soldQuantity,soldQuantity);
        return price*soldQuantity;
    }

    @Override
    public double suggestedPrice(){
        if (getCost()*PROFIT_MARGIN<MIN_SUGGESTED_PRICE){
            return MIN_SUGGESTED_PRICE;
        }
        else{
            return getCost()*PROFIT_MARGIN;
        }
    }

    @Override
    public double totalProfit() {
        return getTotalSales()-(getCost()*getSoldLitre());
    }

    @Override
    public String showDetails() {
        return getName()+"/t/t"+"voucher"+"/t"+getCost()+"/t/t"+suggestedPrice()+"/t"+getSoldLitre()+"/t"+getTotalSales();
    }
}
