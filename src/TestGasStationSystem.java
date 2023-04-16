
import javax.xml.transform.Source;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TestGasStationSystem {
    //initialize constant object for globally use
    public static final int MAX_PRODUCT_TYPE_ITEM = 8;
    private Product[] product=new Product[MAX_PRODUCT_TYPE_ITEM];
    public boolean isValidChoice=true;
    Scanner scanner=new Scanner(System.in);
    //constructor for the system.It will initialize the product from user by inject the array of product.
    public TestGasStationSystem(Product[] productTypes) {
        product=productTypes;
    }

    private void inputCreateNewProductType(){
        //when the current number of products is over the maximum,it will prompt the error message and also prevent the ArrayIndexOutOfBoundsException
        if (Product.totalProducts>=MAX_PRODUCT_TYPE_ITEM){
            System.out.println("Your gas station did not have enough place to add a new product.");
        }else{
        //initialize the variables outside of the loop which is could optimize the performance by reducing the procedure of garbage cycling in JVM
        double productCost=0;
        String productName;
        int ProductType;
        Scanner scannerNL = new Scanner(System.in);
        do {
            try {
                System.out.println("Please Indicate the Type of Product You Want to Create:");
                System.out.println("1 - Fuel");
                System.out.println("2 - Gift Voucher");
                ProductType=Integer.parseInt(scannerNL.nextLine());
                //if the input didn't match the operation,it will prompt an error message until the user input the correct operation code
                if (ProductType>2||ProductType<1){
                    System.out.println("Please input appropriate number");
                    continue;
                }
                break;
            //catch the NumberFormatException when user input symbol and characters
            }catch (NumberFormatException e){
                System.out.println("Please input appropriate number and symbol and characters is prohibited");
            }
        }while (true);
        //run the operation 1
        if (ProductType==1){
            boolean FlagForName=false;
            do {
                System.out.println("Enter the fuel name for the new fuel product:");
                //creat a new scanner for avoiding the line change when using both nextInt and nextLine
                productName = scannerNL.nextLine();
                //when the name is null,prompt the error message
                if (productName.isEmpty()){
                    System.out.println("The name CANNOT be Empty.");
                }
                //validate if there are same name of product filtering by String class method equalsIgnoreCase()
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
                 //catch the NumberFormatException when user input symbol and characters
                } catch (NumberFormatException e) {
                    System.out.println("The cost for the new fuel is invalid.");
                }
            }while (flagForCost);
            //creat a new object and insert into the latest position of the array of product
            product[Product.totalProducts]=new Fuel(productName,productCost);
            //prompt success message
            System.out.println("The product ("+product[Product.totalProducts-1].getName()+") with cost $"+product[Product.totalProducts-1].getCost()+" per litre created.\n" +
                    "The suggested price is "+product[Product.totalProducts-1].suggestedPrice()+" per litre.");

        }if (ProductType==2){
            do {
                try {
                    System.out.println("Enter the Value of the new gift voucher:");
                    //when input the non-numeric characters,the input buffer will not be clear while using nextInt
                    productCost= Double.parseDouble(this.scanner.nextLine());
                    //when the user input correct value,end the looping.Otherwise,prompt the error message and continue the loop.
                    if (productCost>=Gift_voucher.MIN_SUGGESTED_PRICE){
                        break;
                    }else{
                        System.out.printf("The gift voucher should be higher than %.1f or equals to %.1f",Gift_voucher.MIN_SUGGESTED_PRICE,Gift_voucher.MIN_SUGGESTED_PRICE);
                        System.out.println();
                    }
                //catch the NumberFormatException when user input symbol and characters
                }catch (NumberFormatException e){
                    System.out.println("Please input an Integer");
                }
            }while (true);
            //creat a new object and insert into the latest position of the array of product
            product[Product.totalProducts]=new Gift_voucher(productCost);
            //prompt success message
            System.out.println("The gift voucher ("+product[Product.totalProducts-1].getName()+") with value $"+product[Product.totalProducts-1].getCost()+" is created.");
            System.out.println("The suggested price is "+product[Product.totalProducts-1].suggestedPrice()+" per litre.");
        }
        }
    }

    private void inputDisplaySelling() {
        int sellOperation=0;
        double sellPrice;
        int sellQuality;
        String costString;
        //when there are no product in the system.It will prompt the error message and return to the system menu
        if (Product.totalProducts==0){
            System.out.println("There are no product selling in this system.");
            System.out.println("Please Create New Product First.");
        }else{
            //show the product details in correct format
            do {
                System.out.printf("%-3s %-9s %-6s %-13s %-6s %-6s %-6s %n","No."," Name     ","| Category |","   Cost    |","Suggested Price|","Sold Qty |","Sales");
                for (int i = 0; i < Product.totalProducts; i++) {
                    if (product[i] instanceof Gift_voucher){
                        System.out.printf("%-4d %-14s",i+1,product[i].getName());
                        System.out.printf("%-8s %-20.1f","G.V",product[i].getCost());
                    }else{
                        System.out.printf("%-4d %-14s",i+1,product[i].getName());
                        costString=Double.toString(product[i].getCost())+" Per litre";
                        System.out.printf("%-8s %-20s","Fuel",costString);
                    }
                    System.out.printf("%-14.1f %-7d %-6.1f %n",product[i].suggestedPrice(),product[i].getSoldLitre(),product[i].getTotalSales());
                }
                //input the product number and validate the value
                do {
                    try {
                        System.out.printf("Please Input the Fuel Type of Your Current Sales (1 - %d, 0 to previous menu):\n",Product.totalProducts);
                        sellOperation=Integer.parseInt(scanner.next());
                        //when the value is outlier,it will prompt the error message and continue the loop until user input the correct value
                        if (sellOperation<0||sellOperation>Product.totalProducts){
                            System.out.printf("Invalid Choice. It should be a from 1 to %d",Product.totalProducts);
                            System.out.println();
                            continue;
                        }
                        break;
                    //catch the NumberFormatException when user input symbol and characters
                    }catch (NumberFormatException e){
                        System.out.println("Please input an Integer");
                    }
                }while (true);
                //return to previous menu
                if (sellOperation==0){
                    break;
                }
                //input the selling price and validate the value
                do {
                    try {
                        System.out.println("The Selling Suggested Price ("+product[sellOperation-1].getName()+") is "+product[sellOperation-1].suggestedPrice());
                        System.out.println("Please Input the Price You Want to Sell:");
                        sellPrice=Double.parseDouble(scanner.next());
                        //continue the loop when the user input the invalid price and prompt the error message
                        if (product[sellOperation-1].suggestedPrice()>sellPrice){
                            System.out.println("Invalid Price.");
                            continue;
                        }
                        break;
                    //catch the NumberFormatException when user input symbol and characters
                    }catch (NumberFormatException e){
                        System.out.println("Please input an Integer");
                    }
                }while (true);
                //the operation for Gift_voucher object
                if (product[sellOperation-1] instanceof Gift_voucher){
                    do {
                        try {
                            System.out.println("Please Input the quantity of "+product[sellOperation-1].getName()+" You Want to Sell:");
                            sellQuality=Integer.parseInt(scanner.next());
                            //prompt error message when user input invalid Quantity
                            if (sellQuality<1){
                                System.out.println("Invalid Quantity.Quality of sell must higher than or equals to 1");
                                continue;
                            }
                            break;
                        //catch the NumberFormatException when user input symbol and characters
                        }catch (NumberFormatException e){
                            System.out.println("Invalid Quantity.");
                        }
                    }while (true);
                    //prompt success message
                    System.out.println("The Total Sales amount for Gift Voucher ("+product[sellOperation-1].getName()+") is $"+ ((Gift_voucher) product[sellOperation - 1]).sellingProduct(sellQuality,sellPrice));
                }
                //the operation for Fuel object
                if (product[sellOperation-1] instanceof Fuel){
                    double freePreLitre;
                    do{
                        try {
                            System.out.println("Please Input the Litre of "+product[sellOperation-1].getName()+" You Want to Sell:");
                            sellQuality=Integer.parseInt(scanner.next());
                            //prompt error message when user input invalid Quantity
                            if (sellQuality>Fuel.MAX_LITRE){
                                System.out.printf("As the input litre is larger than maximum litre which is  %.2f,the system will change it to the maximum by default.",Fuel.MAX_LITRE);
                            }
                            if (sellQuality<Fuel.MIN_LITRE){
                                System.out.printf("As the input litre is larger than minimum litre which is  %.2f,the system will change it to the minimum by default.",Fuel.MIN_LITRE);
                                System.out.println();
                            }
                            break;
                        //catch the NumberFormatException when user input symbol and characters
                        }catch (NumberFormatException e){
                            System.out.println("Invalid litre");
                        }
                    }while (true);
                    do {
                        try {
                            System.out.println("Please Input the Discount of "+product[sellOperation-1].getName()+" Per Litre:");
                            freePreLitre=Double.parseDouble(scanner.next());
                            //prompt error message when user input invalid discount
                            if (freePreLitre<1){
                                System.out.println("Please input the discount more than or equals to 1");
                                continue;
                            }
                            break;
                        //catch the NumberFormatException when user input symbol and characters
                        }catch (NumberFormatException e){
                            System.out.println("Invalid discount");
                        }
                    }while (true);
                    //prompt success message
                    System.out.println("The Total Sales amount for Fuel ("+product[sellOperation-1].getName()+") is $"+((Fuel) product[sellOperation-1]).sellingProduct(sellPrice,sellQuality,freePreLitre));
                    System.out.printf("Price at %.1f and buy %.1f gets 1 free.",sellPrice,freePreLitre);
                    System.out.println();
                }
            }while (sellOperation!=0);
        }
    }

    private void displayShopStatistics() {
        //initialize the variables
        int totalFuel=0;
        double totalSales=0;
        double totalCost=0;
        double totalProfit=0;
        //calculate the statistics value by summing up
        for (int i = 0; i < Product.totalProducts; i++) {
            if (product[i] instanceof Fuel){
                totalFuel+=product[i].getSoldLitre();
            }
            totalSales+=product[i].getTotalSales();
            totalCost+=product[i].getCost()*product[i].getSoldLitre();
            totalProfit+=product[i].totalProfit();
        }
        //prompt system statistics
        System.out.println("Statistics of Product Station");
        System.out.println("Total Types of product   : "+Product.totalProducts);
        System.out.println("Total Litre of fuel Sold : "+totalFuel);
        System.out.println("Total Sales              : "+totalSales);
        System.out.println("Total Cost               : "+totalCost);
        System.out.println("Total Profit             : "+totalProfit);
    }

    public void start() {
            while (true) {
                try {
                    //prompt the menu function
                    System.out.println("\nWelcome to Product Station System.");
                    System.out.println("1. : Create New Product");
                    System.out.println("2. : Display or Selling Product");
                    System.out.println("3. : Show Product Shop Statistics");
                    System.out.println("4. : Exit");
                    int choice;
                    Scanner sc = new Scanner(System.in);
                    //input operation number
                    do {
                        System.out.println("Please input your choice. (1 - 4):");
                        choice = sc.nextInt();
                        if (choice>4||choice<1){
                            System.out.println("Please input the valid number from 1 to 4");
                            System.out.println();
                            isValidChoice=false;
                        }else{
                            break;
                        }
                    } while (!isValidChoice);
                    //run menu operation method
                    if (choice == 1) {
                        this.inputCreateNewProductType();
                    } else if (choice == 2) {
                        this.inputDisplaySelling();
                    } else if (choice == 3) {
                        this.displayShopStatistics();
                    } else {
                        System.exit(0);
                    }
                //catch the InputMismatchException when user input symbol and characters
                }catch (InputMismatchException e){
                    System.out.println("Please input the valid number,no characters or symbols");
                }
            }
        }

    public static void main(String args[]) {
        //start the system
        new TestGasStationSystem(new Product[MAX_PRODUCT_TYPE_ITEM]).
                start();
    }
}
