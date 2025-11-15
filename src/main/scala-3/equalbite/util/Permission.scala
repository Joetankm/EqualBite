// Description: Utility object for handling permission checks in the application.
// Currently provides a method to check whether the logged-in user is an admin.

package equalbite.util

import equalbite.MainApp
import equalbite.model.Role

// Provides methods for verifying user roles and permissions.
// Helps control access to features based on the current user's role.
object Permission {
  // Checks if the currently logged-in user has the Admin role
  def isAdmin: Boolean = {
    MainApp.currentUser match {
      case Some(user) if user.role == Role.Admin =>
        true
      case Some(user) =>
        false
      case None =>
        false
    }
  }
}
