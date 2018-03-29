-- Description : Script containing tables creation for the Maniot
-- Developer   : Thiago
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS temporal.aplications;
DROP TABLE IF EXISTS temporal.data;
DROP TABLE IF EXISTS temporal.permissions;
DROP TABLE IF EXISTS temporal.resources;
DROP TABLE IF EXISTS temporal.scheduling;
DROP TABLE IF EXISTS temporal.things;
DROP TABLE IF EXISTS temporal.users;

CREATE TABLE temporal.aplications (
  idAplication int(3) unsigned zerofill NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (idAplication)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.data (
  idResource int(3) unsigned zerofill NOT NULL,
  value varchar(100) DEFAULT NULL,
  time datetime DEFAULT NULL,
  KEY idResource (idResource)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.permissions (
  idResource int(3) unsigned zerofill NOT NULL,
  idAplication int(3) unsigned zerofill NOT NULL,
  idUser int(3) unsigned zerofill NOT NULL,
  permission varchar(3) DEFAULT,
  KEY idUser (idUser),
  KEY idAplication (idAplication),
  KEY idResource(idResource),
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.resources (
  idResource int(3) unsigned zerofill NOT NULL AUTO_INCREMENT,
  idThing int(3) unsigned zerofill NOT NULL,
  name varchar(50) NOT NULL,
  idAdm int(3) unsigned zerofill NOT NULL,
  PRIMARY KEY (idResource),
  KEY idThing (idThing)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.scheduling (
  idScheduling int(3) unsigned zerofill NOT NULL AUTO_INCREMENT,
  idThing int(3) unsigned zerofill NOT NULL,
  idResource int(3) unsigned zerofill NOT NULL,
  idUser int(3) unsigned zerofill NOT NULL,
  action int(11) DEFAULT NULL,
  newValue varchar(50) DEFAULT NULL,
  date int(11) DEFAULT NULL,
  time int(11) DEFAULT NULL,
  count int(11) DEFAULT NULL,
  others varchar(50) DEFAULT NULL,
  PRIMARY KEY (idScheduling),
  KEY idThing (idThing),
  KEY idResource (idResource),
  KEY idUser (idUser)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.things (
  idThing int(3) unsigned zerofill NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  location varchar(50) DEFAULT NULL,
  PRIMARY KEY (idThing)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE temporal.users (
  idUser int(3) unsigned zerofill NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  login varchar(20) NOT NULL,
  password varchar(20) DEFAULT NULL,
  PRIMARY KEY (idUser)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

