USE master_of_music_db;

INSERT INTO games (id, name)
VALUES (1, 'Finish the Lyrics');

DROP TABLE IF EXISTS songs;

#Hip-Hop
INSERT INTO songs(id, title, artist, lyrics)
VALUES (4, 'In My Feelings Lyrics', 'Drake', 'Gotta be real with it, yup Kiki, do you love me
Are you riding'),
       (1, 'New York', 'Alica Keys',
        'There''s nothing you can''t do, now you''re in New York! These streets will make you feel brand new'),
       (28, 'Lose Yourself', 'Eminem', 'His palms are sweaty, knees weak, arms are heavy'),
       (29, 'American Boy', 'Estelle ft. Kanye West','Sneaker''s looking ''fresh to def'' I''m lovin'' those shell toes'),
       (30, 'Crazy', 'Gnarles Barkley', 'And I hope that you are having the time of your life'),
       (31, 'Lucid Dreams', 'Juice WRLD', 'And I cannot change you, so I must replace you, oh'),
       (32,'Wings', 'Mac Miller', 'Water my seeds ''til the flower just grow, yeah'),
       (33,'In Da Club (It''s Your Birthday)', '50 Cent','I''m fully focused man, my money on my mind
'),
       (34,'The Real Slim Shady', 'Eminem','''Cause I''m Slim Shady, yes I''m the real Shady');


#Classic Rock
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
       (27, 'Smells Like Teen Spirit', 'Nirvana', 'With the lights out, it''s less dangerous Here we are now, entertain us'),
       (3, 'Shape of You Lyrics', 'Ed Sheeran', 'Come on now, follow my lead
I may be crazy, don''t mind me'),
(2, 'Total Eclipse Of The Heart Lyrics', 'Bonnie Tyler', 'Every now and then I get a little bit terrified');



#Country
INSERT INTO songs (id, title, artist, lyrics)
VALUES(5,'Mercy', 'Brett Young', 'Why you hanging on so tight if this ain''t working ');
    (35,'Need You Now', 'Lady Antebellum', 'Picture-perfect memories scattered all around the floor'),
    (36,'My Wish', 'Rascal Flatts', 'Your dreams stay big, your worries stay small'),
    (37,'Friends In Low Places', 'Garth Brooks', 'Blame it all on my roots, I showed up in boots'),
    (38,'Jolene', 'Dolly Parton', 'Your beauty is beyond compare
With flaming locks of auburn hair'),
    (39,'On the Road Again', 'Willie Nelson', 'The life I love is making music with my friends'),
    (40,'You''re Still The One', 'Shania Twain', 'We mighta took the long way We knew we''d get there someday'),
    (41,'Hey, Good Lookin''', 'Hank Williams', 'So if you wanna have fun come along with me.'),
    (42,'All My Ex''s Live In Texas', 'George Strait', 'Texas is the place I''d really love to be'),
    (43,'Toes', 'Zac Brown Band', 'Not a worry in the world, a cold beer in my hand');


DROP TABLE IF EXISTS lyric_answers;
INSERT INTO lyric_answers (is_correct, lyric_answer, song_id)
VALUES ( false, 'Put your lighters in the air', 1),
       ( true, 'Big lights will inspire you', 1),
       ( false, 'And if you only hold me tight', 1),
       ( false, 'And I need you now tonight', 1),
       (false, 'That the best of all the years have gone by', 2),
       (false, 'And trust me I''ll give it a chance now', 2),
       (false, 'But then I see the look in your eyes', 2),
       (true, 'But then I see the look in your eyes', 2),
       (true, 'Say, boy, let''s not talk too much', 3),
       (false, 'Grab on my waist and put that body on me', 3),
       (false, 'I''m in love with your body', 3),
       (false, 'I''m in love with the shape of you',3),
       (false, ' say you''ll stay', 4),
       (false, 'say you''ll leave', 4),
       (false, 'say you''ll never travel', 4),
       (true, 'say you''ll never ever leave', 4),
       (false, 'Why you wanna stop this song', 5),
       (false,'Why you wanna stop this fire', 5),
       (true, 'Why you wanna stop this flame', 5),
       (false, 'Why you wanna stop this car', 5),
       (false,'Snap back to reality',28),
       (true, 'There''s vomit on his sweater already',28),
       (false,'This opportunity comes once in a lifetime',28),
       (false,'Back to the lab again, yo',28),
       (true,'Walking that walk, talk that slick talk',29),
       (false, 'Dress in all your fancy clothes',29),
       (false,'Dress in all your fancy clothes',29),
       (false,'I really want to come kick it with you',29),
       (true,'But think twice, that''s my only advice',30),
       (false, 'You really think you''re in control',30),
       (false,'It looked like fun',30),
       (false,'Who do you think you are',30),
       (true,'Easier said than done',31),
       (false, 'You found another one',31),
       (false,'But I am the better one',31),
       (false,'I won''t let you forget me',31),
       (true,'Love so much that my heart get broke',32),
       (false, 'Nobody holding my hand, no',32),
       (false,'That''s why I just keep to myself',32),
       (false,'These are my wings',32),
       (false,' I done came up, and I ain''t changed',33),
       (false, 'Nobody holding my hand, no',33),
       (false,'my show brought me the dough',33),
       (true,'Got a mill'' out the deal',33),
       (false,' And there''s a million of us just like me',34),
       (false, 'Who dress like me',34),
       (true,'All you other Slim Shadys are just imitating',34),
       (false,'please stand up',34),
       (true,'Reaching for the phone cause I can''t fight it anymore',35),
       (false, 'It''s a quarter after one',35),
       (false,'Can''t stop looking at the door',35),
       (false,'Said I wouldn''t call, but I lost all control',35),
       (true,'You never need to carry more than you can hold',36),
       (false, 'Yeah, this, is my wish',36),
       (false,'Oh, you find God''s grace, in every mistake',36),
       (false,'Who do you think you are',36),
       (false,'Well, I guess I was wrong',37),
       (true, 'And ruined your black tie affair',37),
       (false,'These boots are steel toed',37),
       (false,'Hey, I didn''t mean to cause a big scene',37),
       (false,'I''m begging of you please don''t take my man',38),
       (false,'He talks about you in his sleep',38),
       (true, 'With ivory skin and eyes of emerald green',38),
       (false,'And I cannot compete with you, Jolene',38),
       (false,'Goin'' places that I''ve never been',39),
       (true,'And I can''t wait to get on the road again',39),
       (false, 'We''re the best of friends',39),
       (false,'Goin'' places that I''ve been too many a time',39),
       (false,'Ain''t nothing better',40),
       (true,'They said, "I bet they''ll never make it."',40),
       (false, 'You''re still the one that I love',40),
       (false,'I''m so glad we made it',40),
       (false,'There''s soda pop and the dancin''s free',41),
       (false,'I''ll keep it ''til it''s covered with age',41),
       (true, 'Hey, good lookin'', Whatcha got cookin''?',41),
       (false,'How''s about cookin'' somethin'' up with me?',41),
       (false,'But I''m alive and well in Tennessee...',42),
       (false,'But I always come back to myself',42),
       (true, 'But all my ex''s live in Texas',42),
       (false,'I remember that old Frio River',42),
       (false,'Adios and vaya con Dios',43),
       (false,'And I''m not going back again',43),
       (false, 'Like this life I''m living in',43),
       (true,'Life is good today, life is good today',43);
