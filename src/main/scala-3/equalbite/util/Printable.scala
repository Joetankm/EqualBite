// Description: Trait defining a contract for printable components in the application.
// Any class that mixes in this trait must provide an implementation for printing records.

package equalbite.util

import javafx.event.ActionEvent

// A trait that enforces print functionality for UI components or data records.
// Classes implementing this trait must define how the print action is handled.
trait Printable {

  // Handles the logic for printing a record when triggered by a user action.
  def handlePrintRecord(action: ActionEvent): Unit
}
