# EqualBite – Food Inventory & Distribution Management System
Desktop app built with Scala, JavaFX, and SceneBuilder for managing food inventory and distribution. Features role-based access, request approvals, and CSV data storage. Implements MVC architecture, OOP inheritance for item types, and a centralized WindowManager for smooth UI navigation.

## 🚀 Features
### 📦 Inventory Management
• Add, edit, and track food items
• Supports multiple item types (Packaged, Perishable, Supplement, Other)
• OOP-based inheritance structure for item models

### 🎯 Distribution & Requests
• Submit, approve, or reject food distribution requests
• Auto-generated distribution codes
• CSV-based record tracking

### 👥 User Roles
• Admin – full access, approvals, data management
• User – submit requests, view allowed pages
• Secure login with role-based UI navigation

### 🖥️ UI & Architecture
• Built using JavaFX with SceneBuilder-generated FXML
• Centralized WindowManager for clean window navigation
• Follows MVC architecture for maintainability
• Uses ObservableBuffer for UI-reactive data updates

## 🛠️ Technologies Used
• Scala 3
• JavaFX / SceneBuilder
• CSVReader & CSVWriter (tototoshi)
• OOP + MVC architecture

## 📦 How to Run
1. Install IntelliJ IDEA with Scala plugin
2. Import project as an SBT project
3. Ensure CSV files are located inside: src/main/resources/
4. Run the app using: MainApp.scala (Run configuration)
