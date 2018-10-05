
package duedatecalculator;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class ViewDueDateController implements Initializable {
    
//<editor-fold defaultstate="collapsed" desc="FXML import">
    @FXML
    private Label currDateLabel;
    @FXML
    private Label resultDateLabel;
    @FXML
    private TextField turnDayLabel;
    @FXML
    private TextField turnHourLabel;
    @FXML
    private TextField turnMinLabel;
    @FXML
    private TextField inputDateLabel;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane basePane;
//</editor-fold>
     

    String s_inputTime;
    String s_currentTime;     
    Calendar inTime;
    Calendar newTime;    
    public int turnTimeDay;
    public int turnTimeHour;
    public int turnTimeMin;  
    public int day;
    public int hour;
    public int minute;      
    private final int SATURDAY = 7;
    private final int SUNDAY = 1;    
    
    public void timeCalculation(){
    newTime = inTime;
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" inTime");      
    
    // it views the begining of the period and fixes it to the working hours   
    day = inTime.get(Calendar.DAY_OF_WEEK);
    hour = inTime.get(Calendar.HOUR_OF_DAY);  
    minute = inTime.get(Calendar.MINUTE);     
    // if the start date is out of worktime it modifies the date
    if (day == SUNDAY){
       newTime.add(Calendar.DAY_OF_WEEK, 1); 
    }
    if (day == SATURDAY){
       newTime.add(Calendar.DAY_OF_WEEK, 2); 
    }
    if (hour < 9){
       newTime.set(Calendar.HOUR_OF_DAY, 9); 
       newTime.set(Calendar.MINUTE, 0);    
    } 
    if (hour > 17){
       newTime.add(Calendar.DAY_OF_WEEK, 1);        
       newTime.set(Calendar.HOUR_OF_DAY, 9);  
       newTime.set(Calendar.MINUTE, 0);    
    }
    // end of time correction 1    
 
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" corrected inTime");      
    
    
   // a for cícle to handle to handle the working hours 
  for (int i=1; i<=turnTimeDay; i++){  
    newTime.add(Calendar.HOUR_OF_DAY, 8); 
    correctEndTime();
  }
    // end of time correction 2    
  
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" after day FOR");  

    // adds the hours & minutes from the turnaround tine
    newTime.add(Calendar.HOUR, turnTimeHour);   
    newTime.add(Calendar.MINUTE, turnTimeMin);     
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" after adding turntime hour & min");      

    correctEndTime();
    // end of time correction 3
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" after 2nd end correction");      
    
    
    newTime.getTime(); 
//    System.out.println(hour+":"+minute +" before finale");     
    System.out.println(newTime.get(Calendar.DAY_OF_WEEK)+" "+newTime.get(Calendar.HOUR_OF_DAY)+":"+newTime.get(Calendar.MINUTE) +" endtime");  
    System.out.println();
    
    Date date = newTime.getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
      //to convert Date to String, use format method of SimpleDateFormat class.
    String strDate = dateFormat.format(date);
    resultDateLabel.setText(strDate); 
    }

    public void correctEndTime(){
    // correcting time after adding extra hours 2nd, same as previous
    day = newTime.get(Calendar.DAY_OF_WEEK);
    hour = newTime.get(Calendar.HOUR_OF_DAY);  
    minute = newTime.get(Calendar.MINUTE);  
  
    int diffHourPM = hour-17;
    int diffHourAM = hour+7;    
    System.out.println("diffPM:"+diffHourPM); 
    System.out.println("diffAM:"+diffHourAM); 

    // if the FINISH DATE is out of worktime,it modifes the date an adds the remaining hours      
    if (day == SUNDAY){
       newTime.add(Calendar.DAY_OF_WEEK, 1); 
       newTime.set(Calendar.HOUR_OF_DAY, 9);  
       newTime.add(Calendar.HOUR_OF_DAY, diffHourPM);     
    
    }
    if (day == SATURDAY){
       newTime.add(Calendar.DAY_OF_WEEK, 2); 
       newTime.set(Calendar.HOUR_OF_DAY, 9);  
       newTime.add(Calendar.HOUR_OF_DAY, diffHourPM); 
        
    }    
    if (hour < 9 && minute != 0){
       newTime.set(Calendar.HOUR_OF_DAY, 9); 
       newTime.add(Calendar.HOUR_OF_DAY, diffHourAM);  
       
      
    }         
    if (hour >= 17 && minute != 0){
       newTime.add(Calendar.DAY_OF_WEEK, 1);        
       newTime.set(Calendar.HOUR_OF_DAY, 9);  
       newTime.add(Calendar.HOUR_OF_DAY, diffHourPM); 
      
    }  
    }
    
    public void inputTime(){
    s_inputTime = inputDateLabel.getText();
    inTime = Calendar.getInstance(); 
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm");  
        try {
            inTime.setTime(dateFormat.parse(s_inputTime));   
            turnTimeDay = Integer.parseInt(turnDayLabel.getText()); 
            turnTimeHour = Integer.parseInt(turnHourLabel.getText()); 
            turnTimeMin = Integer.parseInt(turnMinLabel.getText());             
        } catch (Exception ex) {
            System.out.println(ex);             
            alert("The date or time format is wrong!");            
        }
    }    
    
    public void currentTime(){
    Calendar currTime = Calendar.getInstance(); 
    Date date = currTime.getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
      //to convert Date to String, use format method of SimpleDateFormat class.
      String strDate = dateFormat.format(date);
      s_currentTime = strDate;
    currDateLabel.setText(strDate);

    }
    
//<editor-fold defaultstate="collapsed" desc="Buttons">
    @FXML
    private void calculateButton(ActionEvent event) {
        inputTime();
 
        timeCalculation();
    }
    
    @FXML
    private void currTimeButton(ActionEvent event) {
        inputDateLabel.setText(s_currentTime);
        
    }
//</editor-fold>
   
    public void alert(String text){
        basePane.setDisable(true);
        basePane.setOpacity(0.2);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);

        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 80.0);
        anchor.setLeftAnchor(vbox, 80.0);          
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                basePane.setDisable(false);
                basePane.setOpacity(1);
                vbox.setVisible(false);
            }
        });
 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentTime();



    }    

}
