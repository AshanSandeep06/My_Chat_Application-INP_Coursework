package lk.ijse.socket.util;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class OptionUtil {
    public static void minimizeMouseEnter(Label label){
        label.setStyle("-fx-background-color: #E5E5E5");
    }

    public static void minimizeMouseExit(Label label){
        label.setStyle("-fx-background-color: #F3F3F3");
    }

    public static void closeMouseEnter(Label label, ImageView image){
        label.setStyle("-fx-background-color: #E81123");
        image.setImage(new Image("lk/ijse/socket/assets/images/close2.png"));
    }

    public static void closeMouseExit(Label label, ImageView image){
        label.setStyle("-fx-background-color:  #F3F3F3");
        image.setImage(new Image("lk/ijse/socket/assets/images/close.png"));
    }

    public static void minimizeOnAction(Window window){
        Stage stage = (Stage) window;
        stage.setIconified(true);
    }

    public static void closeOnAction(Window window){
        //Stage stage = (Stage) window;
        //stage.close();
        System.exit(0);
    }
}
