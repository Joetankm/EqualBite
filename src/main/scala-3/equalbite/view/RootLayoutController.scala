package equalbite.view

import equalbite.util.WindowManager
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():

  // Closes the entire application
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    System.exit(0)
  }

  // Opens the About window
  @FXML
  def handleAbout(action: ActionEvent): Unit = {
    WindowManager.showAbout()
  }

  // Opens the login window
  @FXML
  def handleLogin(action: ActionEvent): Unit = {
    WindowManager.showLoginWindow()
  }

  // Returns to the start page
  @FXML
  def handleStartPage(action: ActionEvent): Unit = {
    WindowManager.showStartWindow()
  }
