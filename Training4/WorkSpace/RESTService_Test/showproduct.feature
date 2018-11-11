Feature: Show product
  Scenario: Showing a product in database
    Given I have a product <Product>
    When Show the product <Product>
Then Returns <Product>
