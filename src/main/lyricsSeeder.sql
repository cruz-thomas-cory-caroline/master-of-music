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


#Rock
INSERT INTO songs (id, title, artist, lyrics)
VALUES (6, 'Teenage Dirtbag', 'Wheatus', 'Yeah, I''m just a teenage dirtbag, baby Listen to Iron Maiden'),
       (7, 'All Star', 'Smash Mouth', 'Hey now, you''re an all star Get your game on, go play'),
       (8, 'Under Pressure', 'David Bowie & Freddy Mercury', 'Pressure pushing down on me Pressing down on you, no man ask for'),
       (9, 'Start Me Up', 'The Rolling Stones', 'You make a grown man cry Spread out the oil, the gasoline I walk smooth'),
       (10, 'Proud Mary', 'Creedence Clearwater Revival', 'Big wheel keep on turnin'' Proud Mary keep on burnin'' Rollin'', rollin'', rollin'' on the river'),
       (11, 'Should I Stay or Should I Go', 'The Clash', 'Should I stay or should I go now? If I go, there will be trouble'),
       (12, 'Baba O''Riley', 'The Who', 'Don''t cry, don''t raise your eye It''s only teenage wasteland'),
       (13, 'You Really Got Me', 'The Kinks', 'Yeah, you really got me now You got me so I can''t sleep at night'),
       (14, 'Summer of ''69', 'Bryan Adams', 'I got my first real six string Bought it at the five and dime'),
       (15, 'Edge of Seventeen', 'Stevie Nicks', 'Just like the white winged dove Sings a song Sounds like she''s singin'''),
       (16, 'Brown Eyed Girl', 'Van Morrison', 'In the misty morning fog with Our hearts a thumpin'' and you My brown-eyed girl'),
       (17, '(Don''t Fear) The Reaper', 'Blue Öyster Cult', 'All our times have come Here, but now they''re gone Seasons don''t fear the reaper');

# Pop
INSERT INTO songs (id, title, artist, lyrics)
VALUES (18, 'Don''t Stop Believin''', 'Journey', 'Just a small-town girl Livin'' in a lonely world'),
       (19, 'Wannabe', 'Spice Girls', 'If you wanna be my lover, you gotta get with my friends'),
       (20, 'Bohemian Rhapsody', 'Queen', 'Is this the real life? Is this just fantasy? Caught in a landslide, no escape from reality'),
       (21, 'All I Want for Christmas Is You ', 'Mariah Carey', 'I don''t want a lot for Christmas There is just one thing I need'),
       (22, 'September', 'Earth, Wind, and Fire', 'Do you remember the 21st night of September? Love was changin'' the minds of pretenders'),
       (23, 'I Want It That Way', 'Backstreet Boys', 'You are my fire The one desire Believe when I say I want it that way'),
       (24, 'Sweet Caroline', 'Neil Diamond', 'Sweet Caroline Good times never seemed so good I''ve been inclined To believe they never would'),
       (25, 'Jessie''s Girl', 'Rick Springfield', 'I wish that I had Jessie''s girl Where can I find a woman like that?'),
       (26, 'Billie Jean', 'Michael Jackson', 'Billie Jean is not my lover She''s just a girl who claims that I am the one'),
       (27, 'Smells Like Teen Spirit', 'Nirvana', 'With the lights out, it''s less dangerous Here we are now, entertain us');


INSERT INTO songs (id, title, artist, lyrics, game_id)
VALUES (1, 'New York', 'Alica Keys',
        'There''s nothing you can''t do, now you''re in New York! These streets will make you feel brand new', 1),
       (2, 'Total Eclipse Of The Heart Lyrics', 'Bonnie Tyler', 'Every now and then I get a little bit terrified
But then I see the look in your eyes', 1),
       (3, 'Shape of You Lyrics', 'Ed Sheeran', 'Come on now, follow my lead
I may be crazy, don''t mind me', 1),
       (4, 'In My Feelings Lyrics', 'Drake', 'Gotta be real with it, yup Kiki, do you love me
Are you riding, say you''ll never ever leave', 1),
(5,'Mercy', 'Brett Young', 'Why you hanging on so tight if this ain''t working Why you wanna stop this flame if it''s still burning
Cause it''s still burning', 1);


DROP TABLE IF EXISTS lyric_answers;
INSERT INTO lyric_answers (is_correct, lyric_answer, song_id)
VALUES ( false, 'name', 1),
       ( true, 'new', 1),
       ( false, 'few', 1),
       ( false, 'neat', 1),
       (false, 'heart', 2),
       (false, 'brain', 2),
       (false, 'friend', 2),
       (true, 'eyes', 2),
       (true, 'me', 3),
       (false, 'eat', 3),
       (false, 'dirt', 3),
       (false, 'drink',3),
       (false, 'go', 4),
       (false, 'die', 4),
       (false, 'travel', 4),
       (true, 'leave', 4),
       (false, 'hot', 5),
       (false,'freezing', 5),
       (true, 'burning', 5),
       (false, 'turning', 5);
