
USE swp391_db_releasee;


INSERT INTO t05_role (C05_ROLE_ID, C05_ROLE_DESC) VALUES
(1, 'STUDENT'),
(2, 'PARENT'),
(3, 'TEACHER'),
(4, 'MANAGER'),
(5, 'ADMIN');

INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, C14_ACC_STATUS, C14_USER_CODE, C14_USER_NAME, C14_USER_PHONE, C14_USER_ADDRESS, C14_USER_DOB, C14_USER_GENDER, C14_USER_EMAIL, C14_ROLE_ID, C14_PARENT_ID) VALUES
('student_username_1', 'student_password_1', 1, 'USER10001', 'Student 1', '123456789', '123 Street, City', '2000-01-01', 1, 'student1@example.com', 1, NULL),
('parent_username_1', 'parent_password_1', 1, 'USER20001', 'Parent 1', '123456789', '123 Street, City', '1970-01-01', 0, 'parent1@example.com', 2, NULL),
('teacher_username_1', 'teacher_password_1', 1, 'USER30001', 'Teacher 1', '123456789', '123 Street, City', '1980-01-01', 1, 'teacher1@example.com', 3, NULL),
('manager_username_1', 'manager_password_1', 1, 'USER40001', 'Manager 1', '123456789', '123 Street, City', '1990-01-01', 1, 'manager1@example.com', 4, NULL),
('admin_username_1', 'admin_password_1', 1, 'USER50001', 'Admin 1', '123456789', '123 Street, City', '1995-01-01', 1, 'admin1@example.com', 5, NULL),
('student_username_2', 'student_password_2', 1, 'USER10002', 'Student 2', '123456789', '123 Street, City', '2000-01-01', 1, 'student2@example.com', 1, NULL),
('parent_username_2', 'parent_password_2', 1, 'USER20002', 'Parent 2', '123456789', '123 Street, City', '1970-01-01', 0, 'parent2@example.com', 2, NULL),
('teacher_username_2', 'teacher_password_2', 1, 'USER30002', 'Teacher 2', '123456789', '123 Street, City', '1980-01-01', 1, 'teacher2@example.com', 3, NULL),
('manager_username_2', 'manager_password_2', 1, 'USER40002', 'Manager 2', '123456789', '123 Street, City', '1990-01-01', 1, 'manager2@example.com', 4, NULL),
('student_username_3', 'student_password_3', 1, 'USER10004', 'Student 3', '123456789', '123 Street, City', '2000-01-01', 1, 'student4@example.com', 1, NULL),
('student_username_4', 'student_password_4', 1, 'USER10003', 'Student 4', '123456789', '123 Street, City', '2000-01-01', 1, 'studentABC@example.com', 1, NULL),
('parent_username_3', 'parent_password_3', 1, 'USER20003', 'Parent 3', '123456789', '123 Street, City', '1970-01-01', 0, 'parent3@example.com', 2, NULL),
('teacher_username_3', 'teacher_password_3', 1, 'USER30003', 'Teacher 3', '123456789', '123 Street, City', '1980-01-01', 1, 'teacher3@example.com', 3, NULL),
('manager_username_3', 'manager_password_3', 1, 'USER40003', 'Manager 3', '123456789', '123 Street, City', '1990-01-01', 1, 'manager3@example.com', 4, NULL),
('student_username_5', 'student_password_5', 1, 'USER10236', 'Student 5', '123456789', '123 Street, City', '2000-01-01', 1, 'student5@example.com', 1, NULL),
('student_username_6', 'student_password_6', 1, 'USER10234', 'Student 6', '123456789', '123 Street, City', '2000-01-01', 1, 'student6@example.com', 1, NULL),
('parent_username_4', 'parent_password_4', 1, 'USER20004', 'Parent 4', '123456789', '123 Street, City', '1970-01-01', 0, 'parent4@example.com', 2, NULL),
('teacher_username_4', 'teacher_password_4', 1, 'USER30004', 'Teacher 4', '123456789', '123 Street, City', '1980-01-01', 1, 'teacher4@example.com', 3, NULL),
('manager_username_4', 'manager_password_4', 1, 'USER40004', 'Manager 4', '123456789', '123 Street, City', '1990-01-01', 1, 'manager4@example.com', 4, NULL);


INSERT INTO t13_subject (C13_SUBJECT_CODE, C13_SUBJECT_NAME, C13_SUBJECT_DESC) VALUES
('SWP391', 'Subject 1', 'Description of Subject 1'),
('SWP392', 'Subject 2', 'Description of Subject 2'),
('SWP393', 'Subject 3', 'Description of Subject 3'),
('SWP394', 'Subject 4', 'Description of Subject 4'),
('SWP395', 'Subject 5', 'Description of Subject 5'),
('SWP396', 'Subject 6', 'Description of Subject 6'),
('SWP397', 'Subject 7', 'Description of Subject 7'),
('SWP398', 'Subject 8', 'Description of Subject 8'),
('SWP399', 'Subject 9', 'Description of Subject 9'),
('SWP3100', 'Subject 10', 'Description of Subject 10'),
('SWP3101', 'Subject 11', 'Description of Subject 11'),
('SWP3102', 'Subject 12', 'Description of Subject 12'),
('SWP3103', 'Subject 13', 'Description of Subject 13'),
('SWP3104', 'Subject 14', 'Description of Subject 14'),
('SWP3105', 'Subject 15', 'Description of Subject 15'),
('SWP3106', 'Subject 16', 'Description of Subject 16'),
('SWP3107', 'Subject 17', 'Description of Subject 17'),
('SWP3108', 'Subject 18', 'Description of Subject 18'),
('SWP3109', 'Subject 19', 'Description of Subject 19'),
('SWP3110', 'Subject 20', 'Description of Subject 20');


INSERT INTO t03_center (C03_CENTER_CODE, C03_CENTER_NAME, C03_CENTER_DESC, C03_CENTER_ADDRESS, C03_CENTER_PHONE, C03_CENTER_EMAIL, C03_CENTER_REGULATIONS, C03_IMAGE_PATH, C03_MANAGER_ID, C03_CENTER_STATUS)
VALUES
('CENTER1001', 'Center 1', 'Description of Center 1', '123 Street, City', '123456789', 'center1@example.com', 'Regulations of Center 1', 'path_to_image1', 3, 3),
('CENTER1002', 'Center 2', 'Description of Center 2', '456 Street, City', '987654321', 'center2@example.com', 'Regulations of Center 2', 'path_to_image2', 3, 3),
('CENTER1003', 'Center 3', 'Description of Center 3', '789 Street, City', '111223344', 'center3@example.com', 'Regulations of Center 3', 'path_to_image3', 3, 3),
('CENTER1004', 'Center 4', 'Description of Center 4', '1011 Street, City', '998877665', 'center4@example.com', 'Regulations of Center 4', 'path_to_image4', 3, 3),
('CENTER1005', 'Center 5', 'Description of Center 5', '1213 Street, City', '554433221', 'center5@example.com', 'Regulations of Center 5', 'path_to_image5', 3, 3),
('CENTER1006', 'Center 6', 'Description of Center 6', '1415 Street, City', '667788990', 'center6@example.com', 'Regulations of Center 6', 'path_to_image6', 3, 3),
('CENTER1007', 'Center 7', 'Description of Center 7', '1617 Street, City', '112233445', 'center7@example.com', 'Regulations of Center 7', 'path_to_image7', 2, 3),
('CENTER1008', 'Center 8', 'Description of Center 8', '1819 Street, City', '998877665', 'center8@example.com', 'Regulations of Center 8', 'path_to_image8', 2, 3),
('CENTER1009', 'Center 9', 'Description of Center 9', '2021 Street, City', '554433221', 'center9@example.com', 'Regulations of Center 9', 'path_to_image9', 2, 3),
('CENTER1010', 'Center 10', 'Description of Center 10', '2223 Street, City', '667788990', 'center10@example.com', 'Regulations of Center 10', 'path_to_image10', 1, 3),
('CENTER1011', 'Center 11', 'Description of Center 11', '2425 Street, City', '112233445', 'center11@example.com', 'Regulations of Center 11', 'path_to_image11', 1, 3),
('CENTER1012', 'Center 12', 'Description of Center 12', '2627 Street, City', '998877665', 'center12@example.com', 'Regulations of Center 12', 'path_to_image12', 1, 3),
('CENTER1013', 'Center 13', 'Description of Center 13', '2829 Street, City', '554433221', 'center13@example.com', 'Regulations of Center 13', 'path_to_image13', 1, 3),
('CENTER1014', 'Center 14', 'Description of Center 14', '3031 Street, City', '667788990', 'center14@example.com', 'Regulations of Center 14', 'path_to_image14', 1, 3),
('CENTER1015', 'Center 15', 'Description of Center 15', '3233 Street, City', '112233445', 'center15@example.com', 'Regulations of Center 15', 'path_to_image15', 1, 3),
('CENTER1016', 'Center 16', 'Description of Center 16', '3435 Street, City', '998877665', 'center16@example.com', 'Regulations of Center 16', 'path_to_image16', 1, 3),
('CENTER1017', 'Center 17', 'Description of Center 17', '3637 Street, City', '554433221', 'center17@example.com', 'Regulations of Center 17', 'path_to_image17', 1, 3),
('CENTER1018', 'Center 18', 'Description of Center 18', '3839 Street, City', '667788990', 'center18@example.com', 'Regulations of Center 18', 'path_to_image18', 1, 3),
('CENTER1019', 'Center 19', 'Description of Center 19', '4041 Street, City', '112233445', 'center19@example.com', 'Regulations of Center 19', 'path_to_image19', 1, 3),
('CENTER1020', 'Center 20', 'Description of Center 20', '4243 Street, City', '998877665', 'center20@example.com', 'Regulations of Center 20', 'path_to_image20', 1, 3);

SELECT * FROM t14_user;
SELECT * FROM t03_center;


INSERT INTO t01_course (C01_COURSE_NAME, C01_COURSE_CODE, C01_COURSE_DESC, C01_COURSE_START_DATE, C01_COURSE_END_DATE, C01_AMOUNT_OF_STUDENTS, C01_COURSE_FEE, C01_TOTAL_COURSE_FEE, C01_CENTER_ID, C01_SUBJECT_ID, C01_TEACHER_ID) 
VALUES
('Course 1', 'COURSE1001', 'Description of Class 1', '2023-07-01', '2024-06-30', 30, 200.00, DATEDIFF('2023-07-01', '2024-06-30') / 30.4375 * 200.00, 1, 1, 3),
('Course 2', 'COURSE1002', 'Description of Class 2', '2022-06-02', '2023-07-02', 32, 250.00, DATEDIFF('2022-06-02', '2023-07-02') / 30.4375 * 250.00, 1, 2, 8),
('Course 3', 'COURSE1003', 'Description of Class 3', '2023-06-03', '2024-07-03', 34, 300.00, DATEDIFF('2023-06-03', '2024-07-03') / 30.4375 * 300.00, 1, 3, 13),
('Course 4', 'COURSE1004', 'Description of Class 4', '2023-06-04', '2024-07-04', 40, 200.00, DATEDIFF('2023-06-04', '2024-07-04') / 30.4375 * 200.00, 2, 1, 18),
('Course 5', 'COURSE1005', 'Description of Class 5', '2023-06-05', '2024-07-05', 41, 200.00, DATEDIFF('2023-06-05', '2024-07-05') / 30.4375 * 200.00, 2, 2, 3),
('Course 6', 'COURSE1006', 'Description of Class 6', '2023-06-06', '2024-07-06', 27, 250.00, DATEDIFF('2023-06-06', '2024-07-06') / 30.4375 * 250.00, 2, 3, 8),
('Course 7', 'COURSE1007', 'Description of Class 7', '2022-06-07', '2023-07-07', 29, 350.00, DATEDIFF('2022-06-07', '2023-07-07') / 30.4375 * 350.00, 3, 1, 13),
('Course 8', 'COURSE1008', 'Description of Class 8', '2022-06-08', '2023-07-08', 32, 200.00, DATEDIFF('2022-06-08', '2023-07-08') / 30.4375 * 200.00, 3, 2, 18),
('Course 9', 'COURSE1009', 'Description of Class 9', '2022-06-09', '2023-07-09', 35, 250.00, DATEDIFF('2022-06-09', '2023-07-09') / 30.4375 * 250.00, 3, 3, 3),
('Course 10', 'COURSE1010', 'Description of Class 10', '2023-06-10', '2024-07-10', 27, 300.00, DATEDIFF('2023-06-10', '2024-07-10') / 30.4375 * 300.00, 4, 1, 8),
('Course 11', 'COURSE1011', 'Description of Class 11', '2023-06-11', '2024-07-11', 28, 350.00, DATEDIFF('2023-06-11', '2024-07-11') / 30.4375 * 350.00, 4, 2, 13),
('Course 12', 'COURSE1012', 'Description of Class 12', '2024-02-12', '2024-09-12', 32, 300.00, DATEDIFF('2024-02-12', '2024-09-12') / 30.4375 * 300.00, 4, 3, 18),
('Course 13', 'COURSE1013', 'Description of Class 13', '2024-02-13', '2024-10-13', 35, 200.00, DATEDIFF('2024-02-13', '2024-10-13') / 30.4375 * 200.00, 5, 1, 3),
('Course 14', 'COURSE1014', 'Description of Class 14', '2024-02-14', '2024-07-14', 38, 250.00, DATEDIFF('2024-02-14', '2024-07-14') / 30.4375 * 250.00, 5, 2, 8),
('Course 15', 'COURSE1015', 'Description of Class 15', '2024-01-15', '2024-08-15', 39, 200.00, DATEDIFF('2024-01-15', '2024-08-15') / 30.4375 * 200.00, 5, 3, 13);

SELECT * FROM t01_course;


INSERT INTO t18_room (C18_ROOM_NAME, C18_CENTER_ID) VALUES
('Room A', 1), ('Room B', 1), ('Room C', 1), ('Room D', 1), ('Room E', 1),
('Room A', 2), ('Room B', 2), ('Room C', 2), ('Room D', 2), ('Room E', 2),
('Room A', 3), ('Room B', 3), ('Room C', 3), ('Room D', 3), ('Room E', 3),
('Room A', 4), ('Room B', 4), ('Room C', 4), ('Room D', 4), ('Room E', 4),
('Room A', 5), ('Room B', 5), ('Room C', 5), ('Room D', 5), ('Room E', 5),
('Room A', 6), ('Room B', 6), ('Rt02_slotoom C', 6), ('Room D', 6), ('Room E', 6),
('Room A', 7), ('Room B', 7), ('Room C', 7), ('Room D', 7), ('Room E', 7),
('Room A', 8), ('Room B', 8), ('Room C', 8), ('Room D', 8), ('Room E', 8),
('Room A', 9), ('Room B', 9), ('Room C', 9), ('Room D', 9), ('Room E', 9),
('Room A', 10), ('Room B', 10), ('Room C', 10), ('Room D', 10), ('Room E', 10),
('Room A', 11), ('Room B', 11), ('Room C', 11), ('Room D', 11), ('Room E', 11),
('Room A', 12), ('Room B', 12), ('Room C', 12), ('Room D', 12), ('Room E', 12),
('Room A', 13), ('Room B', 13), ('Room C', 13), ('Room D', 13), ('Room E', 13),
('Room A', 14), ('Room B', 14), ('Room C', 14), ('Room D', 14), ('Room E', 14),
('Room A', 15), ('Room B', 15), ('Room C', 15), ('Room D', 15), ('Room E', 15),
('Room A', 16), ('Room B', 16), ('Room C', 16), ('Room D', 16), ('Room E', 16),
('Room A', 17), ('Room B', 17), ('Room C', 17), ('Room D', 17), ('Room E', 17),
('Room A', 18), ('Room B', 18), ('Room C', 18), ('Room D', 18), ('Room E', 18),
('Room A', 19), ('Room B', 19), ('Roomt19_private_notifications C', 19), ('Room D', 19), ('Room E', 19),
('Room A', 20), ('Room B', 20), ('Room C', 20), ('Room D', 20), ('Room E', 20);


-- t02_slot
INSERT INTO t02_slot (C02_SLOT_DATE, C02_SLOT_START_TIME, C02_SLOT_END_TIME, C02_COURSE_ID, C02_ROOM_ID) VALUES
('2024-06-01', '2024-06-01 08:00:00', '2024-06-01 10:00:00', 1, 1),
('2024-06-01', '2024-06-01 10:30:00', '2024-06-01 12:30:00', 2, 6),
('2024-06-01', '2024-06-01 13:00:00', '2024-06-01 15:00:00', 3,11),
('2024-06-01', '2024-06-01 15:30:00', '2024-06-01 17:30:00', 4, 16),
('2024-06-01', '2024-06-01 18:00:00', '2024-06-01 20:00:00', 5, 21),
('2024-06-02', '2024-06-02 08:00:00', '2024-06-02 10:00:00', 6, 26),
('2024-06-02', '2024-06-02 10:30:00', '2024-06-02 12:30:00', 7, 7);



-- t06_feedback
INSERT INTO t06_feedback (C06_FEEDBACK_DESC, C06_ACTOR_ID, C06_SEND_TO_USER)
VALUES 
('Feedback 1', 1, 'USER101'), 
('Feedback 2', 6, 'USER102') ;


-- t09_attendance
INSERT INTO t09_attendance (C09_ATTENDANCE_STATUS, C09_STUDENT_ID, C09_SLOT_ID) VALUES
(1, 1, 1), (1, 6, 2);



-- t07_material
INSERT INTO t07_material (C07_MATERIALS_NAME, C07_TEACHER_ID, C07_SUBJECT_ID) VALUES 
('Materials 1', 3, 1), ('Materials 2', 8, 2), ('Materials 3', 13, 3), ('Materials 4', 18, 4), 
('Materials 5', 3, 5), ('Materials 6', 8, 6), ('Materials 7', 13, 7), ('Materials 8', 18, 8), 
('Materials 9', 3, 9), ('Materials 10', 8, 10), ('Materials 11', 13, 11), ('Materials 12', 18, 12), 
('Materials 13', 3, 13), ('Materials 14', 8, 14), ('Materials 15', 13, 15), ('Materials 16', 18, 16), 
('Materials 17', 3, 17), ('Materials 18', 8, 18), ('Materials 19', 13, 19), ('Materials 20', 18, 20);


-- t10_result
INSERT INTO t10_result (C10_RESULT_TYPE, C10_RESULT_VAL, C10_STUDENT_ID, C10_COURSE_ID) VALUES
(1, 7, 1, 3), (1, 5, 6, 12);

-- Chèn lại dữ liệu với student id là 31, 32, 33, hoặc 34 (lựa chọn thủ công)
INSERT INTO t15_enrollment (C15_STUDENT_ID, C15_COURSE_ID) VALUES
(1, 5),(6, 8);


INSERT INTO t16_user_center (C16_USER_ID, C16_CENTER_ID) VALUES
    (1, 5), (6, 8), (11, 15), (16, 3), (3, 10),
    (8, 18), (13, 2), (18, 7), (1, 13), (6, 1),
    (11, 14), (16, 6), (3, 9), (8, 12), (13, 19),
    (18, 4), (1, 17), (6, 11), (11, 20), (16, 16);


INSERT INTO t17_student_slot (C17_STUDENT_ID, C17_SLOT_ID) VALUES
    (1, 1), (6, 2);

    
INSERT INTO t11_system_notification (C11_TITLE, C11_CONTENT, C11_CREATED_AT, C11_UPDATED_AT) VALUES
('System Update', 'The system will undergo maintenance on Saturday.', '2024-06-01 08:00:00', NULL),
('New Feature Released', 'We have released a new feature that allows users to track their expenses.', '2024-06-02 09:00:00', NULL),
('Downtime Alert', 'There will be a scheduled downtime for database optimization.', '2024-06-03 10:00:00', NULL),
('Security Update', 'A new security patch has been applied to the system.', '2024-06-04 11:00:00', NULL),
('User Survey', 'Please take a moment to fill out our user satisfaction survey.', '2024-06-05 12:00:00', NULL),
('Bug Fixes', 'Several bugs have been fixed in the latest update.', '2024-06-06 13:00:00', NULL),
('Weekly Report', 'The weekly report is now available in your dashboard.', '2024-06-07 14:00:00', NULL),
('New Tutorial', 'Check out our new tutorial on how to use the advanced features.', '2024-06-08 15:00:00', NULL),
('Feature Request', 'We value your feedback! Submit your feature requests to help us improve.', '2024-06-09 16:00:00', NULL),
('Holiday Announcement', 'The office will be closed for the upcoming holiday.', '2024-06-10 17:00:00', NULL),
('Performance Update', 'System performance has been improved in the latest update.', '2024-06-11 18:00:00', NULL),
('New Integration', 'We have integrated with a new third-party service for better functionality.', '2024-06-12 19:00:00', NULL),
('Feedback Request', 'We would love to hear your thoughts on the recent changes.', '2024-06-13 20:00:00', NULL),
('Scheduled Maintenance', 'Scheduled maintenance will occur this weekend. Expect some downtime.', '2024-06-14 21:00:00', NULL),
('Data Migration', 'We are migrating data to a new server. There may be some service interruptions.', '2024-06-15 22:00:00', NULL),
('Account Verification', 'Please verify your account to continue using our services.', '2024-06-16 23:00:00', NULL),
('Newsletter Subscription', 'Subscribe to our newsletter to stay updated with the latest news.', '2024-06-17 08:00:00', NULL),
('Privacy Policy Update', 'Our privacy policy has been updated. Please review the changes.', '2024-06-18 09:00:00', NULL),
('New Dashboard', 'The new dashboard layout is now live. Enjoy the enhanced user experience.', '2024-06-19 10:00:00', NULL),
('System Health Check', 'A system health check will be performed tonight. Some services may be temporarily unavailable.', '2024-06-20 11:00:00', NULL);

-- T19
INSERT INTO t19_private_notifications (C19_TITLE, C19_CONTENT, C19_CREATED_AT, C19_UPDATED_AT, C19_SEND_TO_CENTER) VALUES
('Default Title', 'Default Content', '2024-06-09 14:30:35', NULL, 4),
('Default Title', 'Default Content', '2024-06-06 14:30:35', NULL, 4),
('Default Title', 'Another Content', '2024-06-10 08:15:00', NULL, 4),
('Special Title', 'Special Content', '2024-06-11 12:45:20', NULL, 4),
('Event Notification', 'Event Content', '2024-06-12 09:30:15', NULL, 4),
('Reminder', 'Reminder Content', '2024-06-13 07:00:00', NULL, 4),
('Alert', 'Alert Content', '2024-06-14 10:20:05', NULL, 4),
('Meeting Reminder', 'Meeting Content', '2024-06-15 13:50:25', NULL, 4),
('Task Update', 'Task Content', '2024-06-16 11:40:30', NULL, 4),
('Weekly Summary', 'Summary Content', '2024-06-17 15:10:45', NULL, 4);

INSERT INTO t19_private_notifications (C19_TITLE, C19_CONTENT, C19_CREATED_AT, C19_UPDATED_AT, C19_SEND_TO_CENTER, c19_actor) VALUES
('Default Title', 'Default Content', '2024-06-09 14:30:35', NULL, 'CENTER1001',1),
('Default Title', 'Default Content', '2024-06-06 14:30:35', NULL, 'CENTER1001',2),
('Default Title', 'Another Content', '2024-06-10 08:15:00', NULL, 'CENTER1001',1),
('Special Title', 'Special Content', '2024-06-11 12:45:20', NULL, 'CENTER1001',1),
('Event Notification', 'Event Content', '2024-06-12 09:30:15', NULL, 'CENTER1001',1),
('Reminder', 'Reminder Content', '2024-06-13 07:00:00', NULL, 'CENTER1001',1),
('Alert', 'Alert Content', '2024-06-14 10:20:05', NULL, 'CENTER1001',1),
('Meeting Reminder', 'Meeting Content', '2024-06-15 13:50:25', NULL, 'CENTER1001',1),
('Task Update', 'Task Content', '2024-06-16 11:40:30', NULL, 'CENTER1001',3),
('Weekly Summary', 'Summary Content', '2024-06-17 15:10:45', NULL, 'CENTER1001',1);


INSERT INTO t20_public_notifications (C20_TITLE, C20_CONTENT, C20_CREATED_AT, C20_UPDATED_AT, C20_SEND_TO_CENTER) VALUES
('New Course Announcement', 'We are excited to introduce a new course on Data Science starting next month.', '2024-06-01 08:00:00', NULL, 'CENTER1001'),
('Summer Camp Enrollment', 'Enroll now for our summer camp program for students aged 10-15.', '2024-06-02 09:00:00', NULL, 'CENTER1002'),
('Guest Lecture', 'Join us for a guest lecture on Machine Learning by Dr. John Doe.', '2024-06-03 10:00:00', NULL, 'CENTER1001'),
('Library Upgrade', 'Our library has been upgraded with the latest educational resources.', '2024-06-04 11:00:00', NULL, 'CENTER1001'),
('Exam Schedule', 'The exam schedule for the upcoming semester is now available online.', '2024-06-05 12:00:00', NULL, 'CENTER1001'),
('Workshop on AI', 'Attend our hands-on workshop on Artificial Intelligence next weekend.', '2024-06-06 13:00:00', NULL, 'CENTER1001'),
('Parent-Teacher Meeting', 'We invite all parents to attend the upcoming parent-teacher meeting.', '2024-06-07 14:00:00', NULL, 'CENTER1001'),
('Scholarship Opportunities', 'Explore new scholarship opportunities for the academic year 2024-2025.', '2024-06-08 15:00:00', NULL, 'CENTER1001'),
('Science Fair', 'Participate in our annual science fair and showcase your projects.', '2024-06-09 16:00:00', NULL, 'CENTER1001'),
('Math Olympiad', 'Register now for the upcoming Math Olympiad competition.', '2024-06-10 17:00:00', NULL, 'CENTER1001'),
('Coding Bootcamp', 'Join our intensive coding bootcamp to enhance your programming skills.', '2024-06-11 18:00:00', NULL, 'CENTER1001'),
('Student Orientation', 'Attend the orientation session for new students on July 1st.', '2024-06-12 19:00:00', NULL, 'CENTER1001'),
('Art Exhibition', 'Visit our art exhibition to see the creative works of our students.', '2024-06-13 20:00:00', NULL, 'CENTER1001'),
('Online Learning Platform', 'Access our new online learning platform for a variety of courses.', '2024-06-14 21:00:00', NULL, 'CENTER1001'),
('Educational Trip', 'Join us for an educational trip to the National Science Museum.', '2024-06-15 22:00:00', NULL, 'CENTER1001'),
('Career Counseling', 'Book a session with our career counselors to plan your future.', '2024-06-16 23:00:00', NULL, 'CENTER1001'),
('Cultural Fest', 'Participate in our annual cultural fest and showcase your talents.', '2024-06-17 08:00:00', NULL, 'CENTER1001'),
('Debate Competition', 'Register for the inter-school debate competition next month.', '2024-06-18 09:00:00', NULL, 'CENTER1001'),
('Health and Wellness', 'Attend our workshop on health and wellness for students.', '2024-06-19 10:00:00', NULL, 'CENTER1001'),
('Environmental Awareness', 'Join our campaign to promote environmental awareness.', '2024-06-20 11:00:00', NULL, 'CENTER1001'),
('Student Exchange Program', 'Apply for our student exchange program to study abroad.', '2024-06-21 12:00:00', NULL, 'CENTER1001'),
('New Faculty', 'Welcome our new faculty members joining us this semester.', '2024-06-22 13:00:00', NULL, 'CENTER1001'),
('Sports Day', 'Participate in our annual sports day events.', '2024-06-23 14:00:00', NULL, 'CENTER1001'),
('Music Workshop', 'Join our music workshop to learn new instruments.', '2024-06-24 15:00:00', NULL, 'CENTER1001'),
('Alumni Meet', 'Attend the alumni meet and reconnect with old friends.', '2024-06-25 16:00:00', NULL, 'CENTER1001'),
('Internship Fair', 'Explore internship opportunities at our internship fair.', '2024-06-26 17:00:00', NULL, 'CENTER1001'),
('Robotics Club', 'Join our robotics club to learn about building robots.', '2024-06-27 18:00:00', NULL, 'CENTER1001'),
('Literary Fest', 'Participate in our literary fest and showcase your writing skills.', '2024-06-28 19:00:00', NULL, 'CENTER1001'),
('Quiz Competition', 'Take part in our general knowledge quiz competition.', '2024-06-29 20:00:00', NULL, 'CENTER1001'),
('Graduation Ceremony', 'Attend the graduation ceremony to celebrate your achievements.', '2024-06-30 21:00:00', NULL, 'CENTER1001');

INSERT INTO t21_center_posts (C21_TITLE, C21_CONTENT, C21_POST_STATUS, C21_FILE_URL, C21_CREATED_AT, C21_UPDATED_AT, C21_SEND_TO_CENTER) VALUES
('Classroom Change', 'Due to renovations, classes in Room 101 will be moved to Room 202.', '1', NULL, '2024-06-01 08:00:00', NULL,'CENTER1001'),
('Attendance System Upgrade', 'Our attendance system will be upgraded this weekend.', '1', NULL, '2024-06-02 09:00:00', NULL,'CENTER1001'),
('New Grading System', 'We are introducing a new grading system for the next semester.', '1', NULL, '2024-06-03 10:00:00', NULL,'CENTER1001'),
('Class Timing Adjustment', 'Class timings have been adjusted to better fit student schedules.', '1', NULL, '2024-06-04 11:00:00', NULL,'CENTER1001'),
('Library Schedule Change', 'The library will now be open from 8 AM to 8 PM.', '1', NULL, '2024-06-05 12:00:00', NULL,'CENTER1001'),
('New Attendance Policy', 'Please review our updated attendance policy.', '1', NULL, '2024-06-06 13:00:00', NULL,'CENTER1001'),
('Exam Room Change', 'The exam room for Math 101 has been changed to Room 303.', '1', NULL, '2024-06-07 14:00:00', NULL,'CENTER1001'),
('Student ID Cards', 'New student ID cards will be distributed next week.', '1', NULL, '2024-06-08 15:00:00', NULL,'CENTER1001'),
('Online Class Schedule', 'The online class schedule for the next term is now available.', '1', NULL, '2024-06-09 16:00:00', NULL,'CENTER1001'),
('New Student Portal', 'We are launching a new student portal for better access to resources.', '1', NULL, '2024-06-10 17:00:00', NULL, 'CENTER1001'),
('Project Submission Guidelines', 'Please follow the new guidelines for project submissions.', '1', NULL, '2024-06-11 18:00:00', NULL, 'CENTER1001'),
('Faculty Meeting', 'A faculty meeting is scheduled for next Monday.', '1', NULL, '2024-06-12 19:00:00', NULL, 'CENTER1001'),
('Extra-Curricular Activities', 'Sign up for new extra-curricular activities available this semester.', '1', NULL, '2024-06-13 20:00:00', NULL, 'CENTER1001'),
('Holiday Homework', 'Check the portal for holiday homework assignments.', '1', NULL, '2024-06-14 21:00:00', NULL, 'CENTER1001'),
('New Textbooks', 'New textbooks are now available in the bookstore.', '1', NULL, '2024-06-15 22:00:00', NULL, 'CENTER1001'),
('Health and Safety Guidelines', 'Review the updated health and safety guidelines for the campus.', '1', NULL, '2024-06-16 23:00:00', NULL, 'CENTER1001'),
('E-Learning Tools', 'Explore new e-learning tools introduced for this term.', '1', NULL, '2024-06-17 08:00:00', NULL, 'CENTER1001'),
('Campus Security Update', 'New security measures have been implemented on campus.', '1', NULL, '2024-06-18 09:00:00', NULL, 'CENTER1001'),
('Student Counseling', 'Student counseling services are now available every weekday.', '1', NULL, '2024-06-19 10:00:00', NULL, 'CENTER1001'),
('Lab Equipment Upgrade', 'The lab has been upgraded with new equipment.', '1', NULL, '2024-06-20 11:00:00', NULL, 'CENTER1001'),
('New Cafeteria Menu', 'The cafeteria has introduced a new menu for the summer.', '1', NULL, '2024-06-21 12:00:00', NULL,'CENTER1001'),
('Class Participation Policy', 'Our class participation policy has been updated.', '1', NULL, '2024-06-22 13:00:00', NULL,'CENTER1001'),
('Exam Preparation Tips', 'Here are some tips to help you prepare for exams.', '1', NULL, '2024-06-23 14:00:00', NULL,'CENTER1001'),
('New Computer Lab', 'A new computer lab is now open for student use.', '1', NULL, '2024-06-24 15:00:00', NULL,'CENTER1001'),
('Library Book Return', 'Please return all library books by the end of the semester.', '1', NULL, '2024-06-25 16:00:00', NULL,'CENTER1001'),
('Course Registration', 'Course registration for the next term opens next week.', '1', NULL, '2024-06-26 17:00:00', NULL,'CENTER1001'),
('Campus WiFi Update', 'Campus WiFi has been upgraded for better connectivity.', '1', NULL, '2024-06-27 18:00:00', NULL,'CENTER1001'),
('Study Group Formation', 'Join a study group to prepare for finals.', '1', NULL, '2024-06-28 19:00:00', NULL,'CENTER1001'),
('Student Handbook', 'The updated student handbook is now available online.', '1', NULL, '2024-06-29 20:00:00', NULL,'CENTER1001');

SELECT * FROM t21_center_posts;
