# Lab 1 Git Race -- Project Report

## What I Specified

Proposed increments:
- A simple log of the last 5 messages with timestamps, displayed on the welcome page and available via a REST API endpoint. Each message should be stored in memory (no database) and should include the time it was received. The REST API should return the messages in JSON format.
- A simple counter that increments each time a new message is received and displays the count on the welcome page.
- Different greeting messages based on the time of day (morning, afternoon, evening) displayed on the welcome page.

The increment is considered successful when the following criteria are met:
1. Time-based greeting:
  - Automated: A unit test verifies that the greeting message changes based on the time of day.
  - Manual: Visually, visiting the welcome page at different times displays the correct greeting matching the system time.
2. Counter and message log:
  - Automated: A test verifies that the counter increments correctly with each POST/request and appends the new message to the log, capped at 5 messages.
  - Manual: Visually, posting new messages and checking the welcome page and API endpoint show the correct counter and message log.
3. Build verification: Running `./gradlew check` completes without errors, confirming that all tests pass and the application builds successfully.

## What I changed

Created the following files:
- `src/main/kotlin/RequestCounterService.kt` 
  - Service to maintain a counter of requests
- `src/main/kotlin/MessageLog.kt`
  - Service to maintain a log of the last 5 messages with timestamps
- `src/main/kotlin/HealthRequestCounterFilter.kt`
  - Filter designed to intercept automatic health check requests and prevent them from incrementing the request counter

Edited the following files:
- `src/main/kotlin/HelloController.kt`
  - Edited to include the request counter and message log
  - Added logic to display different greeting messages based on the time of day
- `src/main/resources/templates/welcome.html`
  - Edited to display the request counter, message log, and time-based greeting message
- `src/main/resources/static/js/http-debug.js`
  - Edited the logic of `testWebBtn` to include a count parameter to filter automatic health check requests and prevent them from incrementing the request counter
  - Edited the logic of `testHealthBtn` to include a count parameter to filter automatic health check requests and prevent them from incrementing the request counter
  - Edited the logic of `testWebBtn`, `testApiBtn`, and `testHealthBtn` to refresh the page counter after every request
  - Edited the logic of `testWebBtn` and `testApiBtn` to refresh the visual message log after every request
- `src/test/kotlin/controller/HelloControllerUnitTests.kt`, `src/test/kotlin/controller/HelloControllerMVCTests.kt`, and `src/test/kotlin/controller/IntegrationTest.kt`
  - Added/adapted tests to verify the functionality of the request counter, message log, and time-based greeting messages

# Technical decisions

- The API was kept the same as in the starter project, with no additional endpoints.


- To test the message log and time-based greeting messages, unit tests were created to verify the functionality of the `HelloController` and its associated services. The request counter was tested through MVC tests to verify the full behaviour of the application.
Additionally, existing tests were adapted to include the new functionality and ensure that the application behaves as expected.


- The responses from the endpoints were changed to include the request counter and message log in the response body, allowing easier verification of the functionality through the API.
  - A separate endpoint for the request counter and message log could have been created, but I decided to keep it simple and maintain the same API shape as the starter project.

## How I verified

Once the new functionality was implemented, the following steps were taken to verify its correctness:
1. Ran `./gradlew check` to ensure that all tests pass and the application builds successfully.
2. Visually verified the functionality of the request counter, message log, and time-based greeting messages by visiting the welcome page and checking the displayed information.
3. Used the API endpoints to verify the functionality of the request counter and message log, ensuring that the correct information is returned in the response body.

As the newly implemented functionalities were simple and straightforward, no major issues were encountered during the verification process. The only **minor issues** were the **original outdated tests** and the **automatic messages** being sent to the application, which were incrementing the request counter and affecting the message log. This was resolved by implementing a filter to intercept these automatic health check requests and prevent them from affecting the request counter and message log.

## AI disclosure

- **Tools / skills:**
  - GitHub Copilot (extension for IntelliJ IDEA) for code completion, suggestions, explanations, and editing.
- **Purpose:** 
  - AI was used to explore different implementation options, fix errors, and improve the quality of the report.
- **Representative prompts:**
  - "Is there a better way to store 5 messages in Kotlin?"
  - "Why is it better to define the counter as a service?"
  - "Check the redaction of the report without changing the meaning"
- **Affected files/sections:**
  - All edited files were affected by AI to some extent, but the final implementation was reviewed by the author, and all suggestions were either accepted, modified, or rejected.
- **Validation steps:**
  - All suggestions were reviewed by the author and either accepted, modified, or rejected. The final implementation was tested and manually verified to ensure correctness.
- **Citations:** 
  - No external sources were used for this lab.
- **Human-reviewed:**
  - All code and the report were reviewed by the author to ensure correctness and clarity. If any code was unclear or not understandable, I would have asked for clarification or rejected the suggestion.
