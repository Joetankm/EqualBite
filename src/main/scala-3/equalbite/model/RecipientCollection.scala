// Description: Manages a collection of Recipient objects in the system.
// Provides CRUD-like operations, data persistence (CSV), and utility methods.

package equalbite.model

import scalafx.collections.ObservableBuffer
import com.github.tototoshi.csv.{CSVReader, CSVWriter} //referenced from https://github.com/tototoshi/scala-csv

import java.io.File

// Singleton object to manage all recipients in the application
object RecipientCollection extends Collection[Recipient]{
  // Utility method: checks if a recipient with a given username already exists
  def itemExists(username: String): Boolean = {
    if (list.map(_.username.value).contains(username)) {
      true
    } else false
  }

  // In-memory observable list of recipients (UI will auto-update when modified)
  override val list: ObservableBuffer[Recipient] = ObservableBuffer()

  // File path where recipient data is stored in CSV format
  override protected val data = new File("src/main/resources/equalbite/csv/recipientList.csv")

  // Adds a new recipient to the list and persists changes
  override def addItem(recipient: Recipient): Unit = {
    list += recipient
    // Save the updated records to the main application data
    saveData()
  }

  // Loads recipients from CSV into the observable list
  override def loadData(): Unit = {
    if data.exists() then
      val reader = CSVReader.open(data)
      for row <- reader.all() do
        val username = row(0)
        val name = row(1)
        val contact = row(2)
        val address = row(3)
        val householdSize = row(4).toInt
        val elderlyCount = row(5).toInt
        val childrenCount = row(6).toInt
        addItem(Recipient(username, name, contact, address, householdSize, elderlyCount, childrenCount))
      reader.close()
  }

  // Saves all recipients back to CSV file
  override def saveData(): Unit = {
    val writer = CSVWriter.open(data)
    for recipient <- list do
      writer.writeRow(List(
        recipient.username.value,
        recipient.name.value,
        recipient.contact.value,
        recipient.address.value,
        recipient.householdSize.value.toString,
        recipient.elderlyCount.value.toString,
        recipient.childrenCount.value.toString
      ))
    writer.close()
  }
}
