package equalbite

import equalbite.model._
import equalbite.util.WindowManager
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

object MainApp extends JFXApp3:

  // -----------------------------
  // Currently logged-in user
  // -----------------------------
  var currentUser: Option[User] = None

  override def start(): Unit = {
    // Load all data from CSV files into collections
    InventoryCollection.loadData()
    RecipientCollection.loadData()
    DistributionCollection.loadData()
    DistributionRequestCollection.loadData()
    UserCollection.loadData()

    // Ensure DistributionCollection is saved after loading
    DistributionCollection.saveData()

    // Initialize the primary stage
    val primaryStage = new PrimaryStage
    WindowManager.init(primaryStage)

    // Show root layout and first window
    WindowManager.showRootLayout()
    WindowManager.showStartWindow()
  }
