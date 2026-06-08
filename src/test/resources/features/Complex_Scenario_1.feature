@ComplexWorkflows
Feature: Complex End-to-End Core Business Flows

  @ComplexScenario1
  Scenario: Employee Creation to Login Validation - Step 1 to Step 4
    # Step 1: Admin Login
    Given the user is navigated to OrangeHrm login page
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the Login button
    Then the Dashboard page should load successfully

#    #step 2:- adding user
#    And navigates to PIM and click on add employee
#    And user enters the employee name "Rav" and "rayudu"
#    And click on toggle button
#    And user fills username "Ravrayudu" and password "Ravireddy123"
#    And click on save button
#    Then profile details should be displayed

    #step 3:-logout of admin
    When the user clicks the profile icon dropdown menu
    And selects the Logout option
    Then the user should be redirected back to the login page successfully

    #step 4:- login as the user
    When the user enters username "Ravrayudu"
    And the user enters password "Ravireddy123"
    And the user clicks the Login button
    Then the Dashboard page should load successfully

    #This is a flaky scenario
#    #step :- apply leave from the user end
#    When the user navigates to Leave and clicks Apply options
#    And selects leave type as "CAN - Bereavement"
#    And picks start date "2026-23-06" and end date "2026-25-06"
#    And adds a leave comment "Personal reasons application"
#    And clicks the apply leave button
#    Then the leave request should be successfully created

    #step 5 :-logout of user
    When the user clicks the profile icon dropdown menu
    And selects the Logout option
    Then the user should be redirected back to the login page successfully

    #step 6:-login as admin again
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the Login button
    Then the Dashboard page should load successfully

    #step 7 :-assign a leave to Rav rayudu from admin account
    When the user navigates to Leave and clicks Assign Leave options
    And handles assignment form input with employee name "Rav rayudu", leave type "CAN - Personal", start date "2026-07-01", and end date "2026-07-03"
    And clicks the Assign button
    Then the assignment form should conclude with status validation result "Success"

    #step 8:-logout from admin
    When the user clicks the profile icon dropdown menu
    And selects the Logout option
    Then the user should be redirected back to the login page successfully

   #step 9 :-login as user
    When the user enters username "Ravrayudu"
    And the user enters password "Ravireddy123"
    And the user clicks the Login button
    Then the Dashboard page should load successfully

    #step 10 :- Navigate to My Leave and verify assigned record row details
    When the user navigates to Leave and clicks My Leave options
    And clicks on the leave details button for the record matching employee name "Rav rayudu"
    Then the detailed leave request overlay panel should load successfully


