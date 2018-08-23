CREATE TABLE IF NOT EXISTS users(
	username varchar(50) not null primary key,
    password varchar(250) not null,
    enabled boolean not null DEFAULT true 
);

create table IF NOT EXISTS authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);


CREATE TABLE IF NOT EXISTS messages(
	ID int NOT NULL AUTO_INCREMENT,
	user varchar(50) not null,
	content VARCHAR(500) NOT NULL,
	FOREIGN KEY (user) REFERENCES users(username)
);
