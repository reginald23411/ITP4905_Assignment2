public class test {
    public static void main(String[] args) {
        Product[] Product = new Product[TestGasStationSystem.MAX_PRODUCT_TYPE_ITEM];
        try {
            Product[0] = new Fuel("Regular 82", 20.0);
            Product[1] = new Fuel("Regular 72", 10);
            Product[2] = new Gift_voucher(50);
            Product[3] = new Gift_voucher(100);
            Product[4] = new Gift_voucher(101);
            Product[5] = new Gift_voucher(101);
            Product[6] = new Gift_voucher(101);
            Product[7] = new Gift_voucher(102);
            //Product[0]=new Gift_voucher(50);
            //Product[1] = new Fuel("Regular 82", 10);

            //inject the products into the system
            TestGasStationSystem bs = new TestGasStationSystem(Product);
            bs.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
