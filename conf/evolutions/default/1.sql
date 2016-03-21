# --- !Ups

CREATE TABLE companies (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL,
	sla_time DOUBLE NOT NULL,
	sla_percentage DOUBLE NOT NULL,
	current_sla_percentage DOUBLE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE identifications (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL,
	time BIGINT NOT NULL,
	waiting_time INTEGER NOT NULL,
	company_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (company_id) REFERENCES companies(id)
);

# --- !Downs
DROP TABLE identifications;
DROP TABLE companies;