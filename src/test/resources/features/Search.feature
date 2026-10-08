Feature: Search product and add it to cart

  As a customer
  I want to search for a product
  So that I can add the required product to my cart

  Background:
    Given I am on the Modern Store home page

  Scenario: Search for jeans and add a product to cart
    When I search for "jeans"
    Then I should see jeans related products
    When I select a jeans product
    And I click on the Add to Cart button
    Then the product should be added to the cart
