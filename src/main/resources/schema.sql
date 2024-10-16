START TRANSACTION;

-- Delete existing data from tables
DELETE FROM room_inventories;
DELETE FROM rooms;
DELETE FROM guesthouses;
DELETE FROM addresses;
DELETE FROM members;

-- Reset AUTO_INCREMENT values (optional)
ALTER TABLE room_inventories AUTO_INCREMENT = 1;
ALTER TABLE rooms AUTO_INCREMENT = 1;
ALTER TABLE guesthouses AUTO_INCREMENT = 1;
ALTER TABLE addresses AUTO_INCREMENT = 1;
ALTER TABLE members AUTO_INCREMENT = 1;


-- Insert member values
INSERT INTO members(email, password, name, phone, role)
VALUES
    ('admin@sonnim.com', '$2a$10$1UitCmGBo5GMvT5a62OSoOktzwDvAdeQ8eE2f.b98HD/wAZC7WtVS', 'admin', '010-1111-1111', 'ADMIN'),
    ('shp@sonnim.com', '$2a$10$h14/EbjcuAgfNLndS/rEpe5wKwrZ0awYrY3ZXWiBiYXvWvz/QXHda', '박OO', '010-1234-1234', 'USER'),
    ('choi@sonnim.com', '$2a$10$h14/EbjcuAgfNLndS/rEpe5wKwrZ0awYrY3ZXWiBiYXvWvz/QXHda', '최OO', '010-1234-1234', 'GUESTHOUSE');

-- Insert guesthouse values
INSERT INTO guesthouses(guesthouse_name, owner_name, region_code, detail_address, phone, description)
VALUES
    ('A게스트하우스', '김OO', '0201', 'OO로 OO길', '010-1234-5678', '편히 쉴 수 있는 게스트하우스입니다.');

-- Insert address values
INSERT INTO addresses(region_code, region_name)
VALUES
    ('0101', '서울특별시 강남구'),
    ('0201', '제주특별자치도 제주시'),
    ('0202', '제주특별자치도 서귀포시');

-- Insert room values
INSERT INTO rooms(room_name, max_capacity)
VALUES
    ('도미토리 6인실', 6),
    ('개인 2인실', 2);

COMMIT;
