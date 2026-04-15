# Hệ Thống Đăng Ký Học Phần

Hệ thống đăng ký học phần cho trường đại học với Oracle Database.

## 🚀 Chạy Nhanh (Click!)

### Windows
```
1. Click đúp file: run.bat
2. Đợi ~2-3 phút cho Docker build
3. Mở: http://localhost:8080
```

### Linux/Mac
```bash
./run.sh
# hoặc
docker-compose up -d
```

---

## 🛠 Yêu Cầu
- [Docker Desktop](https://docker.com) đã cài và đang chạy

---

## 📁 Các File Quan Trọng

| File | Mô tả |
|------|--------|
| `run.bat` | Click để khởi động |
| `stop.bat` | Click để dừng |
| `docker-compose.yml` | Cấu hình Docker |
| `Dockerfile` | Build app thành container |
| `schema.sql` | Tạo bảng + 50 SV + 8 HP |
| `test-data.sql` | Thêm 50 SV + 22 HP |

---

## 📡 API Endpoints

| Method | Endpoint | Mô tả |
|--------|----------|--------|
| GET | `/api/courses` | Danh sách học phần |
| POST | `/api/courses` | Tạo học phần mới |
| GET | `/api/students` | Danh sách sinh viên |
| POST | `/api/registrations` | Đăng ký (optimistic) |
| POST | `/api/registrations/pessimistic` | Đăng ký (pessimistic lock) |
| POST | `/api/registrations/atomic` | Đăng ký (atomic update) |
| POST | `/api/registrations/serializable` | Đăng ký (serializable) |

### Request Body
```json
{"studentId": 1, "courseId": 1}
```

---

## 🤖 Thundering Herd Problem - Solution Interface

Để test giải pháp AI cho bài toán Thundering Herd:

### 1. Tạo class implement Solution
```java
@Service
public class MySolution implements Solution {
    
    @Override
    public String getName() {
        return "My AI Strategy";
    }
    
    @Override
    public boolean register(Long studentId, Long courseId) {
        // Implement giải pháp của bạn
        return true;
    }
}
```

### 2. Truy cập endpoint
```bash
curl -X POST http://localhost:8080/api/solution \
  -H "Content-Type: application/json" \
  -d '{"studentId": 1, "courseId": 1}'
```

---

## 🐳 Lệnh Docker

```bash
# Khởi động
docker-compose up -d

# Xem logs
docker-compose logs -f

# Dừng
docker-compose down

# Xóa hoàn toàn (reset)
docker-compose down -v

# Rebuild sau khi thay đổi code
docker-compose up --build -d
```

---

## 👥 Cho Thành Viên Nhóm

### Setup lần đầu
```bash
git clone https://github.com/Bruhgundy/IS216-SEMINAR-DKHP.git
cd IS216-SEMINAR-DKHP
docker-compose up -d
```

### Mỗi lần sau
```
1. Mở Docker Desktop
2. Click run.bat
3. Mở http://localhost:8080
```
