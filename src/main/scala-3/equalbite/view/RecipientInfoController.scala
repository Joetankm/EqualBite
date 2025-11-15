package equalbite.view

import equalbite.model.Recipient
import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.Includes.*
import scalafx.stage.Stage

@FXML
class RecipientInfoController():
  var stage: Option[Stage] = None
  var okClicked = false // Indicates if OK/close was clicked

  // Labels for recipient details
  @FXML private var usernameLabel: javafx.scene.control.Label = _
  @FXML private var nameLabel: javafx.scene.control.Label = _
  @FXML private var contactLabel: javafx.scene.control.Label = _
  @FXML private var addressLabel: javafx.scene.control.Label = _
  @FXML private var householdSizeLabel: javafx.scene.control.Label = _
  @FXML private var elderlyCountLabel: javafx.scene.control.Label = _
  @FXML private var childrenCountLabel: javafx.scene.control.Label = _

  // Show recipient details
  @FXML
  def showItem(item: Recipient): Unit = {
    if (item != null) {
      usernameLabel.text = item.username.value
      nameLabel.text = item.name.value
      contactLabel.text = item.contact.value
      addressLabel.text = item.address.value
      householdSizeLabel.text = item.householdSize.value.toString
      elderlyCountLabel.text = item.elderlyCount.value.toString
      childrenCountLabel.text = item.childrenCount.value.toString
    } else {
      println("No item provided for RecipientInfoController")
    }
  }

  // Handle closing the dialog
  @FXML
  def handleClose(action: ActionEvent): Unit = {
    okClicked = true
    stage.foreach(_.close())
  }