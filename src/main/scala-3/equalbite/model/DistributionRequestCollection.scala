// Description: This file defines the DistributionRequestCollection object which manages distribution
// requests that have not yet been accepted. It separates pending requests from the main DistributionCollection.
// Requests are loaded from the same CSV file but filtered by `accepted = false`.
// It also updates the combinedList in DistributionCollection so that both accepted and pending
// distributions are tracked together.

package equalbite.model

import scalafx.collections.ObservableBuffer
import com.github.tototoshi.csv.CSVReader //referenced from https://github.com/tototoshi/scala-csv

import java.io.File

// Singleton object to manage pending distribution requests (not accepted yet)
object DistributionRequestCollection extends Collection[Distribution]{

  // Holds only requests (distributions with accepted = false)
  override val list: ObservableBuffer[Distribution] = ObservableBuffer()

  // CSV file storing all distribution records
  override protected val data = new File("src/main/resources/equalbite/csv/distributionList.csv")

  // Add a new distribution request, and also keep it in the global combined list
  override def addItem(distribution: Distribution): Unit = {
    list += distribution
    DistributionCollection.combinedList+=distribution
  }

  // Load only requests (accepted = false) from CSV
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
        val accepted = row(7).toBoolean
        if (!accepted) {
          // Checks whether record is a request
          val name = row(8)
          val contact = row(9)
          addItem(Distribution(itemCode, distributionCode, recipient, item, category, quantity, date, "False".toBoolean, name, contact))
        }
      }
      reader.close()
  }

  // Requests don’t need a separate save, because they are already saved in DistributionCollection
  override def saveData(): Unit = {
    // Left intentionally empty — saving handled by DistributionCollection
  }
}
