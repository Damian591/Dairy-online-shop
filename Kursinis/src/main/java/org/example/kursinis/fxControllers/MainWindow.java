package org.example.kursinis.fxControllers;

import javafx.event.ActionEvent;
import javafx.stage.Modality;
import org.example.kursinis.HelloApplication;
import org.example.kursinis.fxControllers.tableParameters.CustomerTableParameters;
import org.example.kursinis.fxControllers.tableParameters.ManagerTableParameters;
import org.example.kursinis.fxControllers.tableParameters.OrdersTableParameters;
import org.example.kursinis.hibernate.GenericHibernate;
import org.example.kursinis.hibernate.ShopHibernate;
import org.example.kursinis.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    //I add @FXML above all attributes that are linked to form element ids
    //<editor-fold desc="Here are the fields for Shop tab">
    @FXML
    //ListView without a type is just a raw usage. It is best to specify the type of objects that will be stored in that list
    //In our case it is Product
    public ListView<Product> shopProducts;

    @FXML
    public Tab shopTab;
    @FXML
    public ListView<Product> myCartItems;
    @FXML
    public Tab productsTab;
    @FXML
    public ListView<Product> productAdminList;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public TextField productQuantityField;
    @FXML
    public TextField productWeightField;
    @FXML
    public DatePicker productProductionDateField;
    @FXML
    public DatePicker productPackingDateField;
    @FXML
    public DatePicker productBestBeforeField;
    @FXML
    public TextField productFlavourField;
    @FXML
    public TextField productTypeField;
    @FXML
    public TextField productManufacturerField;
    @FXML
    public RadioButton productCheeseRadio;
    @FXML
    public RadioButton productMilkRadio;
    @FXML
    public RadioButton productYogurtRadio;
    //</editor-fold>

    //<editor-fold desc="Here are the fields for User tab">
    public TableColumn<ManagerTableParameters, Integer> managerColId;
    public TableColumn<ManagerTableParameters, String> managerColLogin;
    public TableColumn<ManagerTableParameters, String> managerColName;
    public TableView<ManagerTableParameters> managerTable;
    public TableView<CustomerTableParameters> customerTable;
    public TableColumn<ManagerTableParameters, Void> dummyCol;
    public TableColumn <ManagerTableParameters, String> managerColPassword;
    public TableColumn<ManagerTableParameters, String> managerColSurname;
    public Tab usersTab;
    public TableColumn<CustomerTableParameters, Integer> customerColID;
    public TableColumn<CustomerTableParameters, String> customerColLogin;
    public TableColumn<CustomerTableParameters, String> customerColPassword;
    public TableColumn<CustomerTableParameters, String> customerColName;
    public TableColumn<CustomerTableParameters, String> customerColSurname;
    public TableColumn<CustomerTableParameters, Void> customerdummyCol;
    private ObservableList<ManagerTableParameters> managerData = FXCollections.observableArrayList();
    private ObservableList<CustomerTableParameters> customerData = FXCollections.observableArrayList();
    private ObservableList<OrdersTableParameters> ordersData = FXCollections.observableArrayList();
    public TableColumn <OrdersTableParameters, Integer> productColId;
    public TableColumn <OrdersTableParameters, String> productColTitle;
    public TableColumn <OrdersTableParameters, String> productColDescription;
    public TableColumn <OrdersTableParameters, Integer> productColQuantity;
    public TableColumn <OrdersTableParameters, Float> productColWeight;
    public TableView <OrdersTableParameters> ordersTable;
    public ListView <Cart> ordersListView;
    public ListView <Warehouse> warehousesListView;
    public ListView <Manager> warehouseManagerListView;
    public ListView <Product> warehouseProductsListView;

    public Tab ordersTab;
    public Tab warehousesTab;
    public TextField CustLoginFilterField;
    @FXML
    public TabPane tabPane;
    private EntityManagerFactory entityManagerFactory;
    //This class has methods for entity manipulation
    private ShopHibernate shopHibernate;
    //I need to know which user is selected
    private User user;

    //When class implements Initializable interface, you will be required to implements this method.
    // It allows us to access all the fields before they are rendered
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        managerTable.setEditable(true);
        customerTable.setEditable(true);
        ordersTable.setEditable(true);
        //setCellValueFactory allows to display the data

        managerColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColId.setCellValueFactory(new PropertyValueFactory<>("id"));

        managerColLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColLogin.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
            //Before updating, get the latest version from database
            Manager manager = shopHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setLogin(event.getNewValue());
            shopHibernate.update(manager);
        });

        customerColLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        customerColLogin.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
            //Before updating, get the latest version from database
            Customer customer = shopHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setLogin(event.getNewValue());
            shopHibernate.update(customer);
        });

        productColTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        productColTitle.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitle(event.getNewValue());
            //Before updating, get the latest version from database
            Product product = shopHibernate.getEntityById(Product.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            product.setTitle(event.getNewValue());
            shopHibernate.update(product);
        });

        managerColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        customerColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        productColTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));

        managerColPassword.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColPassword.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            //Before updating, get the latest version from database
            Manager manager = shopHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setPassword(event.getNewValue());
            shopHibernate.update(manager);
        });

        customerColPassword.setCellFactory(TextFieldTableCell.forTableColumn());
        customerColPassword.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            //Before updating, get the latest version from database
            Customer customer = shopHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setPassword(event.getNewValue());
            shopHibernate.update(customer);
        });

        productColDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        productColDescription.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(event.getNewValue());
            //Before updating, get the latest version from database
            Product product = shopHibernate.getEntityById(Product.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            product.setDescription(event.getNewValue());
            shopHibernate.update(product);
        });

        managerColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        customerColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        productColDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

        managerColSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColSurname.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
            //Before updating, get the latest version from database
            Manager manager = shopHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setSurname(event.getNewValue());
            shopHibernate.update(manager);
        });

        customerColSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        customerColSurname.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
            //Before updating, get the latest version from database
            Customer customer = shopHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setSurname(event.getNewValue());
            shopHibernate.update(customer);
        });
        /*productColQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        productColQuantity.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantity(event.getNewValue());
            //Before updating, get the latest version from database
            Product product = shopHibernate.getEntityById(Product.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            product.setQty(event.getNewValue());
            shopHibernate.update(product);
        });*/

        managerColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        customerColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        productColQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        //setCellFactory and setOnEditCommit allows us to edit cell value
        managerColName.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColName.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            //Before updating, get the latest version from database
            Manager manager = shopHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setName(event.getNewValue());
            shopHibernate.update(manager);
        });

        customerColName.setCellFactory(TextFieldTableCell.forTableColumn());
        customerColName.setOnEditCommit(event -> {
            //event - click on cell
            //event.getNewValue - when we click on cell and enter new value
            //event knows which table was selected, which row was selected and which cell was changed
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            //Before updating, get the latest version from database
            Customer customer = shopHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setName(event.getNewValue());
            shopHibernate.update(customer);
        });

        managerColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        //productColCapacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        //This portion of the code is responsible for generating a graphic element (button) in a cell


        Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> callback = param -> {
            final TableCell<ManagerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());
                        shopHibernate.delete(Manager.class, row.getId());

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        dummyCol.setCellFactory(callback);

        Callback<TableColumn<CustomerTableParameters, Void>, TableCell<CustomerTableParameters, Void>> callback2 = param -> {
            final TableCell<CustomerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        CustomerTableParameters row = getTableView().getItems().get(getIndex());
                        shopHibernate.delete(Customer.class, row.getId());

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        customerdummyCol.setCellFactory(callback2);
    }


    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.user = user;
        loadTabData();
        setCustomerView();
    }

    private void setCustomerView() {
        if (user instanceof Customer) {
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(productsTab);
            tabPane.getTabs().remove(warehousesTab);
        } else if (!((Manager) user).isAdmin()) {
            tabPane.getTabs().remove(usersTab);
        }
    }


    private void fillManagerTable() {
        List<Manager> managers = shopHibernate.getAllRecords(Manager.class);
        for (Manager m : managers) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();
            managerTableParameters.setId(m.getId());
            managerTableParameters.setLogin(m.getLogin());
            managerTableParameters.setName(m.getName());
            managerTableParameters.setPassword(m.getPassword());
            managerTableParameters.setSurname(m.getSurname());
            managerData.add(managerTableParameters);
        }
        managerTable.setItems(managerData);
    }
    private void fillCustomerTable() {

        List<Customer> customers = shopHibernate.getAllRecords(Customer.class);
        for (Customer m : customers) {
            CustomerTableParameters customerTableParameters = new CustomerTableParameters();
            customerTableParameters.setId(m.getId());
            customerTableParameters.setLogin(m.getLogin());
            customerTableParameters.setName(m.getName());
            customerTableParameters.setPassword(m.getPassword());
            customerTableParameters.setSurname(m.getSurname());
            customerData.add(customerTableParameters);
        }
        customerTable.setItems(customerData);
    }

    @FXML
    private void fillOrdersTable() {
        ordersTable.getItems().clear();
        Cart cart = ordersListView.getSelectionModel().getSelectedItem();
        if(cart!=null)
        {
            cart=shopHibernate.getEntityById(Cart.class, cart.getId());
            List<Product>products=cart.getProductList();
            for (Product m : products) {
                OrdersTableParameters ordersTableParameters = new OrdersTableParameters();
                ordersTableParameters.setId(m.getId());
                ordersTableParameters.setTitle(m.getTitle());
                ordersTableParameters.setDescription(m.getDescription());
                ordersTableParameters.setQuantity(m.getQty());
                ordersTableParameters.setWeight(m.getWeight());
                ordersData.add(ordersTableParameters);
            }
            ordersTable.setItems(ordersData);
        }

    }
    public void createRecord() {
        if (productCheeseRadio.isSelected()) {
            Cheese cheese = new Cheese(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productPackingDateField.getValue(),
                    productProductionDateField.getValue(),
                    productBestBeforeField.getValue());
            shopHibernate.create(cheese);
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productPackingDateField.setValue(null);
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
        } else if (productMilkRadio.isSelected()) {
            Milk milk = new Milk(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productTypeField.getText(),
                    productManufacturerField.getText(),
                    productProductionDateField.getValue(),
                    productBestBeforeField.getValue());
            shopHibernate.create(milk);
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
            productTypeField.clear();
            productManufacturerField.clear();
        } else {
            Yogurt yogurt = new Yogurt(productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productWeightField.getText()),
                    productManufacturerField.getText(),
                    productFlavourField.getText(),
                    productProductionDateField.getValue(),
                    productBestBeforeField.getValue());
            shopHibernate.create(yogurt);
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
            productManufacturerField.clear();
            productFlavourField.clear();
        }

        productAdminList.getItems().clear();
        productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }
    public void updateRecord() {

        Product product = shopHibernate.getEntityById(Product.class, productAdminList.getSelectionModel().getSelectedItem().getId());

        if (product instanceof Cheese) {
            Cheese cheese = (Cheese) product;
            cheese.setTitle(productTitleField.getText());
            cheese.setDescription(productDescriptionField.getText());
            cheese.setQty(Integer.parseInt(productQuantityField.getText()));
            cheese.setWeight(Float.parseFloat(productWeightField.getText()));
            cheese.setProductionDate(productProductionDateField.getValue());
            cheese.setBestBeforeTill(productBestBeforeField.getValue());
            cheese.setPackingDate(productPackingDateField.getValue());
            shopHibernate.update(cheese);
        } else if (product instanceof Milk) {
            Milk milk = (Milk) product;
            milk.setTitle(productTitleField.getText());
            milk.setDescription(productDescriptionField.getText());
            milk.setQty(Integer.parseInt(productQuantityField.getText()));
            milk.setWeight(Float.parseFloat(productWeightField.getText()));
            milk.setProductionDate(productProductionDateField.getValue());
            milk.setBestBeforeTill(productBestBeforeField.getValue());
            milk.setType(productTypeField.getText());
            milk.setManufakturer(productManufacturerField.getText());
            shopHibernate.update( milk);
        } else {
            Yogurt yogurt = (Yogurt) product;
            yogurt.setTitle(productTitleField.getText());
            yogurt.setDescription(productDescriptionField.getText());
            yogurt.setQty(Integer.parseInt(productQuantityField.getText()));
            yogurt.setWeight(Float.parseFloat(productWeightField.getText()));
            yogurt.setProductionDate(productProductionDateField.getValue());
            yogurt.setBestBeforeTill(productBestBeforeField.getValue());
            yogurt.setManufacturer(productManufacturerField.getText());
            yogurt.setFlavour(productFlavourField.getText());
            shopHibernate.update(yogurt);
        }
        productAdminList.getItems().clear();
        productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void deleteRecord() {
        shopHibernate.delete(Product.class, productAdminList.getSelectionModel().getSelectedItem().getId());
        loadProductData();
        fillLists();
    }
    private void fillLists() {
        productAdminList.getItems().clear();
        productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void disableFields() {
        if (productCheeseRadio.isSelected()) {
            productManufacturerField.setDisable(true);
            productProductionDateField.setDisable(false);
            productPackingDateField.setDisable(false);
            productBestBeforeField.setDisable(false);
            productFlavourField.setDisable(true);
            productTypeField.setDisable(true);
        } else if (productMilkRadio.isSelected()) {
            productManufacturerField.setDisable(false);
            productProductionDateField.setDisable(false);
            productPackingDateField.setDisable(true);
            productBestBeforeField.setDisable(false);
            productFlavourField.setDisable(true);
            productTypeField.setDisable(false);
        } else {
            productManufacturerField.setDisable(false);
            productProductionDateField.setDisable(false);
            productPackingDateField.setDisable(true);
            productBestBeforeField.setDisable(false);
            productFlavourField.setDisable(false);
            productTypeField.setDisable(true);
        }
    }

   public void loadProductData() {

        Product product = productAdminList.getSelectionModel().getSelectedItem();
        //productAdminList.getItems().addAll(shopHibernate.loadAvailableProducts());

        if (product instanceof Cheese cheese) {
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
            productPackingDateField.setValue(null);
            productManufacturerField.clear();
            productFlavourField.clear();
            productTypeField.clear();
            productTitleField.setText(cheese.getTitle());
            productDescriptionField.setText(cheese.getDescription());
            productQuantityField.setText(String.valueOf(cheese.getQty()));
            productWeightField.setText(String.valueOf(cheese.getWeight()));
            productProductionDateField.setValue(cheese.getProductionDate());
            productBestBeforeField.setValue(cheese.getBestBeforeTill());
            productPackingDateField.setValue(cheese.getPackingDate());
        } else if (product instanceof Milk milk) {
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
            productPackingDateField.setValue(null);
            productManufacturerField.clear();
            productFlavourField.clear();
            productTypeField.clear();
            productTitleField.setText(milk.getTitle());
            productDescriptionField.setText(milk.getDescription());
            productQuantityField.setText(String.valueOf(milk.getQty()));
            productWeightField.setText(String.valueOf(milk.getWeight()));
            productTypeField.setText((milk.getType()));
            productManufacturerField.setText(milk.getManufakturer());
            productProductionDateField.setValue(milk.getProductionDate());
            productBestBeforeField.setValue(milk.getBestBeforeTill());
        } else if (product instanceof Yogurt yogurt) {
            productTitleField.clear();
            productDescriptionField.clear();
            productQuantityField.clear();
            productWeightField.clear();
            productProductionDateField.setValue(null);
            productBestBeforeField.setValue(null);
            productPackingDateField.setValue(null);
            productManufacturerField.clear();
            productFlavourField.clear();
            productTypeField.clear();
            productTitleField.setText(yogurt.getTitle());
            productDescriptionField.setText(yogurt.getDescription());
            productQuantityField.setText(String.valueOf(yogurt.getQty()));
            productWeightField.setText(String.valueOf(yogurt.getWeight()));
            productManufacturerField.setText(yogurt.getManufacturer());
            productFlavourField.setText(yogurt.getFlavour());
            productProductionDateField.setValue(yogurt.getProductionDate());
            productBestBeforeField.setValue(yogurt.getBestBeforeTill());
        }
    }

    public void removeFromCart() {
        Product product = myCartItems.getSelectionModel().getSelectedItem();
        shopProducts.getItems().add(product);
        myCartItems.getItems().remove(product);
    }

    public void addToCart() {
        Product product = shopProducts.getSelectionModel().getSelectedItem();
        myCartItems.getItems().add(product);
        shopProducts.getItems().remove(product);
    }

    public void buyItems() {
        shopHibernate.createCart(myCartItems.getItems(), user);
    }
    //</editor-fold>

    public void loadTabData() {
        if (shopTab.isSelected()) {
            shopProducts.getItems().clear();
            shopProducts.getItems().addAll(shopHibernate.loadAvailableProducts());
        } else if (usersTab.isSelected()) {
            managerTable.getItems().clear();
            fillManagerTable();

            customerTable.getItems().clear();
            fillCustomerTable();
        } else if (ordersTab.isSelected()) {
            ordersListView.getItems().clear();
            ordersListView.getItems().addAll(shopHibernate.getAllRecords(Cart.class));
        } else if (productsTab.isSelected()) {
            productAdminList.getItems().clear();
            productAdminList.getItems().addAll(shopHibernate.loadAvailableProducts());
        }
        else if (warehousesTab.isSelected()) {
            warehousesListView.getItems().clear();
            warehousesListView.getItems().addAll(shopHibernate.getAllRecords(Warehouse.class));
        }
    }

    public void deleteOrder () {
        Cart cart = ordersListView.getSelectionModel().getSelectedItem();
        ordersListView.getItems().remove(cart);
        shopHibernate.deleteCart(cart.getId());
    }

    public void AddWarehouse() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("warehouse-window.fxml"));
        Parent parent = fxmlLoader.load();
        WarehouseController warehouseController= fxmlLoader.getController();
        warehouseController.setData(entityManagerFactory,false,null);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        FxUtils.setStageParameters(stage, scene, false);
    }

    public void loadProductReviewForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product-review.fxml"));
        Parent parent = fxmlLoader.load();

        ProductReview productReview = fxmlLoader.getController();
        productReview.setData(entityManagerFactory, user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }
    public void register() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, true);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }

    public void UpdateWarehouse(ActionEvent actionEvent) throws IOException {
        Warehouse warehouse = warehousesListView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("warehouse-window.fxml"));
        Parent parent = fxmlLoader.load();
        WarehouseController warehouseController= fxmlLoader.getController();
        warehouseController.setData(entityManagerFactory,true,warehouse);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        FxUtils.setStageParameters(stage, scene, false);

    }
    public void DeleteWarehouse(ActionEvent actionEvent) {
        Warehouse warehouse = warehousesListView.getSelectionModel().getSelectedItem();
        warehousesListView.getItems().remove(warehouse);
        shopHibernate.deleteWarehouse(warehouse.getId());
        warehouseProductsListView.getItems().clear();
        warehouseManagerListView.getItems().clear();
    }

    public void LoadWarehouseData(){

        Warehouse warehouse = warehousesListView.getSelectionModel().getSelectedItem();
        if(warehouse != null) {
            warehouseProductsListView.getItems().clear();
            warehouseManagerListView.getItems().clear();
            warehouseManagerListView.getItems().addAll(warehouse.getEmployees());
            warehouseProductsListView.getItems().addAll(warehouse.getProductList());
        }

    }


}
