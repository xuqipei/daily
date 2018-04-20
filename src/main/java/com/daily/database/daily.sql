create table daily_category
(
	id int not null auto_increment primary key,
	merchant_id int null,
	name varchar(50) not null,
	create_time datetime not null,
	update_time datetime not null
)
;

create table daily_menu
(
	id int not null auto_increment primary key,
	name varchar(50) null,
	image_url varchar(255) null,
	category_id int null,
	price decimal(20,2) null,
	discount int(3) null,
	sale_num int(10) default 0,
	description varchar(255) null,
	menu_option text null,
	create_time datetime not null,
	update_time datetime not null
);

create table daily_merchant
(
	id int not null auto_increment primary key,
	login_name varchar(255) null,
	login_password varchar(255) null,
	merchant_name varchar(255) null,
	address varchar(255) null,
	contact varchar(20) null,
	image_url varchar(255) null,
	create_time datetime not null,
	update_time datetime not null
)
;

create table daily_comment
{
	id int not null auto_increment primary key,
	menu_id int not null,
	status int not null,
	status_desc varchar(30) not null,
	comment_detail varchar(255),
	create_time datetime not null,
	update_time datetime not null
}

create table daily_merchant_table
(
	id int not null auto_increment primary key,
	table_no varchar(11) null,
	merchant_id int null,
	status int(3) null,
	status_desc varchar(30) not null,
	sit_real int(3) null,
	capacity int(3) null,
	create_time datetime not null,
	update_time datetime not null
)
;

create table daily_order
(
	id int not null auto_increment primary key,
	order_no varchar(30) null,
	user_id int null,
	nums int null,
	total_price decimal(20,2) null,
	pay_time datetime null,
	status int(3) null,
	status_desc varchar(30) null,
	merchant_id int null,
	sit_real int null,
	table_no varchar(20) null,
	table_id int null,
	create_time datetime not null,
	update_time datetime not null,
	constraint order_no_index
		unique (order_no)
)
;

create table daily_order_item
(
	id int not null auto_increment primary key,
	order_no varchar(30),
	user_id int not null,
	menu_id int,
	menu_name varchar(50) null,
	unit_price decimal(20,2) null,
	total_price decimal(20,2) null,
	quantity int null,
	image_url varchar(255) null,
	create_time datetime not null,
	update_time datetime not null
)
;

create index order_no_index
	on daily_order_item (order_no)
;

create index order_no_user_id_index
	on daily_order_item (user_id, order_no)
;

create table daily_pay_info
(
	id int not null auto_increment primary key,
	user_id int,
	merchant_id int,
	order_no varchar(30),
	pay_platform varchar(30),
	status int,
	status_desc varchar(20),
	create_time datetime,
	update_time datetime
)
;

create table daily_user
(
	id int not null auto_increment primary key,
	username varchar(30) null,
	password varchar(255) null,
	union_id varchar(255),
	mobilephone varchar(20),
	nickname varchar(255) not null,
	age int,
	brithday datetime,
	gender varchar(255),
	create_time datetime not null,
	update_time datetime not null,
	constraint user_name_unique
		unique (username)
)
;

create table temp_table_status
(
	id int not null auto_increment
		primary key,
	username varchar(30) not null,
	table_id int not null
)
;