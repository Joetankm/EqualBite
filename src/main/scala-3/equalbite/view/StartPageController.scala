package equalbite.view

import equalbite.util.WindowManager
import javafx.fxml.FXML

@FXML
class StartPageController:
  
  // Navigate to Login Window
  @FXML
  def handleStart(action: javafx.event.ActionEvent): Unit = {
    WindowManager.showLoginWindow()
  }

  // Show About / More Information
  @FXML
  def handleMoreInfo(action: javafx.event.ActionEvent): Unit = {
    WindowManager.showAbout ()
  }

