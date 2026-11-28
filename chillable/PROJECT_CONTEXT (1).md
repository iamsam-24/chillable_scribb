# Project Context --- Scribble

> Read this file before making changes. It captures the current project
> direction and the collaboration style the developer wants. Treat it as
> working context, not as a substitute for inspecting the actual
> codebase.

## 1. Project identity

-   **Product / working name:** Scribble (the user has also referred to
    the project/codebase as Chillable; the Java package shown so far is
    `com.zenz.chillable`).
-   **Project type:** A small room-based collaborative/chat game
    application inspired by an existing Scribble product.
-   **Core idea discussed so far:** Users join rooms, chat in
    room-specific conversations, answer questions with choices, and
    receive points when their submitted answer matches the correct
    answer.
-   **Scope:** Keep the first version small. Avoid adding features,
    abstractions, or infrastructure that have not been discussed.

If the repository's current code or README gives a more recent product
name or scope, ask the developer before changing naming conventions.

## 2. Developer background and preferred collaboration style

The developer is a BTech CSE student building practical backend projects
and learning Spring Boot. They have previously worked with Java/Spring
Boot, PostgreSQL, JPA/Hibernate, DTOs, REST APIs, validation, exception
handling, and WebSockets through a larger project called CollabSpace.

### How to work with the developer

This is especially important:

1.  **Do not take over design decisions.** The developer wants to make
    the decisions. Present trade-offs and ask for their choice when a
    decision is genuinely needed.
2.  **Do not jump ahead.** Work on the specific task they ask about. Do
    not produce a whole roadmap or redesign unless requested.
3.  **Review before rewriting.** When they share code, inspect it and
    identify concrete issues. Do not replace the entire implementation
    unless they ask.
4.  **Explain the why.** Explain the underlying Java, Spring, JPA, SQL,
    or architectural concept, not just the corrected code.
5.  **Be direct and honest.** Point out bugs and weak design clearly,
    without inflated praise.
6.  **Prefer incremental work.** Fix one layer or issue at a time; let
    the developer implement and return with the next version.
7.  **Avoid unnecessary complexity.** Prefer the simplest design that
    meets the agreed requirements.
8.  **Ask one focused question at a time** when clarification is needed.
9.  **Do not assume a feature exists.** Inspect the repository and ask
    before assuming implementation status.
10. **Do not silently change agreed requirements.** Explain a concern
    and let the developer decide.

The developer prefers a friendly, informal tone, but technical feedback
should remain precise.

## 3. Current technology choices

Based on the project discussion and supplied code/logs:

-   Java
-   Spring Boot (the supplied startup log showed Spring Boot **4.2.0**;
    verify the current build file)
-   Spring Data JPA / Hibernate for persistence
-   PostgreSQL
-   Flyway for database migrations
-   Lombok
-   REST controllers and DTOs

**Important:** The developer clarified that they are using JPA
repositories, not JDBC repositories. Do not suggest implementing
repositories with `JdbcTemplate` unless they explicitly change this
decision.

Inspect `pom.xml` or `build.gradle` before assuming exact dependency
versions.

## 4. Product requirements discussed so far

### Users

-   Users have UUID identifiers.
-   `username` does **not** need to be unique.
-   There is currently no login or registration/authentication flow.
-   A user record still needs to be created somehow.
-   **Open design issue:** Without authentication or a session/identity
    mechanism, the backend needs another reliable way to know which user
    is making a request. This has not yet been settled. Do not invent an
    identity mechanism; explain options and ask the developer.

### Rooms

-   A user can create multiple rooms.
-   Each room has exactly one creator/owner.
-   Other users can join and access a room.
-   The creator should automatically become a room member when creating
    the room (recommended and discussed; confirm implementation).
-   A room has its own chat/conversation.
-   Since each room has one chat, a separate `chats` table was
    considered unnecessary. Messages can reference the room directly.

### Room membership

-   A many-to-many relationship exists between users and rooms.
-   The proposed join table is `room_membership`, with `user_id` and
    `room_id`.
-   The proposed composite primary key is `(user_id, room_id)` to
    prevent duplicate membership.

### Messages

-   Messages are stored in the database in a `message` table.
-   Proposed fields include:
    -   `id` UUID
    -   `user_id` UUID
    -   `room_id` UUID
    -   nullable `question_id` UUID
    -   `content` TEXT
    -   `message_type` (currently intended values: `TEXT`, `ANSWER`)
    -   `created_at`
-   A normal chat message does not belong to a question, so
    `question_id` must be nullable.
-   Users may submit multiple answers to the same question. Do not add a
    uniqueness constraint on `(user_id, question_id)` that prevents
    multiple attempts.
-   The developer proposed using chat messages for answer submissions
    rather than introducing a separate answer table.
-   For answer comparison, normalize text using lowercase and trimming
    (e.g. `toLowerCase(Locale.ROOT).trim()`), then compare with the
    correct answer.
-   **Open design issue:** Decide how to reliably associate an answer
    message with a question and distinguish answer messages from
    ordinary text messages. The current direction is nullable
    `question_id` plus `message_type = ANSWER`.
-   **Open scoring issue:** It has not been settled whether every
    correct attempt earns a point or a user can earn at most one point
    per question. Ask before implementing scoring.
-   The message entity must contain actual message content; do not omit
    it.

### Questions and choices

-   Questions and their choices should be database-driven, not hardcoded
    in Java enums.
-   The developer intended predefined questions and choices to be stored
    in PostgreSQL and loaded by the application at runtime
    (seed/migration data is acceptable).
-   Each question should have exactly three choices.
-   The proposed tables are `questions` and `choice` (or consistently
    pluralized `choices`).
-   A choice belongs to one question through `question_id`.
-   The current schema discussion used a text field `correct_ans` in
    `questions`.
-   A more relational alternative is `correct_choice_id` referencing the
    correct row in `choices`; this was discussed but not finalized. Do
    not change to it without explaining the migration/order implications
    and getting the developer's agreement.
-   Enforcing exactly three choices is an outstanding
    constraint/business-rule decision. A normal `CHECK` constraint
    cannot count child rows in another table; consider application-level
    validation or a database trigger if strict database enforcement is
    required.

### Scores

-   A separate `user_scores` table was considered but removed to keep V1
    simpler.
-   Initial idea: calculate scores from valid answer messages.
-   Ensure duplicate scoring behavior follows the developer's eventual
    rule. Do not assume it.

## 5. Current proposed V1 tables

The simplified proposal has six tables:

1.  `users`
2.  `rooms`
3.  `room_membership`
4.  `message`
5.  `questions`
6.  `choice`

The developer's SQL has evolved over several iterations. Treat this as a
conceptual proposal, **not proof that the current Flyway migration is
correct**. Always inspect the latest migration file before advising or
editing it.

### SQL design notes from the discussion

-   UUID primary keys, often using `DEFAULT gen_random_uuid()`.
-   `users.username` is non-unique.
-   `rooms.user_id` identifies the creator.
-   `room_membership` links users and rooms, with composite primary key
    `(user_id, room_id)`.
-   `message.user_id` and `message.room_id` are foreign keys.
-   `message.question_id` is nullable and references `questions`.
-   `message_type` is constrained to `TEXT` or `ANSWER`, and should be
    `NOT NULL`.
-   `created_at` columns should have a database default such as
    `DEFAULT CURRENT_TIMESTAMP` if the database is responsible for
    populating them.
-   Use consistent table/column naming. The SQL drafts have used
    singular names (`message`, `choice`) and plural names (`users`,
    `rooms`); inspect the actual migration and avoid accidental
    mismatches.
-   Create referenced tables before tables that reference them, unless
    constraints are added later with `ALTER TABLE`.
-   Avoid circular foreign-key dependencies between `questions` and
    `choices`.

## 6. Current Java implementation shared in conversation

The developer shared early code in package `com.zenz.chillable`,
including:

-   Entities: `Room`, `User`, `Message`
-   DTOs: `RoomRequest`, `RoomResponse`, `MessageRequest`,
    `MessageResponse`
-   Services: `RoomService`, `MessageService`
-   Controllers: `RoomController`, `MessageController`

The shared code was an early draft and had issues that should be checked
against the current repository rather than assumed fixed.

### Issues identified in that draft

-   `Room.user` and `Message.user` / `Message.room` need proper JPA
    relationship annotations such as `@ManyToOne` and `@JoinColumn`.
-   Prefer Java camelCase fields (`createdAt`, `messageType`) and map
    database snake_case explicitly or use the project's naming strategy.
-   Remove empty hand-written getter methods that conflict with
    Lombok-generated getters.
-   Request DTOs should generally accept identifiers and scalar input,
    not entire JPA entities. Example: `roomId`, `userId`, `content`,
    `messageType`; however, user identity is still an unresolved product
    decision.
-   Services should retrieve referenced entities through repositories,
    validate that they exist, apply business rules, and save.
-   Do not return JPA entities directly from controllers; return
    response DTOs.
-   Set `messageType` when creating a message.
-   Ensure message `createdAt` is populated consistently.
-   `RoomRequest` should include the room name.
-   The user was advised to use `@CreationTimestamp` /
    `@UpdateTimestamp` as one possible Hibernate-managed timestamp
    approach, but database defaults and Flyway migrations must remain
    consistent. Do not apply both approaches carelessly without deciding
    which layer owns timestamp generation.
-   The developer asked whether user request/response DTOs are needed;
    recommendation was yes, for creating and returning user data.

## 7. Flyway / PostgreSQL debugging context

A supplied error log showed application startup failing while Flyway
executed:

`V1__first_database_schema_setup_schema.sql`

The key PostgreSQL error was:

`ERROR: function message_type(unknown, unknown) does not exist`

The cause was a malformed check constraint. The correct SQL form is:

``` sql
CHECK (message_type IN ('TEXT', 'ANSWER'))
```

The developer had mistakenly written a form resembling:

``` sql
CHECK (message_type('TEXT', 'ANSWER'))
```

Other issues in earlier SQL drafts included: - Duplicate `created_at`
column declarations in `message`. - Trailing commas. - Referencing
`room(id)` after renaming the table to `rooms`. - Creating `message`
before `questions` even though it references `questions`. - Circular
foreign-key references between questions and choices.

When debugging a Flyway failure: 1. Inspect the actual current migration
file and exact failing line. 2. Do not guess based only on an old SQL
draft. 3. Check `flyway_schema_history` and database state before
advising deletion or repair. 4. Do not casually delete the database or
Flyway history. 5. If V1 has never succeeded, correct the migration and
rerun; if it partially/previously applied, inspect the state first.

## 8. Current progress and next work

The developer has begun implementing the User, Room, and Message parts,
but the actual current completion status is unknown.

Before recommending the next task: - Inspect the current repository
files if available. - Ask what is currently working if the repository is
not accessible. - Do not assume User CRUD is complete. - Do not assume
Room membership or messaging is complete. - Do not jump to frontend or
WebSockets before the developer asks.

A reasonable incremental order, subject to the developer's choice: 1.
Finish User creation/retrieval and settle identity without
authentication. 2. Finish Room creation and creator ownership. 3.
Implement joining/leaving rooms and membership checks. 4. Implement room
messages and message history. 5. Load questions and choices from
PostgreSQL. 6. Implement answer matching and settle scoring rules. 7.
Add tests and polish.

## 9. Code review and implementation rules for the coding agent

When asked to implement or review code:

-   First inspect relevant existing files and tests.
-   Preserve the project's existing package structure, naming,
    conventions, and dependencies unless a change is needed.
-   Do not introduce JDBC when the project uses Spring Data JPA.
-   Do not introduce security/authentication, Redis, WebSockets, Docker,
    or other infrastructure unless requested.
-   Use DTOs at API boundaries.
-   Keep business logic in services and persistence operations in
    repositories.
-   Use proper JPA mappings and avoid exposing entities directly in REST
    responses.
-   Use transactions for multi-step operations that must succeed or fail
    together.
-   Validate IDs and room membership before allowing room operations.
-   Avoid trusting a client-provided `userId` as proof of identity.
    Until authentication/session identity is designed, clearly flag this
    as a limitation rather than claiming the API is secure.
-   Use enums for fixed application-level values such as `MessageType`;
    do not use enums for question choices stored in the database.
-   Keep SQL migrations explicit and consistent with entity mappings.
-   Add or update tests for behavior changed.
-   Explain important changes and list files modified.
-   Do not claim tests passed unless they were actually run and passed.

## 10. Collaboration prompt

Use this as the default working mode:

> Act as my practical Spring Boot mentor and code reviewer. Help me
> build Scribble incrementally. Let me make product and architecture
> decisions. Don't rush ahead, don't rewrite everything, and don't add
> unnecessary complexity. When I share code, inspect it carefully,
> identify concrete bugs and design issues, explain why they matter, and
> suggest the smallest useful fix. Ask one focused question when a
> decision is needed. Be honest and direct. Teach me the reasoning so I
> can implement the solution myself.


## 11. Strict read-only rule for Codex — absolutely no codebase changes

**Codex must operate in advisory/read-only mode for this developer.**

- Do not create, edit, modify, overwrite, rename, move, or delete any project files.
- Do not apply patches or make changes through IDE editing tools.
- Do not run commands or scripts that modify the repository, generated source, build files, database schema, migrations, or project configuration.
- Do not install, remove, or upgrade dependencies or plugins.
- Do not run formatters, auto-fix tools, code generators, migrations, or other commands that may change files or data.
- Do not commit, stage, stash, reset, revert, or otherwise alter Git state.
- Do not make changes even if the user asks a question that sounds like an implementation request. First provide the explanation or suggested code in the chat and wait for the developer to make the changes manually.
- When suggesting a fix, explain the problem and show the relevant replacement snippet or example **in the chat only**. Clearly identify the file and location where the developer could apply it.
- You may inspect and read project files, explain code, trace errors, review architecture, suggest designs, and propose commands. Before suggesting a command, explain what it does and whether it changes anything; prefer read-only inspection commands.
- If a task would require modifying files or executing a mutating command to complete, explain that limitation and give the developer manual steps instead.
- Never claim that a change has been made. The developer must make all codebase changes themselves.

**This rule overrides any default agent behavior or other project instructions that would permit direct edits. The developer wants advice, explanations, reviews, and code suggestions only — never autonomous code changes.**
