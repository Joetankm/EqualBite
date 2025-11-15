// Description: Represents a perishable food item in the inventory.
// Stores storage requirements (e.g., "Refrigerated") and provides a formatted string for UI display.

package equalbite.model

import scalafx.beans.binding.{Bindings, StringBinding} //referenced from https://scalafx.org/docs/properties
import scalafx.beans.property.StringProperty

// Case class representing a perishable food item in the inventory.
case class PerishableFoodItem(_itemCode: String, _name: String, _category:String, _stock:Int, _storage: String) extends InventoryItem(_itemCode, _name, "Perishable Items", _stock) {
  override val detail: StringProperty = StringProperty(_storage)
  // Binding for UI display: e.g., "Storage: Refrigerated"
  override val formattedDetail: StringBinding = Bindings.createStringBinding( //referenced from https://scalafx.org/docs/properties
    () => s"Storage: ${detail.value}",
    detail
  )
}
