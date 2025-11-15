// Description: Represents a packaged food item in the inventory.
// Stores pack size information and provides a formatted string for UI display.

package equalbite.model

import scalafx.beans.binding.{Bindings, StringBinding} //referenced from https://scalafx.org/docs/properties
import scalafx.beans.property.StringProperty

// Case class representing a packaged food item in the inventory.
case class PackagedFoodItem(_itemCode: String, _name: String, _category:String, _stock:Int, _packSize: String) extends InventoryItem(_itemCode, _name, "Packaged Items", _stock) {
  override val detail: StringProperty = StringProperty(_packSize)   // reactive property for pack size
  // binding: auto-updates UI with "Pack Size: X"
  override val formattedDetail: StringBinding = Bindings.createStringBinding( //referenced from https://scalafx.org/docs/properties
    () => s"Pack Size: ${detail.value}",
    detail
  )
}
