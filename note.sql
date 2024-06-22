
-- admin --> all users in system!
DROP TABLE IF EXISTS t19_view_system_notification;
CREATE TABLE t19_view_system_notification (
     C19_ID INT AUTO_INCREMENT PRIMARY KEY,
     C19_SYSTEM_NOTIFICATION_ID INT NOT NULL,
     C19_HAS_SEEN_BY INT NOT NULL,
     C19_SEEN_TIME DATETIME DEFAULT(current_timestamp()),
     CONSTRAINT FK_T19_T11 FOREIGN KEY (C19_SYSTEM_NOTIFICATION_ID) REFERENCES t11_system_notification(C11_ID),
     CONSTRAINT FK_T19_T14 FOREIGN KEY (C19_HAS_SEEN_BY) REFERENCES t14_user(C14_USER_ID)
);

-- admin, manager --> 1 user
-- 0: false --> chưa xem
-- 1: true  --> đã xem
-- > set lại thuộc tính has_seen trong get api luôn
DROP TABLE IF EXISTS t20_individual_notification;
CREATE TABLE t20_individual_notification (
     C20_ID INT AUTO_INCREMENT PRIMARY KEY,
     C20_TITLE VARCHAR(255) NOT NULL,
     C20_CONTENT VARCHAR(255) NOT NULL,
     C20_ACTOR_ID INT NOT NULL, -- nếu admin gửi thì ghi là thông báo từ hệ thống
                                -- còn là manager gửi thì lấy thông tin đầy đủ của manager đó show ra
     C20_CREATE_AT DATETIME DEFAULT(current_timestamp()),
     C20_UPDATE_AT DATETIME,
     C20_SEND_TO_USER VARCHAR(255) NOT NULL, 
     C20_HAS_SEEN BIT(1) DEFAULT 0, 
     C20_SEEN_TIME DATETIME,
     CONSTRAINT FK_T20_T14 FOREIGN KEY (C20_SEND_TO_USER) REFERENCES t14_user(C14_USERNAME),
     CONSTRAINT FK_T20_T14_ID FOREIGN KEY (C20_ACTOR_ID) REFERENCES t14_user(C14_USER_ID)
);

-- manager --> all users in certain center
DROP TABLE IF EXISTS t22_center_notification;
CREATE TABLE t22_center_notification (
     C22_ID INT AUTO_INCREMENT PRIMARY KEY,
     C22_TITLE VARCHAR(255) NOT NULL,
     C22_CONTENT VARCHAR(255) NOT NULL,
     C22_CREATE_AT DATETIME DEFAULT(current_timestamp()),
     C22_UPDATE_AT DATETIME,
     C22_CENTER_ID INT NOT NULL, -- ở đây có thể lấy được thông tin của manager nên bảng này ko cần thêm thuộc tính actor_id 
     CONSTRAINT FK_T22_T03 FOREIGN KEY (C22_CENTER_ID) REFERENCES t03_center(C03_CENTER_ID)
);

DROP TABLE IF EXISTS t23_view_center_notification;
CREATE TABLE t23_view_center_notification (
     C23_ID INT AUTO_INCREMENT PRIMARY KEY,
     C23_CENTER_NOTIFICATION_ID INT NOT NULL,
     C23_HAS_SEEN_BY INT NOT NULL,
     C23_SEEN_TIME DATETIME DEFAULT(current_timestamp()),
     CONSTRAINT FK_T23_T22 FOREIGN KEY (C23_CENTER_NOTIFICATION_ID) REFERENCES t22_center_notification(C22_ID),
     CONSTRAINT FK_T23_T14 FOREIGN KEY (C23_HAS_SEEN_BY) REFERENCES t16_user_center(C16_USER_ID)
);

-- -----------------------------------------------------------------------------------------------------------------------------
-- Chỉnh lại các bảngt21_center_posts
ALTER TABLE `t02_slot`
    DROP FOREIGN KEY `FKtc7rvu77wdk1doli4xewunopi`,
    DROP COLUMN `c02_student_id`;

ALTER TABLE `t03_center`
    MODIFY COLUMN `c03_center_name` varchar(255) NOT NULL,
    MODIFY COLUMN `c03_center_desc` varchar(255) ,
    MODIFY COLUMN `c03_center_address` varchar(255) NOT NULL,
    MODIFY COLUMN `c03_center_phone` varchar(255) NOT NULL,
    MODIFY COLUMN `c03_center_email` varchar(255) NOT NULL,
    MODIFY COLUMN `c03_center_regulations` varchar(255) ,
    MODIFY COLUMN `c03_center_status` tinyint NOT NULL,
    MODIFY COLUMN `c03_image_path` varchar(255) ,
    MODIFY COLUMN `c03_cloudinary_image_id` varchar(255);
    
DROP TABLE IF EXISTS t13_subject;

-- Xóa thuộc tính C01_TOTAL_COURSE_FEE
ALTER TABLE t01_course DROP COLUMN C01_TOTAL_COURSE_FEE;

-- Xóa thuộc tính C01_SUBJECT_ID
ALTER TABLE t01_course DROP FOREIGN KEY FK_COURSE_SUBJECT;
ALTER TABLE t01_course DROP COLUMN C01_SUBJECT_ID;

ALTER TABLE t01_course 
ADD COLUMN C01_SUBJECT_NAME VARCHAR(255) NOT NULL;






































