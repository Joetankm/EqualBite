// Description: Enumeration representing the possible user roles in the system.
// Used to distinguish access levels and functionality.

package equalbite.model

//referenced from https://www.scala-lang.org/api/current/scala/Enumeration.html
object Role extends Enumeration{
  type Role = Value
  val Admin, User, Recipient = Value
}
