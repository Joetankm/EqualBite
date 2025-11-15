// Description: Represents a Recipient user in the system.
// Inherits from User but fixes the role to Role.Recipient.
// Stores additional demographic information such as household size, elderly count, and children count.

package equalbite.model

import scalafx.beans.property.ObjectProperty

// A case class representing an Recipient user in the system.
// It extends the base User class but enforces that the role is always Role.Recipient.
case class Recipient (_username: String, _name: String, _contact: String, _address: String, _householdSize: Int, _elderlyCount: Int, _childrenCount: Int, override val password:String = "") extends User(_username, _name, _contact, _address, _role = Role.Recipient, password):
  
  // Reactive properties for UI binding
  val householdSize: ObjectProperty[Int] = ObjectProperty(_householdSize)
  val elderlyCount: ObjectProperty[Int] = ObjectProperty(_elderlyCount)
  val childrenCount: ObjectProperty[Int] = ObjectProperty(_childrenCount)
