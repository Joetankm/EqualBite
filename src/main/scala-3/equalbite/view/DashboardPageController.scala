package equalbite.view

import equalbite.model.{Distribution, DistributionCollection, InventoryCollection, RecipientCollection}
import equalbite.util.WindowManager
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

@FXML
class DashboardPageController:

  // Dashboard statistics labels
  @FXML
  private var stockLabel: javafx.scene.control.Label = _

  @FXML
  private var recipientLabel: javafx.scene.control.Label = _

  @FXML
  private var stockDistributedLabel: javafx.scene.control.Label = _

  @FXML
  private var recipientsHelpedLabel: javafx.scene.control.Label = _

  // Table for showing recent distributions
  @FXML
  private var distributionTableView: javafx.scene.control.TableView[Distribution] = _

  @FXML
  private var distributionCodeTableColumn: javafx.scene.control.TableColumn[Distribution, Int] = _

  @FXML
  private var recipientTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _

  @FXML
  private var itemTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _

  @FXML
  private var quantityTableColumn: javafx.scene.control.TableColumn[Distribution, Int] = _

  @FXML
  private var dateTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _

  // Initialization method called automatically after FXML is loaded
  @FXML
  def initialize(): Unit = {
    // Set initial values for total distributed stock and unique recipients helped
    stockDistributedLabel.setText(DistributionCollection.list.map(_.quantity.value).sum.toString)
    recipientsHelpedLabel.setText(DistributionCollection.list.map(_.recipient.value).distinct.size.toString)

    // Helper functions to update labels dynamically
    def updateStockLabel(): Unit =
      stockLabel.setText(InventoryCollection.list.map(_.stock.value).sum.toString)

    def updateRecipientLabel(): Unit =
      recipientLabel.setText(RecipientCollection.list.size.toString)

    def updateStockDistributed(): Unit =
      stockDistributedLabel.setText(DistributionCollection.list.map(_.quantity.value).sum.toString)

    def updateRecipientsHelped(): Unit =
      recipientsHelpedLabel.setText(DistributionCollection.list.map(_.recipient.value).distinct.size.toString)

    // Run updates initially
    updateStockLabel()
    updateRecipientLabel()
    updateStockDistributed()
    updateRecipientsHelped()

    // Attach listeners to automatically update stock and recipient counts
    //referenced from https://scalafx.org/docs/properties
    InventoryCollection.list.addListener((_: javafx.collections.ListChangeListener.Change[_]) => updateStockLabel())
    RecipientCollection.list.addListener((_: javafx.collections.ListChangeListener.Change[_]) => updateRecipientLabel())

    // Populate table with the 5 most recent distributions
    distributionTableView.items = DistributionCollection.list.takeRight(5)
    distributionCodeTableColumn.cellValueFactory = _.value.distributionCode
    recipientTableColumn.cellValueFactory = _.value.recipient
    itemTableColumn.cellValueFactory = _.value.item
    quantityTableColumn.cellValueFactory = _.value.quantity
    dateTableColumn.cellValueFactory = _.value.date
  }

  // Handle button click to open "Add Inventory" window
  @FXML
  def handleAddInventory(action:ActionEvent): Unit = {
    WindowManager.showAddItemWindow()
  }

  // Handle button click to open "Add Recipient" window
  @FXML
  def handleAddRecipient(action:ActionEvent): Unit = {
    WindowManager.showAddRecipientWindow()
  }

