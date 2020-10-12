Feature: Browser Demo
  Scenario: Load test.io web page with chrome
    Given I have a driver for "chrome"
    When I navigate to test.io
    Then The page is displayed

  Scenario: Load test.io web page with firefox
    Given I have a driver for "firefox"
    When I navigate to test.io
    Then The page is displayed

  Scenario: Load test.io web page with MS Edge
    Given I have a driver for "edge"
    When I navigate to test.io
    Then The page is displayed

  Scenario: Load test.io web page with Safari
    Given I have a driver for "safari"
    When I navigate to test.io
    Then The page is displayed