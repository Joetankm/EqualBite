package equalbite.view

import javafx.event.ActionEvent
import javafx.fxml.FXML
import scalafx.stage.Stage

@FXML
class AboutController():
  var stage: Option[Stage] = None    // reference to the About window's stage, set by WindowManager
  var okClicked = false              // flag to indicate whether the user confirmed/closed the window

  // function to handle closing the window (triggered by Close button)
  @FXML
  private def handleClose(action: ActionEvent): Unit = {
    okClicked = true // mark as closed
    stage.foreach(x => x.close()) // close the About window if stage is defined
  }