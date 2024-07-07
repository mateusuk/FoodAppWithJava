CREATE TABLE payments (
 id bigint(20) NOT NULL AUTO_INCREMENT,
 value decimal(19,2) NOT NULL,
 name varchar(100) DEFAULT NULL,
 number varchar(19) DEFAULT NULL,
 expire_date varchar(7) DEFAULT NULL,
 security_code varchar(3) DEFAULT NULL,
 status varchar(255) NOT NULL,
 order_id bigint(20) NOT NULL,
 payments_methods bigint(20) NOT NULL,
PRIMARY KEY (id)
);