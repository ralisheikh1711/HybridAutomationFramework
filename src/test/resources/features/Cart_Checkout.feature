
Feature: Update cart and proceed to checkout

  @Smoke @Cart
  Scenario Outline: Search jeans, update quantity, apply coupon and checkout
    Given the user opens the ModernStore home page
    When the user searches for "jeans"
    And the user selects a jeans product
    And the user adds the product to the cart
    And the user opens the cart
    Then the cart page should be displayed
    And the product should be displayed in the cart
    When the user changes the product quantity to 3
    And the user updates the cart
    Then the product quantity should be 3
    When the user applies coupon "<couponCode>"
    Then the coupon should be applied successfully
    When the user proceeds to checkout
    Then the checkout page should be displayed

    Examples:
      | couponCode     |
      | YOUR_VALID_CODE |
