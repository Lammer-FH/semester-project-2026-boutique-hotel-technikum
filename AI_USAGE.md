# AI Usage Documentation

This document serves as the mandatory record for all AI-assisted work on the **Boutique Hotel Technikum** project.

> **Automated Maintenance:** This file is automatically updated and iterated upon by an AI agent (see [AGENTS.md](./AGENTS.md)) following commits and implementation milestones.

---

## AI Interaction Log

| Feature / Task | AI Tool | Prompt / Task Description | Usage & Modifications |
| :--- | :--- | :--- | :--- |
| **Example Task** | *Name of AI* | *"Short description of what you asked"* | *e.g., Used code as-is / Modified logic to fit Java 25* |
| Best practices update | GitHub Copilot | "Add best practice guidelines to best-practices.md" | Added guideline sections for API, backend, DB, frontend, state management, testing |
| Markdown structure fix | GitHub Copilot | "Make sure markdown correctly structured" | Reordered sections and fixed list formatting |
| Best practices alignment | GitHub Copilot | "Ensure Clean Code, Clean Architecture, Richardson Level 2, Atomic Design, Pinia applied in best-practices.md" | Added explicit Clean Architecture/Clean Code guidance and clarified Level 2, Atomic Design, Pinia |
| Backend gaps follow-up | GitHub Copilot | "Add remaining backend best practices to [best-practices.md](best-practices/best-practices.md): 'copy-pasted best-practices from the lecture pdf's converted to markdown as input'" | Added domain purity, bean validation, transaction boundaries, and feature-based package structure |
| DB design draft | GitHub Copilot | "Create a Mermaid ERD diagram inside @DB_DESIGN.md for my initial MySQL DB design" | Drafted ERD diagram |
| API Specification draft | GitHub Copilot | "Based on project_specification.md, best-practices.md, and DB_DESIGN.md: create the initial draft plan of API Specification in API_SPECIFICATION.md using OpenAPI best practices" | Created API specification covering all 5 user stories |
| DB design refinements | Codex | "Document definite DB design review findings" | Added date semantics, constraints, indexes, and FK delete behavior |
| API specification improvements | Codex | 1. "You are an expert in Java Backends an Spring Boot API specifications. First look at @docs/pre-project/project_specification.md so you have a understanding of this project, then analyze @docs/API_SPECIFICATION.md and find faults/problems that should be improved, so i can adjust this API_SPECIFICATION before i start implementing the backend." 2. "Turn the revision findings into a concrete revision checklist, then use this to update/fix the existing @docs/API_SPECIFICATION.md file based on the previous suggestions." | Added revisions: content-type/date rules, error codes, pagination behavior, response examples, booking idempotency, validation defaults |
| API/DB alignment follow-up | Codex | "Adjust date format and align API with DB constraints" | Documented date format, constant EUR currency, idempotency non-persistence, and DB parsing note |
| Date format typo fix | Codex | "Fix date format typo to DD.MM.YYYY" | Updated API and DB docs to use DD.MM.YYYY |
| Backend readiness fixes | Codex | "Fix spec gaps for backend implementation, move backend DB migrations to backend/db" | Updated API/DB docs for image URLs, pricing, timestamps, seed data; added JPA/validation deps and config examples, relocated Flyway migration files. |
| Pre-implementation gaps fix | opencode | "Fix 6 gaps: pom parent version, PK AUTO_INCREMENT, alt_text, context-path, package structure, HotelContact constants" | Fixed parent to 3.4.0, added AUTO_INCREMENT to all PKs and alt_text column in V1, added context-path to yaml, created package directories + HotelProperties config class, updated seed data with alt_text |
| Central logging utility | opencode | "Create central logger in util submodule (backend/util/) that improves log readability and removes thread name padding" | Created multi-module Maven structure (hotel-parent > util + hotel), Logger.java wrapper over SLF4J with factory method, Log.java static-import style, logback-spring.xml with clean format (HH:mm:ss, colored level, compact class name, ▶ separator) |
| Run backend from parent | opencode | "Make backend Maven commands always target hotel module" | Added `backend/.mvn/maven.config` and configured parent to skip Spring Boot plugin by default (overridden in `hotel`) so `spring-boot:run` works from `backend/` without `-pl` |

---

## Documentation Guidelines

To keep this file clean and easy to review, follow these principles:

1.  **Be Very Concise:** You don't need to paste entire transcripts. A summary of the prompt is sufficient.
2.  **Define Modifications:** Explicitly state if you accepted the AI output "as-is" or if you had to refactor it to meet project standards.
3.  **Update Frequently:** Add an entry as soon as you use an AI tool to avoid missing details during final submission.
