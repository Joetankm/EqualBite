package equalbite.model

// A case class representing an Admin user in the system.
// It extends the base User class but enforces that the role is always Role.Admin.
case class Admin(_username: String, _name: String, _contact: String, _address: String, _password:String) extends User(_username, _name, _contact, _address, _role = Role.Admin, _password)
