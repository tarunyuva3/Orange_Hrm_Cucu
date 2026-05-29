@AllTests
Feature: OrangeHRM Different Sceanrios
  @Regression
  #Scenario1-login validation
  Scenario: Verify login with valid credentials
    Given the user is navigated to OrangeHrm login page
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the Login button
    Then the Dashboard page should load successfully

   #Scenario1.1-login failure validation
   Scenario Outline: Verify login failure with invalid credentials
     Given the user is navigated to OrangeHrm login page
     When the user enters username "<username>"
     And the user enters password "<password>"
     And the user clicks the Login button
     Then an error message "<errorMessage>" should be displayed

     Examples:
       | username | password  | errorMessage        |
       | Invalid  | admin123  | Invalid credentials |
       | Admin    | wrongPass | Invalid credentials |

   #Scenario2-Add Employee validation
   Scenario: Verify adding a new employee successfully
     Given the user is navigated to OrangeHrm login page
     When the user enters username "Admin"
     And the user enters password "admin123"
     And the user clicks the Login button
     Then the Dashboard page should load successfully
     And navigates to PIM and click on add employee
     And user enters the employee name "Rav" and "rayudu"
     And click on toggle button
     And user fills username "Ravrayudu" and password "Ravireddy123"
     And click on save button
     Then profile details should be displayed

   # Scenario3 - Employee Search validation: covers autocomplete, search filter and data table
   Scenario: Verify employee search using filters
     Given the user is navigated to OrangeHrm login page
     When login as admin user
     And the user searches employee "Rav rayudu" from Employee List
     Then matching employee rows for "Rav rayudu" should be displayed

   # Scenario4 - Edit Employee validation: covers tabs, radio buttons and dropdowns
   Scenario: Verify editing employee personal details
     Given the user is navigated to OrangeHrm login page
     When login as admin user
     And the user opens employee "Rav rayudu" and updates gender "Male" nationality "Indian" and marital status "Single"
     Then employee nationality should persist as "Indian"

   # Scenario5 - Deleting an employee id
   Scenario: Verify deleting an employee using checkbox and confirmation modal
     Given login as admin user
     And the user searches employee "Rav rayudu" from Employee List
     When the user selects the checkbox for employee "Rav rayudu"
     And clicks the delete selected button
     And confirms the deletion in the modal popup
     Then a success message notification should be displayed
     And the employee record "Rav rayudu" should no longer exist in the table

 #   #Scenario6 - Applying Leave --> this is a wrong scenario
 #  Scenario: Verify submitting a leave application successfully
 #    Given login as admin user
 #    When the user navigates to Leave and clicks Apply options
 #    And selects leave type as "CAN - Personal"
 #    And picks start date "2026-06-10" and end date "2026-06-12"
 #    And adds a leave comment "Personal reasons application"
 #    And clicks the apply leave button
 #    Then the leave request should be successfully created

   @Regression
 # Scenario7 - Assigning a Leave
   Scenario Outline: Verify assigning leave behaviors for both valid data and missing required fields
     Given login as admin user
     When the user navigates to Leave and clicks Assign Leave options
     And handles assignment form input with employee name "<fullEmployee>", leave type "<leaveType>", start date "<startDate>", and end date "<endDate>"
     And clicks the Assign button
     Then the assignment form should conclude with status validation result "<expectedValidationResult>"

     Examples:
       | fullEmployee | leaveType      | startDate  | endDate    | expectedValidationResult |
       | Rav rayudu   | CAN - Personal | 2026-07-01 | 2026-07-03 | Success                  |
 #      |              | -- Select --   |            |            | Required Validation      |

   @Regression
   #Scenario8 - Punched In and Out Time
   Scenario: Verify time registration state alterations from Punched In to Punched Out on Dashboard
     Given login as admin user
     When the user accesses Punch module from the Dashboard clock widget shortcut
     And enters Punch In time configuration value "06:00 AM" and clicks login
     And enters Punch Out time configuration value "07:00 AM" and clicks logout
     And returns back onto the Dashboard module view panel
     Then the dashboard time at work status badge must display as "Punched Out"

  #Scenario9 - Admin Module – Add User
  @Regression
  Scenario: Admin Module - Add user role configuration and verification
    Given login as admin user
    When the user navigates to Admin User Management module
    And clicks the Add User button
    And fills out user details with role "Admin", employee name "Rav rayudu", status "Enabled", username "RaviRayudu", and password "Ravireddy123"
    And clicks the save configuration button
    Then the user searches for username "RaviRayudu" in the system users table
    And verifies that the user appears successfully in the data records row


  #Scenario15 - Profile Menu - Dropdown + Logout
  @Regression
  Scenario: Profile Menu – Dropdown and Logout validation
    Given login as admin user
    When the user clicks the profile icon dropdown menu
    And selects the Logout option
    Then the user should be redirected back to the login page successfully

