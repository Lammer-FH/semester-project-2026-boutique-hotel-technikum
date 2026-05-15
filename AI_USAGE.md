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
| Frontend blank page fix | Codex | "Homepage not rendering; fix Ionic Vue mounting" | Removed custom-element override, imported Ionic components explicitly in App/Hero/FeatureGrid |
| Ionic layout refactor | Codex | "Avoid custom CSS for layout; use Ionic components" | Refactored hero/story/info/contact/footer to Ionic grid/cards/lists; replaced section layout with Ionic spacing utilities; trimmed layout CSS to styling-only |
| UI polish: colors + hero panel | Codex | "Buttons/links not blue; improve at-a-glance design" | Set Ionic primary color to terracotta, styled anchors, enhanced at-a-glance list item styling |
| Footer imprint link position | Codex | "Fix footer imprint link, it should be displayed below Vienna, Austria" | Moved the Imprint link under the location text in the footer. |
| Footer imprint as link | Codex | "Imprint should be a link and not a button in the footer" | Replaced the Imprint button with a router link in the footer. |
| Centered content layout | Codex | "The entire content should be more centered ... good UX" | Added a centered content wrapper to landing, about, and imprint pages and constrained page width for readability. |
| Header layout refinement | Codex | "Adjust the header, I don't quite like it yet" | Switched header to centered layout with text links, added sticky styling, and softened visual transition to content. |
| Header spacing + nav styling | Codex | "A bit more spacing after the text and links - also visually adjust the nav menu" | Increased brand-to-nav spacing and added a pill-style nav container with hover highlights. |
| Mobile nav dropdown under header | Codex | "Nav menu shouldn't be an overlay, it should appear under the header as an overlay and adjust the color" | Replaced the side menu with a header dropdown and styled it with a cream background. |
| Mobile nav auto-hide | Codex | "Nav menu should automatically disappear after a click" | Wrapped menu items with IonMenuToggle to auto-close after navigation. |
| About page UX polish | Codex | "Improve about us UX and design slightly, adjust colors etc." | Constrained About text width, increased paragraph line-height and font-size for readability, adjusted terracotta color for better contrast, and tuned info-list text color. |
| About page ionic icons | Codex | "Use Ionic components, icons etc. for about page as well" | Added IonIcon to the values list and neighborhood highlights; updated `InfoList.vue` and `hotelContent.ts`. |
| Box styling polish | Codex | "Make the boxes even more visually appealing" | Enhanced card and list item styling with gradients, borders, and shadows; added styling hooks for About lists. |
| Consistent icon box styling | Codex | "Design should always be consistent, boxes with icons on home and about page should have the same design" | Unified icon box styling across home feature cards and About list items using a shared `icon-box` class. |
| About boxes match landing | Codex | "Boxes in about should look like the ones on the landing page" | Switched About values and neighborhood sections to use FeatureGrid cards with shared icon styles. |
| Reusable icon box component | Codex | "Create an extra component for these boxes" | Added IconBoxCard component and refactored FeatureGrid to use it. |
| Hero at-a-glance list | Codex | "No white boxes for 'at a glance', but a list with icons" | Replaced hero panel boxed items with an icon bullet list. |
| At a glance spacing | Codex | "More spacing below the heading for 'at a glance'" | Increased spacing under the At a glance heading. |
| At a glance spacing fix | Codex | "Didn't change anything" | Made the At a glance heading block-level to ensure margin spacing is visible. |
| Imprint ionic redesign + cleanup | Codex | "Optimize the code and CSS, remove unnecessary things - use Ionic for imprint as well and adjust the design there" | Redid Imprint with Ionic card/list + icons, removed unused InfoList component and CSS, cleaned duplicate styles. |
| Footer not sticky | Codex | "Footer not sticky" | Moved footer inside page content so it scrolls with the page. |
| Footer full width | Codex | "Footer full width at the bottom and reaching the bottom edge without spacing" | Made the footer span full width and removed the bottom padding gap inside content. |
| Footer padding fix | Codex | "The div container still has padding from the footer" | Moved page padding to inner container and removed content padding so the footer sits flush. |
| Footer centered text | Codex | "Footer text also centered" | Centered footer column content and link styling. |
| Imprint footer spacing | Codex | "Imprint page footer still has spacing at the bottom" | Removed bottom padding on page content and footer margin to eliminate the gap. |
| Footer grid padding | Codex | "Ion-grid still has padding in imprint" | Removed Ionic grid padding in the footer to eliminate extra spacing. |
| Footer full-screen gap fix | Codex | "Still a gap at the bottom in fullscreen - also display the imprint link centered in the footer" | Removed scroll padding, zeroed grid column padding, and centered the Imprint link in the footer. |
| Footer imprint position | Codex | "Imprint should be between the others, not full length" | Placed Imprint as a middle column between brand and tagline in the footer. |
| Footer bottom alignment | Codex | "Footer still not positioned at the very bottom in fullscreen" | Made pages flex columns so content grows and footer stays at the bottom. |
| Header logo sizing | Codex | "Make the header logo larger and properly scaled" | Increased header logo size and moved sizing to responsive CSS classes. |
| Story section accent optional | Codex | "Fix Vue warning about accentText being undefined" | Made accent props optional and render accent card only when content is provided. |
| About content accents | Codex | "add accentTitle & accentText to hotelConten" | Added `accentTitle` and `accentText` to `aboutContent` in hotelContent.ts |
| CSS refactor cleanup (remove legacy file) | Codex | "Remove legacy style.css import and delete empty file" | Removed main.css import and deleted legacy global stylesheet |
| CSS refactor cleanup (legacy file) | Codex | "Remove remaining placeholder from legacy global stylesheet" | Emptied legacy style.css after migrating all rules |
| CSS refactor cleanup | Codex | "Move BaseSectionTitle styles into component and remove unused globals" | Scoped section title styles to BaseSectionTitle and removed unused global classes |
| CSS refactor step 3 (imprint page) | Codex | "Move imprint card styles into ImprintPage scoped block" | Migrated imprint card styles into ImprintPage.vue and removed unused global imprint rules |
| CSS refactor step 3 (story section) | Codex | "Move story section styles into StorySection scoped block" | Migrated story typography and accent card styles into StorySection.vue and removed from global stylesheet |
| CSS refactor step 3 (feature grid) | Codex | "Move feature grid layout rules into FeatureGrid scoped block" | Migrated feature-grid equal-height rules into FeatureGrid.vue and removed from global stylesheet |
| CSS refactor step 3 (contact strip) | Codex | "Move contact strip styles into ContactStrip scoped block" | Migrated contact card and action styles into ContactStrip.vue and removed from global stylesheet |
| CSS refactor step 3 (icon box card) | Codex | "Move icon box styles into IconBoxCard scoped block" | Migrated icon-box and feature-card styling into IconBoxCard.vue and removed from global stylesheet |
| CSS refactor step 3 (hero banner) | Codex | "Move hero banner styles into HeroBanner scoped block" | Migrated hero banner styles into HeroBanner.vue and removed from global stylesheet |
| CSS refactor step 3 (footer) | Codex | "Move footer styles from global CSS into TheFooter scoped block" | Migrated app-footer styles into TheFooter.vue and removed from global stylesheet |
| CSS refactor step 3 (header) | Codex | "Move header styles from global CSS into TheHeader scoped block" | Migrated app-header styles into TheHeader.vue and removed from global stylesheet |
| CSS refactor step 2 | Codex | "Extract global styles into variables/base and update main import" | Moved root tokens and base typography/layout to new files; routed app styles through main.css |
| CSS refactor step 1 | Codex | "Create global styles folder structure and base files" | Added styles directory with variables/base/main CSS scaffolding |
| Pre-implementation gaps fix | opencode | "Fix 6 gaps: pom parent version, PK AUTO_INCREMENT, alt_text, context-path, package structure, HotelContact constants" | Fixed parent to 3.4.0, added AUTO_INCREMENT to all PKs and alt_text column in V1, added context-path to yaml, created package directories + HotelProperties config class, updated seed data with alt_text |
| Central logging utility | opencode | "Create central logger in util submodule (backend/util/) that improves log readability and removes thread name padding" | Created multi-module Maven structure (hotel-parent > util + hotel), Logger.java wrapper over SLF4J with factory method, Log.java static-import style, logback-spring.xml with clean format (HH:mm:ss, colored level, compact class name, ▶ separator) |
| Run backend from parent | opencode | "Make backend Maven commands always target hotel module" | Added `backend/.mvn/maven.config` and configured parent to skip Spring Boot plugin by default (overridden in `hotel`) so `spring-boot:run` works from `backend/` without `-pl` |
| Pre-implementation doc fixes | opencode | "Analyze ARCHITECTURE.md for backend issues; fix DB_DESIGN.md, API_SPECIFICATION.md, ARCHITECTURE.md" | Added `guest_count` to bookings schema/ERD/API spec, added `alt_text` to room_images ERD, added email-confirmation server-side validation rule, documented pricing calc formula with `breakfast-price-per-person` config, expanded HotelProperties with address/directions/breakfastPrice fields, added `EMAIL_MISMATCH` error code, added `JpaExtraRepositoryTest` to test plan, added `guest_count > 0` constraint |
| Architecture alignment update | opencode | "Update ARCHITECTURE.md to align with API/DB specs" | Added architectural responsibilities, API rule alignment (validation, pricing, pagination), JSON naming strategy guidance, transaction boundary note, properties injection note, and expanded testing focus |
| Backend skeleton implementation | opencode | "Implement backend structure per architecture and API/DB specs" | Added Spring Boot entrypoint, config, controllers, DTOs, domain models, JPA entities, mappers, repositories, services, and Jackson snake_case config with Clean Architecture layering and hotel-util logging |
| Backend simplification refactoring | opencode | "Refactor over-engineered backend: remove dead code, merge error enums, replace Logger wrapper with SLF4J, eliminate hotel-util module, simplify RoomExtraEntity to @ManyToMany, remove repository adapters for Extra/Room, remove pessimistic lock & unnecessary @Transactional, add @Component to mappers, simplify BookingResponseMapper params" | Deleted 14 files, modified ~20 files. Removed `AvailabilityRequest`, `Log.java`, `ApiErrorCode` (merged into `ErrorCode`), replaced `Logger` wrapper with direct SLF4J in 10 files, deleted `hotel-util` module, converted `RoomExtraEntity` composite-key join to `@ManyToMany`, removed `ExtraRepository`/`RoomRepository` interfaces + adapters (inlined logic into services with JPA repos + `@Component` mappers), removed `JpaConfig`, removed `@Lock(PESSIMISTIC_WRITE)`, removed `@Transactional(readOnly=true)` on single-repo reads, simplified `BookingResponseMapper.toResponse()` from 6 params to 3 params using pre-built `PriceBreakdown` DTO. |
| Swagger API spec & documentation | opencode | "Integrate Swagger API specification & documentation in backend/" | Added springdoc-openapi-starter-webmvc-ui (v2.8.6) dependency, created OpenApiConfig with hotel metadata, annotated all 5 controllers with @Tag/@Operation/@ApiResponse/@Parameter, annotated all 8 DTOs with @Schema (descriptions + examples) |
| Frontend API | codex | "Implement backend endpoints based use httpClient.ts" | Added API endpoints implementation, API mappers, core models |

---

## Documentation Guidelines

To keep this file clean and easy to review, follow these principles:

1.  **Be Very Concise:** You don't need to paste entire transcripts. A summary of the prompt is sufficient.
2.  **Define Modifications:** Explicitly state if you accepted the AI output "as-is" or if you had to refactor it to meet project standards.
3.  **Update Frequently:** Add an entry as soon as you use an AI tool to avoid missing details during final submission.