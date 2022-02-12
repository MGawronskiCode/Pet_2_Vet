-- Data for the initial DB population

-- AppUser
INSERT INTO public.app_user(name, sex, login, password, role, is_deleted)
VALUES ('Alex', 2, 'alex@gmail.com', '$2a$12$TubK7CsuGdoLC7k/5t9rVuk895OFB1.ptaoRqT8ochIE/NDPqlwra', 1, false),
       ('Samantha', 3, 'samantha@gmail.com', '$2a$12$7X4MtR8D2YVJJYbIDCID.O3L8vNfNvu1vY6tdONaspjeCd8xGuS9q', 1, false),
       ('Brad', 2, 'brad@gmail.com', '$2a$12$3oMRmMAvHoEqWkMrwmnAc./wu5KI0TEmVxYrTnWV7sya05eOeu84S', 1, false),
       ('Caroline', 3, 'caroline@gmail.com', '$2a$12$AKlcgdKpBZ5LibEEJq/nuevu7qU17d8T0jHbGjUKn6ugcef0wGEG2', 2, false),
       ('Test0', 3, 'deleted@user', '$2a$12$Wmx.OKQyzwslJClf0k9JP.kUX7SsbIuWre4S1QEctpjRUQMvJrcbm', 1, true),
       ('Test1', 3, 'user@user', '$2a$12$Wmx.OKQyzwslJClf0k9JP.kUX7SsbIuWre4S1QEctpjRUQMvJrcbm', 1, false),
       ('Test2', 3, 'admin@admin', '$2a$12$66qPPyfCUxkdr1mYb1MMte6VHB/laSE6hzp25r5OETokWQXoTdPp2', 0, false);


-- Specie
INSERT INTO public.specie(name, is_deleted)
VALUES ('Mammal', false),
       ('Bird', false),
       ('Rodent', false),
       ('Reptile', false);

-- Pet
INSERT INTO public.pet(name, sex, birthday, specie_id, is_deleted)
VALUES ('Wizard', 0, '2021-01-05', 1, false),
       ('Kitana', 1, '2019-02-05', 2, false),
       ('Neo', 0, '2021-05-11', 1, false),
       ('Audrey', 1, '2021-06-26', 3, false),
       ('Rudolph', 0, '2020-12-15', 4, false),
       ('Grinch', 0, '2020-09-09', 1, false),
       ('Dizzy', 1, '2021-02-13', 4, false),
       ('Simba', 0, '2018-05-03', 2, false),
       ('Tiara', 1, '2020-10-29', 3, false),
       ('Merlin', 0, '2015-04-30', 1, false);

-- Users pets
INSERT INTO public.users_pets(user_id, pet_id)
VALUES (1, 2),
       (1, 7),
       (2, 1),
       (2, 8),
       (1, 4),
       (3, 3),
       (4, 6),
       (6, 10),
       (6, 9),
       (6, 5);

-- Meal
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id, is_deleted)
VALUES ('2021-11-23T07:00:00', '2021-11-23T07:30:00', 'Dry food', 300, 1, false),
       ('2021-11-23T07:20:00', '2021-11-23T07:20:00', 'Wet food', 150, 2, false),
       ('2021-11-23T08:00:00', '2021-11-23T08:05:00', 'Dry food', 200, 3, false),
       ('2021-11-23T08:00:00', '2021-11-23T07:50:00', 'Corns', 10, 4, false),
       ('2021-11-23T12:00:00', '2021-11-23T13:00:00', 'Vegetables', 150, 5, false),
       ('2021-11-23T09:00:00', '2021-11-23T08:55:00', 'Crickets', 35, 6, false),
       ('2021-11-23T06:30:00', '2021-11-23T06:10:00', 'Vegetables', 200, 7, false),
       ('2021-11-23T07:10:00', '2021-11-23T07:30:00', 'Dry food', 150, 8, false),
       ('2021-11-23T07:45:00', '2021-11-23T07:30:00', 'Corns', 10, 9, false),
       ('2021-11-23T09:05:00', '2021-11-23T09:00:00', 'Wet food', 150, 10, false);

-- Note
INSERT INTO public.note(content, created, modified, title, user_id, pet_id, is_deleted)
VALUES ('Wizard smiled to me =)', '2021-11-23T11:38:00', null, 'New', 2, null, false),
       ('Buy food', '2021-11-19T09:17:00', '2021-11-23T12:45:00', 'Important', 4, null, false),
       ('Buy a new lanyard for Neo', '2021-11-23T08:31:00', null, 'In spare time', null, 3, false),
       ('Make a new photo', '2021-11-23T08:31:00', null, 'Important', null, 3, false),
       ('Buy food', '2021-11-23T10:16:00', null, 'Important', null, 4, false),
       ('Call dr. Henry!', '2021-11-23T05:08:00', null, 'Important', null, 10, false);

-- Vaccine
INSERT INTO public.vaccine(date_time, name, is_deleted)
VALUES ('2021-01-20T13:30:00', 'Vaccine1', false),
       ('2021-07-20T09:00:00', 'Vaccine2', false),
       ('2021-06-15T10:00:00', 'Vaccine for cat', false),
       ('2021-04-25T15:45:00', 'Vaccine for dog', false);

-- Pet_vaccines
INSERT INTO public.pet_vaccines(pet_id, vaccines_id)
VALUES (1, 1),
       (1, 2),
       (8, 3),
       (3, 4);
