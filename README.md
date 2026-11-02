# Gym Plan Generator

A Java web app that creates a personal gym training plan in seconds. You fill in a short form (goal, experience, equipment, days per week, …), Google Gemini proposes the plan, and my own Java code checks the answer and calculates calories and macros.

> **The AI provides the ideas. The code provides structure, validation and correct numbers.**

Final project of the Passerelle program at BFH (Berner Fachhochschule), October 2026.

---

## Features

- **Form with 14 inputs:** age, sex, height, weight, optional body fat, experience, goal, equipment, days per week, minutes per session, preferred split, desired exercises, focus muscles and plan language.
- **Server-side validation:** errors appear next to the fields and your input is kept. Extra rules apply too: push/pull/legs needs at least 3 days and upper/lower at least 2.
- **Structured AI output:** Gemini must answer in a fixed JSON schema.
- **Plan check:** `PlanChecker` verifies the number of days, sets (1–10), RIR (0–5), muscle groups and text lengths. If a plan is invalid, the app asks Gemini again, up to 3 attempts.
- **Nutrition targets calculated in Java, not by the AI:**
  - Mifflin-St Jeor, or Katch-McArdle when body fat is given.
  - Activity factor and goal adjustment.
  - Safety limits: a maximum deficit and a minimum calorie floor.
  - Protein, fat and carbs, plus the expected weekly weight change.
- **Weekly sets per muscle group:** values under 10 or over 20 are highlighted.
- **Goals:** Cut, Aggressive Cut, Lean Bulk, Bulk, Strength, General Fitness.
- **Result page in English, German or Ukrainian.**
- **Copy, print and save:** copy the plan as text, print it or save it as PDF.
- **Clear error messages** instead of crashes, for example when the API quota is reached (HTTP 429).

## Tech stack

Java · Spring Boot 4 (Web MVC, Thymeleaf, Bean Validation) · Maven · HTML/CSS/JavaScript · Google Gemini API (`gemini-3.5-flash-lite`) · Jackson

## How it works

```
Form ──► PlanController ──► PromptBuilder ──► GeminiClient ──► Gemini API
                                                    │
Result page ◄── NutritionCalculator ◄── PlanChecker ◄┘  (invalid → retry, max. 3)
               VolumeCalculator
```

1. `PlanController` receives the form and validates `PlanRequest`.
2. `PromptBuilder` turns the inputs into a prompt. Free-text fields are cleaned and limited to protect against prompt injection.
3. `GeminiClient` calls the Gemini API with a JSON schema.
4. `PlanService` parses the JSON into `Plan` / `Day` / `Exercise`, and `PlanChecker` validates it.
5. `NutritionCalculator` and `VolumeCalculator` add the numbers, and Thymeleaf renders the result.

## Project structure

```
src/main/java/.../gymplan
├── PlanController, HomeController, LocaleConfig      # web layer
├── PlanRequest                                       # form + validation rules
├── PlanService, PlanChecker                          # generation + checks
├── NutritionCalculator, NutritionTargets             # calories & macros
├── VolumeCalculator                                  # weekly sets per muscle
├── GeminiClient, PromptBuilder, GeminiException      # AI layer
└── Plan, Day, Exercise                               # model classes
src/main/resources
├── templates/plan-form.html, plan-result.html
├── static/style.css, app.js
├── messages*.properties                              # en / de / uk
└── application.properties
```

## Getting started

### Requirements
- JDK 25 (as set in `pom.xml`)
- A free Gemini API key from [Google AI Studio](https://aistudio.google.com/)

### 1. Clone
```bash
git clone https://github.com/daniiiil11111/gymplan.git
cd gymplan
```

### 2. Set the API key
The key is read from an environment variable and is **never** stored in the code.

macOS / Linux:
```bash
export GEMINI_API_KEY="your-key-here"
```
Windows (PowerShell):
```powershell
$env:GEMINI_API_KEY="your-key-here"
```

### 3. Run
```bash
./mvnw spring-boot:run
```
Then open **http://localhost:8080** in your browser.

### Configuration
`src/main/resources/application.properties`:
```properties
gemini.model=gemini-3.5-flash-lite
gemini.enabled=true
```

## Limitations

- Calorie and macro values are estimates, not medical advice.
- The free Gemini tier has rate limits. If you send too many requests you get a message asking you to wait.
- There are no user accounts yet, so plans are not saved.

## Ideas for the future

- User accounts with saved plans
- Training log with progress tracking
- Deploying the app online with a daily limit per user
- Units in lb/inches and more automated tests

## Author

**Daniil Kryvoruchko**: Passerelle Informatik, BFH
