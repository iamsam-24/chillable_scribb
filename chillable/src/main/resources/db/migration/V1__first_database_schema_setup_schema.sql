CREATE TABLE users(
        id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
        username VARCHAR(150) NOT NULL,
        created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE rooms(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    user_id uuid NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_room_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE

);

CREATE TABLE room_membership(
    user_id uuid NOT NULL,
    room_id uuid NOT NULL,

    CONSTRAINT fk_room_membership_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_room_membership_room_id FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT pk_room_membership_ PRIMARY KEY (user_id,room_id)


);



CREATE TABLE questions(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    correct_ans VARCHAR(144) NOT NULL,

    room_id uuid NOT NULL,
    CONSTRAINT fk_question_room_id FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE choice(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    choices VARCHAR(144) NOT NULL,
    question_id uuid NOT NULL,

    CONSTRAINT fk_choice_question_id FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);
CREATE TABLE message(

                        id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id uuid NOT NULL,
                        room_id uuid NOT NULL,
                        question_id uuid,
                        content TEXT NOT NULL,
                        message_type VARCHAR(10) NOT NULL,
                        created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,


                        CONSTRAINT fk_message_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        CONSTRAINT fk_message_room_id FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE  CASCADE,
                        CONSTRAINT fk_message_question_id FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
                        CONSTRAINT chk_message_type
                        CHECK (message_type IN ('TEXT','ANSWER'))

);