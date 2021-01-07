USE master_of_music_db;


INSERT INTO questions (id, question, game_id)
VALUES  (1,'What scale does the Phrygian mode belong to?',2),
        (2, 'What is the most common time signature?', 2),
        (3, 'What is the third mode of the G Major Scale?',2),
        (4,'Assuming a guitar is in standard tuning, what note is on the 5th fret of the low E string?',2),
        (5,'How many notes are in a scale?',2),
        (6,'What notes are in the E minor scale?',2);

INSERT INTO answers (answer, is_correct, question_id)
VALUES ('Harmonic Minor',true,1),
('Diminished',false,1),
('Pentatonic',false,1),
('Major',false,1),
('4/4',true,2),
('7/8',false,2),
('5/4',false,2),
('6/4',false,2),
('Phrygian',true,3),
('Dorian',false,3),
('Aeolian',false,3),
('Mixolydian',false,3),
('B',false,4),
('F#',false,4),
('B flat',false,4),
('A',true,4),
('4',false,5),
('5',false,5),
('8 not including the octave of the root note',false,5),
('7',true,5),
('E,E#,F,D,A,B,Octave',false,6),
('E,F,A♭,A,B,B#,E#',false,6),
('E,F#,G,A,B,C,D',true,6),
('G♭,G,A,B,B#,C#,Octave',false,6);