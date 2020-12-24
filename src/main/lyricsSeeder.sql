USE master_of_music_db;

INSERT INTO games (id, name)
VALUES (1, 'Finish the Lyrics');

DROP TABLE IF EXISTS songs;
INSERT INTO songs (id, title, artist, lyrics)
VALUES (1, 'New York', 'Alica Keys', 'There''s nothing you can''t do, now you''re in New York! These streets will make you feel brand new'),
       (2, 'Total Eclipse Of The Heart Lyrics', 'Bonnie Tyler', 'Every now and then I get a little bit terrified. But then I see the look in your eyes'),
       (3, 'Shape of You Lyrics', 'Ed Sheeran', 'Come on now, follow my lead I may be crazy, don''t mind me'),
       (4, 'In My Feelings Lyrics', 'Drake', 'Gotta be real with it, yup Kiki, do you love me Are you riding, say you''ll never ever leave'),
       (5, 'Mercy', 'Brett Young', 'Why you hanging on so tight if this ain''t working Why you wanna stop this flame if it''s still burning Cause it''s still burning');

INSERT INTO songs (id, title, artist, lyrics)
VALUES (6, 'Teenage Dirtbag', 'Wheatus', 'Yeah, I''m just a teenage dirtbag, baby Listen to Iron Maiden, baby, with me, ooh'),
       (7, 'All Star', 'Smash Mouth', 'Hey now, you''re an all star Get your game on, go play Hey now, you''re a rock star Get the show on, get paid'),
       (8, 'Under Pressure', 'David Bowie & Freddy Mercury', 'Pressure pushing down on me Pressing down on you, no man ask for'),
       (9, 'Start Me Up', 'The Rolling Stones', 'You make a grown man cry Spread out the oil, the gasoline I walk smooth, ride in a mean, mean machine'),
       (10, 'Proud Mary', 'Creedence Clearwater Revival', 'Big wheel keep on turnin'' Proud Mary keep on burnin'' Rollin'', rollin'', rollin'' on the river'),
       (11, 'Should I Stay or Should I Go', 'The Clash', 'Should I stay or should I go now? If I go, there will be trouble And if I stay it will be double'),
       (12, 'Baba O''Riley', 'The Who', 'Don''t cry, don''t raise your eye It''s only teenage wasteland'),
       (13, 'You Really Got Me', 'The Kinks', 'Yeah, you really got me now You got me so I can''t sleep at night'),
       (14, 'Summer of ''69', 'Bryan Adams', 'I got my first real six string Bought it at the five and dime Played it til my fingers bled Was the summer of ''69'),
       (15, 'Edge of Seventeen', 'Stevie Nicks', 'Just like the white winged dove Sings a song Sounds like she''s singin'''),
       (16, 'Brown Eyed Girl', 'Van Morrison', 'In the misty morning fog with Our hearts a thumpin'' and you My brown-eyed girl'),
       (17, '(Don''t Fear) The Reaper', 'Blue Öyster Cult', 'All our times have come Here, but now they''re gone Seasons don''t fear the reaper');




# INSERT INTO answers (id, answer, is_correct, question_id)
# VALUES (1, 'Big lights will inspire you', true, 1),
#        (2, 'So just beat it', false, 1),
#        (3, 'But so will a new hairdo', false, 1),
#        (4, 'And a good dog is Marmaduke', false, 1)