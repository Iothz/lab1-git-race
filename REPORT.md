# Lab 1 Git Race -- Project Report

This note uses the same disclosure fields as the group-project **AI use (10%)** slice. Lab 1 is still **limited**: assistive GenAI only — not a full or substantial generated solution. The project will later expect agents plus `AGENTS.md` and one skill; you do **not** need those here.

Do not invent a percentage of “AI vs original” lines. Empty or fake disclosure fails this lab.

## What I specified

Increments proposed:
- A simple log of the last 5 messages with timestamps, displayed on the welcome page and available via a REST API endpoint. Each message should be stored in memory (no database) and should include the time it was received. The REST API should return the messages in JSON format.
- A simple counter that increments each time a new message is received and displays the count on the welcome page.
- Different greeting messages based on the time of day (morning, afternoon, evening) displayed on the welcome page.

The increment will be considered successful when the following criteria are met:
1. Time-based greeting:
  - Automated: A unit test verifies that the greeting message changes based on the time of day.
  - Manual: Visually, visiting the welcome page at different times displays the correct greeting matching the system time.
2. Counter and message log:
  - Automated: A test verifies that the counter increments correctly with each post/request and appends the new message to the log capping at 5 messages.
  - Manual: Visually, posting new messages and checking the welcome page and the API endpoint shows the correct counter and message log.
3. Build verification: Running `./gradlew check` completes without errors, confirming that all tests pass and the application builds successfully.
## What I changed

[Files and behaviour. Not a restatement of the starter README.]

## Technical decisions

[Choices you own: API shape, tests, data, what you rejected.]

## How I verified

[Commands (`./gradlew check`), what failed first, what you fixed. You remain accountable for correctness.]

## AI disclosure

Fill **either** the list **or** the no-AI line.

- **Tools / skills:** …
- **Purpose:** …
- **Representative prompts:** … (or appendix)
- **Affected files/sections:** …
- **Validation steps:** …
- **Citations:** … (external snippets you adapted)
- **Human-reviewed:** what you checked, changed, or rejected

Or: **No AI assistance** was used for this lab.
