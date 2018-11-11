Feature: Show products
  Scenario: Showing all products in database
    Given I have products <List<Product>>
    When Show the products <List<Product>>
Then Returns <List<Product>>
