package equalbite.view

import equalbite.model.{AppUser, UserCollection}
import equalbite.util.{AlertUtil, WindowManager}
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class SignUpPageController:

  @FXML
  private var usernameField: javafx.scene.control.TextField = _

  @FXML
  private var nameField: javafx.scene.control.TextField = _

  @FXML
  private var contactField: javafx.scene.control.TextField = _

  @FXML
  private var addressField: javafx.scene.control.TextField = _

  @FXML
  private var passwordField: javafx.scene.control.PasswordField = _
  
  // Sign-up Action
  @FXML
  def handleSignUp(action: ActionEvent): Unit = {
    if (usernameField.getText.isEmpty || nameField.getText.isEmpty ||
      contactField.getText.isEmpty || addressField.getText.isEmpty ||
      passwordField.getText.isEmpty) {
      AlertUtil.showWarning("Incomplete Information", "Please fill in all fields before proceeding.")
      return
    }

    //Validate that contact is numbers
    if (!contactField.getText.forall(_.isDigit)) {
      AlertUtil.showWarning("Information Invalid", "Contact must be numbers")
      return
    }

    if (!UserCollection.containsUsername(usernameField.getText)) {
      UserCollection.addItem(AppUser(
        usernameField.getText,
        nameField.getText,
        contactField.getText,
        addressField.getText,
        passwordField.getText
      ))
      AlertUtil.showInfo("Account created!", "Your account has been successfully created!")
    } else {
      AlertUtil.showWarning("Username Taken", "Please choose a different username.")
    }
  }

  // Navigate to Login Window
  @FXML
  def handleLogIn(action: ActionEvent): Unit = {
    WindowManager.showLoginWindow()
  }
