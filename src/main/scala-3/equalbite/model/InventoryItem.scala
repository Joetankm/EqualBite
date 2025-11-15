// Description: Abstract base class for inventory items. 
// Provides common properties such as itemCode, name, category, and stock.
// Subclasses must define `detail` (specific attribute) and its formatted display version.

package equalbite.model

import scalafx.beans.binding.StringBinding
import scalafx.beans.property.{ObjectProperty, StringProperty}

// Abstract parent class for all types of inventory items
abstract class InventoryItem(_itemCode: String, _name: String, _category:String, _stock:Int):
  val itemCode: StringProperty = StringProperty(_itemCode) // Unique code for identifying the item
  val name: StringProperty = StringProperty(_name) // Human-readable name of the item
  val category: StringProperty = StringProperty(_category) // Category: e.g., Packaged Items, Perishable Items, etc.
  val stock: ObjectProperty[Int] = ObjectProperty[Int](_stock) // Current stock quantity
  val detail: StringProperty // Each subclass defines its own detail (e.g., expiry date, weight, etc.)
  val formattedDetail: StringBinding // Formatted version of detail, can be used for display in tables or UI