package equalbite.view

import equalbite.MainApp
import equalbite.model.{Distribution, DistributionCollection, DistributionRequestCollection, InventoryCollection, InventoryItem, Role}
import equalbite.util.AlertUtil.showWarning
import equalbite.util.{Permission, Searchable}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

// Controller for the Distribution Request page
// Handles initialization, search, and actions (accept/reject requests)
@FXML
class DistributionRequestPageController extends Searchable:

  // Table and column references
  @FXML var requestsTableView: javafx.scene.control.TableView[Distribution] = _
  @FXML private var nameTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var contactTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var recipientTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var itemCodeTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var quantityTableColumn: javafx.scene.control.TableColumn[Distribution, Int] = _
  @FXML private var dateTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var searchText: javafx.scene.control.TextField = _

  // Sets up table bindings with DistributionRequestCollection data
  @FXML
  def initialize(): Unit = {
    requestsTableView.items = DistributionRequestCollection.list
    nameTableColumn.cellValueFactory = _.value.name
    contactTableColumn.cellValueFactory = _.value.contact
    recipientTableColumn.cellValueFactory = _.value.recipient
    itemCodeTableColumn.cellValueFactory = _.value.itemCode
    quantityTableColumn.cellValueFactory = _.value.quantity
    dateTableColumn.cellValueFactory = _.value.date
  }

  // Accepts a selected request if user is admin and stock is sufficient
  @FXML
  def handleAccept(action: ActionEvent): Unit = {
    if (Permission.isAdmin) {
      val selectedRequest = requestsTableView.selectionModel().getSelectedItem
      val item = InventoryCollection.list.find(_.itemCode.value == selectedRequest.itemCode.value)

      if (selectedRequest.quantity.get > item.map(_.stock.value).getOrElse(0)) {
        showWarning("Insufficient Stock", "The requested quantity exceeds available stock.")
        return
      }

      if (selectedRequest != null) {
        DistributionRequestCollection.list.remove(selectedRequest)
        DistributionCollection.combinedList.remove(selectedRequest)
        selectedRequest.accepted.value = true
        selectedRequest.distributionCode.value = DistributionCollection.generateNextCode()
        item.get.stock.value -= selectedRequest.quantity.value
        DistributionCollection.addItem(selectedRequest)
        DistributionCollection.saveData()
        InventoryCollection.saveData()
        requestsTableView.items = DistributionRequestCollection.list
      } else {
        showWarning("No request selected", "Please select a request to accept.")
      }
    } else {
      showWarning("Permission Denied", "You do not have permission to accept distribution requests.")
    }
  }

  // Rejects a selected request if user is admin
  @FXML
  def handleReject(action: ActionEvent): Unit = {
    if (Permission.isAdmin) {
      val selectedRequest = requestsTableView.selectionModel().getSelectedItem
      if (selectedRequest != null) {
        DistributionRequestCollection.list.remove(selectedRequest)
        DistributionCollection.combinedList.remove(selectedRequest)
        DistributionCollection.saveData()
        requestsTableView.items = DistributionRequestCollection.list
      } else {
        showWarning("No request selected", "Please select a request to reject.")
      }
    } else {
      showWarning("Permission Denied", "You do not have permission to reject distribution requests.")
    }
  }

  // Filters requests based on search input
  @FXML
  override def handleSearch(action: ActionEvent): Unit = {
    val searchTerm = searchText.text.value.toLowerCase
    if (searchTerm.isEmpty) {
      requestsTableView.items = DistributionRequestCollection.list
    } else {
      val filteredItems = DistributionRequestCollection.list.filter(item =>
        item.name.value.toLowerCase.contains(searchTerm) ||
          item.contact.value.toLowerCase.contains(searchTerm) ||
          item.recipient.value.toLowerCase.contains(searchTerm) ||
          item.item.value.toLowerCase.contains(searchTerm)
      )
      requestsTableView.items = filteredItems
    }
  }
