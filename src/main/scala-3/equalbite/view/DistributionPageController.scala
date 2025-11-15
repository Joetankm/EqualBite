package equalbite.view

import scalafx.stage.Stage
import equalbite.MainApp
import equalbite.model.{Distribution, DistributionCollection, DistributionRequestCollection, InventoryCollection, InventoryItem, Recipient, RecipientCollection}
import equalbite.util.{AlertUtil, Permission, WindowManager}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

import java.time.LocalDate

// Controller class for handling the Distribution Page UI logic
@FXML
class DistributionPageController:
  var stage: Option[Stage] = None // Stage reference for window handling
  var selectedRecipient: Recipient = _ // Currently selected recipient
  var selectedInventory: InventoryItem = _ // Currently selected inventory item

  // TableViews for displaying inventory and recipients
  @FXML
  private var inventoryTableView: javafx.scene.control.TableView[InventoryItem] = _

  @FXML
  private var recipientTableView: javafx.scene.control.TableView[Recipient] = _

  // TableColumns for inventory
  @FXML
  private var itemNameTableColumn: javafx.scene.control.TableColumn[InventoryItem, String] = _

  @FXML
  private var stockTableColumn: javafx.scene.control.TableColumn[InventoryItem, Int] = _

  // TableColumns for recipients
  @FXML
  private var recipientNameTableColumn: javafx.scene.control.TableColumn[Recipient, String] = _

  @FXML
  private var householdSizeTableColumn: javafx.scene.control.TableColumn[Recipient, Int] = _

  // Labels for displaying selected inventory details
  @FXML
  private var inventoryNameLabel: javafx.scene.control.Label = _

  @FXML
  private var stockLabel: javafx.scene.control.Label = _

  // Input field for entering distribution amount
  @FXML
  private var inventoryAmountText: javafx.scene.control.TextField = _

  // Labels for displaying selected recipient details
  @FXML
  private var recipientNameLabel: javafx.scene.control.Label = _

  @FXML
  private var householdSizeLabel: javafx.scene.control.Label = _

  // Initialize method to set up table data bindings
  def initialize(): Unit = {
    inventoryTableView.items = InventoryCollection.list
    recipientTableView.items = RecipientCollection.list
    itemNameTableColumn.cellValueFactory = _.value.name
    stockTableColumn.cellValueFactory = _.value.stock
    recipientNameTableColumn.cellValueFactory = _.value.name
    householdSizeTableColumn.cellValueFactory = _.value.householdSize
  }

  // Opens additional info window for selected inventory item
  @FXML
  def handleMoreInventoryInfo(action: ActionEvent): Unit = {
    selectedInventory = inventoryTableView.selectionModel.value.selectedItem.value
    if (selectedInventory != null) {
      WindowManager.showInventoryInfo(Some(selectedInventory))
    } else {
      AlertUtil.showWarning("Invalid Selection", "Please select an Item.")
      println("No item selected for more info")
    }
  }

  // Opens additional info window for selected recipient
  @FXML
  def handleMoreRecipientInfo(action: ActionEvent): Unit = {
    selectedRecipient = recipientTableView.selectionModel.value.selectedItem.value
    if (selectedRecipient != null) {
      WindowManager.showRecipientInfo(Some(selectedRecipient))
    } else {
      AlertUtil.showWarning("Invalid Selection", "Please select a Recipient.")
      println("No item selected for more info")
    }
  }

  // Handles selection of an inventory item and updates labels
  @FXML
  def handleSelectInventory(action: ActionEvent): Unit = {
    selectedInventory = inventoryTableView.selectionModel.value.selectedItem.value
    if (selectedInventory != null) {
      inventoryNameLabel.text = selectedInventory.name.value
      stockLabel.text = selectedInventory.stock.value.toString
      inventoryAmountText.text = "0" // Default to 0
    } else {
      AlertUtil.showWarning("Invalid Selection", "Please select an Item.")
      println("No item selected for distribution")
    }
  }

  // Handles selection of a recipient and updates labels
  @FXML
  def handleSelectRecipient(action: ActionEvent): Unit = {
    selectedRecipient = recipientTableView.selectionModel.value.selectedItem.value
    if (selectedRecipient != null) {
      recipientNameLabel.text = selectedRecipient.name.value
      householdSizeLabel.text = selectedRecipient.householdSize.value.toString
    } else {
      AlertUtil.showWarning("Invalid Selection", "Please select a Recipient.")
      println("No recipient selected for distribution")
    }
  }

  // Handles the process of distributing items
  @FXML
  def handleDistribute(action: ActionEvent): Unit = {
    // Ensure selections are made
    selectedInventory = inventoryTableView.selectionModel.value.selectedItem.value
    selectedRecipient = recipientTableView.selectionModel.value.selectedItem.value
    if (selectedInventory == null || selectedRecipient == null) {
      AlertUtil.showWarning("Invalid Selection", "Please select both an Item and a Recipient.")
      return
    }

    try {
      val amountToDistribute = inventoryAmountText.text.value.toInt
      // Validate distribution amount
      if (amountToDistribute <= 0 || amountToDistribute > selectedInventory.stock.value) {
        AlertUtil.showWarning("Invalid Amount", "Please enter a valid amount to distribute.")
        return
      }

      // Create new distribution record
      val date: String = LocalDate.now().toString
      val distributionCode: Int = DistributionCollection.generateNextCode()

      if (Permission.isAdmin) {
        // Admin can directly update inventory
        selectedInventory.stock.value -= amountToDistribute
        InventoryCollection.saveData()

        AlertUtil.showInfo("Distribution Successful",
          s"Distributed $amountToDistribute ${selectedInventory.name.value} to ${selectedRecipient.name.value}.")

        val dist = Distribution(
          selectedInventory.itemCode.value,
          distributionCode,
          selectedRecipient.name.value,
          selectedInventory.name.value,
          selectedInventory.category.value,
          amountToDistribute,
          date
        )

        DistributionCollection.addItem(dist)
        DistributionCollection.saveData()

      } else {
        // Non-admins create a distribution request
        val dist = Distribution(
          selectedInventory.itemCode.value,
          distributionCode,
          selectedRecipient.name.value,
          selectedInventory.name.value,
          selectedInventory.category.value,
          amountToDistribute,
          date,
          false,
          MainApp.currentUser.get.name.value,
          MainApp.currentUser.get.contact.value
        )
        DistributionRequestCollection.addItem(dist)
        DistributionCollection.saveData()

        AlertUtil.showInfo("Distribution Request Sent",
          s"Requested to distribute $amountToDistribute ${selectedInventory.name.value} to ${selectedRecipient.name.value}.")
      }

    } catch {
      case _: NumberFormatException =>
        AlertUtil.showWarning("Invalid Input", "Please enter a valid number for the amount to distribute.")
    }
  }

