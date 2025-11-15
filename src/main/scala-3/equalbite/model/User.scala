// Description: Defines the abstract base class `User` for the EqualBite system.
// Encapsulates common attributes (username, name, contact, address, role, password).

package equalbite.model

import equalbite.model.Role.Role
import scalafx.beans.property.StringProperty

// Abstract base class representing a user in the system.
// Other specific roles like `Admin` and `Recipient` extend this class.
abstract class User (_username: String, _name: String, _contact: String, _address: String, _role: Role, val password: String):

  // Reactive properties for UI binding
  val username: StringProperty = StringProperty(_username)
  val name: StringProperty = StringProperty(_name)
  val contact: StringProperty = StringProperty(_contact)
  val address: StringProperty = StringProperty(_address)
  val role: Role = _role

