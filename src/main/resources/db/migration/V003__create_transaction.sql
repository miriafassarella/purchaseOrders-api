CREATE TABLE transaction(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(100) NOT NULL,
	tic VARCHAR(20) NOT NULL,
	date DATETIME NOT NULL,
	id_direction BIGINT(20) NOT NULL,
	id_product BIGINT(20) NOT NULL,
	FOREIGN KEY (id_direction) REFERENCES direction(id),
	FOREIGN KEY (id_product) REFERENCES product(id)
);