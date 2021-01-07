Feature: CucumberJava

Scenario: Open Amazon and verify the title searched

Given I open "chrome" browser
When I open "Amazon" page
And I search "Nikon" in search bar
And I sort price from high to low
And I click on second highest product 
Then I verify title contains "Nikon D850"
And I close browser