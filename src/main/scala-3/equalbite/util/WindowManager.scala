// Description: Utility object that manages all windows and navigation in the EqualBite application.
// Handles switching between main pages, showing popups, and loading FXML views into the stage.

package equalbite.util

import equalbite.model.{InventoryItem, Recipient}
import equalbite.view.{AboutController, AddInventoryController, AddRecipientController, InventoryInfoController, InventoryPageController, RecipientInfoController}
import javafx.fxml.FXMLLoader
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.stage.Modality.ApplicationModal
import scalafx.stage.Stage

import java.net.URL

// Object responsible for window and scene management in the application.
// Provides methods for loading main pages, subpages, and popup windows.
object WindowManager {
  private var stage: Stage = _

  // Initializes the main application stage.
  def init(stage: Stage): Unit = {
    this.stage = stage
  }

  // Root layout container, used for setting different windows in the center.
  var rootPane: Option[javafx.scene.layout.BorderPane] = None //for making rootPane accessible

  // Loads the main root layout depending on user type (Admin, User, Recipient).
  def showRootLayout(userType: String = ""): Unit = {
    val rootLayoutResource: URL = getClass.getResource(s"/equalbite/view/${userType}RootLayout.fxml")
    val loader = new FXMLLoader(rootLayoutResource)
    val rootLayout = loader.load[javafx.scene.layout.BorderPane]()
    rootPane = Option(loader.getRoot[javafx.scene.layout.BorderPane]())
    // Set the scene
    stage.setScene(new Scene(rootLayout))

    // Set the title
    stage.setTitle("EqualBite")

    // Set the application icon
    stage.getIcons.add( //referenced from https://codemia.io/knowledge-hub/path/javafx_application_icon
      new javafx.scene.image.Image(getClass.getResourceAsStream("/equalbite/images/AppIcon.png"))
    )
    // Show the stage
    stage.show()
  }

  def showMainRoot(): Unit = {
    showRootLayout("Main")
  }

  // Loads a window from a given FXML path into the root layout.
  def showWindow(fxmlpath: String): Unit = {
    val window = getClass.getResource(fxmlpath)
    val loader = new FXMLLoader(window)
    val pane = loader.load[javafx.scene.layout.AnchorPane]()
    rootPane.foreach(_.setCenter(pane))
  }

  // ===== Main pages ===== //
  def showStartWindow(): Unit = showWindow("/equalbite/view/StartPage.fxml")
  def showDashboardWindow(): Unit = showWindow("/equalbite/view/DashboardPage.fxml")
  def showDataInsightWindow(): Unit = showWindow("/equalbite/view/DataInsightPage.fxml")
  def showInventoryWindow(): Unit = showWindow("/equalbite/view/InventoryPage.fxml")
  def showRecipientWindow(): Unit = showWindow("/equalbite/view/RecipientPage.fxml")
  def showDistributionWindow(): Unit = showWindow("/equalbite/view/DistributionPage.fxml")
  def showDistributionRecordWindow(): Unit = showWindow("/equalbite/view/DistributionRecordPage.fxml")
  def showLoginWindow(): Unit = showWindow("/equalbite/view/LoginPage.fxml")
  def showSignUpWindow(): Unit = showWindow("/equalbite/view/SignUpPage.fxml")
  def showDistributionRequestWindow(): Unit = showWindow("/equalbite/view/DistributionRequestPage.fxml")


  // ===== Popup windows ===== //

  // Displays the "About" popup window.
  def showAbout(): Unit = {
    val about = getClass.getResource("/equalbite/view/About.fxml")
    val loader = new FXMLLoader(about)
    loader.load()
    val pane = loader.getRoot[javafx.scene.layout.AnchorPane]()
    val mywindow = new Stage():
      initOwner(stage)
      initModality(ApplicationModal)
      title = "About"
      scene = new Scene():
        root = pane
    val ctrl = loader.getController[AboutController]()
    ctrl.stage = Option(mywindow)
    mywindow.showAndWait()
  }

  // Displays Add/Edit Inventory popup.
  def showAddItemWindow(_mode: String = "add", editItem: Option[InventoryItem] = None): Unit = {
    val page = getClass.getResource("/equalbite/view/AddInventory.fxml")
    val loader = new FXMLLoader(page)
    loader.load()
    val pane = loader.getRoot[javafx.scene.layout.AnchorPane]()
    val mywindow = new Stage():
      initOwner(stage)
      initModality(ApplicationModal)
      title = "Add/Edit Item"
      scene = new Scene():
        root = pane
    val ctrl = loader.getController[AddInventoryController]()
    ctrl.stage = Option(mywindow)
    ctrl.mode = _mode
    ctrl.editItem = editItem
    if (editItem.isDefined) ctrl.initializeEditItem(editItem.get)
    mywindow.showAndWait()
  }

  // Displays Add/Edit Recipient popup.
  def showAddRecipientWindow(_mode: String = "add", editItem: Option[Recipient] = None): Unit = {
    val page = getClass.getResource("/equalbite/view/AddRecipient.fxml")
    val loader = new FXMLLoader(page)
    loader.load()
    val pane = loader.getRoot[javafx.scene.layout.AnchorPane]()
    val mywindow = new Stage():
      initOwner(stage)
      initModality(ApplicationModal)
      title = "Add/Edit Recipient"
      scene = new Scene():
        root = pane
    val ctrl = loader.getController[AddRecipientController]()
    ctrl.stage = Option(mywindow)
    ctrl.mode = _mode
    ctrl.editItem = editItem
    if (editItem.isDefined) ctrl.initializeEditItem(editItem.get)
    mywindow.showAndWait()
  }

  // Displays Inventory Info popup.
  def showInventoryInfo(item: Option[InventoryItem] = None): Unit = {
    val page = getClass.getResource("/equalbite/view/InventoryInfo.fxml")
    val loader = new FXMLLoader(page)
    loader.load()
    val pane = loader.getRoot[javafx.scene.layout.AnchorPane]()
    val mywindow = new Stage():
      initOwner(stage)
      initModality(ApplicationModal)
      title = "More Information"
      scene = new Scene():
        root = pane
    val ctrl = loader.getController[InventoryInfoController]()
    ctrl.stage = Option(mywindow)

    if (item.isDefined)
      ctrl.showItem(item.get)

    mywindow.showAndWait()
  }

  // Displays Recipient Info popup.
  def showRecipientInfo(recipient: Option[Recipient] = None): Unit = {
    val page = getClass.getResource("/equalbite/view/RecipientInfo.fxml")
    val loader = new FXMLLoader(page)
    loader.load()
    val pane = loader.getRoot[javafx.scene.layout.AnchorPane]()
    val mywindow = new Stage():
      initOwner(stage)
      initModality(ApplicationModal)
      title = "More Information"
      scene = new Scene():
        root = pane
    val ctrl = loader.getController[RecipientInfoController]()
    ctrl.stage = Option(mywindow)

    if (recipient.isDefined)
      ctrl.showItem(recipient.get)

    mywindow.showAndWait()
  }
}
