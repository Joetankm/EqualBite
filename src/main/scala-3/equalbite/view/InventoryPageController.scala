package equalbite.view

import equalbite.model.{InventoryCollection, InventoryItem}
import equalbite.util.AlertUtil.showWarning
import equalbite.util.{Permission, PrintUtil, Printable, Searchable, WindowManager}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

// Controller for the Inventory Page
// Manages display, search, addition, deletion, editing, and printing of inventory items
@FXML
class InventoryPageController extends Printable, Searchable:

  // TableView displaying the list of inventory items
  @FXML var inventoryTableView: javafx.scene.control.TableView[InventoryItem] = _

  // Columns in the inventory TableView
  @FXML private var itemCodeTableColumn: javafx.scene.control.TableColumn[InventoryItem, String] = _
  @FXML private var nameTableColumn: javafx.scene.control.TableColumn[InventoryItem, String] = _
  @FXML private var categoryTableColumn: javafx.scene.control.TableColumn[InventoryItem, String] = _
  @FXML private var stockTableColumn: javafx.scene.control.TableColumn[InventoryItem, Int] = _
  @FXML private var detailTableColumn: javafx.scene.control.TableColumn[InventoryItem, String] = _

  // TextField for searching inventory items
  @FXML private var searchText: javafx.scene.control.TextField = _

  // Initialize the table view and set up column cell factories
  @FXML
  def initialize(): Unit = {
    inventoryTableView.items = InventoryCollection.list
    itemCodeTableColumn.cellValueFactory = _.value.itemCode
    nameTableColumn.cellValueFactory = _.value.name
    categoryTableColumn.cellValueFactory = _.value.category
    stockTableColumn.cellValueFactory = _.value.stock
    detailTableColumn.cellValueFactory = _.value.formattedDetail
  }

  // Handle adding a new inventory item
  // Opens the AddItem window
  @FXML
  def handleNewItem(): Unit = {
    WindowManager.showAddItemWindow()
  }

  // Handle deletion of the selected inventory item
  // Checks for admin permission and whether an item is selected
  @FXML
  def handleDelete(action: ActionEvent): Unit = {
    if (Permission.isAdmin) {
      val index = inventoryTableView.selectionModel.value.selectedIndex.value
      if (index >= 0 && index < InventoryCollection.list.size) {
        InventoryCollection.list.remove(index)
        InventoryCollection.saveData()
      }
    } else {
      showWarning("Permission Denied", "You do not have permission to delete items.")
    }
  }

  // Handle editing the selected inventory item
  // Checks for admin permission and opens AddItem window in edit mode
  @FXML
  def handleEdit(action: ActionEvent): Unit = {
    if (Permission.isAdmin){
      if (inventoryTableView.selectionModel.value.isEmpty) {
        showWarning("No item selected", "Please select an item to edit.")
        return
      }
      val food = inventoryTableView.selectionModel.value.selectedItem.value
      WindowManager.showAddItemWindow("edit", Some(food))
    } else {
      showWarning("Permission Denied", "You do not have permission to edit items.")
    }
  }

  // Handle search action
  // Filters inventory items based on the search term
  @FXML
  override def handleSearch(action: ActionEvent): Unit = {
    val searchTerm = searchText.text.value.toLowerCase
    if (searchTerm.isEmpty) {
      inventoryTableView.items = InventoryCollection.list
    } else {
      val filteredItems = InventoryCollection.list.filter(item =>
        item.itemCode.value.toLowerCase.contains(searchTerm) ||
          item.name.value.toLowerCase.contains(searchTerm) ||
          item.category.value.toLowerCase.contains(searchTerm) ||
          item.formattedDetail.value.toLowerCase.contains(searchTerm)
      )
      inventoryTableView.items = filteredItems
    }
  }

  // Handle printing of the inventory table
  // Uses PrintUtil to print the TableView of inventory items
  @FXML
  override def handlePrintRecord(action: ActionEvent): Unit = {
    PrintUtil.printTable(inventoryTableView)
  }
