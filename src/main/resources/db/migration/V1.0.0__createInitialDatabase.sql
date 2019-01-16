CREATE TABLE DISTRIBUTOR (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(255) NOT NULL,
  PHONE_NUMBER VARCHAR(50),
  EMAIL VARCHAR(255),
  CITY VARCHAR(255),
  STREET VARCHAR(255),
  ZIP_CODE VARCHAR(32),
  COUNTRY VARCHAR(255),
  PRIMARY KEY (ID)
) DEFAULT CHARSET=utf8;

CREATE TABLE PRODUCT (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  BRAND_NAME VARCHAR(255) NOT NULL,
  PRODUCT_NAME VARCHAR(255) NOT NULL,
  DESCRIPTION VARCHAR(500),
  NUMBER_OF_UNITS SMALLINT,
  DISTRIBUTOR_ID BIGINT(20),
  FOREIGN KEY (DISTRIBUTOR_ID) REFERENCES DISTRIBUTOR(ID),
  PRIMARY KEY (ID)
) DEFAULT CHARSET=utf8;