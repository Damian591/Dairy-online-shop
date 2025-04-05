package org.example.kursinis.fxControllers;

import org.example.kursinis.HelloApplication;
import org.example.kursinis.hibernate.ShopHibernate;
import org.example.kursinis.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductReview {
    @FXML
    public ListView<Product> productListField;
    @FXML
    public TreeView<Comment> commentTreeField;
    private EntityManagerFactory entityManagerFactory;
    private ShopHibernate shopHibernate;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.user = user;
        fillLists();
    }

    private void fillLists() {
        productListField.getItems().clear();
        productListField.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void previewProduct() {
        Product product = shopHibernate.getEntityById(Product.class, productListField.getSelectionModel().getSelectedItem().getId());
        if (product instanceof Cheese cheese) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, cheese.getTitle(), cheese.toString());
        }else if(product instanceof Milk milk) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, milk.getTitle(), milk.toString());
        }else if(product instanceof Yogurt yogurt) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, yogurt.getTitle(), yogurt.toString());
        }
    }

    public void addReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, user, productListField.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void loadReviews() {
        //Get info about product from database
        Product product = shopHibernate.getEntityById(Product.class, productListField.getSelectionModel().getSelectedItem().getId());
        commentTreeField.setRoot(new TreeItem<>());
        commentTreeField.setShowRoot(false);
        commentTreeField.getRoot().setExpanded(true);
        product.getComments().forEach(comment -> addTreeItem(comment, commentTreeField.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void updateComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void reply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue(), user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void delete() {
        shopHibernate.deleteComment(commentTreeField.getSelectionModel().getSelectedItem().getValue().getId());
    }
}
