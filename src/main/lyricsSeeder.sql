USE master_of_music_db;

INSERT INTO games (id, name)
VALUES (1, 'Finish the Lyrics');

DROP TABLE IF EXISTS songs;
INSERT INTO songs (id, title, artist, lyrics)
VALUES (1, 'New York', 'Alica Keys',
        'There''s nothing you can''t do, now you''re in New York! These streets will make you feel brand new'),
       (2, 'Total Eclipse Of The Heart Lyrics', 'Bonnie Tyler', 'Every now and then I get a little bit terrified
But then I see the look in your eyes'),
       (3, 'Shape of You Lyrics', 'Ed Sheeran', 'Come on now, follow my lead
I may be crazy, don''t mind me'),
       (4, 'In My Feelings Lyrics', 'Drake', 'Gotta be real with it, yup Kiki, do you love me
Are you riding, say you''ll never ever leave'),
(5,'Mercy', 'Brett Young', 'Why you hanging on so tight if this ain''t working Why you wanna stop this flame if it''s still burning
Cause it''s still burning');



# INSERT INTO answers (id, answer, is_correct, question_id)
# VALUES (1, 'Big lights will inspire you', true, 1),
#        (2, 'So just beat it', false, 1),
#        (3, 'But so will a new hairdo', false, 1),
#        (4, 'And a good dog is Marmaduke', false, 1)