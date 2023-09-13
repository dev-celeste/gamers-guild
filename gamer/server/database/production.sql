drop database if exists gamer;
create database gamer;
use gamer;

create table app_user (
	app_user_id int primary key auto_increment,
	username varchar(255) not null,
    `password` varchar(2048) not null,
	enabled boolean not null default true
);

create table app_role (
	app_role_id int primary key auto_increment,
	role_name varchar(10) not null
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint fk_app_user_role_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	constraint fk_app_user_role_app_role_id
		foreign key (app_role_id)
		references app_role(app_role_id)
);

create table gamer (
	gamer_id int primary key auto_increment,
	app_user_id int not null,
    gender_type varchar(20) not null,
    gamer_tag varchar(20) not null,
    birth_date date not null,
    bio varchar(500) not null,
	constraint fk_gamer_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)
);

create table `match` (
    date_match date not null,
    gamer_receiver_id int not null,
    gamer_sender_id int not null,
    constraint fk_match_gamer_receiver_id
		foreign key (gamer_receiver_id)
		references gamer(gamer_id),
	constraint fk_match_gamer_sender_id
		foreign key (gamer_sender_id)
		references gamer(gamer_id)
);

create table game (
    game_id int primary key auto_increment,
    game_title varchar(255) not null
);

create table gamer_game (
    gamer_id int not null,
    game_id int not null,
    constraint fk_gamer_game_gamer_id
		foreign key (gamer_id)
		references gamer(gamer_id),
	constraint fk_gamer_game_game_id
		foreign key (game_id)
		references game(game_id)
);

create table posting (
    posting_id int primary key auto_increment,
    gamer_id int not null,
    game_id int not null,
    header varchar(255) not null,
    `description` varchar(2000) not null,
    date_posted date not null,
    constraint fk_posting_gamer_id
		foreign key (gamer_id)
		references gamer(gamer_id),
	constraint fk_posting_game_id
		foreign key (game_id)
		references game(game_id)
);

-- insert into app_user (app_user_id, email, `password`, gamer_tag, birth_date, bio, enabled, gender_type)
-- 		values
-- 		(1, 'maria@alcantara.com', 'abc123', 'gt_maria', '1995-08-18', 'Hello, I love playing fps and rpg games!', true, 'FEMALE'),
--         (2, 'jay@wu.com', 'abc123', 'gt_jay', '1997-09-19', 'Hello, I am a game designer that loves playing Animal Crossing New Horizons!', true, 'NONBINARY'),
--         (3, 'jackie@luu.com', 'abc123', 'gt_jackie', '1999-07-17', 'Hello, I love playing league of legends!', true, 'MALE'),
--         (4, 'brit@hemming.com', 'abc123', 'gt_brit', '1993-06-16', 'Hello, I love playing puzzle games!', true, 'FEMALE'),
--         (5, 'scott@certain.com', 'abc123', 'gt_scott', '1991-05-15', 'Hello, I love playing adventure games!', false, 'MALE');

insert into app_user (app_user_id, username, `password`, enabled)
    values
    (1, 'maria@alcantara.com', '_maria1234!!', true),
	(2, 'jay@wu.com', '_jay1234!!', true),
	(3, 'jackie@luu.com', '_jackie1234!!', true),
	(4, 'brit@hemming.com', '_brit1234!!', true),
	(5, 'scott@certain.com', '_scott1234!!', true),
	(6, 'nilam_bellerose@gmail.com', '_taylor1234!!', true),
	(7, 'enes_abbott@yahoo.com', '_enes1234!!', true),
    (8, 'mohini_kurz@msn.com', '_mohini1234!!', false),
    (9, 'muireann_herschel@hotmail.com', '_muireann1234!!', true),
    (10, 'maksat_putin@yahoo.com', '_maksat1234!!', true),
    (11, 'nikita_slovacek@aim.com', '_nikita1234!!', true),
    (12, 'dragomir_kelly@xfinity.com', '_dragomir1234!!', false),
    (13, 'gamer4lyfe@outlook.com', '_asa_montes_1234!!', true),
    (14, 'threesixtynoscope@gmail.com', '_ingram_schultz_1234!!', true),
    (15, 'doritosmountaindue@yahoo.com', '_radka_napoli_1234!!', true),
    (16, 'ilovemydogsparky@outlook.com', '_albina_basik_1234!!', true),
    (17, 'clemensc@gmail.com', '_collins_1234!!', true),
    (18, 'pewdiepiefan@aim.com', '_river_shimada_1234!!', true),
    (19, 'overwatch4ever@hotmail.com', '_ada_holt_1234!!', true),
    (20, 'ashbyrneofficial@gmail.com', '_byrne_1234!!', true);
        
insert into app_role (app_role_id, role_name)
	values
	(1, 'ADMIN'),
	(2, 'USER');
        
insert into app_user_role (app_user_id, app_role_id)
	values
	(1, 2),
	(2, 2),
	(3, 2),
	(4, 1),
	(5, 2),
    (6, 1),
	(7, 1),
    (8, 2),
	(9, 2),
	(10, 2),
	(11, 1),
	(12, 2),
    (13, 2),
	(14, 2),
    (15, 2),
	(16, 2),
    (17, 2),
	(18, 2),
    (19, 2),
	(20, 2);
    
insert into gamer (gamer_id, app_user_id, gender_type, gamer_tag, birth_date, bio) 
		values 
        (1, 1, 'FEMALE', 'gt_maria', '1995-08-18', 'Hey there, I am maria, a passionate gamer with a thirst for virtual adventures and pixelated challenges! Since I first held a controller in my hands, I knew I had found my true calling in the world of gaming. Born and raised in the digital realm, I have embarked on countless quests, battled formidable foes, and explored vast, immersive worlds. From the nostalgic classics that sparked my love for gaming to the latest cutting-edge releases, I have left no virtual stone unturned.'),
        (2, 2, 'NONBINARY', 'gt_jay', '1997-09-19', 'Hey there, fellow gamers! I am jay, a devoted gamer who has traversed countless digital realms and conquered epic quests. Armed with a controller in hand and a passion for virtual adventures, I dive headfirst into immersive gaming experiences that transport me to worlds beyond imagination.'),
        (3, 3, 'MALE', 'gt_jackie', '1999-07-17', 'I thrive in the heat of intense multiplayer battles, where teamwork and quick reflexes are the keys to victory. Whether I am leading a squad or going solo, I bring a strategic mindset to every challenge, analyzing the battlefield and making split-second decisions that turn the tide of the game.'),
        (4, 4, 'FEMALE', 'gt_brit', '1993-06-16', 'I thrive in the heat of intense multiplayer battles, where teamwork and quick reflexes are the keys to victory. Whether I am leading a squad or going solo, I bring a strategic mindset to every challenge, analyzing the battlefield and making split-second decisions that turn the tide of the game.'),
        (5, 5, 'MALE', 'gt_scott', '1991-05-15', 'As a gamer, I have honed my reflexes, strategic thinking, and adaptability. I thrive in the fast-paced chaos of competitive multiplayer battles, always striving to reach the top of the leaderboards. Yet, I also find solace in the captivating narratives of single-player campaigns, losing myself in epic tales that leave an indelible mark on my heart.'),
        (6, 6, 'OTHER', 'Agilitee', '1990-04-14', 'Beyond the screen, I am an advocate for the positive impact of gaming. I believe in its power to inspire, educate, and entertain. Through streaming and content creation, I aim to spread joy, laughter, and knowledge to a wider audience, celebrating the incredible artistry and innovation that resides within the gaming industry.'),
        (7, 7, 'PREFER_NOT_TO_SAY', 'MobyDuck', '1991-03-13', 'Hello! I am a passionate gamer who lives and breathes the world of virtual adventures. Since I was a kid, gaming has been my ultimate escape, allowing me to explore uncharted realms and embark on thrilling quests from the comfort of my gaming setup. With a controller in hand and a headset on, I dive headfirst into a variety of gaming genres, immersing myself in captivating narratives, heart-pounding action, and mind-bending puzzles.'),
        (8, 8, 'FEMALE', 'FoxxxyS', '1995-02-12', 'Hi! I am a passionate gamer who lives and breathes the world of virtual adventures. Since I was a kid, gaming has been my ultimate escape, allowing me to explore uncharted realms and embark on thrilling quests from the comfort of my gaming setup. With a controller in hand and a headset on, I dive headfirst into a variety of gaming genres, immersing myself in captivating narratives, heart-pounding action, and mind-bending puzzles.'),
        (9, 9, 'NONBINARY', 'Maceface', '1997-01-11', 'Gaming is not just a hobby for me; it is a way of life. I have spent countless hours honing my skills, perfecting my strategies, and building a community of fellow gamers who share the same passion. Together, we embark on epic quests, engage in friendly competition, and support one another in our virtual endeavors.'),
        (10, 10, 'MALE', 'Cleopatrick', '1999-12-02', 'Join me on this epic gaming journey, where we can push the boundaries of virtual reality, forge unforgettable memories, and celebrate the artistry and innovation of game development. Together, let us level up, conquer new challenges, and make our mark in the ever-evolving world of gaming! Remember, feel free to customize and add personal details to make it truly reflect your own gaming experiences and preference.'),
        (11, 11, 'FEMALE', 'Pledger', '1993-11-01', 'Hiiiii! I am your virtual guide through the realms of adventure and excitement. I have been immersed in the world of gaming since I was old enough to hold a controller, and my passion for this incredible form of entertainment has only grown stronger over the years. As a seasoned gamer, I have explored vast virtual worlds, battled legendary foes, and embarked on epic quests.'),
        (12, 12, 'MALE', 'Pearoo', '1991-10-30', 'It is not just about the games themselves; it is the community that makes the gaming world truly special. I have met incredible individuals from all walks of life who share the same love for gaming. Together, we have formed friendships, laughed, strategized, and celebrated our victories. Gaming has not only provided me with endless entertainment but also a sense of belonging.'),
        (13, 13, 'OTHER', 'Slapdash', '1990-09-09', 'it is not just about the victories. I believe in the power of gaming as a storytelling medium. The captivating narratives, complex characters, and thought-provoking themes have inspired me and opened my mind to new perspectives. Gaming has the incredible ability to transport us to fantastical realms while also reflecting the beauty and challenges of our own world.'),
        (14, 14, 'PREFER_NOT_TO_SAY', '2Hot2BCool', '1991-08-18', 'Helloo~! I am a dedicated gamer with an insatiable passion for all things gaming. From the moment I held a controller in my hands, I knew I had discovered my true calling. Gaming has become an integral part of who I am, shaping my skills, my mindset, and my identity. I thrive in the digital realm, where I can unleash my imagination and embark on extraordinary adventures'),
        (15, 15, 'FEMALE', 'Powerbutton', '1995-08-19', 'As a gamer, I constantly strive for excellence. I am constantly seeking to sharpen my reflexes, improve my strategy, and expand my gaming repertoire. I embrace challenges and see every defeat as an opportunity to learn and grow. With unwavering determination, I push my limits, aiming to reach the pinnacle of my gaming abilities. Beyond the confines of the game, I find immense joy in sharing my gaming experiences with others.'),
        (16, 16, 'NONBINARY', 'Splendido76', '1997-09-18', 'Hey there, fellow gamers! I am jay, a devoted gamer who has traversed countless digital realms and conquered epic quests. Armed with a controller in hand and a passion for virtual adventures, I dive headfirst into immersive gaming experiences that transport me to worlds beyond imagination. From the early days of pixelated classics to the cutting-edge graphics of today, I have witnessed the evolution of gaming firsthand.'),
        (17, 17, 'MALE', 'Gigggatic', '1999-07-16', 'Gaming is not just about virtual achievements for me; it is a platform for creativity and self-expression. I am fascinated by the intricate storytelling, stunning visuals, and captivating soundtracks that make each game a unique work of art. I revel in the immersive narratives that transport me to fantastical realms, allowing me to experience emotions and adventures beyond the ordinary.'),
        (18, 18, 'FEMALE', 'NumberCrunch', '1993-06-15', 'Oh, hello there! I am a relentless adventurer in the vast realm of gaming. From the moment I picked up a controller, I knew I had discovered my true calling. Gaming has become an integral part of my life, allowing me to explore countless virtual worlds and forge unforgettable memories. I thrive on the adrenaline rush of intense battles and the thrill of overcoming seemingly insurmountable challenges.'),
        (19, 19, 'MALE', 'LostTheGame', '1991-05-14', 'I have honed my skills across various platforms and genres, from the lightning-fast precision of first-person shooters to the strategic depths of real-time strategy games. Each gaming experience has contributed to my growth as a player, constantly pushing me to adapt, learn, and evolve. Beyond the thrill of competition, gaming has fostered a sense of camaraderie within me. I relish in the opportunity to connect with fellow gamers, exchanging tips, stories, and laughter.'),
        (20, 20, 'OTHER', 'Atlantean', '1990-04-13', 'Gaming is not just a hobby; it is a journey of self-discovery. It has taught me valuable lessons about perseverance, problem-solving, and teamwork. It has sparked my creativity and inspired me to explore new horizons. Gaming has become a catalyst for personal growth, pushing me to reach beyond my limits and embrace the unknown. Join me as we embark on thrilling quests, unravel captivating narratives, and create unforgettable memories in the ever-expanding world of gaming.');
        
insert into `match` (gamer_receiver_id, gamer_sender_id, date_match)
		values
		(2, 6, '2023-06-27'),
        (3, 7, '2023-06-26'),
        (8, 2, '2023-06-25'),
        (3, 10, '2023-06-24'),
        (13, 17, '2023-06-23'),
        (13, 7, '2023-06-22'),
        (3, 7, '2023-06-21'),
        (15, 5, '2023-07-01'),
        (20, 4, '2023-07-02'),
        (6, 11, '2023-07-03');
	
insert into game (game_id, game_title)
		values
		(1, 'Yakuza 0'),
        (2, 'League of Legends'),
        (3, 'Ghost Trick: Phantom Detective'),
        (4, 'Animal Crossing: New Horizons'),
        (5, 'Sims 4'),
        (6, 'Pokemon Super Mystery Dungeon'),
        (7, 'Fortnite'),
        (8, 'Red Dead Redemption 2'),
        (9, 'The Last of Us'),
        (10, 'Grand Theft Auto V'),
        (11, 'Minecraft'),
        (12, 'GoldenEye 007'),
        (13, 'BioShock'),
        (14, 'God of War'),
        (15, 'The Legend of Zelda: Breath of the Wild'),
        (16, 'Halo 2'),
        (17, 'Half-Life 2'),
        (18, 'Final Fantasy VII'),
        (19, 'Street Fighter II'),
        (20, 'World of Warcraft'),
        (21, 'Batman: Arkham City'),
        (22, 'Destiny 2'),
        (23, 'Call of Duty 4'),
        (24, 'Tony Hawks Pro Skater 2');
	
insert into gamer_game (gamer_id, game_id)
		values
		(1, 5),
        (2, 1),
        (2, 3),
        (3, 2),
        (3, 1),
        (4, 5),
        (5, 2),
        (6, 5),
        (7, 5),
        (7, 6),
        (7, 7),
        (8, 8),
        (8, 9),
        (9, 10),
        (9, 11),
        (10, 12),
        (11, 13),
        (11, 14),
        (11, 15),
        (12, 16),
        (12, 17),
        (13, 18),
        (14, 19),
        (14, 20),
        (14, 21),
        (15, 22),
        (15, 23),
        (16, 21),
        (16, 1),
        (16, 2),
        (17, 3),
        (17, 4),
        (18, 5),
        (19, 6),
        (19, 7),
        (19, 8),
        (20, 9),
        (20, 10),
        (20, 11);
        
insert into posting (posting_id, gamer_id, game_id, header, `description`, date_posted)
		values
		(1, 1, 5, 'Does anyone have any good mods?', 'Hey just wondering if anyone has and links to some good mods, thanks.', '2023-06-27'),
        (2, 1, 2, 'Looking for a carry', 'Just made a smurf and I need someone to hard carry for a few levels pleaseee', '2023-06-26'),
        (3, 2, 1, 'Found a weird bug', 'Has anyone else noticed a small bug when opening the door on level 4?', '2023-06-25'),
        (4, 2, 3, 'I need help finding a quest item', 'I reread the quest prompt and I am not understanding where this gun is located...', '2023-06-24'),
        (5, 3, 2, 'Does anyone need a carry?', 'I am bored and have time tonight to carry a n00b out there, let me know', '2023-06-23'),
        (6, 3, 2, 'The new character looks amazing', 'I am once again amazed at the new character and lore added to this game!!!', '2023-06-23'),
        (8, 4, 4, 'I have no idea what I am supposed to do...', 'This game is so confusing, why are these animals trashing my garden HELP!!', '2023-06-22'),
        (9, 5, 5, 'I made some sick living room mods', 'If anyone wants to make their living room look amazing DM me I have a link', '2023-06-21'),
        (10, 6, 5, 'What do I do if I die?', 'My toon just died trying to put a house fire out, not sure what to do?', '2023-06-20'),
        (11, 6, 5, 'Looking for friends to play with', 'I have been out of the gaming scene for a while and could use some buddies to play with!', '2023-06-19'),
		(12, 7, 6, 'Exploring the Wonders of Pokemon Super Mystery Dungeon!', 'Hey everyone! I wanted to take a moment to share my thoughts and experiences with the incredible game, Pokemon Super Mystery Dungeon. If you are a fan of the Pokemon franchise or love dungeon-crawling adventures, this game is an absolute gem. First of all, let me just say how impressed I am with the games captivating storyline. The plot takes you on an unforgettable journey as you awaken one day to find yourself transformed into a Pokemon!', '2023-05-27'),
        (13, 8, 7, 'Fortnite Is The Ultimate Battle Royale Experience', 'Hey there, Fortnite enthusiasts! I wanted to take a moment to express my excitement for the adrenaline-fueled phenomenon that is Fortnite. This game has taken the gaming world by storm, and its not hard to see why. Fortnite offers a unique blend of fast-paced action, strategic gameplay, and a vibrant, ever-evolving world. The Battle Royale mode, in particular, is an absolute thrill.', '2023-05-26'),
        (14, 9, 8, 'A question for fellow red dead lovers', 'But heres a question for you: What is your most memorable encounter or moment in Red Dead Redemption 2? Was it a heart-pounding chase, a heartfelt interaction with a companion, or a breathtaking view that took your breath away? Share your tales from the wild frontier and lets relive those cherished moments together. So, fellow outlaws and gunslingers, Im eager to hear about the experiences that made Red Dead Redemption 2 stand out for you.', '2023-06-26'),
        (15, 10, 9, 'Highkey simping for Joel', 'How did the evolving dynamic between Joel and Ellie impact your experience playing The Last of Us? Did it resonate with you on a personal level, or did it leave you pondering the complexities of human connections in dire circumstances? Feel free to share your insights, favorite moments, or any other thoughts related to this extraordinary game. Lets dive into the post-pandemic world of The Last of Us and explore the depths of its storytelling together.', '2023-07-01'),
        (16, 11, 9, 'You gottaaaa play this game', 'Hello, survivors! Lets gather around and discuss the critically acclaimed game, The Last of Us. This post-apocalyptic masterpiece from Naughty Dog has captivated players worldwide with its compelling narrative and emotional depth. As we navigate the desolate world overrun by infected creatures, Im curious to hear your thoughts on one particular aspect: the complex relationship between Joel and Ellie. Their journey together is filled with hardships, danger, and unexpected bonds. ', '2023-07-01'),
        (17, 12, 10, 'Glitch in Grand Theft Auto V', 'I recently stumbled upon an intriguing bug while exploring the vast and dynamic world of Grand Theft Auto V, and I wanted to share my experience with all of you. As we know, no game is perfect, and even the most meticulously crafted titles can occasionally have some unexpected surprises.During one of my escapades through Los Santos, I encountered a peculiar glitch that caused my character to momentarily freeze in mid-air while performing a high-speed jump', '2023-07-02'),
        (18, 13, 11, 'Seeking Minecraft Masterminds', 'Greetings, crafters and builders! I find myself in need of some expert advice in the enchanting world of Minecraft, and Im turning to the incredible community for guidance. Whether youre a seasoned player or a redstone genius, I would greatly appreciate any tips and tricks you can share to enhance my Minecraft experience. Heres my burning question: What are your top recommendations for establishing a solid base and gathering essential resources efficiently', '2023-06-30'),
        (19, 14, 11, 'Seeking Companions for an Epic Journey', 'Im on the lookout for like-minded individuals to embark on an unforgettable Minecraft adventure together. Exploring this vast virtual world is undeniably thrilling, but the experience becomes even more remarkable when shared with others. So, I invite you to join me on this journey! If youre passionate about mining, crafting, building, and surviving in the diverse landscapes of Minecraft, lets team up and create something extraordinary.', '2023-06-29'),
        (20, 15, 14, 'Kratos Mighty Moves in God of War', 'Which of Kratos moves in God of War have left you in awe or proved to be particularly effective in battle? From his iconic Leviathan Axe strikes to his brutal bare-handed attacks, every move in Kratos arsenal possesses its own unique power and flair. Do you have a favorite combo that unleashes unrivaled devastation upon your enemies? Or perhaps theres a move youve recently discovered that has turned the tide of battle in your favor?', '2023-06-28'),
        (21, 16, 15, 'Seeking Wisdom', 'Greetings, brave adventurers of Hyrule! I find myself in need of some sage advice in my quest through the vast and enchanting world of The Legend of Zelda: Breath of the Wild. As I traverse the sprawling landscapes, encounter formidable foes, and unearth the secrets of this captivating game, I turn to the esteemed community for guidance. Heres my plea: What are your top tips and tricks for mastering the challenges that await in Breath of the Wild', '2023-06-19');