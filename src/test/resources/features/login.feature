Feature: Login functionality

  Scenario: Successful login with valid credentials

    Given User is on the SauceDemo login page
    When User enters username "standard_user"
    And User enters password "secret_sauce"
    And User clicks on the Login button
    Then User should be successfully logged in