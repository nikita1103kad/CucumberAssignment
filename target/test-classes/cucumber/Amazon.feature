Feature: CucumberJava

Scenario: Open Amazon and verify title searched

Given I open "chrome" browser
When I open "Amazon" page
And I search "Nikon" in search bar
And I sort price from high to low
Then I click on second highest product 
And I verify title contains "Nikon D850"
Then I close browser