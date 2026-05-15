INSERT INTO rooms (id, title, description, max_guests, base_price_per_night, created_at, updated_at)
VALUES
  (1, 'Deluxe Room', 'Bright room with city view.', 2, 129.00, NOW(), NOW()),
  (2, 'Garden Suite', 'Spacious suite with garden access.', 3, 189.00, NOW(), NOW()),
  (3, 'City Loft', 'Open-plan loft with skyline views.', 2, 149.00, NOW(), NOW()),
  (4, 'Family Studio', 'Comfortable studio with extra space.', 4, 179.00, NOW(), NOW()),
  (5, 'Skyline Suite', 'Top-floor suite with panoramic windows.', 3, 219.00, NOW(), NOW()),
  (6, 'Cozy Single', 'Quiet single room for short stays.', 1, 99.00, NOW(), NOW()),
  (7, 'Wellness Retreat', 'Relaxing room with spa-inspired accents.', 2, 169.00, NOW(), NOW()),
  (8, 'Business King', 'King bed with dedicated work area.', 2, 159.00, NOW(), NOW()),
  (9, 'Heritage Room', 'Classic decor with warm wood tones.', 2, 139.00, NOW(), NOW());

INSERT INTO extras (id, code, title, description, icon_name)
VALUES
  (1, 'wifi', 'WiFi', 'Free wireless internet', 'wifi'),
  (2, 'breakfast', 'Breakfast', 'Optional breakfast buffet', 'coffee'),
  (3, 'parking', 'Parking', 'Reserved parking space', 'car'),
  (4, 'spa', 'Spa Access', 'Access to the wellness area', 'sparkles'),
  (5, 'minibar', 'Minibar', 'Curated in-room minibar', 'wine'),
  (6, 'balcony', 'Balcony', 'Private balcony seating', 'sunny'),
  (7, 'pet_friendly', 'Pet Friendly', 'Pets welcome on request', 'paw'),
  (8, 'workspace', 'Workspace', 'Desk with task lighting', 'briefcase');

INSERT INTO room_extras (room_id, extra_id)
VALUES
  (1, 1),
  (1, 2),
  (1, 5),
  (2, 1),
  (2, 2),
  (2, 6),
  (3, 1),
  (3, 5),
  (3, 8),
  (4, 1),
  (4, 2),
  (4, 3),
  (5, 1),
  (5, 4),
  (5, 6),
  (6, 1),
  (6, 5),
  (7, 1),
  (7, 2),
  (7, 4),
  (8, 1),
  (8, 3),
  (8, 8),
  (9, 1),
  (9, 2),
  (9, 7);

INSERT INTO room_images (id, room_id, file_name, sort_order, alt_text)
VALUES
  (1, 1, 'main.jpg', 0, 'Deluxe Room'),
  (2, 2, 'main.jpg', 0, 'Garden Suite'),
  (3, 3, 'main.jpg', 0, 'City Loft'),
  (4, 4, 'main.jpg', 0, 'Family Studio'),
  (5, 5, 'main.jpg', 0, 'Skyline Suite'),
  (6, 6, 'main.jpg', 0, 'Cozy Single'),
  (7, 7, 'main.jpg', 0, 'Wellness Retreat'),
  (8, 8, 'main.jpg', 0, 'Business King'),
  (9, 9, 'main.jpg', 0, 'Heritage Room');
