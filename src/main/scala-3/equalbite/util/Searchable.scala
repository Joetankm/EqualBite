// Description: Trait that defines a contract for searchable functionality in the application.
// Classes mixing in this trait must implement the handleSearch method, which is triggered by a UI action.

package equalbite.util

import javafx.event.ActionEvent

// Trait for adding search functionality to controllers or components.
// Requires an implementation of handleSearch that responds to a search action event.
trait Searchable {
  def handleSearch(action: ActionEvent): Unit
}
