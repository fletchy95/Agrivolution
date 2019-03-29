DROP SCHEMA IF EXISTS ticket;
CREATE SCHEMA ticket;
USE ticket;
Drop TABLE IF EXISTS Tickets;

CREATE TABLE Tickets(
	ticket_ID int NOT NULL auto_increment,
    farmName varchar(20) NOT NULL,
    farmAddress varchar(50) NOT NULL,
    locationDetail varchar(30) NOT NULL,
    ticketTitle varchar(20) NOT NULL,
    dateTicket varchar(12) NOT NULL,
    email varchar(20) NOT NULL,
    optionalContact varchar(12) NOT NULL,
    description varchar(250) NOT NULL,
    PRIMARY KEY (ticket_ID)
);
select * from Tickets;