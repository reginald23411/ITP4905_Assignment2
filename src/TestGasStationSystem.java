
import javax.xml.transform.Source;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TestGasStationSystem {

    public static final int MAX_PRODUCT_TYPE_ITEM = 8;
    private Product[] product=new Product[MAX_PRODUCT_TYPE_ITEM];
    public boolean isValidChoice=true;
    Scanner scanner=new Scanner(System.in);
    public TestGasStationSystem(Product[] productTypes) {
        product=productTypes;
    }

    private void inputCreateNewProductType(){
        if (Product.totalProducts>=MAX_PRODUCT_TYPE_ITEM){
            System.out.println("Your gas station did not have enough place to add a new product.");
        }else{
        double productCost=0;
        String productName;
        int ProductType;
        Scanner scannerNL = new Scanner(System.in);
        System.out.println("Please Indicate the Type of Product You Want to Create:");
        System.out.println("1 - Fuel");
        System.out.println("2 - Gift Voucher");
        ProductType=Integer.parseInt(scannerNL.nextLine());
        if (ProductType==1){
            boolean FlagForName=false;
            do {
                System.out.println("Enter the fuel name for the new fuel product:");
                //creat a new scanner for avoiding the line change when using both nextInt and nextLine
                productName = scannerNL.nextLine();
                if (productName.isEmpty()){
                    System.out.println("The name CANNOT be Empty.");
                }
                for (int i = 0; i < Product.totalProducts; i++) {
                    FlagForName=productName.equalsIgnoreCase(product[i].getName());
                    if (FlagForName){
                        System.out.println("The name is already exist.");
                        break;
                    }
                }
            }while (productName.isEmpty()||FlagForName);
            boolean flagForCost=true;
            do {
                try {
                    System.out.println("Enter the cost per litre:");
                    //when input the non-numeric characters,the input buffer will not be clear while using nextInt
                    productCost = Double.parseDouble(this.scanner.next());
                    if (productCost>0){
                        flagForCost=false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The cost for the new fuel is invalid.");
                }
            }while (flagForCost);
            product[Product.totalProducts]=new Fuel(productName,productCost);
            System.out.println("The product ("+product[Product.totalProducts-1].getName()+") with cost $"+product[Product.totalProducts-1].getCost()+" per litre created.\n" +
                    "The suggested price is "+product[Product.totalProducts-1].suggestedPrice()+" per litre.");

        }if (ProductType==2){
            do {
                try {
                    System.out.println("Enter the Value of the new gift voucher:");
                    //when input the non-numeric characters,the input buffer will not be clear while using nextInt
                    productCost= Double.parseDouble(this.scanner.next());
                    if (productCost>0){
                        break;
                    }
                }catch (NumberFormatException e){
                    System.out.println("Please input an Integer");
                }
            }while (true);
            product[Product.totalProducts]=new Gift_voucher(productCost);
            System.out.println("The gift voucher ("+product[Product.totalProducts-1].getName()+") with value $"+product[Product.totalProducts-1].getCost()+" is created.");
            System.out.println("The suggested price is "+product[Product.totalProducts-1].suggestedPrice()+" per litre.");
        }
        }
    }

    private void inputDisplaySelling() {
        int sellOperation=0;
        double sellPrice;
        int sellQuality;
        if (Product.totalProducts==0){
            System.out.println("There are no product selling in this system.");
            System.out.println("Please Create New Product First.");
        }else{
            //show the product details
            do {
                System.out.println("Product Sales:");
                System.out.println("No. Name | Category | Cost | Suggested | Sold | Sales");
                System.out.println("\t\t | \t\t    | \t   | Price\t   | Qty  | \t\t");
                for (int i = 0; i < Product.totalProducts; i++) {
                    System.out.print(i+1+" ");
                    System.out.print(product[i].getName());
                    if (product[i] instanceof Gift_voucher){
                        System.out.print("\t"+"G.V");
                        System.out.print("\t"+product[i].getCost());
                    }else{
                        System.out.print("\t"+"Fuel");
                        System.out.print("\t"+product[i].getCost()+" Per litre");
                    }
                    System.out.print("\t"+product[i].suggestedPrice());
                    System.out.print("\t"+product[i].getSoldLitre());
                    System.out.print("\t"+product[i].getTotalSales());
                    System.out.println();
                }

                System.out.printf("Please Input the Fuel Type of Your Current Sales (1 - %d, 0 to previous menu):\n",Product.totalProducts);
                sellOperation=Integer.parseInt(scanner.next());
                if (sellOperation==0){
                    break;
                }
                System.out.println("The Selling Suggested Price ("+product[sellOperation-1].getName()+") is "+product[sellOperation-1].suggestedPrice());
                System.out.println("Please Input the Price You Want to Sell:");
                sellPrice=Double.parseDouble(scanner.next());
                if (product[sellOperation-1] instanceof Gift_voucher){
                    System.out.println("Please Input the quantity of "+product[sellOperation-1].getName()+" You Want to Sell:");
                    sellQuality=Integer.parseInt(scanner.next());
                    System.out.println("The Total Sales amount for Gift Voucher ("+product[sellOperation-1].getName()+") is $"+ ((Gift_voucher) product[sellOperation - 1]).sellingProduct(sellQuality,sellPrice));
                }
                if (product[sellOperation-1] instanceof Fuel){
                    double freePreLitre;
                    System.out.println("Please Input the Litre of "+product[sellOperation-1].getName()+" You Want to Sell:");
                    sellQuality=Integer.parseInt(scanner.next());
                    System.out.println("Please Input the Discount of "+product[sellOperation-1].getName()+" Per Litre:");
                    freePreLitre=Double.parseDouble(scanner.next());
                    System.out.println("The Total Sales amount for Fuel ("+product[sellOperation-1].getName()+") is $"+((Fuel) product[sellOperation-1]).sellingProduct(sellPrice,sellQuality,freePreLitre));
                    System.out.printf("Price at %.1f and buy %.1f gets 1 free.",sellPrice,freePreLitre);
                    System.out.println();
                }
            }while (sellOperation!=0);
        }
    }

    private void displayShopStatistics() {
        int totalFuel=0;
        double totalSales=0;
        double totalCost=0;
        double totalProfit=0;
        for (int i = 0; i < Product.totalProducts; i++) {
            if (product[i] instanceof Fuel){
                totalFuel+=product[i].getSoldLitre();
            }
            totalSales+=product[i].getTotalSales();
            totalCost+=product[i].getCost()*product[i].getSoldLitre();
            totalProfit+=product[i].totalProfit();
        }
        System.out.println("Statistics of Product Station");
        System.out.println("Total Types of product   : "+Product.totalProducts);
        System.out.println("Total Litre of fuel Sold : "+totalFuel);
        System.out.println("Total Sales              : "+totalSales);
        System.out.println("Total Cost               : "+totalCost);
        System.out.println("Total Profit             : "+totalProfit);
    }

    public void start() {
        try {
            while (true) {
                System.out.println("\nWelcome to Product Station System.");
                System.out.println("1. : Create New Product");
                System.out.println("2. : Display or Selling Product");
                System.out.println("3. : Show Product Shop Statistics");
                System.out.println("4. : Exit");
                int choice;
                Scanner sc = new Scanner(System.in);
                do {
                    System.out.println("Please input your choice. (1 - 4):");
                    choice = sc.nextInt();
                    if (choice>4||choice<1){
                        System.out.println("Please input the valid number from 1 to 4");
                        System.out.println();
                        isValidChoice=false;
                    }
                } while (!isValidChoice);

                if (choice == 1) {
                    this.inputCreateNewProductType();
                } else if (choice == 2) {
                    this.inputDisplaySelling();
                } else if (choice == 3) {
                    this.displayShopStatistics();
                } else {
                    System.exit(0);
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Please input the valid number,no characters or symbols");
        }
    }

    public static void main(String args[]) {
        new TestGasStationSystem(new Product[MAX_PRODUCT_TYPE_ITEM]).
                start();
    }
}
