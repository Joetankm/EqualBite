// Description: This file defines the Distribution case class which represents a distribution record in the EqualBite application.
// It stores details about the distributed item, recipient information, quantity, date, and acceptance status.
// The class uses ScalaFX Property types for all fields to enable data binding, making it easier to update and 
// reflect changes in the JavaFX UI dynamically.

package equalbite.model

import scalafx.beans.property.{ObjectProperty, StringProperty}

// This case class represents a distribution record in the EqualBite application.
case class Distribution(
  _itemCode: String,
  _distributionCode: Int,
  _recipient: String,
  _item: String,
  _category: String,
  _quantity: Int,
  _date: String,
  _accepted: Boolean = true,
  _name: String = "",
  _contact: String = ""
) 
{
  // Properties for the distribution record
  // Using ObjectProperty for numeric values and StringProperty for text values
  // This allows for easy binding and updates in the UI.
  val itemCode: StringProperty = StringProperty(_itemCode)
  val distributionCode: ObjectProperty[Int] = ObjectProperty[Int](_distributionCode)
  val recipient: StringProperty = StringProperty(_recipient)
  val item: StringProperty = StringProperty(_item)
  val category: StringProperty = StringProperty(_category)
  val quantity:  ObjectProperty[Int] = ObjectProperty[Int](_quantity)
  val date: StringProperty = StringProperty(_date)
  var accepted: ObjectProperty[Boolean] = ObjectProperty[Boolean](_accepted)
  val name: StringProperty = StringProperty(_name)
  val contact: StringProperty = StringProperty(_contact)
}

