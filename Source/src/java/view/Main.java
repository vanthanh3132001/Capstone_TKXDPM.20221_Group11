package view;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

import javafx.scene.Parent;

public class Main extends Application {

        public static void main(String[] args) {
        launch(args);

         }


//        @Override
//        public void start(Stage stage) throws IOException, SQLException {
//            FXMLLoader fxmlLoader = new FXMLLoader(view.Main.class.getResource("StationsForm.fxml"));
//            Scene scene = new Scene(fxmlLoader.load());
//            stage.setTitle("Trang chủ");
//            stage.setScene(scene);
//            stage.setResizable(false);
////            stage.setWidth(800);
////            stage.setHeight(700);
//            stage.show();
//
//        }
    public void start(Stage primaryStage) {
    try{
//        System.out.print(this.getClass().getResource("StationsForm.fxml"));
        Parent root = FXMLLoader.load(this.getClass().getResource("StationsForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }catch (Exception e){
        System.out.println(e);
    }
}




    }

