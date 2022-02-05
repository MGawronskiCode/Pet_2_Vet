-- Data for the initial DB population

-- AppUser
INSERT INTO public.app_user(name, sex, login, password) VALUES ('Alex', 2, 'alex@gmail.com', 'alexpassword');
INSERT INTO public.app_user(name, sex, login, password) VALUES ('Samantha', 3, 'samantha@gmail.com', 'samanthapassword');
INSERT INTO public.app_user(name, sex, login, password) VALUES ('Brad', 2, 'brad@gmail.com', 'bradpassword');
INSERT INTO public.app_user(name, sex, login, password) VALUES ('Caroline', 3, 'caroline@gmail.com', 'carolinepassword');
INSERT INTO public.app_user(name, sex, login, password) VALUES ('Lara', 3, 'lara@gmail.com', 'larapassword');

-- Specie
INSERT INTO public.specie(name) VALUES ('Mammal');
INSERT INTO public.specie(name) VALUES ('Bird');
INSERT INTO public.specie(name) VALUES ('Rodent');
INSERT INTO public.specie(name) VALUES ('Reptile');

-- Pet
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Wizard', 0, '2021-01-05', 1); --     Dog #1
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Kitana', 1, '2019-02-05', 2); --   Cat #2
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Neo', 0, '2021-05-11', 1); --        Dog #3
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Audrey', 1, '2021-06-26', 3); --  Bird #4
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Rudolph', 0, '2020-12-15', 4); -- Rodent #5
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Grinch', 0, '2020-09-09', 1); -- Reptile #6
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Dizzy', 1, '2021-02-13', 4); -- Rodent #7
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Simba', 0, '2018-05-03', 2); --      Cat #8
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Tiara', 1, '2020-10-29', 3); --   Bird #9
INSERT INTO public.pet(name, sex, birthday, specie_id) VALUES ('Merlin', 0, '2015-04-30', 1); --     Dog #10

-- Users pets
INSERT INTO public.users_pets(user_id, pet_id) VALUES (1, 2);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (1, 7);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (2, 1);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (2, 3);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (1, 4);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (3, 8);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (4, 6);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (4, 10);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (5, 9);
INSERT INTO public.users_pets(user_id, pet_id) VALUES (5, 5);

-- Meal
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T07:00:00', '2021-11-23T07:30:00', 'Dry food', 300, 1);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T07:20:00', '2021-11-23T07:20:00', 'Wet food', 150, 2);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T08:00:00', '2021-11-23T08:05:00', 'Dry food', 200, 3);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T08:00:00', '2021-11-23T07:50:00', 'Corns', 10, 4);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T12:00:00', '2021-11-23T13:00:00', 'Vegetables', 150, 5);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T09:00:00', '2021-11-23T08:55:00', 'Crickets', 35, 6);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T06:30:00', '2021-11-23T06:10:00', 'Vegetables', 200, 7);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T07:10:00', '2021-11-23T07:30:00', 'Dry food', 150, 8);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T07:45:00', '2021-11-23T07:30:00', 'Corns', 10, 9);
INSERT INTO public.meal(actual_feeding_time, expected_feeding_time, food, food_amount, pet_id) VALUES ('2021-11-23T09:05:00', '2021-11-23T09:00:00', 'Wet food', 150, 10);

-- Note
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Wizard smiled to me =)', '2021-11-23T11:38:00', null, 'New', 2, null);
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Buy food', '2021-11-19T09:17:00', '2021-11-23T12:45:00', 'Important', 4, null);
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Buy a new lanyard for Neo', '2021-11-23T08:31:00', null, 'In spare time', null, 3);
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Make a new photo', '2021-11-23T08:31:00', null, 'Important', null, 3);
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Buy food', '2021-11-23T10:16:00', null, 'Important', null, 4);
INSERT INTO public.note(content, created, modified, title, user_id, pet_id) VALUES ('Call dr. Henry!', '2021-11-23T05:08:00', null, 'Important', null, 10);

-- Vaccine
INSERT INTO public.vaccine(date_time, name) VALUES ('2021-01-20T13:30:00', 'Vaccine1');
INSERT INTO public.vaccine(date_time, name) VALUES ('2021-07-20T09:00:00', 'Vaccine2');
INSERT INTO public.vaccine(date_time, name) VALUES ('2021-06-15T10:00:00', 'Vaccine for cat');
INSERT INTO public.vaccine(date_time, name) VALUES ('2021-04-25T15:45:00', 'Vaccine for dog');

-- Pet_vaccines
INSERT INTO public.pet_vaccines(pet_id, vaccines_id) VALUES (1, 1);
INSERT INTO public.pet_vaccines(pet_id, vaccines_id) VALUES (1, 2);
INSERT INTO public.pet_vaccines(pet_id, vaccines_id) VALUES (8, 3);
INSERT INTO public.pet_vaccines(pet_id, vaccines_id) VALUES (3, 4);

-- Visits
INSERT INTO public.visit(pet_id, date_time, description, place, purpose, recommendation) VALUES (3, '2021-11-23T10:16:00', 'A visit to the doctor regarding pain in the abdomen', 'Struga 25 st.', 'Drug recipe', 'Porridge water diet');