// Description: This file defines the InventoryCollection object which manages a collection 
// of InventoryItem objects. It supports adding new items, checking for duplicates, 
// loading from a CSV file, and saving back to the CSV file. Different categories 
// of items (Packaged, Perishable, Supplement, Other) are automatically mapped 
// to their respective classes when loading.

package equalbite.model

import scalafx.collections.ObservableBuffer
import com.github.tototoshi.csv.{CSVReader, CSVWriter} //referenced from https://github.com/tototoshi/scala-csv

import java.io.File

// Singleton object that manages inventory items
object InventoryCollection extends Collection[InventoryItem] {

  // Check if an item already exists in the inventory by itemCode
  def itemExists(itemCode: String): Boolean = {
    if (list.map(_.itemCode.value).contains(itemCode)){
      true
    } else false
  }

  // Holds all inventory items in memory (dynamic, updates UI automatically)
  override val list: ObservableBuffer[InventoryItem] = ObservableBuffer()

  // File path where inventory data is stored
  override protected val data = new File("src/main/resources/equalbite/csv/inventoryList.csv")

  // Add a new item to the collection and immediately save the data
  override def addItem(item: InventoryItem): Unit = {
    list += item
    // Save the updated records to the main application data
    saveData()
  }

  // Load items from CSV file into memory
  override def loadData(): Unit = {
    if data.exists() then
      val reader = CSVReader.open(data)
      for row <- reader.all() do
        val itemCode = row(0)
        val name = row(1)
        val category = row(2)
        val stock = row(3).toInt
        val detail = row(4)

        // Create the appropriate subclass of InventoryItem depending on category
        category match {
          case "Packaged Items" =>
            InventoryCollection.addItem(new PackagedFoodItem(itemCode, name, category, stock, detail))
          case "Perishable Items" =>
            InventoryCollection.addItem(new PerishableFoodItem(itemCode, name, category, stock, detail))
          case "Supplement Items" =>
            InventoryCollection.addItem(new SupplementItem(itemCode, name, category, stock, detail))
          case _ =>
            InventoryCollection.addItem(new OtherFoodItem(itemCode, name, category, stock, detail))
        }
      reader.close()
  }

  // Save all items in memory back to the CSV file
  override def saveData(): Unit = {
    val writer = CSVWriter.open(data)
    for item <- list do
      writer.writeRow(List(item.itemCode.value, item.name.value, item.category.value, item.stock.value.toString, item.detail.get))
    writer.close()
  }
}
