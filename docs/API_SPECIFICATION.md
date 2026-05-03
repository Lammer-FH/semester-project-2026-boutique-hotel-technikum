# Boutique Hotel Technikum – API Specification (Draft)

## Overview

REST API for the Boutique Hotel Technikum booking application.

**Base URL:** `/api/v1` – Explicit versioning allows backward compatibility if future API changes are needed. 

---

## Global Standards

### HTTP Verbs & Status Codes
| Method | Purpose | Status |
|--------|---------|--------|
| GET | Retrieve | 200 OK |
| POST | Create | 201 Created |
| PUT/PATCH | Update | 200 OK / 204 No Content |
| DELETE | Remove | 204 No Content |
| - | Invalid input | 400 Bad Request |
| - | Not authenticated | 401 Unauthorized |
| - | Resource not found | 404 Not Found |
| - | Conflict | 409 Conflict |

### Error Response Format
```json
{
  "error": {
    "message": "Human-readable message",
    "code": "ERROR_CODE",
    "details": [
      {"field": "email", "message": "Already registered"}
    ]
  }
}
```

### Pagination
Query parameters: `page` (default: 1), `size` (default: 5, max: 5)

Validation:
- `page` must be greater than or equal to 1.
- `size` must be between 1 and 5.

Response metadata:
```json
{
  "data": [...],
  "pagination": {
    "page": 1,
    "size": 5,
    "total": 12,
    "total_pages": 3
  }
}
```

---

### Rooms

**GET /rooms** – List rooms (paginated, max 5 per page)  
Query: `?page=1&size=5`  
Response (200): Paginated response with room objects in `data`. Each room contains `id, title, description, max_guests, base_price_per_night, images[], extras[]`  
Errors: 400 (invalid pagination)

**GET /rooms/{roomId}** – Get room details  
Response (200): Room object with `id, title, description, max_guests, base_price_per_night, images[], extras[]`  
Errors: 404

---

### Availability

**GET /rooms/{roomId}/availability** – Check availability  
Query: `?check_in_date=YYYY-MM-DD&check_out_date=YYYY-MM-DD`  
Room availability is determined by overlapping bookings. `check_in_date` is inclusive and `check_out_date` is exclusive, so another booking may start on the same date a previous booking ends.

Overlap rule:
`existing.check_in_date < requested.check_out_date && existing.check_out_date > requested.check_in_date`

Response (200):
```json
{
  "room_id": 1,
  "is_available": true,
  "message": "Room is available"
}
```
Errors: 400 (invalid dates), 404

Validation:
- Dates must use `YYYY-MM-DD`.
- `check_out_date` must be after `check_in_date`.

---

### Bookings

**POST /bookings** – Create booking  
Request:
```json
{
  "room_id": 1,
  "guest_first_name": "John",
  "guest_last_name": "Doe",
  "guest_email": "john@example.com",
  "confirm_email": "john@example.com",
  "check_in_date": "2026-05-15",
  "check_out_date": "2026-05-20",
  "breakfast_included": true
}
```
Response (201): Booking confirmation data with booking period, guest data, and room details used by U5
```json
{
  "booking_id": 42,
  "check_in_date": "2026-05-15",
  "check_out_date": "2026-05-20",
  "breakfast_included": true,
  "guest": {
    "first_name": "John",
    "last_name": "Doe",
    "email": "john@example.com"
  },
  "room": {
    "id": 1,
    "title": "Deluxe Room",
    "description": "Bright room with city view.",
    "max_guests": 2,
    "base_price_per_night": 129.00,
    "images": [
      {
        "id": 1,
        "url": "/images/rooms/1/main.jpg",
        "sort_order": 0,
        "alt_text": "Deluxe Room"
      }
    ],
    "extras": [
      {
        "id": 1,
        "code": "wifi",
        "title": "WiFi",
        "description": "Free wireless internet",
        "icon_name": "wifi"
      }
    ]
  },
  "hotel_contact": {
    "name": "Boutique Hotel Technikum",
    "email": "contact@hotel-technikum.example",
    "phone": "+43 1 234567"
  }
}
```
Errors: 400 (validation), 409 (unavailable dates)

Validation:
- `room_id`, guest names, `guest_email`, `confirm_email`, `check_in_date`, and `check_out_date` are required.
- `guest_email` must be a valid email address and match `confirm_email`.
- `check_out_date` must be after `check_in_date`.
- If the requested dates overlap an existing booking, return 409 Conflict.

---

### Extras

**GET /extras** – List all extras  
Response (200): Array of extra objects: `id, code, title, description, icon_name`  
No pagination needed (typically < 20 items)

---

## Data Models

| Model | Key Fields |
|-------|-----------|
| **Room** | id, title, description, max_guests, base_price_per_night, created_at, updated_at |
| **Room Image** | id, room_id, file_name, sort_order |
| **Extra** | id, code, title, description, icon_name |
| **Booking** | id, room_id, guest_first_name, guest_last_name, guest_email, check_in_date, check_out_date, breakfast_included, created_at, updated_at |

### Response Object Notes

Room images are returned as objects with `id, url, sort_order, alt_text`. The backend may store only `file_name`, but API clients should use `url`.

Extras are returned as objects with `id, code, title, description, icon_name`.

---

## Required Endpoints

**U2 Room Selection:** `GET /rooms`, `GET /rooms/{roomId}`, `GET /extras`  
**U3 Availability Check:** `GET /rooms/{roomId}/availability`  
**U4 Booking:** `POST /bookings`  
**U5 Booking Confirmation:** returned directly from `POST /bookings`