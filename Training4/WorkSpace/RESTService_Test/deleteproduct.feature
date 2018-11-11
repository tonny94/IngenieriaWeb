Feature: Delete product
  Scenario: Deleting a product in database
    Given I have a product <Product>
    When Delete the product <Product>
Then Returns <Null>
