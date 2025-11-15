package equalbite.view

import equalbite.MainApp
import equalbite.model.{Admin, AppUser, UserCollection}
import equalbite.util.{AlertUtil, WindowManager}
import javafx.event.ActionEvent
import javafx.fxml.FXML

// Controller for the Login Page
// Handles user and admin login, pre-fill login buttons, sign-up, forget password, and navigation back
@FXML
class LoginPageController:

  // FXML input fields for username and password
  @FXML private var usernameField: javafx.scene.control.TextField = _
  @FXML private var passwordField: javafx.scene.control.PasswordField = _

  // Pre-fills a test user login (for convenience during testing/demo)
  @FXML
  def handleUserLogin(action: ActionEvent): Unit = {
    usernameField.setText("User 243")
    passwordField.setText("hiddenpassword123")
  }

  // Pre-fills the admin login (for convenience during testing/demo)
  @FXML
  def handleAdminLogin(action: ActionEvent): Unit = {
    usernameField.setText("Admin")
    passwordField.setText("Adminhiddenpassword123")
  }

  // Opens the sign-up window for creating new users
  @FXML
  def handleSignUp(action: ActionEvent): Unit = {
    WindowManager.showSignUpWindow()
  }

  // Handles forget password action (currently not implemented)
  @FXML
  def handleForgetPassword(action: ActionEvent): Unit = {
    AlertUtil.showInfo("Forgot Password", "Unfortunately the forget password feature is not available yet.")
  }

  // Handles the actual login process for both users and admin
  @FXML
  def handleLogin(action: ActionEvent): Unit = {
    val username = usernameField.getText
    val password = passwordField.getText

    if (username.isEmpty || password.isEmpty) {
      AlertUtil.showWarning("Login Error", "Username and password cannot be empty.")
    }
    else if (username == "Admin" && password == UserCollection.retrievePassword("Admin")) {
      // Admin login
      AlertUtil.showInfo("Login Successful", "Welcome Admin!")
      MainApp.currentUser = Some(Admin("Admin", "Admin User", "0123456789", "123 Admin Street", "Adminhiddenpassword123"))
      if (!UserCollection.containsUsername(username)) {
        UserCollection.addItem(MainApp.currentUser.get)
        UserCollection.saveData()
      }
      WindowManager.showMainRoot()
      WindowManager.showDashboardWindow()
    }
    else if (password == UserCollection.retrievePassword(username)) {
      // Regular user login
      AlertUtil.showInfo("Login Successful", s"Welcome $username!")
      val user = UserCollection.list.find(u => u.username.get == username && u.password == password).get
      MainApp.currentUser = Some(AppUser(username, user.name.get, user.contact.get, user.address.get, user.password))
      if (!UserCollection.containsUsername(username)) {
        UserCollection.addItem(MainApp.currentUser.get)
        UserCollection.saveData()
      }
      WindowManager.showMainRoot()
      WindowManager.showDashboardWindow()
    }
    else {
      // Invalid login
      AlertUtil.showWarning("Login Failed", "Invalid username or password.")
    }
  }

  // Handles back navigation to the start page
  @FXML
  def handleBack(action: ActionEvent): Unit = {
    WindowManager.showStartWindow()
  }
