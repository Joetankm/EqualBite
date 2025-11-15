// Description: Represents a supplement item in the inventory (e.g., vitamins, minerals).
// Stores the nutrient information for display and binding in the UI.

package equalbite.model

import scalafx.beans.binding.{Bindings, StringBinding} //referenced from https://scalafx.org/docs/properties
import scalafx.beans.property.StringProperty

// Case class representing a supplement items in the inventory.
case class SupplementItem(_itemCode: String, _name: String, _category:String, _stock:Int, _nutrient: String) extends InventoryItem(_itemCode, _name, "Supplement Items", _stock) {
  override val detail: StringProperty = StringProperty(_nutrient)
  // Binding: auto-updates the UI with "Nutrient: X"
  override val formattedDetail: StringBinding = Bindings.createStringBinding( //referenced from https://scalafx.org/docs/properties
    () => s"Nutrient: ${detail.value}",
    detail
  )
}
