USE master_of_music_db;

INSERT INTO games (id, name)
VALUES (1, 'Finish the Lyrics');

DROP TABLE IF EXISTS songs;
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
