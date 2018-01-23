/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HydraGame;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Abdelkader
 */
public class HydraGameController implements Initializable {

    @FXML
    private GridPane BallPane;
    @FXML
    private Label hydraHeadSize;
    @FXML
    private Slider hydraSlider;
    @FXML
    private Label message;

    private int startingSize;

    @FXML
    public void setHeadSize() {
        Double size = hydraSlider.getValue();
        startingSize = size.intValue();
        hydraHeadSize.setText(Integer.toString(startingSize));
    }
    ListInterface<HydraHead> headList = null;
    
    ListInterface <String> workList = null; //worklist added for counting cuts


    public void play() {
        message.setText("");
        headList = new AList<>();
        workList = new AList<>();
        
        BallPane.getChildren().removeAll(BallPane.getChildren());
        HydraHead head = makeHead(startingSize);
        headList.add(head);
        BallPane.add(head, head.getGridX(), head.getGridY());
    }

    public void updatePane() {
        BallPane.getChildren().removeAll(BallPane.getChildren());
        for (int i = 1; i <= headList.getLength(); i++) {
            HydraHead head = headList.getEntry(i);
            BallPane.add(head, head.getGridX(), head.getGridY());
        }
    }

    private HydraHead makeHead(int size) {
        Random randomGenerator = new Random();
        HydraHead head = new HydraHead(size, randomGenerator.nextInt(15), randomGenerator.nextInt(15));
        head.setFill(Color.BLACK);
        head.setOnMouseClicked(e -> {
            HydraHead ball = (HydraHead) e.getSource();
            BallPane.getChildren().remove(ball); // remove from the screen
            headList.remove(headList.getPosition(ball)); // remove from the list
            
            String d = "chop";
            
            workList.add (d); //adds the string "chop" to the worklist
            
            
            int s = ball.getHeadSize();
            if (s > 1) {
                // add two smaller balls
                headList.add(makeHead(s - 1));
                headList.add(makeHead(s - 1));              
                updatePane();
            }
            if (headList.getLength() == 0) {
               message.setText("Good Job, you have made " +workList.getLength()+" cuts."); // shows how many cuts were made to the hydra
            };
        });
        return head;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startingSize = 5;
        hydraHeadSize.setText(Integer.toString(startingSize));
        message.setText("");
    }
}
