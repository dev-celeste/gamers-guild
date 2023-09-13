-- use gamer;

-- select * from app_user;

use gamer_test;
call set_known_good_state();

select * from game;

select 
	p.posting_id, 
    gr.gamer_tag, 
    g.game_title, 
    p.header, 
    p.`description`, 
    p.date_posted
from posting p
inner join gamer gr on gr.gamer_id = p.gamer_id
inner join gamer_game grg on gr.gamer_id = grg.gamer_id
inner join game g on grg.game_id = g.game_id
order by p.date_posted;

select
	posting_id, 
    gamer_id,
    game_id,
    header,
	`description`,
    date_posted
from posting;

select p.posting_id, p.gamer_id, p.game_id, p.header, p.`description`, p.date_posted
from posting p
inner join gamer gr on p.gamer_id = gr.gamer_id
inner join app_user u on u.app_user_id = gr.app_user_id
where u.username = 'maria@alcantara.com';

select p.posting_id, p.gamer_id, p.game_id, p.header, p.`description`, p.date_posted
from posting p
inner join game g on p.game_id = g.game_id
where g.game_title = 'Sims 4';

select posting_id, gamer_id, game_id, header, `description`, date_posted
from posting 
where date_posted = '2023-06-27';

select 
	posting_id, 
    gamer_id, 
    game_id, 
    header, 
    `description`,
	date_posted
from posting;

select 
	count(p.game_id)
from posting p
left outer join game g on g.game_id = p.game_id  
where p.game_id = 2;

select 
	count(grg.game_id)
from gamer_game grg
left outer join game g on g.game_id = grg.game_id   
where grg.game_id = 2;

select 
	count(p.game_id)
from game g
left outer join posting p on g.game_id = p.game_id
where g.game_id = 2;

select match_id, gamer_1, gamer_2, date_match
from `match`
where gamer_1 = 2
order by date_match desc;

select *
from `match`;

select 
app_user_id, 
username, 
`password`, 
enabled 
from app_user 
where username = 'maria@alcantara.com';

-- update gamer 
-- set gender_type = ?, 
-- gamer_tag = ?, 
-- birth_date = ?, bio = ? where gamer_id = ?;

select *
from app_user;