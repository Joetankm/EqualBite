// Description: Utility object for displaying alert dialogs in the application. 
// Provides methods for showing warning and information alerts with customizable headers and content.

package equalbite.util

//Alerts referenced from https://scalafx.org/docs/dialogs_and_alerts
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object AlertUtil {

  def showWarning(header: String, content: String): Unit = {
    new Alert(AlertType.Warning) {
      title = "Warning" // Title of the dialog window
      headerText = header // Header section of the alert dialog
      contentText = content // Main message content
    }.showAndWait() // Show the alert and wait until the user dismisses it
  }

  def showInfo(header: String, content: String): Unit = {
    new Alert(AlertType.Information) {
      title = "Info" // Title of the dialog window
      headerText = header // Header section of the alert dialog
      contentText = content // Main message content
    }.showAndWait() // Show the alert and wait until the user dismisses it
  }
}
