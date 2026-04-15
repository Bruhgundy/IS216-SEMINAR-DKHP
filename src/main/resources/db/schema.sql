-- ============================================================
--  Course Registration System  –  Oracle DDL
--  Run as DBA or a user with CREATE TABLE / SEQUENCE privileges
-- ============================================================

-- ── Drop in reverse dependency order ──────────────────────────
DROP TABLE registrations  CASCADE CONSTRAINTS PURGE;
DROP TABLE students        CASCADE CONSTRAINTS PURGE;
DROP TABLE courses         CASCADE CONSTRAINTS PURGE;

-- ── COURSES ───────────────────────────────────────────────────
--  day_of_week : 1 = Monday … 7 = Sunday
--  start_period: 1-based period slot (e.g. 1 = 07:00, 2 = 08:00 …)
--  period_count: number of consecutive periods this course occupies
--  version_    : optimistic-lock counter (renamed; VERSION is reserved in some Oracle configs)
CREATE TABLE courses (
    id             NUMBER         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name           VARCHAR2(255)  NOT NULL,
    available_seats NUMBER(5)     NOT NULL CHECK (available_seats >= 0),
    day_of_week    NUMBER(1)      NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    start_period   NUMBER(2)      NOT NULL CHECK (start_period >= 1),
    period_count   NUMBER(2)      NOT NULL CHECK (period_count >= 1),
    version_       NUMBER         DEFAULT 0 NOT NULL,   -- optimistic locking
    created_at     TIMESTAMP      DEFAULT SYSTIMESTAMP,
    updated_at     TIMESTAMP      DEFAULT SYSTIMESTAMP
);

-- index for schedule-conflict detection
CREATE INDEX idx_courses_day_period ON courses (day_of_week, start_period);

-- ── STUDENTS ──────────────────────────────────────────────────
CREATE TABLE students (
    id             NUMBER         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_code   VARCHAR2(20)   NOT NULL UNIQUE,   -- e.g. "SV2024001"
    full_name      VARCHAR2(255)  NOT NULL,
    email          VARCHAR2(255)  UNIQUE,
    created_at     TIMESTAMP      DEFAULT SYSTIMESTAMP
);

-- ── REGISTRATIONS ─────────────────────────────────────────────
CREATE TABLE registrations (
    id             NUMBER         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id     NUMBER         NOT NULL,
    course_id      NUMBER         NOT NULL,
    status         VARCHAR2(20)   DEFAULT 'CONFIRMED' NOT NULL,
    registered_at  TIMESTAMP      DEFAULT SYSTIMESTAMP,
    CONSTRAINT fk_reg_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_reg_course  FOREIGN KEY (course_id)  REFERENCES courses(id),
    CONSTRAINT uq_student_course UNIQUE (student_id, course_id)
);

CREATE INDEX idx_reg_course   ON registrations (course_id);
CREATE INDEX idx_reg_student  ON registrations (student_id);

-- ── TRIGGER: keep updated_at fresh on courses ─────────────────
CREATE OR REPLACE TRIGGER trg_courses_upd
BEFORE UPDATE ON courses
FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSTIMESTAMP;
END;
/

-- ============================================================
--  SEED DATA  –  8 sample courses
-- ============================================================
INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Introduction to Computer Science', 30, 2, 1, 3);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Calculus I', 25, 3, 3, 2);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Linear Algebra', 20, 4, 5, 2);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Data Structures & Algorithms', 15, 2, 4, 3);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Database Systems', 20, 5, 2, 2);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Operating Systems', 18, 3, 6, 2);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Software Engineering', 22, 4, 1, 3);

INSERT INTO courses (name, available_seats, day_of_week, start_period, period_count)
VALUES ('Artificial Intelligence', 10, 5, 4, 3);

-- ── 50 synthetic students ─────────────────────────────────────
BEGIN
    FOR i IN 1..50 LOOP
        INSERT INTO students (student_code, full_name, email)
        VALUES (
            'SV' || TO_CHAR(2024000 + i),
            'Student ' || TO_CHAR(i),
            'student' || TO_CHAR(i) || '@university.edu'
        );
    END LOOP;
END;
/

COMMIT;
