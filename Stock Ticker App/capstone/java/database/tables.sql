START TRANSACTION;
--ROLLBACK;
DROP TABLE IF EXISTS game_details_game_player, stock_performance, game_player_stock, friends, game_player, trade, stock, company, friendship_status, game_details, trade_type, game_status, difficulty, game_player_status;
DROP SEQUENCE IF EXISTS company_serial, trade_serial, game_player_serial, game_details_serial;

-- CREATE SEQUENCE users_serial;
-- CREATE TABLE users (
-- 	user_id int NOT NULL DEFAULT nextval('stock_ticker_user_serial')
-- 	username VARCHAR(50) NOT NULL,
-- 	is_public BOOLEAN NOT NULL,
-- 	profile_pic VARCHAR(150) NULL,
-- 	CONSTRAINT PK_stock_ticker_user PRIMARY KEY(user_id)
-- );

CREATE TABLE game_status (
	game_status_id int NOT NULL,
	game_status_desc VARCHAR(30) NOT NULL,
	CONSTRAINT PK_game_status PRIMARY KEY (game_status_id)
);

CREATE TABLE game_player_status (
	game_player_status_id int NOT NULL,
	game_player_status_desc VARCHAR(30) NOT NULL,
	CONSTRAINT PK_game_player_status PRIMARY KEY (game_player_status_id)
);

CREATE TABLE difficulty (
	difficulty_id int NOT NULL,
	difficulty_setting_desc VARCHAR(30) NOT NULL,
	CONSTRAINT PK_difficulty PRIMARY KEY (difficulty_id)
);

CREATE TABLE stock (
	stock_symbol VARCHAR(20) NOT NULL,
	open DECIMAL(20,10) NOT Null,
	daily_high DECIMAL(20,10) NOT NULL,
	daily_low DECIMAL(20,10) NOT NULL,
	volume int NOT NULL,
	latest_trading_day DATE NOT NULL,
	previous_close DECIMAL(20,10) NOT NULL,
	change DECIMAL(20,10) NOT NULL,
	change_percent DECIMAL(4,2) NOT NULL,
	CONSTRAINT PK_stock_symbol PRIMARY KEY(stock_symbol),
	CONSTRAINT UQ_stock_symbol UNIQUE (stock_symbol)
);

CREATE TABLE stock_performance (
	stock_symbol VARCHAR(20) NOT NULL,
	date_time TIMESTAMP NOT NULL,
	stock_price DECIMAL(20,10) NOT NULL,
	CONSTRAINT PK_stock_performance PRIMARY KEY (stock_symbol, date_time),
	CONSTRAINT FK_stock_symbol FOREIGN KEY (stock_symbol) REFERENCES stock (stock_symbol)
);

CREATE SEQUENCE game_player_serial INCREMENT BY 1 START WITH 10001 NO MAXVALUE;
CREATE TABLE game_player (
	game_player_id int NOT NULL DEFAULT nextval('game_player_serial'),
	user_id int NOT NULL,
	cash decimal(30, 2) NOT NULL,
	is_host BOOLEAN NOT NULL,
	CONSTRAINT PK_game_player PRIMARY KEY(game_player_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE game_player_stock (
	game_player_id int NOT NULL,
	stock_symbol VARCHAR(20) NOT NULL,
	number_of_shares int NOT NULL,
	CONSTRAINT PK_game_player_stock_symbol PRIMARY KEY(game_player_id, stock_symbol),
	CONSTRAINT FK_stock_symbol FOREIGN KEY (stock_symbol) REFERENCES stock (stock_symbol),
	CONSTRAINT FK_game_player FOREIGN KEY (game_player_id) REFERENCES game_player (game_player_id)
);

CREATE SEQUENCE game_details_serial;
CREATE TABLE game_details (
	game_details_id int NOT NULL DEFAULT nextval('game_details_serial'),
	game_status_id int NOT NULL,
	difficulty_id int NOT NULL,
	game_name VARCHAR(50) NOT NULL,
	game_start TIMESTAMP NOT NULL,
	game_end TIMESTAMP NOT NULL,
	commission_fee decimal(10,2) NOT NULL,
	player_count int NOT NULL,
	CONSTRAINT PK_game_details PRIMARY KEY(game_details_id),
	CONSTRAINT FK_game_status_id FOREIGN KEY (game_status_id) REFERENCES game_status (game_status_id),
	CONSTRAINT FK_difficulty_id FOREIGN KEY (difficulty_id) REFERENCES difficulty (difficulty_id)
);


CREATE SEQUENCE company_serial;
CREATE TABLE company (
	company_id int NOT NULL DEFAULT nextval('company_serial'),
	stock_symbol VARCHAR(20) NOT NULL,
	asset_type VARCHAR(20) NOT NULL,
	company_name VARCHAR(50) NOT NULL,
	description text NULL,
	exchange VARCHAR(20) NOT NULL,
	currency VARCHAR(20) NOT NULL,
	country VARCHAR(50) NOT NULL,
	sector VARCHAR(20) NOT NULL,
	industry VARCHAR(50) NOT NULL,
	address VARCHAR(100) NOT NULL,
	flavicon VARCHAR(150) NULL,
	company_url VARCHAR(150) NULL,
	eps DECIMAL(20,10) NOT NULL,
	forward_pe DECIMAL(20,10) NOT NULL,
	fifty_day_moving_average DECIMAL(20,10) NOT NULL,
	fifty_two_week_high DECIMAL(20,10) NOT NULL,
	fifty_two_week_low DECIMAL(20,10) NOT NULL,
	CONSTRAINT PK_company_id PRIMARY KEY(company_id),
	CONSTRAINT FK_stock_symbol FOREIGN KEY (stock_symbol) REFERENCES stock (stock_symbol)
);

CREATE TABLE trade_type (
	trade_type_id int NOT NULL,
	trade_type_desc VARCHAR(50) NOT NULL,
	CONSTRAINT PK_trade_type PRIMARY KEY(trade_type_id)
);

CREATE SEQUENCE trade_serial;
CREATE TABLE trade (
	trade_id int NOT NULL DEFAULT nextval('trade_serial'),
	game_player_id int NOT NULL,
	stock_symbol VARCHAR(20) NOT NULL,
	trade_type_id int NOT NULL,
	number_of_shares int NOT NULL,
	price_of_stock_at_trade DECIMAL(20,10) NOT NULL,
	date_time TIMESTAMP NOT NULL,
	CONSTRAINT PK_trade PRIMARY KEY(trade_id),
	CONSTRAINT FK_game_player FOREIGN KEY (game_player_id) REFERENCES game_player (game_player_id),
	CONSTRAINT FK_stock_symbol FOREIGN KEY (stock_symbol) REFERENCES stock (stock_symbol),
	CONSTRAINT FK_trade_type FOREIGN KEY (trade_type_id) REFERENCES trade_type (trade_type_id)
);

CREATE TABLE friendship_status (
	friendship_status_id int NOT NULL,
	friendship_status_desc VARCHAR(20) NOT NULL,
	CONSTRAINT PK_friendship_status PRIMARY KEY(friendship_status_id)
);

CREATE TABLE friends (
	user_id int NOT NULL,
	friendship_status_id int NOT NULL,
	CONSTRAINT PK_friends PRIMARY KEY(friendship_status_id, user_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT FK_friendship_status_id FOREIGN KEY (friendship_status_id) REFERENCES friendship_status (friendship_status_id)
);

CREATE TABLE game_details_game_player (
	game_details_id int NOT NULL,
	game_player_id int NOT NULL,
	
	CONSTRAINT PK_game_details_game_player PRIMARY KEY(game_details_id, game_player_id),
	CONSTRAINT FK_game_details FOREIGN KEY (game_details_id) REFERENCES game_details (game_details_id),
	CONSTRAINT FK_game_player FOREIGN KEY (game_player_id) REFERENCES game_player (game_player_id)
);

INSERT INTO friendship_status (friendship_status_id, friendship_status_desc) VALUES 
	(1, 'Pending'),
	(2, 'Approved'),
	(3, 'Rejected');
	

INSERT INTO game_status (game_status_id, game_status_desc) VALUES 
	(1, 'Staging'),
	(2, 'In Progress'),
	(3, 'Game Over');

INSERT INTO trade_type (trade_type_id, trade_type_desc) VALUES 
	(1, 'Buy'),
	(2, 'Sell');
	
INSERT INTO difficulty (difficulty_id, difficulty_setting_desc) VALUES 
	(1, 'Easy'),
	(2, 'Medium'),
	(3, 'Hard');

INSERT INTO game_player_status (game_player_status_id, game_player_status_desc) VALUES
	(1, 'Pending'),
	(2, 'Accepted'),
	(3, 'Rejected');

INSERT INTO stock (stock_symbol,open, daily_high, daily_low, volume, latest_trading_day, previous_close, change, change_percent)
VALUES (
    'AAPL', --stock symbol
    152.00, --opening price
    155.75, --daily high
    148.50, --daily low
    10000,  --volume
    '2023-12-07', -- latest trading day
    149.50, --previous day's closing price
    1.75,   --  change
    1.17    -- change percent
);

INSERT INTO stock_performance (stock_symbol, stock_price, date_time) VALUES
	('AAPL', 150.25, '2023-12-13T05:00:00.000Z'),
	('AAPL', 140.25, '2022-12-13T05:00:00.000Z');
	
commit;