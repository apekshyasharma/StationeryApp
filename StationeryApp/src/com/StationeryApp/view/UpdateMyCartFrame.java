/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.StationeryApp.view;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.StationeryApp.model.MyProductsInventory;
import com.StationeryApp.model.StationeryModel;
/**
 *This class hold the GUI components for the productAdminPage frame.
 * It involves various features like displaying admin Dashboard, products, viewing and modifying inventory and viewing products information in the cart.
 * Various methods are defined to initiate CRUD (Create,Read,Delete and Update) operations in Aurora Stationery inventory.
 * It displays the product information added by the user in the cart allowing user to checkout.
 * @author Apekshya Sharma 
 */
public class UpdateMyCartFrame extends javax.swing.JFrame {

    /**
     * Creates new form productAdminPage.
     * declaring instance variable auroraInventory to store products.
     */
    private final MyProductsInventory auroraInventory;
    //declaring a Set to store products selected for the cart.
    private final Set<StationeryModel>auroraCartSet;
    //Testing and displaying pre defined products and new products in the stationery inventory.
    public UpdateMyCartFrame() {
        initComponents();//Initiate GUI components.
        //Updating new inventory.
        auroraInventory= new MyProductsInventory();
        //adding pre defined products which is helpful for testing.
        auroraCartSet=new LinkedHashSet<>();//initiating a LinkedHashSet.
        auroraInventory.myProductsAddition(new StationeryModel(1,"Pack of 32 colouring pencils",10,500.00));
        auroraInventory.myProductsAddition(new StationeryModel(2,"Pack of 3 erasers and sharpners",50,50.00));
        auroraInventory.myProductsAddition(new StationeryModel(3,"Pack of scissors",5,85.00));
        auroraInventory.myProductsAddition(new StationeryModel(4,"Sketch Pencils",4,10.00));
        auroraInventory.myProductsAddition(new StationeryModel(5,"Art Supplies",1,790.00));
        auroraInventory.myProductsAddition(new StationeryModel(6,"Painting Colours",1,400.00));
        
        updateProductTable();//adds pre existing products in the inventory table.
    }
    
    /**
     * method to display updated inventory in the stationery inventory table.
     * repopulate table with newly updated inventory information.
     */
    private void updateProductTable(){
        //retrieving objects in the inventory table and displaying it.
        DefaultTableModel tableModel=(DefaultTableModel) availableProductTable.getModel();
        tableModel.setRowCount(0);//clears rows from the table.        
        //loop for adding and updating rows in the inventory table.
        for (StationeryModel startItem : auroraInventory.getEntireItems()){
            tableModel.addRow(new Object[]{
            startItem.getStatProductId(),
            startItem.getStatProductName(),
            startItem.getStatProductQuantity(),
            startItem.getStatProductPrice()
        });
        }
    }
    /**
     * This method adds selected products to the cart Set.
     * updates product cart when the add to cart button is selected.
     * @param selectedProduct defines product chosen by the user to add to the cart from the display products panel.
     */
    private void addProductToCart(StationeryModel selectedProduct){
       if(auroraCartSet.add(selectedProduct)){
           updateCartTableProducts();
           //update the current cart label in the admin dashboard panel.
           updateCartNumberLabel.setText(String.valueOf(auroraCartSet.size()));
           //display suitable message for product being added with its name.
           JOptionPane.showMessageDialog(this, selectedProduct.getStatProductName()+"has been successfully added to the cart."); 
       }else{
           JOptionPane.showMessageDialog(this, "This product already exists in the cart.");
          
       }  
    }
    
    /**
     * this method removes products from the cart.
     * @param selectedProduct represents the product the user selects to remove from the cart.
     */
    private void removeFromCart(StationeryModel selectedProduct){
        if(auroraCartSet.remove(selectedProduct)){
            updateCartTableProducts();
            updateCartNumberLabel.setText(String.valueOf(auroraCartSet.size())); // Update cart count to the label in admin dashboard panel.
            //display appropriate message.
            JOptionPane.showMessageDialog(this, selectedProduct.getStatProductName() + " has been successfully removed from the cart.");
        }
    }
    /**
     *this method updates the selected product in the cart table.
     */
    private void updateCartTableProducts(){
        DefaultTableModel myCartTableModel=(DefaultTableModel)cartProductsTable.getModel();//adds rows to the table with selected products added to the cart.
        myCartTableModel.setRowCount(0);//clears existing row in the cart table.
        //looping through every products existing in the cart.
        for (StationeryModel productItem:auroraCartSet){
            myCartTableModel.addRow(new Object[]{
              productItem.getStatProductId(),//product Id.
              productItem.getStatProductName(),//product name.
              productItem.getStatProductQuantity(),//product quantity.
              productItem.getStatProductPrice(),//product price.
            });
        }
    }
    
    /**
     * this method adds product when the button is selected and removes product when the button is selected.
     * @param selectedProduct refers to the product chosen by the user to order.
     * @param checkForSelection if true, add product to the cart else remove product from the cart.
     */
    private void toggleProductInCart(StationeryModel selectedProduct,boolean checkForSelection){
        if (checkForSelection){
            addProductToCart(selectedProduct);//adds product from cart.
        }else{
            removeFromCart(selectedProduct);//removes product from cart.
        }
    }
    
      /**
     * This method is for sorting the quantity of products in the cart table from the lowest to the highest.
     */
    public void productQuantityBeingSorted(){
        //Since a Set does't maintain order, it is implemented as an ArrayList to allow sorting.
        ArrayList <StationeryModel>itemsInCart=new ArrayList<>(auroraCartSet);
        int listSize=itemsInCart.size();//gives number of elements in the list.
        
        //Implementing the insertion sort algorithm for sorting product quantities in the cart.
        for (int index_given=1;index_given<listSize;index_given++){//outer loop starting from second element.
            StationeryModel currentItem=itemsInCart.get(index_given);//item to be inserted.
            int currentQuantity = currentItem.getStatProductQuantity();//retrieves quantity of given product.
            int previousIndex = index_given - 1;// index of the last element in the sorted portion is stored as previousIndex.
            
            /**
             *inner loop.
             *shifts sorted elements to the right.
             *shifts the element at previousIndex one position to the right.
             */
            while (previousIndex>=0&&itemsInCart.get(previousIndex).getStatProductQuantity() > currentQuantity){
                 itemsInCart.set(previousIndex + 1, itemsInCart.get(previousIndex));
                 previousIndex--;  //moves to the left.
            }
            itemsInCart.set(previousIndex + 1, currentItem);//places items in correct position in the sorted ArrayList.
        }
        auroraCartSet.clear();//Ckear previously occuring elements in the Set.
        auroraCartSet.addAll(itemsInCart);//add sorted items with correct quantity in the set.
    }
    /**
     * Implementing merge sort algorithm.
     */
    public void productIdBeingSorted() {
    // Convert the Set to a List for sorting
    ArrayList<StationeryModel> itemsInCart = new ArrayList<>(auroraCartSet);
    
    // Sort the list using merge sort
    mergeSort(itemsInCart);
    
    // Clear the original Set and add the sorted items back
    auroraCartSet.clear();
    auroraCartSet.addAll(itemsInCart);
    
    // Update the cart table to reflect the sorted order
    updateCartTableProducts();
}

private void mergeSort(ArrayList<StationeryModel> products) {
    if (products.size() <= 1) {
        return; // Base case: a list of zero or one element is already sorted
    }

    // Find the middle index
    int mid = products.size() / 2;

    // Create left and right halves
    ArrayList<StationeryModel> leftHalf = new ArrayList<>();
    ArrayList<StationeryModel> rightHalf = new ArrayList<>();

    // Fill the left half
    for (int i = 0; i < mid; i++) {
        leftHalf.add(products.get(i));
    }

    // Fill the right half
    for (int i = mid; i < products.size(); i++) {
        rightHalf.add(products.get(i));
    }

    // Recursively sort both halves
    mergeSort(leftHalf);
    mergeSort(rightHalf);

    // Merge the sorted halves back into the original list
    merge(products, leftHalf, rightHalf);
}

private void merge(ArrayList<StationeryModel> products, ArrayList<StationeryModel> left, ArrayList<StationeryModel> right) {
    int i = 0, j = 0, k = 0;

    // Merge the two halves into the original list
    while (i < left.size() && j < right.size()) {
        if (left.get(i).getStatProductId() < right.get(j).getStatProductId()) {
            products.set(k++, left.get(i++));
        } else {
            products.set(k++, right.get(j++));
        }
    }

    // If there are remaining elements in the left half, add them
    while (i < left.size()) {
        products.set(k++, left.get(i++));
    }

    // If there are remaining elements in the right half, add them
    while (j < right.size()) {
        products.set(k++, right.get(j++));
    }
}
    
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainBgPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        myCartPanel = new javax.swing.JTabbedPane();
        adminDashboardPanel = new javax.swing.JPanel();
        productPanel = new javax.swing.JPanel();
        product2Panel = new javax.swing.JPanel();
        totalProductsLabel = new javax.swing.JLabel();
        productNumLabel = new javax.swing.JLabel();
        productStatement = new javax.swing.JLabel();
        dashboardTopicLabel = new javax.swing.JLabel();
        totalSalesPanel = new javax.swing.JPanel();
        totalSales2Panel = new javax.swing.JPanel();
        totalSalesText = new javax.swing.JLabel();
        totalsPriceNumLabel = new javax.swing.JLabel();
        totalsalesStatement = new javax.swing.JLabel();
        cartPanel = new javax.swing.JPanel();
        cart2Panel = new javax.swing.JPanel();
        cartTopicLabel = new javax.swing.JLabel();
        updateCartNumberLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        barGraphTitleLabel = new javax.swing.JLabel();
        barGraphImg = new javax.swing.JLabel();
        pieChartImg = new javax.swing.JLabel();
        lineGraphImg = new javax.swing.JLabel();
        productsPanel = new javax.swing.JPanel();
        pencilColorImg = new javax.swing.JLabel();
        prodDescLabel = new javax.swing.JLabel();
        addProduct1Button = new javax.swing.JToggleButton();
        erasersimg = new javax.swing.JLabel();
        erasersDescLabel = new javax.swing.JLabel();
        addProduct2Button = new javax.swing.JToggleButton();
        scissorsImg = new javax.swing.JLabel();
        priceLabel1 = new javax.swing.JLabel();
        priceLabel2 = new javax.swing.JLabel();
        scissorsDescLabel = new javax.swing.JLabel();
        productPrice3Label = new javax.swing.JLabel();
        addProduct3Button = new javax.swing.JToggleButton();
        pencilsImg = new javax.swing.JLabel();
        productDesc4Label = new javax.swing.JLabel();
        product4PriceLabel = new javax.swing.JLabel();
        addProduct4Button = new javax.swing.JToggleButton();
        paintBrushImg = new javax.swing.JLabel();
        product5DescLabel = new javax.swing.JLabel();
        product5PriceLabel = new javax.swing.JLabel();
        addProduct5Button = new javax.swing.JToggleButton();
        paintColourImg = new javax.swing.JLabel();
        product6Description = new javax.swing.JLabel();
        product6PriceLabel = new javax.swing.JLabel();
        addProduct6Button = new javax.swing.JToggleButton();
        formfillPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        availableProductTable = new javax.swing.JTable();
        viewingProductsLabel = new javax.swing.JLabel();
        sortByPriceButton = new javax.swing.JButton();
        productInfoPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        productInfoLabel = new javax.swing.JLabel();
        imgProductInfo = new javax.swing.JLabel();
        img2ProductInfo = new javax.swing.JLabel();
        productIdLabel = new javax.swing.JLabel();
        productIdTxtField = new javax.swing.JTextField();
        productNameLabel = new javax.swing.JLabel();
        productNameTxtField = new javax.swing.JTextField();
        productQtyLabel = new javax.swing.JLabel();
        productQtyTxtField = new javax.swing.JTextField();
        productPriceLabel = new javax.swing.JLabel();
        productPriceTxtField = new javax.swing.JTextField();
        addProductButton = new javax.swing.JButton();
        removeProductButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartProductsTable = new javax.swing.JTable();
        checkoutButton = new javax.swing.JButton();
        updateProductQuantityTopic = new javax.swing.JLabel();
        productIDLabel = new javax.swing.JLabel();
        qtyProductIdTxtField = new javax.swing.JTextField();
        desiredQuantityLabel = new javax.swing.JLabel();
        desiredQtyTxtField = new javax.swing.JTextField();
        updateDesiredQtyButton = new javax.swing.JButton();
        sortByLabel = new javax.swing.JLabel();
        sortProductIdButton = new javax.swing.JButton();
        orLabel = new javax.swing.JLabel();
        productQtySortButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainBgPanel.setBackground(new java.awt.Color(102, 102, 102));
        mainBgPanel.setPreferredSize(new java.awt.Dimension(1000, 900));

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/Aurora Logo.png"))); // NOI18N

        myCartPanel.setBackground(new java.awt.Color(204, 153, 255));
        myCartPanel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

        adminDashboardPanel.setBackground(new java.awt.Color(255, 255, 102));

        productPanel.setBackground(new java.awt.Color(255, 51, 51));

        product2Panel.setBackground(new java.awt.Color(153, 0, 0));

        totalProductsLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        totalProductsLabel.setForeground(new java.awt.Color(255, 255, 255));
        totalProductsLabel.setText(" AVAILABLE PRODUCT");

        javax.swing.GroupLayout product2PanelLayout = new javax.swing.GroupLayout(product2Panel);
        product2Panel.setLayout(product2PanelLayout);
        product2PanelLayout.setHorizontalGroup(
            product2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(product2PanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(totalProductsLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        product2PanelLayout.setVerticalGroup(
            product2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(product2PanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(totalProductsLabel)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        productNumLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 60)); // NOI18N
        productNumLabel.setForeground(new java.awt.Color(204, 204, 204));
        productNumLabel.setText("6");

        productStatement.setForeground(new java.awt.Color(255, 255, 255));
        productStatement.setText("Number of products available to add in the cart.");

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(product2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(productStatement))
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(productNumLabel)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addComponent(product2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productStatement, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dashboardTopicLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        dashboardTopicLabel.setForeground(new java.awt.Color(102, 0, 0));
        dashboardTopicLabel.setText("ADMIN DASHBOARD");

        totalSalesPanel.setBackground(new java.awt.Color(153, 153, 0));

        totalSales2Panel.setBackground(new java.awt.Color(153, 255, 51));

        totalSalesText.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        totalSalesText.setForeground(new java.awt.Color(255, 255, 255));
        totalSalesText.setText("TOTAL PRICE");

        javax.swing.GroupLayout totalSales2PanelLayout = new javax.swing.GroupLayout(totalSales2Panel);
        totalSales2Panel.setLayout(totalSales2PanelLayout);
        totalSales2PanelLayout.setHorizontalGroup(
            totalSales2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalSales2PanelLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(totalSalesText, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        totalSales2PanelLayout.setVerticalGroup(
            totalSales2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalSales2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalSalesText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        totalsPriceNumLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 48)); // NOI18N
        totalsPriceNumLabel.setForeground(new java.awt.Color(204, 204, 204));

        totalsalesStatement.setForeground(new java.awt.Color(255, 255, 255));
        totalsalesStatement.setText("Your total Price.");

        javax.swing.GroupLayout totalSalesPanelLayout = new javax.swing.GroupLayout(totalSalesPanel);
        totalSalesPanel.setLayout(totalSalesPanelLayout);
        totalSalesPanelLayout.setHorizontalGroup(
            totalSalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalSales2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalSalesPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalsalesStatement)
                .addGap(103, 103, 103))
            .addComponent(totalsPriceNumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        totalSalesPanelLayout.setVerticalGroup(
            totalSalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalSalesPanelLayout.createSequentialGroup()
                .addComponent(totalSales2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalsPriceNumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalsalesStatement)
                .addContainerGap())
        );

        cartPanel.setBackground(new java.awt.Color(0, 204, 204));

        cart2Panel.setBackground(new java.awt.Color(0, 102, 153));

        cartTopicLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        cartTopicLabel.setForeground(new java.awt.Color(255, 255, 255));
        cartTopicLabel.setText("CURRENT CART");

        javax.swing.GroupLayout cart2PanelLayout = new javax.swing.GroupLayout(cart2Panel);
        cart2Panel.setLayout(cart2PanelLayout);
        cart2PanelLayout.setHorizontalGroup(
            cart2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cart2PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cartTopicLabel)
                .addGap(56, 56, 56))
        );
        cart2PanelLayout.setVerticalGroup(
            cart2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cart2PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cartTopicLabel)
                .addGap(19, 19, 19))
        );

        updateCartNumberLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 60)); // NOI18N
        updateCartNumberLabel.setForeground(new java.awt.Color(204, 204, 204));
        updateCartNumberLabel.setText("0");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total number of products in your Cart.");

        javax.swing.GroupLayout cartPanelLayout = new javax.swing.GroupLayout(cartPanel);
        cartPanel.setLayout(cartPanelLayout);
        cartPanelLayout.setHorizontalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cart2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cartPanelLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cartPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cartPanelLayout.createSequentialGroup()
                        .addComponent(updateCartNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))))
        );
        cartPanelLayout.setVerticalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cartPanelLayout.createSequentialGroup()
                .addComponent(cart2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateCartNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        barGraphTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        barGraphTitleLabel.setText("AURORA STATIONERY'S POPULAR SOLD ITEMS DATA");

        barGraphImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/barGraph-removebg-preview.png"))); // NOI18N

        pieChartImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/PIECHART.png"))); // NOI18N

        lineGraphImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/lineGraph.png"))); // NOI18N

        javax.swing.GroupLayout adminDashboardPanelLayout = new javax.swing.GroupLayout(adminDashboardPanel);
        adminDashboardPanel.setLayout(adminDashboardPanelLayout);
        adminDashboardPanelLayout.setHorizontalGroup(
            adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(324, Short.MAX_VALUE)
                .addComponent(barGraphTitleLabel)
                .addGap(318, 318, 318))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminDashboardPanelLayout.createSequentialGroup()
                .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(dashboardTopicLabel))
                    .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                                .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(28, 28, 28)
                                .addComponent(totalSalesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                                .addComponent(barGraphImg, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(pieChartImg, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(lineGraphImg))
                    .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(cartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
        adminDashboardPanelLayout.setVerticalGroup(
            adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(dashboardTopicLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(productPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalSalesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminDashboardPanelLayout.createSequentialGroup()
                        .addComponent(barGraphTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(adminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lineGraphImg, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(adminDashboardPanelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(barGraphImg, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19))
                    .addComponent(pieChartImg, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(327, Short.MAX_VALUE))
        );

        myCartPanel.addTab("MY DASHBOARD", adminDashboardPanel);

        productsPanel.setBackground(new java.awt.Color(255, 255, 102));

        pencilColorImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/pencilcolour.png"))); // NOI18N
        pencilColorImg.setText("jLabel1");

        prodDescLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        prodDescLabel.setText("PACK OF 32 COLOURING PENCILS");

        addProduct1Button.setText("ADD TO CART");
        addProduct1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct1ButtonActionPerformed(evt);
            }
        });

        erasersimg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/erasers.png"))); // NOI18N

        erasersDescLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        erasersDescLabel.setText("PACK OF 3 ERASERS & SHARPNERS");

        addProduct2Button.setText("ADD TO CART");
        addProduct2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct2ButtonActionPerformed(evt);
            }
        });

        scissorsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/scissors.png"))); // NOI18N

        priceLabel1.setText("Rs. 500/-");

        priceLabel2.setText("Rs. 50/-");

        scissorsDescLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        scissorsDescLabel.setText("PACK OF SCISSORS");

        productPrice3Label.setText("Rs. 85/-");

        addProduct3Button.setText("ADD TO CART");
        addProduct3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct3ButtonActionPerformed(evt);
            }
        });

        pencilsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/pencilssets.png"))); // NOI18N

        productDesc4Label.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        productDesc4Label.setText("SKETCH PENCILS");

        product4PriceLabel.setText("Rs. 60/-");

        addProduct4Button.setText("ADD TO CART");
        addProduct4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct4ButtonActionPerformed(evt);
            }
        });

        paintBrushImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/paintbrush.png"))); // NOI18N

        product5DescLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        product5DescLabel.setText("ART SUPPLIES");

        product5PriceLabel.setText("Rs. 790/-");

        addProduct5Button.setText("ADD TO CART");
        addProduct5Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct5ButtonActionPerformed(evt);
            }
        });

        paintColourImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/PAINTINGC.png"))); // NOI18N

        product6Description.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        product6Description.setText("PAINTING COLOURS");

        product6PriceLabel.setText("Rs. 400/-");

        addProduct6Button.setText("ADD TO CART");
        addProduct6Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct6ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(productsPanelLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addProduct1Button)
                                    .addComponent(pencilsImg)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(addProduct4Button)
                                        .addComponent(productDesc4Label)))
                                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(207, 207, 207)
                                        .addComponent(addProduct2Button, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(product5DescLabel)
                                            .addComponent(addProduct5Button))
                                        .addGap(13, 13, 13))))
                            .addGroup(productsPanelLayout.createSequentialGroup()
                                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(pencilColorImg, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(prodDescLabel))
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(123, 123, 123)
                                        .addComponent(priceLabel1)))
                                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addComponent(erasersimg, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(184, 184, 184)
                                        .addComponent(priceLabel2))
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(erasersDescLabel)
                                            .addComponent(paintBrushImg, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE))
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(product4PriceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(product5PriceLabel)
                        .addGap(231, 231, 231)))
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(addProduct3Button)
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scissorsImg, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paintColourImg, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(productPrice3Label)
                        .addGap(150, 150, 150))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(scissorsDescLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(product6Description)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(product6PriceLabel)
                        .addGap(157, 157, 157))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                        .addComponent(addProduct6Button)
                        .addGap(124, 124, 124))))
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addComponent(erasersimg, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erasersDescLabel)
                            .addComponent(prodDescLabel)
                            .addComponent(scissorsDescLabel)))
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pencilColorImg, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scissorsImg, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addComponent(productPrice3Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addProduct3Button)
                        .addGap(18, 18, 18)
                        .addComponent(paintColourImg, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productsPanelLayout.createSequentialGroup()
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceLabel2)
                            .addComponent(priceLabel1))
                        .addGap(9, 9, 9)
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addProduct2Button)
                            .addComponent(addProduct1Button))
                        .addGap(36, 36, 36)
                        .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(product6Description)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(product6PriceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addProduct6Button)
                                .addGap(13, 13, 13))
                            .addGroup(productsPanelLayout.createSequentialGroup()
                                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addComponent(paintBrushImg, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(product5DescLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(product5PriceLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addProduct5Button))
                                    .addGroup(productsPanelLayout.createSequentialGroup()
                                        .addComponent(pencilsImg, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(productDesc4Label)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(product4PriceLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addProduct4Button)))
                                .addGap(0, 13, Short.MAX_VALUE)))))
                .addContainerGap(319, Short.MAX_VALUE))
        );

        myCartPanel.addTab("PRODUCTS DISPLAY", productsPanel);

        formfillPanel.setBackground(new java.awt.Color(0, 153, 153));
        formfillPanel.setAutoscrolls(true);

        availableProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product ID", "Product Name", "Product Quantity", "Product Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        availableProductTable.setMinimumSize(new java.awt.Dimension(10, 20));
        jScrollPane1.setViewportView(availableProductTable);
        if (availableProductTable.getColumnModel().getColumnCount() > 0) {
            availableProductTable.getColumnModel().getColumn(0).setResizable(false);
            availableProductTable.getColumnModel().getColumn(1).setResizable(false);
            availableProductTable.getColumnModel().getColumn(2).setResizable(false);
            availableProductTable.getColumnModel().getColumn(3).setResizable(false);
        }

        viewingProductsLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        viewingProductsLabel.setForeground(new java.awt.Color(255, 255, 255));
        viewingProductsLabel.setText("VIEWING AVAILABLE PRODUCTS");

        sortByPriceButton.setBackground(new java.awt.Color(51, 255, 255));
        sortByPriceButton.setText("SORT BY PRICE");
        sortByPriceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByPriceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout formfillPanelLayout = new javax.swing.GroupLayout(formfillPanel);
        formfillPanel.setLayout(formfillPanelLayout);
        formfillPanelLayout.setHorizontalGroup(
            formfillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(formfillPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(viewingProductsLabel)
                .addContainerGap(446, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formfillPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sortByPriceButton)
                .addGap(427, 427, 427))
        );
        formfillPanelLayout.setVerticalGroup(
            formfillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formfillPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(viewingProductsLabel)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sortByPriceButton)
                .addContainerGap(400, Short.MAX_VALUE))
        );

        myCartPanel.addTab("INVENTORY", formfillPanel);

        productInfoPanel.setBackground(new java.awt.Color(204, 0, 204));

        productInfoLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 48)); // NOI18N
        productInfoLabel.setForeground(new java.awt.Color(255, 255, 255));
        productInfoLabel.setText("PRODUCT INFORMATION");

        imgProductInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/balloons1.png"))); // NOI18N

        img2ProductInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/StationeryApp/view/resources/balloons1.png"))); // NOI18N

        productIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        productIdLabel.setText("PRODUCT ID :");

        productNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        productNameLabel.setText("PRODUCT NAME :");

        productQtyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        productQtyLabel.setText("PRODUCT QUANTITY :");

        productPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        productPriceLabel.setText("PRODUCT PRICE :");

        addProductButton.setBackground(new java.awt.Color(255, 204, 204));
        addProductButton.setText("ADD PRODUCT");
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });

        removeProductButton.setBackground(new java.awt.Color(255, 204, 204));
        removeProductButton.setText("REMOVE PRODUCT");
        removeProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProductButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productInfoPanelLayout = new javax.swing.GroupLayout(productInfoPanel);
        productInfoPanel.setLayout(productInfoPanelLayout);
        productInfoPanelLayout.setHorizontalGroup(
            productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productInfoPanelLayout.createSequentialGroup()
                .addComponent(imgProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(productInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(img2ProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(productInfoPanelLayout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(removeProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(productInfoPanelLayout.createSequentialGroup()
                            .addComponent(productIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(100, 100, 100)
                            .addComponent(productIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(productInfoPanelLayout.createSequentialGroup()
                            .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(productNameLabel)
                                .addComponent(productQtyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(productPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(48, 48, 48)
                            .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(productQtyTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(productNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(productPriceTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(318, 318, 318))
        );
        productInfoPanelLayout.setVerticalGroup(
            productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productInfoPanelLayout.createSequentialGroup()
                .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productInfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(img2ProductInfo)
                            .addComponent(imgProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(productInfoPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(productInfoLabel)
                        .addGap(75, 75, 75)
                        .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productIdLabel))
                        .addGap(27, 27, 27)
                        .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productQtyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productQtyTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(productInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productPriceLabel)
                    .addComponent(productPriceTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(addProductButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(removeProductButton)
                .addContainerGap(353, Short.MAX_VALUE))
        );

        myCartPanel.addTab("MODIFY INVENTORY", productInfoPanel);

        jPanel1.setBackground(new java.awt.Color(255, 255, 102));

        cartProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product ID", "Product Name", "Product Quantity", "Product Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(cartProductsTable);
        if (cartProductsTable.getColumnModel().getColumnCount() > 0) {
            cartProductsTable.getColumnModel().getColumn(0).setResizable(false);
            cartProductsTable.getColumnModel().getColumn(1).setResizable(false);
            cartProductsTable.getColumnModel().getColumn(2).setResizable(false);
            cartProductsTable.getColumnModel().getColumn(3).setResizable(false);
        }

        checkoutButton.setBackground(new java.awt.Color(255, 204, 51));
        checkoutButton.setText("CHECKOUT");
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });

        updateProductQuantityTopic.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateProductQuantityTopic.setText("UPDATE QUANTITY OF PRODUCTS ACCORDINGLY");

        productIDLabel.setText("PRODUCT ID :");

        desiredQuantityLabel.setText("DESIRED QUANTITY :");

        desiredQtyTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desiredQtyTxtFieldActionPerformed(evt);
            }
        });

        updateDesiredQtyButton.setBackground(new java.awt.Color(255, 153, 51));
        updateDesiredQtyButton.setText("UPDATE DESIRED QUANTITY");
        updateDesiredQtyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDesiredQtyButtonActionPerformed(evt);
            }
        });

        sortByLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sortByLabel.setText("SORT PRODUCTS BY");

        sortProductIdButton.setText("PRODUCT ID");
        sortProductIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortProductIdButtonActionPerformed(evt);
            }
        });

        orLabel.setText("OR");

        productQtySortButton.setText("PRODUCT QUANTITY");
        productQtySortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productQtySortButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(desiredQuantityLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(productIDLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(qtyProductIdTxtField)
                                    .addComponent(desiredQtyTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                                .addGap(298, 298, 298)
                                .addComponent(sortProductIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(orLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(productQtySortButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(updateProductQuantityTopic)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sortByLabel)
                                .addGap(159, 159, 159))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(413, 413, 413)
                        .addComponent(checkoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(updateDesiredQtyButton)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateProductQuantityTopic)
                    .addComponent(sortByLabel))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productIDLabel)
                    .addComponent(qtyProductIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortProductIdButton)
                    .addComponent(orLabel)
                    .addComponent(productQtySortButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desiredQuantityLabel)
                    .addComponent(desiredQtyTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(updateDesiredQtyButton)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(checkoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(299, 299, 299))
        );

        myCartPanel.addTab("MY CART", jPanel1);

        logoutButton.setText("LOG OUT");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainBgPanelLayout = new javax.swing.GroupLayout(mainBgPanel);
        mainBgPanel.setLayout(mainBgPanelLayout);
        mainBgPanelLayout.setHorizontalGroup(
            mainBgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBgPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton)
                .addGap(20, 20, 20))
            .addComponent(myCartPanel)
        );
        mainBgPanelLayout.setVerticalGroup(
            mainBgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBgPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(mainBgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logoLabel)
                    .addComponent(logoutButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myCartPanel))
        );

        getContentPane().add(mainBgPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 980));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addProduct2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct2ButtonActionPerformed
        // TODO add your handling code here:
    toggleProductInCart(new StationeryModel(2, "Pack of 3 erasers and sharpeners ", 1, 50.00), addProduct2Button.isSelected());
    }//GEN-LAST:event_addProduct2ButtonActionPerformed

    private void addProduct3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct3ButtonActionPerformed
        // TODO add your handling code here:
     toggleProductInCart(new StationeryModel(3, "Pack of scissors ", 1, 85.00), addProduct3Button.isSelected());
    }//GEN-LAST:event_addProduct3ButtonActionPerformed

    private void addProduct5ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct5ButtonActionPerformed
        // TODO add your handling code here:
     toggleProductInCart(new StationeryModel(5, "Art Supplies ", 1, 790.00), addProduct5Button.isSelected());       
    }//GEN-LAST:event_addProduct5ButtonActionPerformed

    private void addProduct6ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct6ButtonActionPerformed
        // TODO add your handling code here:
    toggleProductInCart(new StationeryModel(6, "Painting Colours ", 1, 400.00), addProduct6Button.isSelected());       
    }//GEN-LAST:event_addProduct6ButtonActionPerformed

    /**
     * whenever the addProductButton is clicked this method is called.
     * It does not allow adding already existing products by checking for unique product Ids.
     * @param evt for action events. (button clicks)
     */
    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed
        // TODO add your handling code here:
        //code for exception handeling.
        try{
            //parsing user input from the text field into integer.
            int product_id=Integer.parseInt(productIdTxtField.getText());
            String product_name=productNameTxtField.getText();
            //parsing user input from the text field into integer.
            int product_quantity=Integer.parseInt(productQtyTxtField.getText());
            //parsing user input from the text field into double.
            double product_price=Double.parseDouble(productPriceTxtField.getText());
            
            //condition for checking if the product id given by the user already exists in the inventory.
            //if true it generates a suitable message.
            if(auroraInventory.checkProductId(product_id)){
                JOptionPane.showMessageDialog(this, "Please enter non existing product Id.");
               return; //if the condition is true, the method is terminated.
            }
            //creates a new stationeryModel object with the provided product details.
            StationeryModel newestProduct=new StationeryModel(product_id,product_name,product_quantity,product_price);
            auroraInventory.myProductsAddition(newestProduct);//adds new products to the stationery inventory.
            
            //clearing text fields after a product has been added.
            productIdTxtField.setText("");
            productNameTxtField.setText("");
            productQtyTxtField.setText("");
            productPriceTxtField.setText("");
            //refreshes inventory table with newly added product information.
            updateProductTable();
            //notify user with successful product addition.
            JOptionPane.showMessageDialog(this, "Product has been added Successfully.");
            //code for exception handeling.
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(this,"An unexpected error has been occured."+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addProductButtonActionPerformed
    /**
     * whenever the removeProductButton is clicked this method is called.
     * @param evt for action events (button clicks).
     * before deleting a product from an inventory, it checks for whether the product id input by the user exists in the inventory table or not.
     */
    private void removeProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProductButtonActionPerformed
        // TODO add your handling code here:
        //code for exception handeling.
        try{
            //parsing user input in the text field into integer.
            int product_id=Integer.parseInt(productIdTxtField.getText());
            //condition to check whether the product id input by the user exist in the inventory table or not before deleting. 
            if(!auroraInventory.checkProductId(product_id)){
                //display suitable message.
                JOptionPane.showMessageDialog(this,"This product Id does't exist in the inventory. Please try entering existing product Id.");
                return;//terminate the method if the product with user input product id does not exist.
            }
            auroraInventory.deleteProduct(product_id);
            productIdTxtField.setText("");//clearing user input in the text field.
            //refreshes inventory table with latest products.
            updateProductTable();
            //notify user with suitable message.
            JOptionPane.showMessageDialog(this,"Product has been deleted successfully.");
            //code for error handeling.
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Please Try Again.");
        }
    }//GEN-LAST:event_removeProductButtonActionPerformed

    private void addProduct1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct1ButtonActionPerformed
        // TODO add your handling code here:
        toggleProductInCart(new StationeryModel(1, "Pack of 32 colouring pencils ", 1, 500.00), addProduct1Button.isSelected());
    }//GEN-LAST:event_addProduct1ButtonActionPerformed

    private void addProduct4ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct4ButtonActionPerformed
        // TODO add your handling code here:
    toggleProductInCart(new StationeryModel(4, "Sketch Pencils ", 1, 10.00), addProduct4Button.isSelected());
    }//GEN-LAST:event_addProduct4ButtonActionPerformed
    /**
     * whenever the sortByPriceButton is being clicked, this method is called.
     * @param evt for action events. (button clicks)
     * method to sort the products according to the lowest price to highest price.
     */
    private void sortByPriceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByPriceButtonActionPerformed
      try{
          auroraInventory.priceBeingSorted();
          updateProductTable();
          JOptionPane.showMessageDialog(this, "Successful in sorting products with lower to higher price.");
      }catch (HeadlessException e){
          JOptionPane.showMessageDialog(this, "An unexpected error occured.");
      }
      
        
    }//GEN-LAST:event_sortByPriceButtonActionPerformed

    private void desiredQtyTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desiredQtyTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_desiredQtyTxtFieldActionPerformed

    private void productQtySortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productQtySortButtonActionPerformed
        // TODO add your handling code here
        try{
        productQuantityBeingSorted();
        updateCartTableProducts(); 
        JOptionPane.showMessageDialog(this,"The products have been successfully sorted from lower quantity to higher quantity.");
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(this,"An unexpected error has been occured while sorting product quantity.");
        }
    }//GEN-LAST:event_productQtySortButtonActionPerformed

    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutButtonActionPerformed
         // TODO add your handling code here:
        double totalProductPrice=0.0;//starting price.
        for (StationeryModel first_item :auroraCartSet){
            //calculating total price.
            totalProductPrice+=first_item.getStatProductPrice()*first_item.getStatProductQuantity();
            totalsPriceNumLabel.setText("Rs."+String.valueOf(totalProductPrice));
        }
        //display suitable message.
        JOptionPane.showMessageDialog(this, "Your Total Price is Rs. "+totalProductPrice+"\nThank You so much for purchasing with Aurora Stationery.");
    }//GEN-LAST:event_checkoutButtonActionPerformed

    private void updateDesiredQtyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDesiredQtyButtonActionPerformed
        // TODO add your handling code here:
        try{//handeling exceptions.
           int desiredProductId=Integer.parseInt(qtyProductIdTxtField.getText());
           int desiredQuantity=Integer.parseInt(desiredQtyTxtField.getText());
           boolean findProduct=false;//variable that determines if the product exists in the cart or not.
           
           //does not let the input for desired quantity be negative.
           if(desiredQuantity<=0){
                JOptionPane.showMessageDialog(this, "Please enter a positive value for product  quantity.");
                return;    
           } 
           //loop through each element in the set to determine product with Id.
            for(StationeryModel myItem:auroraCartSet){
                //when product Id matches, update the quantity of product with desired quantity.
                if(myItem.getStatProductId()==desiredProductId){
                   myItem.setStatProductQuantity(desiredQuantity);
                   findProduct= true;//product found.
                   updateCartTableProducts();//refresh table.
                   JOptionPane.showMessageDialog(this, "Product quantity has been updated successfully."); 
                   break;
                   }
        } 
        //condition for product not being found.
        if(!findProduct){
            //display message.
            JOptionPane.showMessageDialog(this, "The entered product Id was not found in the cart.");
        }
        }catch(HeadlessException | NumberFormatException e){//exception handeling.
            //display message
            JOptionPane.showMessageDialog(this, "Please enter valid input for Product ID and product uantity.");
        }
            
        
    }//GEN-LAST:event_updateDesiredQtyButtonActionPerformed

    private void sortProductIdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortProductIdButtonActionPerformed
        // TODO add your handling code here:
            try{
        productIdBeingSorted();
        updateCartTableProducts(); 
        JOptionPane.showMessageDialog(this,"The products have been successfully sorted from low to high Product ID.");
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(this,"An unexpected error has been occured while sorting Product ID.");
        }
        
    }//GEN-LAST:event_sortProductIdButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        try{
            new UpdateMyCartFrame().setVisible(false);
            new StationeryLoginFrame().setVisible(true);
            JOptionPane.showMessageDialog(this,"You have been successfully logged out.");
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(this,"Error.");
        }
    }//GEN-LAST:event_logoutButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateMyCartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateMyCartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateMyCartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateMyCartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateMyCartFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton addProduct1Button;
    private javax.swing.JToggleButton addProduct2Button;
    private javax.swing.JToggleButton addProduct3Button;
    private javax.swing.JToggleButton addProduct4Button;
    private javax.swing.JToggleButton addProduct5Button;
    private javax.swing.JToggleButton addProduct6Button;
    private javax.swing.JButton addProductButton;
    private javax.swing.JPanel adminDashboardPanel;
    private javax.swing.JTable availableProductTable;
    private javax.swing.JLabel barGraphImg;
    private javax.swing.JLabel barGraphTitleLabel;
    private javax.swing.JPanel cart2Panel;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JTable cartProductsTable;
    private javax.swing.JLabel cartTopicLabel;
    private javax.swing.JButton checkoutButton;
    private javax.swing.JLabel dashboardTopicLabel;
    private javax.swing.JTextField desiredQtyTxtField;
    private javax.swing.JLabel desiredQuantityLabel;
    private javax.swing.JLabel erasersDescLabel;
    private javax.swing.JLabel erasersimg;
    private javax.swing.JPanel formfillPanel;
    private javax.swing.JLabel img2ProductInfo;
    private javax.swing.JLabel imgProductInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lineGraphImg;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel mainBgPanel;
    private javax.swing.JTabbedPane myCartPanel;
    private javax.swing.JLabel orLabel;
    private javax.swing.JLabel paintBrushImg;
    private javax.swing.JLabel paintColourImg;
    private javax.swing.JLabel pencilColorImg;
    private javax.swing.JLabel pencilsImg;
    private javax.swing.JLabel pieChartImg;
    private javax.swing.JLabel priceLabel1;
    private javax.swing.JLabel priceLabel2;
    private javax.swing.JLabel prodDescLabel;
    private javax.swing.JPanel product2Panel;
    private javax.swing.JLabel product4PriceLabel;
    private javax.swing.JLabel product5DescLabel;
    private javax.swing.JLabel product5PriceLabel;
    private javax.swing.JLabel product6Description;
    private javax.swing.JLabel product6PriceLabel;
    private javax.swing.JLabel productDesc4Label;
    private javax.swing.JLabel productIDLabel;
    private javax.swing.JLabel productIdLabel;
    private javax.swing.JTextField productIdTxtField;
    private javax.swing.JLabel productInfoLabel;
    private javax.swing.JPanel productInfoPanel;
    private javax.swing.JLabel productNameLabel;
    private javax.swing.JTextField productNameTxtField;
    private javax.swing.JLabel productNumLabel;
    private javax.swing.JPanel productPanel;
    private javax.swing.JLabel productPrice3Label;
    private javax.swing.JLabel productPriceLabel;
    private javax.swing.JTextField productPriceTxtField;
    private javax.swing.JLabel productQtyLabel;
    private javax.swing.JButton productQtySortButton;
    private javax.swing.JTextField productQtyTxtField;
    private javax.swing.JLabel productStatement;
    private javax.swing.JPanel productsPanel;
    private javax.swing.JTextField qtyProductIdTxtField;
    private javax.swing.JButton removeProductButton;
    private javax.swing.JLabel scissorsDescLabel;
    private javax.swing.JLabel scissorsImg;
    private javax.swing.JLabel sortByLabel;
    private javax.swing.JButton sortByPriceButton;
    private javax.swing.JButton sortProductIdButton;
    private javax.swing.JLabel totalProductsLabel;
    private javax.swing.JPanel totalSales2Panel;
    private javax.swing.JPanel totalSalesPanel;
    private javax.swing.JLabel totalSalesText;
    private javax.swing.JLabel totalsPriceNumLabel;
    private javax.swing.JLabel totalsalesStatement;
    private javax.swing.JLabel updateCartNumberLabel;
    private javax.swing.JButton updateDesiredQtyButton;
    private javax.swing.JLabel updateProductQuantityTopic;
    private javax.swing.JLabel viewingProductsLabel;
    // End of variables declaration//GEN-END:variables
}
