INSERT INTO rooms (id, title, description, max_guests, base_price_per_night, created_at, updated_at)
VALUES
  (1, 'Deluxe Room', 'Bright room with city view.', 2, 129.00, NOW(), NOW()),
  (2, 'Garden Suite', 'Spacious suite with garden access.', 3, 189.00, NOW(), NOW());

INSERT INTO extras (id, code, title, description, icon_name)
VALUES
  (1, 'wifi', 'WiFi', 'Free wireless internet', 'wifi'),
  (2, 'breakfast', 'Breakfast', 'Optional breakfast buffet', 'coffee');

INSERT INTO room_extras (room_id, extra_id)
VALUES
  (1, 1),
  (1, 2),
  (2, 1);

INSERT INTO room_images (id, room_id, file_name, sort_order, alt_text)
VALUES
  (1, 1, 'main.jpg', 0, 'Deluxe Room'),
  (2, 2, 'main.jpg', 0, 'Garden Suite');
