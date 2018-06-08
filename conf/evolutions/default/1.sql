# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table branch (
  id                        bigint auto_increment not null,
  location                  varchar(255),
  name                      varchar(255),
  shop_id                   bigint,
  created_at                datetime,
  updated_at                datetime,
  constraint pk_branch primary key (id))
;

create table brand (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_brand primary key (id))
;

create table market (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  location                  varchar(255),
  image                     varchar(255),
  open_at                   varchar(255),
  close_at                  varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_market primary key (id))
;

create table product (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  quanty                    double,
  quality                   varchar(255),
  price                     double,
  popularity                integer,
  product_category_id       bigint,
  brand_id                  bigint,
  shop_id                   bigint,
  created_at                datetime,
  updated_at                datetime,
  constraint pk_product primary key (id))
;

create table product_category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_product_category primary key (id))
;

create table product_image (
  id                        bigint auto_increment not null,
  image                     varchar(255),
  product_id                bigint,
  created_at                datetime,
  updated_at                datetime,
  constraint pk_product_image primary key (id))
;

create table product_order (
  id                        bigint auto_increment not null,
  quantity                  integer,
  price                     double,
  user_id                   bigint,
  product_id                bigint,
  status                    varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_product_order primary key (id))
;

create table rating (
  id                        bigint auto_increment not null,
  emil                      varchar(255),
  comment                   varchar(255),
  rating                    integer,
  created_at                datetime,
  updated_at                datetime,
  constraint pk_rating primary key (id))
;

create table shop (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  type                      varchar(255),
  image                     varchar(255),
  location                  varchar(255),
  market_id                 bigint,
  user_id                   bigint,
  open_at                   varchar(255),
  close_at                  varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_shop primary key (id))
;

create table shop_category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_shop_category primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  names                     varchar(255),
  profile                   varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  phone                     varchar(255),
  role                      varchar(255),
  address                   varchar(255),
  gender                    varchar(255),
  created_at                datetime,
  updated_at                datetime,
  constraint pk_user primary key (id))
;

alter table branch add constraint fk_branch_shop_1 foreign key (shop_id) references shop (id) on delete restrict on update restrict;
create index ix_branch_shop_1 on branch (shop_id);
alter table product add constraint fk_product_productCategory_2 foreign key (product_category_id) references product_category (id) on delete restrict on update restrict;
create index ix_product_productCategory_2 on product (product_category_id);
alter table product add constraint fk_product_brand_3 foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_product_brand_3 on product (brand_id);
alter table product add constraint fk_product_shop_4 foreign key (shop_id) references shop (id) on delete restrict on update restrict;
create index ix_product_shop_4 on product (shop_id);
alter table product_image add constraint fk_product_image_product_5 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_product_image_product_5 on product_image (product_id);
alter table product_order add constraint fk_product_order_user_6 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_product_order_user_6 on product_order (user_id);
alter table product_order add constraint fk_product_order_product_7 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_product_order_product_7 on product_order (product_id);
alter table shop add constraint fk_shop_market_8 foreign key (market_id) references market (id) on delete restrict on update restrict;
create index ix_shop_market_8 on shop (market_id);
alter table shop add constraint fk_shop_user_9 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_shop_user_9 on shop (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table branch;

drop table brand;

drop table market;

drop table product;

drop table product_category;

drop table product_image;

drop table product_order;

drop table rating;

drop table shop;

drop table shop_category;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

