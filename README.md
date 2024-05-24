# InventoryManagement

InventoryManagementSystem

Database Name - inventory_management

Schema

create table product (
    id bigint AUTO_INCREMENT,
    product_id varchar(50) not null,
   `name` varchar(100) NOT NULL,
    supplier_id varchar(50),
    price double not null,
    quantity int default 0,
    description varchar(500),
    is_deleted boolean default false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key(id),
    index idx_product_id (product_id, is_deleted),
    index idx_supplier_id (supplier_id, is_deleted)
);



create table suppliers (
    id bigint AUTO_INCREMENT,
    supplier_id varchar(50) NOT NULL,
    name varchar(100) NOT NULL,
    contact_number varchar(20) NOT NULL,
    email_id varchar(100),
    address varchar(500),
    is_deleted boolean default false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key(id),
    INDEX idx_supplier_id (supplier_id, is_deleted),
    INDEX idx_contact_number (contact_number, is_deleted)
);
