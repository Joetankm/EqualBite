// Description: Represents a generic food item that does not fall into the predefined categories 
// (e.g., Packaged, Perishable, Supplement). Stores basic detail information.

package equalbite.model

import scalafx.beans.binding.{Bindings, StringBinding} //referenced from https://scalafx.org/docs/properties
import scalafx.beans.property.StringProperty

// Case class for "Other Items" category, inherits from InventoryItem
case class OtherFoodItem(_itemCode: String, _name: String, _category:String, _stock:Int, _detail: String) extends InventoryItem(_itemCode, _name, "Other Items", _stock) {
  // Specific detail about the item (e.g., description or notes)
  override val detail: StringProperty = StringProperty(_detail)
  // Formatted version of detail, used for display in UI
  override val formattedDetail: StringBinding = Bindings.createStringBinding( //referenced from https://scalafx.org/docs/properties
    () => s"Detail: ${detail.value}",
    detail
  )
}
