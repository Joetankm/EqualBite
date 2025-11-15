// Description: This file defines the DistributionCollection object which manages a collection of Distribution records.
// It provides methods to add new distribution records, load them from a CSV file, save them back to the file,
// and generate the next available distribution code. It uses ObservableBuffer to allow dynamic updates and
// smooth integration with the JavaFX UI.

package equalbite.model

import scalafx.collections.ObservableBuffer
import com.github.tototoshi.csv.{CSVReader, CSVWriter} //referenced from https://github.com/tototoshi/scala-csv
import scalafx.Includes.*

import java.io.File

// Singleton object to manage all distribution records in the application
object DistributionCollection extends Collection[Distribution] {
  // Main list of distribution records used by the application
  override val list: ObservableBuffer[Distribution] = ObservableBuffer()

  // Additional list to hold both active and historical distribution records
  val combinedList: ObservableBuffer[Distribution] = ObservableBuffer()

  // CSV file where distribution data is stored
  override protected val data = new File("src/main/resources/equalbite/csv/distributionList.csv")

  // Add a new distribution record to both the active and combined lists
  override def addItem(rec: Distribution): Unit = {
    list += rec
    combinedList += rec
  }

  // Load distribution records from CSV into memory
  override def loadData(): Unit = {
    if data.exists() then
      val reader = CSVReader.open(data)
      for row <- reader.all() do {
        val itemCode = row(0)
        val distributionCode = row(1).toInt
        val recipient = row(2)
        val item = row(3)
        val category = row(4)
        val quantity = row(5).toInt
        val date = row(6)
        val accepted = row(7).toBoolean // Assuming the 6th column is for accepted status
        if (accepted) {
          // Checks whether the record is an accepted distribution, if accepted, add to distribution collection
          addItem(Distribution(itemCode,distributionCode, recipient, item, category, quantity, date))
        }
      }
      reader.close()
  }

  // Save all distribution records (both active and historical) back to CSV
  override def saveData(): Unit = {
    val writer = CSVWriter.open(data)
    for distribution <- combinedList do
      writer.writeRow(List(
        distribution.itemCode.get,
        distribution.distributionCode.get.toString,
        distribution.recipient.value,
        distribution.item.value,
        distribution.category.value,
        distribution.quantity.value.get.toString,
        distribution.date.value,
        distribution.accepted.value.toString,
        distribution.name.value,
        distribution.contact.value
      ))
    writer.close()
  }

  // Method to get the next available distribution code
  def generateNextCode(): Int = {
    // If there are no records, return 1 as the first distribution code
    // Otherwise, return the maximum distribution code + 1
    if (list.isEmpty) 1
    else {
      list.map(_.distributionCode.value).max + 1
    }
  }
}
