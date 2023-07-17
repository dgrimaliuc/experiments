Feature: Password Test


  Scenario: List size validation
    Given I have a list
      | Apple    |
      | Banana   |
      | Cucumber |
    Then Assert that "'Banana' in {0}"

  Scenario: String contains substring
    Given I have a string "OpenAI"
    When Execute Python Script "'AI' in '{0}'"
    Then Assert that "{1} == True"

  Scenario: String length
    Given I have a string "OpenAI"
    When Execute Python Script "len('{0}')"
    Then Assert that "{1} == 6"

  Scenario: Concatenating Scenario
    Given I have two strings "Hello" and "World"
    When Execute Python Script "'{0[0]}' + '{0[1]}'"
    Then Assert that "'{1}' == 'HelloWorld'"

  Scenario: Adding two numbers
    Given I have two numbers "4" and "5"
    When Execute Python Script "{0[0]} + {0[1]}"
    Then Assert that "{1} == 9"

  Scenario: Adding two numbers
    Given I have two numbers "10" and "4"
    * Execute Python Script "{0[0]} - {0[1]}"
    * Assert that "{1} == 6"


  Scenario Outline: Sum verification
    Given I have two numbers "<num1>" and "<num2>"
    When Execute Python Script "{0[0]} - {0[1]}"
    Then Assert that "{1} == <result>"

    Examples:
      | num1 | num2 | result |
      | 10   | 4    | 6      |
