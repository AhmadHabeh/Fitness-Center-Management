-- Begin transaction
BEGIN TRANSACTION;

-- Drop tables if they exist
DROP TABLE IF EXISTS class_registration CASCADE;
DROP TABLE IF EXISTS equipment_usage CASCADE;
DROP TABLE IF EXISTS workout_session CASCADE;
DROP TABLE IF EXISTS gym_class CASCADE;
DROP TABLE IF EXISTS user_details CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS exercises CASCADE;





-- Users Table 
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);


-- Insert sample starting data
-- Password for all users is 'password'
INSERT INTO users (username, password_hash, role)
VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');

-- *************************************************************************************************
-- Optional Tables and Data

-- Gym Classes Table
CREATE TABLE gym_class (
    class_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE user_details (
    user_id INTEGER PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    photo TEXT,
    workout_goals TEXT,
    workout_profile TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE exercises (
    exercise_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    muscle_group VARCHAR(50) NOT NULL,
    description TEXT,
    equipment VARCHAR(100)
);

-- Workout Session Table 
CREATE TABLE workout_session (
    session_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    check_in_time TIMESTAMP NOT NULL,
    check_out_time TIMESTAMP,
    checked_in_by INTEGER, 
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (checked_in_by) REFERENCES users(user_id)
);


-- Equipment Usage Table 
CREATE TABLE equipment_usage (
    usage_id SERIAL PRIMARY KEY,
    session_id INTEGER NOT NULL,
    exercise_id INTEGER NOT NULL,
    date_time TIMESTAMP NOT NULL,
    reps INTEGER,
    weight_per_rep NUMERIC(6,2),
    notes TEXT,
    FOREIGN KEY (session_id) REFERENCES workout_session(session_id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(exercise_id) ON DELETE CASCADE
);

-- Gym Classes registeration Table
CREATE TABLE class_registration (
    registration_id SERIAL PRIMARY KEY,
    class_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    registration_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (class_id) REFERENCES gym_class(class_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (class_id, user_id)
);




-- *************************************************************************************************
-- *************************************************************************************************
-- Insert some sample starting data
-- Insert exercises into the exercises table

-- Chest Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Flat Bench Press', 'Chest', 'A compound exercise targeting the chest muscles.', 'Barbell'),
('Incline Bench Press', 'Chest', 'Targets the upper portion of the chest.', 'Barbell'),
('Chest Fly Machine', 'Chest', 'Isolates the chest muscles.', 'Machine');

-- Shoulders Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Shoulder Press', 'Shoulders', 'Targets the deltoid muscles.', 'Dumbbells or Barbell'),
('Lateral Raises', 'Shoulders', 'Isolates the side deltoids.', 'Dumbbells'),
('Shoulder Shrugs', 'Shoulders', 'Targets the trapezius muscles.', 'Dumbbells or Barbell');

-- Triceps Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Tricep Pushdown', 'Triceps', 'Isolates the triceps muscles.', 'Cable Machine'),
('Tricep Extension', 'Triceps', 'Targets the triceps.', 'Dumbbell or Cable');

-- Back Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Lat Pull Down', 'Back', 'Targets the latissimus dorsi.', 'Lat Pull Down Machine'),
('Seated Cable Row Machine', 'Back', 'Works the middle back.', 'Cable Machine'),
('Rear Delt Fly Machine', 'Back', 'Targets the rear deltoids.', 'Machine'),
('Back Extensions', 'Back', 'Strengthens the lower back.', 'Back Extension Bench');

-- Biceps Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Dumbbell Bicep Curls', 'Biceps', 'Isolates the biceps.', 'Dumbbells'),
('Hammer Curls', 'Biceps', 'Targets the biceps and forearms.', 'Dumbbells');

-- Legs Exercises
INSERT INTO exercises (name, muscle_group, description, equipment) VALUES
('Barbell Squats', 'Legs', 'Compound exercise targeting the quadriceps, hamstrings, and glutes.', 'Barbell'),
('Deadlift', 'Legs', 'Works the entire posterior chain.', 'Barbell'),
('Leg Extension', 'Legs', 'Isolates the quadriceps.', 'Machine'),
('Leg Curls', 'Legs', 'Targets the hamstrings.', 'Machine'),
('Leg Press', 'Legs', 'Works the quadriceps and glutes.', 'Machine'),
('Calf Raises', 'Legs', 'Isolates the calf muscles.', 'Machine or Dumbbells');




-- Insert initial gym classes
INSERT INTO gym_class (name, description, start_time, end_time, capacity) VALUES
('Yoga Basics','A beginner-friendly yoga class focusing on fundamental poses and breathing techniques.','2024-11-01 09:00:00','2024-11-01 10:00:00',20);
INSERT INTO gym_class (name, description, start_time, end_time, capacity) VALUES
('HIIT Blast','High-intensity interval training to burn calories and build strength.', '2024-11-01 11:00:00', '2024-11-01 12:00:00', 15);
INSERT INTO gym_class (name, description, start_time, end_time, capacity) VALUES
('Spin Session', 'Cycling class for all fitness levels, focusing on endurance and stamina.', '2024-11-01 17:00:00', '2024-11-01 18:00:00', 25);


-- *************************************************************************************************
-- *************************************************************************************************
-- Commit transaction
-- *************************************************************************************************
COMMIT TRANSACTION;
