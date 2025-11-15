package equalbite.view

import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.stage.Stage
import equalbite.model.{InventoryCollection, InventoryItem, OtherFoodItem, PackagedFoodItem, PerishableFoodItem, SupplementItem}
import equalbite.util.AlertUtil
import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import javafx.scene.control.ChoiceBox //referenced from https://www.geeksforgeeks.org/java/javafx-choicebox

@FXML
class AddInventoryController():
  var stage: Option[Stage] = None              // reference to the current stage (window)
  var okClicked = false                        // flag to indicate if OK/Close was clicked

  var mode = "add"                             // determines whether the window is for "add" or "edit"
  var editItem: Option[InventoryItem] = None   // stores the item being edited (if mode == edit)

  // FXML bindings to UI controls
  @FXML private var itemCodeText: javafx.scene.control.TextField = _
  @FXML private var nameText: javafx.scene.control.TextField = _
  @FXML private var detailText: javafx.scene.control.TextField = _
  @FXML private var detailLabel: javafx.scene.control.Label = _
  @FXML private var categoryChoiceBox: ChoiceBox[String] = _
  @FXML private var stockText: javafx.scene.control.TextField = _

  // Function: initialize()
  // Purpose: called automatically when the FXML is loaded.
  // It populates the category choice box and adjusts the detail label dynamically based on the selection.
  @FXML
  def initialize(): Unit = {
    // Initialize the category choice box with predefined categories
    categoryChoiceBox.items = ObservableBuffer("Packaged Items", "Perishable Items", "Supplement Items", "Other Items")

    // Listener to update label text when category changes
    categoryChoiceBox.valueProperty.addListener((_, _, newValue) => {
      //referenced from https://www.geeksforgeeks.org/java/javafx-choicebox
      //referenced from https://scalafx.org/docs/properties
      if (newValue != null) {
        newValue match {
          case "Packaged Items"   => detailText.text = ""; detailLabel.text = "Pack Size:"
          case "Perishable Items" => detailText.text = ""; detailLabel.text = "Storage:"
          case "Supplement Items" => detailText.text = ""; detailLabel.text = "Nutrient:"
          case "Other Items"      => detailText.text = ""; detailLabel.text = "Detail:"
        }
      } else {
        detailLabel.setText("Please select a household type.") // fallback
      }
    })
  }

  // Function: handleClose()
  // Purpose: handles the closing of the window when user clicks "Close"
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    okClicked = true
    stage.foreach(x => x.close())
  }

  // Function: setEditItem()
  // Purpose: used to set text field values when editing an existing item
  @FXML
  def initializeEditItem(item: InventoryItem): Unit = {
    editItem = Some(item)
    itemCodeText.text = item.itemCode.value
    nameText.text = item.name.value
    categoryChoiceBox.value.value = item.category.value
    stockText.text = item.stock.value.toString
    detailText.text = item.detail.value
  }

  // Function: handleOK()
  // Purpose: validates input fields, and either updates an existing item (edit mode)
  //          or creates a new item (add mode). Shows warnings for invalid input.
  @FXML
  def handleOK(action: ActionEvent): Unit = {
    try {
      // Input validation
      if (itemCodeText.text.value.isEmpty || nameText.text.value.isEmpty || categoryChoiceBox.value.value == null || stockText.text.value.isEmpty || detailText.text.value.isEmpty) {
        AlertUtil.showWarning("Incomplete Information", "Please fill in all fields before proceeding.")
        return
      }
      else if (stockText.text.value.toInt <= 0) {
        AlertUtil.showWarning("Invalid Stock", "Stock must be greater than 0.")
        return
      }
      

      // Edit mode: update existing item
      if (mode == "edit") {
        if (editItem.isDefined) {
          val item = editItem.get
          item.itemCode.value = itemCodeText.text.value
          item.name.value = nameText.text.value
          item.category.value = categoryChoiceBox.value.value
          item.stock.value = stockText.text.value.toInt
          item.detail.value = detailText.text.value
          InventoryCollection.saveData()
          stage.foreach(x => x.close()) // close window
        } else {
          println("No item to edit")
        }
      }

      // Add mode: create a new item and save
      if (mode == "add") {
        if (InventoryCollection.itemExists(itemCodeText.text.value)) {
          AlertUtil.showWarning("Item code already exists", "Please enter a different item code.")
          return
        }
        categoryChoiceBox.value.value match {
          case "Supplement Items" =>
            val item = SupplementItem(itemCodeText.text.value, nameText.text.value, categoryChoiceBox.value.value, stockText.text.value.toInt, detailText.text.value)
            InventoryCollection.addItem(item)
          case "Perishable Items" =>
            val item = PerishableFoodItem(itemCodeText.text.value, nameText.text.value, categoryChoiceBox.value.value, stockText.text.value.toInt, detailText.text.value)
            InventoryCollection.addItem(item)
          case "Packaged Items" =>
            val item = PackagedFoodItem(itemCodeText.text.value, nameText.text.value, categoryChoiceBox.value.value, stockText.text.value.toInt, detailText.text.value)
            InventoryCollection.addItem(item)
          case _ =>
            val item = OtherFoodItem(itemCodeText.text.value, nameText.text.value, categoryChoiceBox.value.value, stockText.text.value.toInt, detailText.text.value)
            InventoryCollection.addItem(item)
        }
        InventoryCollection.saveData()
        stage.foreach(x => x.close())
      }

    } catch {
      case _: NumberFormatException =>
        // Handle case where stock input is not a valid number
        AlertUtil.showWarning("Invalid input", "Please enter valid numbers only.")
    }
  }
