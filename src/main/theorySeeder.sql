USE master_of_music_db;

INSERT INTO questions (id, question, game_id)VALUES
(1,'What scale does the Phrygian mode belong to?',2),
(2, 'What is the most common time signature?', 2),
(3, 'What is the third mode of the G Major Scale?',2),
(4,'Assuming a guitar is in standard tuning, what note is on the 5th fret of the low E string?',2),
(5,'Which of the following can be played over a b phrygian chord progression?',2),
(6,'What notes are in the E minor scale?',2);
INSERT INTO answers (id, answer, is_correct, question_id)
VALUES (1,'Harmonic Minor',true,1),
(2,'Diminished',false,1),
(3,'Pentatonic',false,1),
(4,'Major',false,1),
(5,'4/4',true,2),
(6,'7/8',false,2),
(7,'5/4',false,2),
(8,'6/4',false,2),
(9,'Phrygian',true,3),
(10,'Dorian',false,3),
(11,'Aeolian',false,3),
(12,'Mixolydian',false,3),
(13,'B',false,4),
(14,'F#',false,4),
(15,'B flat',false,4),
(16,'A',true,4),
(17,'E major scale',false,5),
(18,'E phrygian dominant',false,5),
(19,'E Hungarian Minor',true,5),
(20,'G Minor',false,5),
(21,'E,E#,F,D,A,B,Octave',false,6),
(22,'E,F,A♭,A,B,B#,E#',false,6),
(23,'E,F#,G,A,B,C,D',true,6),
(24,'G♭,G,A,B,B#,C#,Octave',false,6);
