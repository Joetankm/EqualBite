package equalbite.model

// A case class representing an App user in the system.
// It extends the base User class but enforces that the role is always Role.User.
case class AppUser (_username: String, _name: String, _contact: String, _address: String, _password:String) extends User(_username, _name, _contact, _address, _role = Role.User, _password)