CREATE TABLE rooms (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description TEXT NOT NULL,
  max_guests INT NOT NULL,
  base_price_per_night DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT chk_rooms_max_guests CHECK (max_guests > 0),
  CONSTRAINT chk_rooms_base_price CHECK (base_price_per_night >= 0)
);

CREATE TABLE room_images (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_id BIGINT NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  alt_text VARCHAR(255) NULL,
  sort_order INT NOT NULL DEFAULT 0,
  CONSTRAINT chk_room_images_sort CHECK (sort_order >= 0),
  CONSTRAINT fk_room_images_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE extras (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  title VARCHAR(100) NOT NULL,
  description TEXT NULL,
  icon_name VARCHAR(100) NOT NULL
);

CREATE TABLE room_extras (
  room_id BIGINT NOT NULL,
  extra_id BIGINT NOT NULL,
  PRIMARY KEY (room_id, extra_id),
  CONSTRAINT fk_room_extras_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
  CONSTRAINT fk_room_extras_extra FOREIGN KEY (extra_id) REFERENCES extras(id) ON DELETE RESTRICT
);

CREATE TABLE bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_id BIGINT NOT NULL,
  guest_first_name VARCHAR(100) NOT NULL,
  guest_last_name VARCHAR(100) NOT NULL,
  guest_email VARCHAR(255) NOT NULL,
  guest_count INT NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL,
  breakfast_included BOOLEAN NOT NULL DEFAULT FALSE,
  total_price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT chk_booking_dates CHECK (check_out_date > check_in_date),
  CONSTRAINT chk_booking_guest_count CHECK (guest_count > 0),
  CONSTRAINT fk_bookings_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE RESTRICT
);

CREATE INDEX idx_bookings_room_dates ON bookings (room_id, check_in_date, check_out_date);
CREATE UNIQUE INDEX uq_room_images_room_sort ON room_images (room_id, sort_order);
