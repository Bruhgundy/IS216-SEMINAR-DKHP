-- Test data for TKB Hoc Ky 1 2025-2026
ALTER SESSION SET CONTAINER=XEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = COURSE_REG;

-- 50 more students (SV2024051 to SV2024100)
BEGIN
    FOR i IN 51..100 LOOP
        INSERT INTO students (student_code, full_name, email)
        VALUES ('SV' || TO_CHAR(2024000 + i), 'Sinh Vien ' || TO_CHAR(i), 'sv' || TO_CHAR(i) || '@student.edu.vn');
    END LOOP;
END;
/

-- More courses
INSERT ALL
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Thiet ke vi mach tuong tu', 60, 2, 1, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Khoa hoc may tinh', 40, 2, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Khai thac du lieu va ung dung', 80, 3, 6, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Marketing can ban', 50, 2, 1, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Quan tri quan he khach hang', 70, 5, 1, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Quan tri chien luoc kinh doanh dien tu', 70, 3, 1, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('An toan va bao mat thuong mai dien tu', 50, 5, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Thuong mai xa hoi', 50, 5, 2, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Nhap mon bao dam va an ninh thong tin', 70, 2, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Thiet ke giao dien nguoi dung', 70, 2, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Thuong mai dien tu', 70, 3, 2, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Phan tich du lieu kinh doanh', 80, 3, 1, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 1', 30, 4, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 1', 30, 6, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 2', 30, 4, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 2', 30, 6, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 3', 30, 6, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Anh van 3', 30, 4, 11, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Giao duc the chat 1', 60, 3, 3, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Giao duc the chat 2', 60, 6, 6, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Phat trien ky nang lap trinh Game', 50, 2, 6, 3)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Nhap mon Quan tri chuoi cung ung', 50, 3, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('Phap luat trong thuong mai dien tu', 50, 5, 6, 4)
    INTO courses (name, available_seats, day_of_week, start_period, period_count) VALUES ('He thong thanh toan truc tuyen', 70, 6, 6, 4)
SELECT 1 FROM DUAL;

COMMIT;