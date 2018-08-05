# --- !Ups

INSERT INTO COMPANY (ID, NAME, SLA_TIME_IN_SECONDS, SLA_PERCENTAGE, CURRENT_SLA_PERCENTAGE, IS_DELETED) VALUES (1, 'Company1', 60, 0.9, 0.95, false);
INSERT INTO COMPANY (ID, NAME, SLA_TIME_IN_SECONDS, SLA_PERCENTAGE, CURRENT_SLA_PERCENTAGE, IS_DELETED) VALUES (2, 'Company2', 60, 0.9, 0.9, false);
INSERT INTO COMPANY (ID, NAME, SLA_TIME_IN_SECONDS, SLA_PERCENTAGE, CURRENT_SLA_PERCENTAGE, IS_DELETED) VALUES (3, 'Company3', 120, 0.8, 0.95,false);
INSERT INTO COMPANY (ID, NAME, SLA_TIME_IN_SECONDS, SLA_PERCENTAGE, CURRENT_SLA_PERCENTAGE, IS_DELETED) VALUES (4, 'Company4', 120, 0.8, 0.8,false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (1, 'default', DATEADD('SECOND', -30, CURRENT_TIMESTAMP()), 1, true, false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (2, 'default', DATEADD('SECOND', -45, CURRENT_TIMESTAMP()), 1, true, false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (3, 'default', DATEADD('SECOND', -30, CURRENT_TIMESTAMP()), 2, true, false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (4, 'default', DATEADD('SECOND', -30, CURRENT_TIMESTAMP()), 3, true, false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (5, 'default', DATEADD('SECOND', -30, CURRENT_TIMESTAMP()), 4, true, false);
INSERT INTO IDENTIFICATION (ID, USERNAME, STARTED_AT, COMPANY_ID, IS_PENDING, IS_DELETED) VALUES (6, 'default', DATEADD('SECOND', -45, CURRENT_TIMESTAMP()), 1, true, false);


# --- !Downs

DELETE FROM IDENTIFICATION;
DELETE FROM COMPANY;