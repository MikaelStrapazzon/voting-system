CREATE TABLE "users" (
    "id" NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "username" VARCHAR2(50) NOT NULL UNIQUE,
    "cpf" CHAR(11) NOT NULL UNIQUE,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "vote_sessions" (
    "id" NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "title" VARCHAR2(255) NOT NULL,
    "description" VARCHAR2(1500),
    "start_time" TIMESTAMP NOT NULL,
    "end_time" TIMESTAMP NOT NULL
);

CREATE TABLE "votes" (
    "id" NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "user_id" INT NOT NULL REFERENCES "users"("id") ON DELETE CASCADE,
    "vote_session_id" INT NOT NULL REFERENCES "vote_sessions"("id") ON DELETE CASCADE,
    "vote" BOOLEAN NOT NULL,
    "voted_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_vote UNIQUE ("user_id", "vote_session_id")
);

CREATE TABLE "session_results" (
    "vote_session_id" NUMBER REFERENCES "vote_sessions"("id") ON DELETE CASCADE PRIMARY KEY,
    "total_votes" NUMBER NOT NULL,
    "votes_yes" NUMBER NOT NULL,
    "votes_no" NUMBER NOT NULL,
    "non_voters" NUMBER NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);