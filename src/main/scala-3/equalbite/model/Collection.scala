// Description: This file defines the abstract Collection class which serves as a base for managing collections
// of data entities (e.g., Inventory, Recipients, Users) in the EqualBite application.
// It enforces a consistent structure by requiring implementations to maintain an observable list of items,
// specify a data source file, and provide methods for adding, loading, and saving data.
// This ensures uniform data handling across different modules of the application.

package equalbite.model

import scalafx.collections.ObservableBuffer
import java.io.File

// Abstract base class for managing collections of data entities (e.g., Inventory, Recipients, Users).
// Provides a consistent structure for storing, loading, and saving data across the application.
abstract class Collection[T] {
  // Observable list of items of type T (used so that UI can automatically update when data changes)
  val list: ObservableBuffer[T]

  // The data source file linked to this collection
  protected val data: File
  
  def addItem(item: T): Unit // Adds a new item to the collection
  def loadData(): Unit // Loads data from the file into the list
  def saveData(): Unit // Saves the current list of items back into the file
}

