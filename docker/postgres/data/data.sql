create table business_project (
      bp_index  serial not null,
      delete_date timestamp,
      last_modify_date timestamp,
      register_date timestamp not null,
      bp_description varchar(5000) default '' not null,
      bp_status varchar(255),
      bp_title varchar(255) not null,
      primary key (bp_index)
);

create table business_section (
      bs_index  bigserial not null,
      delete_date timestamp,
      last_modify_date timestamp,
      register_date timestamp not null,
      bs_description varchar(1000) default '' not null,
      bs_status varchar(255) not null,
      bs_title varchar(255) not null,
      bp_index int4,
      primary key (bs_index)
);

create table business_task (
       bt_index  bigserial not null,
       delete_date timestamp,
       last_modify_date timestamp,
       register_date timestamp not null,
       bt_description varchar(255),
       bt_status varchar(255),
       bt_title varchar(255) not null,
       bm_index int8,
       bs_index int8,
       primary key (bt_index)
);

create table business_member (
                                 bm_index  bigserial not null,
                                 delete_date timestamp,
                                 last_modify_date timestamp,
                                 register_date timestamp not null,
                                 bm_affiliation varchar(255),
                                 bm_kakao_id varchar(200) default '',
                                 bm_name varchar(255) not null,
                                 primary key (bm_index)
);

create table business_review (
                                 br_index  bigserial not null,
                                 delete_date timestamp,
                                 last_modify_date timestamp,
                                 register_date timestamp not null,
                                 br_opiinion varchar(500) default '' not null,
                                 bm_index int8,
                                 bt_index int8,
                                 primary key (br_index)
);
