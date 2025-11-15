package equalbite.view

import equalbite.model.InventoryItem
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.stage.Stage
import scalafx.Includes.*

// Controller for the Inventory Info dialog
// Handles displaying details of a selected inventory item
@FXML
class InventoryInfoController():
  var stage: Option[Stage] = None        // Reference to the dialog stage
  var okClicked = false                  // Flag to indicate if OK/close button was clicked

  // UI labels to display inventory item details
  @FXML private var itemCodeLabel: javafx.scene.control.Label = _
  @FXML private var nameLabel: javafx.scene.control.Label = _
  @FXML private var categoryLabel: javafx.scene.control.Label = _
  @FXML private var stockLabel: javafx.scene.control.Label = _
  @FXML private var detailLabel: javafx.scene.control.Label = _
  @FXML private var detailTitleLabel: javafx.scene.control.Label = _

  // Sets the labels to display the provided inventory item's information
  @FXML
  def showItem(item: InventoryItem): Unit = {
    if (item != null) {
      itemCodeLabel.text = item.itemCode.value
      nameLabel.text = item.name.value
      categoryLabel.text = item.category.value
      stockLabel.text = item.stock.value.toString
      detailLabel.text = item.detail.value
      // Set a descriptive label for the detail field based on category
      detailTitleLabel.text = item.category.value match {
        case "Packaged Items" => "Pack Size:"
        case "Perishable Items" => "Storage:"
        case "Supplement Items" => "Nutrient:"
        case _ => "Detail:"
      }
    } else {
      println("No item provided for InventoryInfoController")
    }
  }

  // Handles closing the inventory info dialog
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    okClicked = true
    stage.foreach(x => x.close())
  }