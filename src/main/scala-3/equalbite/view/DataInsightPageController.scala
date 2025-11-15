package equalbite.view

import equalbite.model.{DistributionCollection, InventoryCollection}
import javafx.fxml.FXML
import javafx.scene.chart.{BarChart, PieChart, XYChart} //referenced from https://www.tutorialspoint.com/javafx/javafx_charts.htm
import javafx.collections.FXCollections

@FXML
class DataInsightPageController:

  // Predefined item categories used for analysis
  private val categories = Seq(
    "Perishable Items",
    "Supplement Items",
    "Packaged Items",
    "Other Items"
  )

  // Bar chart UI element for showing stock by category
  @FXML
  private var barChart: BarChart[String, Number] = _

  // Pie chart UI element for showing distributed items by category
  @FXML
  private var pieChart: PieChart = _

  // Called automatically after FXML is loaded
  @FXML
  def initialize(): Unit = {
    generateChartData()  // Populate bar chart with inventory stock data
    generatePieData()    // Populate pie chart with distribution data
  }

  // Generate pie chart data based on distributed quantities
  //referenced from https://www.tutorialspoint.com/javafx/javafx_charts.htm
  @FXML
  def generatePieData(): Unit = {
    val pieChartData = FXCollections.observableArrayList[PieChart.Data]()
    for (category <- categories) {
      // Calculate total distributed quantity for each category
      val amount = DistributionCollection.list
        .filter(_.category.value == category)
        .map(_.quantity.value)
        .sum
      pieChartData.add(new PieChart.Data(category, amount))
    }
    pieChart.setData(pieChartData) // Assign data to pie chart
  }

  // Generate bar chart data based on available stock quantities
  //referenced from https://www.tutorialspoint.com/javafx/javafx_charts.htm
  @FXML
  def generateChartData(): Unit = {
    val series = new XYChart.Series[String, Number]()
    series.setName("Category Distribution")

    for (category <- categories) {
      // Calculate total stock available for each category
      val amount = InventoryCollection.list
        .filter(_.category.value == category)
        .map(_.stock.value)
        .sum
      series.getData.add(new XYChart.Data(category, amount))
    }
    barChart.getData.add(series) // Add series to bar chart
  }
