package equalbite.view

import equalbite.model.{Distribution, DistributionCollection}
import equalbite.util.{PrintUtil, Printable, Searchable}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*

// Controller for the Distribution Record page
// Handles table initialization, search, and print functionality
@FXML
class DistributionRecordPageController extends Printable, Searchable:

  // Table and column references
  @FXML private var distributionTableView: javafx.scene.control.TableView[Distribution] = _
  @FXML private var distributionCodeTableColumn: javafx.scene.control.TableColumn[Distribution, Int] = _
  @FXML private var recipientTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var itemTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var quantityTableColumn: javafx.scene.control.TableColumn[Distribution, Int] = _
  @FXML private var dateTableColumn: javafx.scene.control.TableColumn[Distribution, String] = _
  @FXML private var searchText: javafx.scene.control.TextField = _

  // Sets up table bindings with DistributionCollection data
  @FXML
  def initialize(): Unit = {
    distributionTableView.items = DistributionCollection.list
    distributionCodeTableColumn.cellValueFactory = _.value.distributionCode
    recipientTableColumn.cellValueFactory = _.value.recipient
    itemTableColumn.cellValueFactory = _.value.item
    quantityTableColumn.cellValueFactory = _.value.quantity
    dateTableColumn.cellValueFactory = _.value.date
  }

  // Filters distribution records based on search input
  @FXML
  override def handleSearch(action: ActionEvent): Unit = {
    val searchTerm = searchText.text.value.toLowerCase
    if (searchTerm.isEmpty) {
      distributionTableView.items = DistributionCollection.list
    } else {
      val filteredItems = DistributionCollection.list.filter(distribution =>
        distribution.distributionCode.value.toString.contains(searchTerm) ||
          distribution.recipient.value.toLowerCase.contains(searchTerm) ||
          distribution.item.value.toLowerCase.contains(searchTerm) ||
          distribution.date.value.toLowerCase.contains(searchTerm)
      )
      distributionTableView.items = filteredItems
    }
  }

  // Prints the current table of distribution records
  @FXML
  override def handlePrintRecord(action: ActionEvent): Unit = {
    PrintUtil.printTable(distributionTableView)
  }
