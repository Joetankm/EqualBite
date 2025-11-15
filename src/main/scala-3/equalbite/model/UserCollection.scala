// Description: Manages the collection of all users (Admin, AppUser, etc.).
// Handles persistence to/from CSV and provides lookup utilities.

package equalbite.model

import scalafx.collections.ObservableBuffer
import com.github.tototoshi.csv.{CSVReader, CSVWriter} //referenced from https://github.com/tototoshi/scala-csv

import java.io.File

// Singleton object representing the collection of users in the system.
// Supports adding, saving, loading, and searching users.
object UserCollection extends Collection[User]{
  // Observable list of users, reactive for UI binding
  override val list: ObservableBuffer[User] = ObservableBuffer()

  // File path where recipient data is stored in CSV format
  override val data = new File("src/main/resources/equalbite/csv/userList.csv")

  // Adds a new user to the collection and saves changes to file
  override def addItem(user: User): Unit = {
    list += user
    saveData()
  }

  // Loads users from CSV into memory
  override def loadData(): Unit = {
    if data.exists() then
      val reader = CSVReader.open(data)
      for row <- reader.all() do
        val username = row(0)
        val name = row(1)
        val contact = row(2)
        val address = row(3)
        val password = row(5)
        val role: Unit = row(4) match {
          case "Admin" => {
            addItem(Admin(username, name, contact, address, password))
          }
          case "User" => {
            addItem(AppUser(username, name, contact, address, password))
          }
          case _ => {
            addItem(AppUser(username, name, contact, address, password))
          } // Default to User if role is not recognized
        }
  }

  // Saves users from memory into CSV
  override def saveData(): Unit = {
    val writer = CSVWriter.open(data)
    for user <- list do
      writer.writeRow(List(
        user.username.value,
        user.name.value,
        user.contact.value,
        user.address.value,
        user.role.toString,
        user.password
      ))
    writer.close()
  }

  // Checks if a username already exists in the collection
  def containsUsername(username: String): Boolean = {
    if (list.map(_.username.get).contains(username)){
      true
    } else false
  }

  // Retrieves password for a given username (empty string if not found)
  def retrievePassword(username: String): String = {
    list
      .find(_.username.get == username) // look for the user
      .map(_.password) // if found, take their password
      .getOrElse("")

  }
}
