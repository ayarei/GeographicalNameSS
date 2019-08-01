use location;
create table places (
  id int(11) NOT NULL AUTO_INCREMENT,
  province varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  county varchar(255) DEFAULT NULL,
  longitude float DEFAULT NULL,
  latitude float DEFAULT NULL,
  primary key (id),
  index idx_province_city (province(255),city(255))
)engine = InnoDB default charset = utf8;