// Description: Utility object that provides printing functionality for TableView components.
// It handles scaling the table to fit the printable page area and uses JavaFX PrinterJob for printing.
// Includes success alerts after a successful print operation.

package equalbite.util

//printing feature referenced from https://www.javadoc.io/static/org.scalafx/scalafx_2.12/23.0.1-R34/scalafx/print/PrinterJob$.html
import scalafx.print.PrinterJob
import scalafx.scene.control.TableView
import scalafx.scene.transform.Scale


object PrintUtil {

  // Prints the given TableView, scaling it to fit within the printer's page layout.
  def printTable(table: TableView[_]): Unit = {
    val job = PrinterJob(javafx.print.PrinterJob.createPrinterJob())

    if (job != null && job.showPrintDialog(null)) {
      val pageLayout = job.jobSettings.getPageLayout //reference https://www.javadoc.io/static/org.scalafx/scalafx_2.12/23.0.1-R34/scalafx/print/PageLayout.html
      val printableWidth = pageLayout.getPrintableWidth
      val printableHeight = pageLayout.getPrintableHeight

      // Scale node to fit page
      val scaleX = printableWidth / table.boundsInParent.value.getWidth
      val scaleY = printableHeight / table.boundsInParent.value.getHeight
      val scale = Math.min(scaleX, scaleY) // keep aspect ratio

      val transform = new Scale(scale, scale) //reference https://javadoc.io/doc/org.scalafx/scalafx_2.13/latest/scalafx/scene/transform/Scale.html
      table.transforms.add(transform) // add scaling transform to the table so that the page fits to the printable area

      val success = job.printPage(table)
      if (success) {
        job.endJob()
        // Shows a print dialog to the user, and if successful, notifies the user with an information alert.
        AlertUtil.showInfo("Print Successful", "The table has been printed successfully.")
      }

      table.transforms.remove(transform) // remove scaling after printing
    }
  }
}