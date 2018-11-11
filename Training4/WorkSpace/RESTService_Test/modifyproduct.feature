Feature: Modify product
  Scenario: Modifing a product in database
    Given I have a product <Product>
    When Modify the product <Product>
Then Returns <Product>
