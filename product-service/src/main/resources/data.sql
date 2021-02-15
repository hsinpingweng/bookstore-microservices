insert into category (name) values('Fiction');
insert into category (name) values('Crime');
insert into category (name) values('Romance');
insert into category (name) values('Children');
insert into category (name) values('Teen');

insert into publisher (name) values('Atlantic');
insert into publisher (name) values('Vintage');
insert into publisher (name) values('Harper');
insert into publisher (name) values('Simon & Schuster Childrens UK');

insert into author (name, description) values('Sam Blake', 'best writer');
insert into author (name, description) values('Billy OCallaghan', 'best writer');
insert into author (name, description) values('Sophy Henn', 'best writer');


insert into book (name, description, isbn, price, published_date, author_id, category_id, publisher_id) values('Keep Your Eyes On Me', 'NUMBER ONE IRISH TIMES BESTSELLER', '9781786498380', 10.0, parseDateTime('02-01-2020','dd-MM-yyyy'), 1, 2, 1);
insert into book (name, description, isbn, price, published_date, author_id, category_id, publisher_id) values('The Dark Room', 'NUMBER ONE IRISH TIMES BESTSELLER', '9781786498601', 5.5, parseDateTime('07-01-2021','dd-MM-yyyy'), 1, 2, 1);
insert into book (name, description, isbn, price, published_date, author_id, category_id, publisher_id) values('Thw Boatman And Other Stories', 'The breathtaking short story collection from the Costa-shortlisted Irish writer Three gunshots on the Irish border define the course of a young mans life', '9781784708757', 11.0, parseDateTime('28-04-2020','dd-MM-yyyy'), 2, 1, 2);
insert into book (name, description, isbn, price, published_date, author_id, category_id, publisher_id) values('MY CONEY ISLAND BABY', 'A poignant, piercing meditation on middle age and the passing of time. will linger with you long after the book is closed', '9780062856562', 12.0, parseDateTime('15-01-2019','dd-MM-yyyy'), 2, 1, 3);
insert into book (name, description, isbn, price, published_date, author_id, category_id, publisher_id) values('Pizazz vs the New Kid: The super awesome new superhero series! (Volume 2)', 'The super (like, actually, with powers and stuff) series from the amazingly talented Sophy Henn, award-winning author and illustrator of Bad Nana! ', '9781471194153', 14.99, parseDateTime('22-07-2020','dd-MM-yyyy'), 3, 4, 4);