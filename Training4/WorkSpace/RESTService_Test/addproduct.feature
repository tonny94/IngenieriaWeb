Feature: Add product
  Scenario: Adding a new product in database
    Given I have a product <Product>
    When Save the product <Product>
Then Returns <Product>
