package equalbite.view

import equalbite.MainApp
import equalbite.util.WindowManager
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class MainRootLayoutController():

  // Label showing current user mode (Admin/User)
  @FXML private var modeLabel: javafx.scene.control.Label = _

  // Initialize method sets the mode label to current user's role
  @FXML
  def initialize(): Unit = {
    modeLabel.setText(s"${MainApp.currentUser.get.role.toString} mode")
  }

  // Close the application
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    System.exit(0)
  }

  // Show About dialog
  @FXML
  def handleAbout(action: ActionEvent): Unit= {
    WindowManager.showAbout()
  }

  // -----------------------------
  // Navigation to main sections
  // -----------------------------

  @FXML def handleInventory(action: ActionEvent): Unit = {
    WindowManager.showInventoryWindow()
  }

  @FXML def handleRecipient(action: ActionEvent): Unit = {
    WindowManager.showRecipientWindow()
  }

  @FXML def handleDistribution(action: ActionEvent): Unit = {
    WindowManager.showDistributionWindow()
  }

  @FXML def handleDistributionRecord(action: ActionEvent): Unit = {
    WindowManager.showDistributionRecordWindow()
  }

  @FXML def handleDataInsight(action: ActionEvent): Unit = {
    WindowManager.showDataInsightWindow()
  }

  @FXML def handleDashboard(action: ActionEvent): Unit = {
    WindowManager.showDashboardWindow()
  }

  // -----------------------------
  // Navigation to login/start page
  // -----------------------------

  @FXML def handleLogin(action: ActionEvent): Unit = {
    WindowManager.showRootLayout()
    WindowManager.showLoginWindow()
  }

  @FXML def handleStartPage(action: ActionEvent): Unit = {
    WindowManager.showRootLayout()
    WindowManager.showStartWindow()
  }

  // -----------------------------
  // Quick-access actions for adding records
  // -----------------------------

  @FXML def handleAddInventory(action: ActionEvent): Unit = {
    WindowManager.showAddItemWindow()
  }

  @FXML def handleAddRecipient(action: ActionEvent): Unit = {
    WindowManager.showAddRecipientWindow()
  }

  @FXML def handleDistributionRequest(action: ActionEvent): Unit = {
    WindowManager.showDistributionRequestWindow()
  }
