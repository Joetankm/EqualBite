package equalbite.view

import equalbite.model.{Recipient, RecipientCollection}
import equalbite.util.AlertUtil
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*
import scalafx.stage.Stage

@FXML
class AddRecipientController():
  var stage: Option[Stage] = None
  var okClicked = false // Tracks whether OK was clicked

  var mode = "add" // Mode: "edit" or "add"
  var editItem: Option[Recipient] = None // Item being edited, if any

  // FXML-injected text fields
  @FXML private var usernameText: javafx.scene.control.TextField = _
  @FXML private var nameText: javafx.scene.control.TextField = _
  @FXML private var contactText: javafx.scene.control.TextField = _
  @FXML private var addressText: javafx.scene.control.TextField = _
  @FXML private var householdSizeText: javafx.scene.control.TextField = _
  @FXML private var elderlyCountText: javafx.scene.control.TextField = _
  @FXML private var childrenCountText: javafx.scene.control.TextField = _

  // Closes the window when "Close" is clicked
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    okClicked = true
    stage.foreach(_.close())
  }

  // Populates text fields with values from the item to be edited
  @FXML
  def initializeEditItem(item: Recipient): Unit = {
    editItem = Some(item)
    usernameText.text = item.username.value
    nameText.text = item.name.value
    contactText.text = item.contact.value
    addressText.text = item.address.value
    householdSizeText.text = item.householdSize.value.toString
    elderlyCountText.text = item.elderlyCount.value.toString
    childrenCountText.text = item.childrenCount.value.toString
  }

  // Validates that household size, elderly count, and children count are logical
  def validateHouseholdCounts(householdSize: Int, elderlyCount: Int, childrenCount: Int): Boolean = {
    if (elderlyCount > householdSize || childrenCount > householdSize) {
      AlertUtil.showWarning("Invalid Input", "Elderly or children count cannot exceed household size.")
      return true
    }
    else if (elderlyCount + childrenCount > householdSize) {
      AlertUtil.showWarning("Invalid Input", "Combined elderly and children count cannot exceed household size.")
      return true
    }
    else if (householdSize <= 0 || elderlyCount < 0 || childrenCount < 0) {
      AlertUtil.showWarning("Invalid Input", "Household size must be > 0, counts cannot be negative.")
      return true
    }
    false
  }

  // Handles the OK button: validates input, then saves or updates a recipient
  @FXML
  def handleOK(action: ActionEvent): Unit = {
    try {
      // Ensure no field is left empty
      if (usernameText.text.value.isEmpty || nameText.text.value.isEmpty || contactText.text.value.isEmpty ||
        addressText.text.value.isEmpty || householdSizeText.text.value.isEmpty ||
        elderlyCountText.text.value.isEmpty || childrenCountText.text.value.isEmpty) {
        AlertUtil.showWarning("Incomplete Information", "Please fill in all fields before proceeding.")
        return
      }

      //Validate that contact is numbers
      if (!contactText.text.value.forall(_.isDigit)){
        AlertUtil.showWarning("Information Invalid", "Contact must be numbers")
        return
      }

      // Validate household-related counts
      if (validateHouseholdCounts(householdSizeText.text.value.toInt, elderlyCountText.text.value.toInt, childrenCountText.text.value.toInt)) {
        return
      }

      // Edit mode: update existing recipient
      if (mode == "edit") {
        editItem.foreach { recipient =>
          recipient.username.value = usernameText.text.value
          recipient.name.value = nameText.text.value
          recipient.contact.value = contactText.text.value
          recipient.address.value = addressText.text.value
          recipient.householdSize.value = householdSizeText.text.value.toInt
          recipient.elderlyCount.value = elderlyCountText.text.value.toInt
          recipient.childrenCount.value = childrenCountText.text.value.toInt
          RecipientCollection.saveData()
          stage.foreach(_.close())
        }
      }

      // Add mode: create new recipient
      if (mode == "add") {
        // Prevent duplicate usernames
        if (RecipientCollection.itemExists(usernameText.text.value)) {
          AlertUtil.showWarning("Username Already Exists", "Please enter a different username.")
          return
        }
        val recipient = Recipient(
          usernameText.text.value,
          nameText.text.value,
          contactText.text.value,
          addressText.text.value,
          householdSizeText.text.value.toInt,
          elderlyCountText.text.value.toInt,
          childrenCountText.text.value.toInt
        )
        RecipientCollection.addItem(recipient)
        RecipientCollection.saveData()
        stage.foreach(_.close())
      }

    } catch {
      case _: NumberFormatException =>
        AlertUtil.showWarning("Invalid Input", "Please enter valid numbers only.")
    }
  }
