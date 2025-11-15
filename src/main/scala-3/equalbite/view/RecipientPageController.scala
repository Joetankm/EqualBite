package equalbite.view

import equalbite.model.{Recipient, RecipientCollection}
import equalbite.util.{AlertUtil, Permission, PrintUtil, Printable, Searchable, WindowManager}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

@FXML
class RecipientPageController extends Printable, Searchable:
  @FXML private var recipientTableView: javafx.scene.control.TableView[Recipient] = _
  @FXML private var usernameTableColumn: javafx.scene.control.TableColumn[Recipient, String] = _
  @FXML private var nameTableColumn: javafx.scene.control.TableColumn[Recipient, String] = _
  @FXML private var contactTableColumn: javafx.scene.control.TableColumn[Recipient, String] = _
  @FXML private var addressTableColumn: javafx.scene.control.TableColumn[Recipient, String] = _
  @FXML private var householdSizeTableColumn: javafx.scene.control.TableColumn[Recipient, Int] = _
  @FXML private var elderlyCountTableColumn: javafx.scene.control.TableColumn[Recipient, Int] = _
  @FXML private var childrenCountTableColumn: javafx.scene.control.TableColumn[Recipient, Int] = _
  @FXML private var searchText: javafx.scene.control.TextField = _

  // Initialize TableView
  @FXML
  def initialize(): Unit = {
    recipientTableView.items = RecipientCollection.list
    usernameTableColumn.cellValueFactory = _.value.username
    nameTableColumn.cellValueFactory = _.value.name
    contactTableColumn.cellValueFactory = _.value.contact
    addressTableColumn.cellValueFactory = _.value.address
    householdSizeTableColumn.cellValueFactory = _.value.householdSize
    elderlyCountTableColumn.cellValueFactory = _.value.elderlyCount
    childrenCountTableColumn.cellValueFactory = _.value.childrenCount
  }

  // CRUD Methods
  @FXML def handleNewItem(): Unit = {
    WindowManager.showAddRecipientWindow()
  }

  @FXML def handleDelete(action: ActionEvent): Unit = {
    if Permission.isAdmin then
      val index = recipientTableView.selectionModel.value.selectedIndex.value
      if index >= 0 && index < RecipientCollection.list.size then {
        RecipientCollection.list.remove(index)
        RecipientCollection.saveData()
      } else
      AlertUtil.showWarning("Permission Denied", "You do not have permission to delete recipients.")
  }

  @FXML def handleEdit(action: ActionEvent): Unit = {
    if Permission.isAdmin then
      if recipientTableView.selectionModel.value.isEmpty then
        println("No recipient selected for editing")
        return
      val recipient = recipientTableView.selectionModel.value.selectedItem.value
      WindowManager.showAddRecipientWindow("edit", Some(recipient))
    else
      AlertUtil.showWarning("Permission Denied", "You do not have permission to edit recipients.")
  }

  // Search
  @FXML override def handleSearch(action: ActionEvent): Unit = {
    val searchTerm = searchText.text.value.toLowerCase
    if searchTerm.isEmpty then
      recipientTableView.items = RecipientCollection.list
    else
      val filteredItems = RecipientCollection.list.filter(recipient =>
        recipient.username.value.toLowerCase.contains(searchTerm) ||
          recipient.name.value.toLowerCase.contains(searchTerm) ||
          recipient.contact.value.toLowerCase.contains(searchTerm) ||
          recipient.address.value.toLowerCase.contains(searchTerm)
      )
      recipientTableView.items = filteredItems
  }
  // Print
  @FXML override def handlePrintRecord(action: ActionEvent): Unit = {
    PrintUtil.printTable(recipientTableView)
  }
