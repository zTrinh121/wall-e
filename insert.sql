
USE swp391_db_releasee;

INSERT INTO t05_role (C05_ROLE_ID, C05_ROLE_DESC) VALUES
(1, 'STUDENT'),
(2, 'PARENT'),
(3, 'TEACHER'),
(4, 'MANAGER'),
(5, 'ADMIN');


-- Chèn 1 admin
INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, c14_acc_status, C14_USER_CODE, c14_user_name, c14_user_phone, c14_user_address, c14_user_dob, c14_user_gender, c14_user_email, C14_VERIFICATION_CODE, C14_PROFILE_IMAGE, C14_ROLE_ID, C14_PARENT_ID)
VALUES ('admin_username', 'admin_password', 1, 'USER101', 'Admin Name', 'admin_phone', 'admin_address', '1990-09-23', 1, 'admin@example.com', 'admin_verification_code', 'admin_profile_image', 5, NULL);

-- Chèn 3 quản lý
INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, c14_acc_status, C14_USER_CODE, c14_user_name, c14_user_phone, c14_user_address, c14_user_dob, c14_user_gender, c14_user_email, C14_VERIFICATION_CODE, C14_PROFILE_IMAGE, C14_ROLE_ID, C14_PARENT_ID)
VALUES 
    ('manager_username1', 'manager_password', 1, 'USER301', 'Manager 1', 'manager_phone1', 'manager_address1', '2001-12-12', 0, 'manager1@example.com', 'manager_verification_code1', 'manager_profile_image1', 4, NULL),
    ('manager_username2', 'manager_password', 1, 'USER302', 'Manager 2', 'manager_phone2', 'manager_address2', '1998-10-04', 1, 'manager2@example.com', 'manager_verification_code2', 'manager_profile_image2', 4, NULL),
    ('manager_username3', 'manager_password', 1, 'USER303', 'Manager 3', 'manager_phone3', 'manager_address3', '1997-11-05', 1, 'manager3@example.com', 'manager_verification_code3', 'manager_profile_image3', 4, NULL);


-- Chèn 15 giáo viên
INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, c14_acc_status, C14_USER_CODE, c14_user_name, c14_user_phone, c14_user_address, c14_user_dob, c14_user_gender, c14_user_email, C14_VERIFICATION_CODE, C14_PROFILE_IMAGE, C14_ROLE_ID, C14_PARENT_ID)
VALUES 
    ('teacher_username1', 'teacher_password', 1, 'USER440', 'Teacher 1', 'teacher_phone1', 'teacher_address1', '1990-01-01', 1, 'teacher1@example.com', 'teacher_verification_code1', 'teacher_profile_image1', 3, NULL),
    ('teacher_username2', 'teacher_password', 1, 'USER441', 'Teacher 2', 'teacher_phone2', 'teacher_address2', '1990-01-01', 1, 'teacher2@example.com', 'teacher_verification_code2', 'teacher_profile_image2', 3, NULL),
    ('teacher_username3', 'teacher_password', 1, 'USER442', 'Teacher 3', 'teacher_phone3', 'teacher_address3', '1990-01-01', 1, 'teacher3@example.com', 'teacher_verification_code3', 'teacher_profile_image3', 3, NULL),
    ('teacher_username4', 'teacher_password', 1, 'USER443', 'Teacher 4', 'teacher_phone4', 'teacher_address4', '1990-01-01', 1, 'teacher4@example.com', 'teacher_verification_code4', 'teacher_profile_image4', 3, NULL),
    ('teacher_username5', 'teacher_password', 1, 'USER444', 'Teacher 5', 'teacher_phone5', 'teacher_address5', '1990-01-01', 1, 'teacher5@example.com', 'teacher_verification_code5', 'teacher_profile_image5', 3, NULL),
    ('teacher_username6', 'teacher_password', 1, 'USER445', 'Teacher 6', 'teacher_phone6', 'teacher_address6', '1990-01-01', 1, 'teacher6@example.com', 'teacher_verification_code6', 'teacher_profile_image6', 3, NULL),
    ('teacher_username7', 'teacher_password', 1, 'USER446', 'Teacher 7', 'teacher_phone7', 'teacher_address7', '1990-01-01', 0, 'teacher7@example.com', 'teacher_verification_code7', 'teacher_profile_image7', 3, NULL),
    ('teacher_username8', 'teacher_password', 1, 'USER447', 'Teacher 8', 'teacher_phone8', 'teacher_address8', '1990-01-01', 1, 'teacher8@example.com', 'teacher_verification_code8', 'teacher_profile_image8', 3, NULL),
    ('teacher_username9', 'teacher_password', 1, 'USER448', 'Teacher 9', 'teacher_phone9', 'teacher_address9', '1990-01-01', 1, 'teacher9@example.com', 'teacher_verification_code9', 'teacher_profile_image9', 3, NULL),
    ('teacher_username10', 'teacher_password', 1, 'USER499', 'Teacher 10', 'teacher_phone10', 'teacher_address10', '1990-01-01', 1, 'teacher10@example.com', 'teacher_verification_code10', 'teacher_profile_image10', 3, NULL),
    ('teacher_username11', 'teacher_password', 1, 'USER4411', 'Teacher 11', 'teacher_phone11', 'teacher_address11', '1990-01-01', 1, 'teacher11@example.com', 'teacher_verification_code11', 'teacher_profile_image11', 3, NULL),
    ('teacher_username12', 'teacher_password', 1, 'USER4412', 'Teacher 12', 'teacher_phone12', 'teacher_address12', '1990-01-01', 1, 'teacher12@example.com', 'teacher_verification_code12', 'teacher_profile_image12', 3, NULL),
    ('teacher_username13', 'teacher_password', 1, 'USER4413', 'Teacher 13', 'teacher_phone13', 'teacher_address13', '1990-01-01', 1, 'teacher13@example.com', 'teacher_verification_code13', 'teacher_profile_image13', 3, NULL),
    ('teacher_username14', 'teacher_password', 1, 'USER4414', 'Teacher 14', 'teacher_phone14', 'teacher_address14', '1990-01-01', 1, 'teacher14@example.com', 'teacher_verification_code14', 'teacher_profile_image14', 3, NULL),
    ('teacher_username15', 'teacher_password', 1, 'USER4409', 'Teacher 15', 'teacher_phone15', 'teacher_address15', '1990-01-01', 1, 'teacher15@example.com', 'teacher_verification_code15', 'teacher_profile_image15', 3, NULL);

-- Chèn 2 phụ huynh
INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, c14_acc_status, C14_USER_CODE, c14_user_name, c14_user_phone, c14_user_address, c14_user_dob, c14_user_gender, c14_user_email, C14_VERIFICATION_CODE, C14_PROFILE_IMAGE, C14_ROLE_ID, C14_PARENT_ID)
VALUES 
    ('parent_username1', 'parent_password', 1, 'USER991', 'Parent 1', 'parent_phone1', 'parent_address1', '1975-01-01', 1, 'parent1@example.com', 'parent_verification_code1', 'parent_profile_image1', 2, NULL),
    ('parent_username2', 'parent_password', 1, 'USER992', 'Parent 2', 'parent_phone2', 'parent_address2', '1972-01-01', 1, 'parent2@example.com', 'parent_verification_code2', 'parent_profile_image2', 2, NULL);

-- Chèn 5 học sinh
INSERT INTO t14_user (C14_USER_USERNAME, C14_USER_PASSWORD, c14_acc_status, C14_USER_CODE, c14_user_name, c14_user_phone, c14_user_address, c14_user_dob, c14_user_gender, c14_user_email, C14_VERIFICATION_CODE, C14_PROFILE_IMAGE, C14_ROLE_ID, C14_PARENT_ID)
VALUES 
    ('student_username1', 'student_password', 1, 'USER201', 'Student 1', 'student_phone1', 'student_address1', '1997-01-01', 1, 'student1@example.com', 'student_verification_code1', 'student_profile_image1', 1, NULL),
    ('student_username2', 'student_password', 1, 'USER202', 'Student 2', 'student_phone2', 'student_address2', '2005-01-01', 1, 'student2@example.com', 'student_verification_code2', 'student_profile_image2', 1, NULL),
    ('student_username3', 'student_password', 1, 'USER203', 'Student 3', 'student_phone3', 'student_address3', '2005-01-01', 1, 'student3@example.com', 'student_verification_code3', 'student_profile_image3', 1, NULL),
    ('student_username4', 'student_password', 1, 'USER204', 'Student 4', 'student_phone4', 'student_address4', '2005-01-01', 1, 'student4@example.com', 'student_verification_code4', 'student_profile_image4', 1, NULL),
    ('student_username5', 'student_password', 1, 'USER205', 'Student 5', 'student_phone5', 'student_address5', '2005-01-01', 1, 'student5@example.com', 'student_verification_code5', 'student_profile_image5', 1, NULL);

INSERT INTO t14_user (C14_USER_USERNAME,C14_USER_PASSWORD,c14_acc_status,C14_USER_CODE,c14_user_name,c14_user_phone,c14_user_address,c14_user_dob,c14_user_gender,c14_user_email,C14_VERIFICATION_CODE,C14_PROFILE_IMAGE,C14_ROLE_ID,C14_PARENT_ID) VALUES
('student6', 'password', 1, 'USER20006', 'Nguyễn Hữu 6', '0123456789', 'Address 6', '2000-01-01', 1, 'student6@example.com', 'verif6', 'profile6', 1, NULL),
('student7', 'password', 1, 'USER20007', 'Nguyễn Hữu 7', '0123456789', 'Address 7', '2000-01-01', 1, 'student7@example.com', 'verif7', 'profile7', 1, NULL),
('student8', 'password', 1, 'USER20008', 'Nguyễn Hữu 8', '0123456789', 'Address 8', '2000-01-01', 1, 'student8@example.com', 'verif8', 'profile8', 1, NULL),
('student9', 'password', 1, 'USER20009', 'Nguyễn Hữu 9', '0123456789', 'Address 9', '2000-01-01', 1, 'student9@example.com', 'verif9', 'profile9', 1, NULL),
('student10', 'password', 1, 'USER20010', 'Nguyễn Hữu 10', '0123456789', 'Address 10', '2000-01-01', 1, 'student10@example.com', 'verif10', 'profile10', 1, NULL),
('student11', 'password', 1, 'USER20011', 'Nguyễn Hữu 11', '0123456789', 'Address 11', '2000-01-01', 1, 'student11@example.com', 'verif11', 'profile11', 1, NULL),
('student12', 'password', 1, 'USER20012', 'Nguyễn Hữu 12', '0123456789', 'Address 12', '2000-01-01', 1, 'student12@example.com', 'verif12', 'profile12', 1, NULL),
('student13', 'password', 1, 'USER20013', 'Nguyễn Hữu 13', '0123456789', 'Address 13', '2000-01-01', 1, 'student13@example.com', 'verif13', 'profile13', 1, NULL),
('student14', 'password', 1, 'USER20014', 'Nguyễn Hữu 14', '0123456789', 'Address 14', '2000-01-01', 1, 'student14@example.com', 'verif14', 'profile14', 1, NULL),
('student15', 'password', 1, 'USER20015', 'Nguyễn Hữu 15', '0123456789', 'Address 15', '2000-01-01', 1, 'student15@example.com', 'verif15', 'profile15', 1, NULL),
('student16', 'password', 1, 'USER20016', 'Nguyễn Hữu 16', '0123456789', 'Address 16', '2000-01-01', 1, 'student16@example.com', 'verif16', 'profile16', 1, NULL),
('student17', 'password', 1, 'USER20017', 'Nguyễn Hữu 17', '0123456789', 'Address 17', '2000-01-01', 1, 'student17@example.com', 'verif17', 'profile17', 1, NULL),
('student18', 'password', 1, 'USER20018', 'Nguyễn Hữu 18', '0123456789', 'Address 18', '2000-01-01', 1, 'student18@example.com', 'verif18', 'profile18', 1, NULL),
('student19', 'password', 1, 'USER20019', 'Nguyễn Hữu 19', '0123456789', 'Address 19', '2000-01-01', 1, 'student19@example.com', 'verif19', 'profile19', 1, NULL),
('student20', 'password', 1, 'USER20020', 'Nguyễn Hữu 20', '0123456789', 'Address 20', '2000-01-01', 1, 'student20@example.com', 'verif20', 'profile20', 1, NULL),
('student21', 'password', 1, 'USER20021', 'Nguyễn Hữu 21', '0123456789', 'Address 21', '2000-01-01', 1, 'student21@example.com', 'verif21', 'profile21', 1, NULL),
('student22', 'password', 1, 'USER20022', 'Nguyễn Hữu Lân 22', '0123456789', 'Address 22', '2000-01-01', 1, 'student22@example.com', 'verif22', 'profile22', 1, NULL),
('student23', 'password', 1, 'USER20023', 'Nguyễn Hữu Lân 23', '0123456789', 'Address 23', '2000-01-01', 1, 'student23@example.com', 'verif23', 'profile23', 1, NULL),
('student24', 'password', 1, 'USER20024', 'Nguyễn Hữu Lân 24', '0123456789', 'Address 24', '2000-01-01', 1, 'student24@example.com', 'verif24', 'profile24', 1, NULL),
('student25', 'password', 1, 'USER20025', 'Nguyễn Hữu Lân 25', '0123456789', 'Address 25', '2000-01-01', 1, 'student25@example.com', 'verif25', 'profile25', 1, NULL),
('student26', 'password', 1, 'USER20026', 'Nguyễn Hữu Lân 26', '0123456789', 'Address 26', '2000-01-01', 1, 'student26@example.com', 'verif26', 'profile26', 1, NULL),
('student27', 'password', 1, 'USER20027', 'Nguyễn Hữu Lân 27', '0123456789', 'Address 27', '2000-01-01', 1, 'student27@example.com', 'verif27', 'profile27', 1, NULL),
('student28', 'password', 1, 'USER20028', 'Thắng bán cam 6', '0123456789', 'Address 6', '2000-01-01', 1, 'student28@example.com', 'verifiA6', 'profile6', 1, NULL),
('student29', 'password', 1, 'USER20029', 'Thắng bán cam 7', '0123456789', 'Address 7', '2000-01-01', 1, 'student29@example.com', 'verifiA7', 'profile7', 1, NULL),
('student30', 'password', 1, 'USER20030', 'Thắng bán cam 8', '0123456789', 'Address 8', '2000-01-01', 1, 'student30@example.com', 'verifiA8', 'profile8', 1, NULL),
('student31', 'password', 1, 'USER20031', 'Thắng bán cam 9', '0123456789', 'Address 9', '2000-01-01', 1, 'student31@example.com', 'verifiA9', 'profile9', 1, NULL),
('student32', 'password', 1, 'USER20032', 'Thắng bán cam 10', '0123456789', 'Address 10', '2000-01-01', 1, 'student32@example.com', 'verifiA10', 'profile10', 1, NULL),
('student33', 'password', 1, 'USER20033', 'Thắng bán cam 11', '0123456789', 'Address 11', '2000-01-01', 1, 'student33@example.com', 'verifiA11', 'profile11', 1, NULL),
('student34', 'password', 1, 'USER20034', 'Thắng bán cam 12', '0123456789', 'Address 12', '2000-01-01', 1, 'student34@example.com', 'verifiA12', 'profile12', 1, NULL),
('student35', 'password', 1, 'USER20035', 'Thắng bán cam 13', '0123456789', 'Address 13', '2000-01-01', 1, 'student35@example.com', 'verifiA13', 'profile13', 1, NULL),
('student36', 'password', 1, 'USER20036', 'Thắng bán cam 14', '0123456789', 'Address 14', '2000-01-01', 1, 'student36@example.com', 'verifiA14', 'profile14', 1, NULL),
('student37', 'password', 1, 'USER20037', 'Thắng bán cam 15', '0123456789', 'Address 15', '2000-01-01', 1, 'student37@example.com', 'verifiA15', 'profile15', 1, NULL),
('student38', 'password', 1, 'USER20038', 'Thắng bán cam 16', '0123456789', 'Address 16', '2000-01-01', 1, 'student38@example.com', 'verifiA16', 'profile16', 1, NULL),
('student39', 'password', 1, 'USER20039', 'Thắng bán cam 17', '0123456789', 'Address 17', '2000-01-01', 1, 'student39@example.com', 'verifiA17', 'profile17', 1, NULL),
('student40', 'password', 1, 'USER20040', 'Thắng bán cam 18', '0123456789', 'Address 18', '2000-01-01', 1, 'student40@example.com', 'verifiA18', 'profile18', 1, NULL),
('student41', 'password', 1, 'USER20041', 'Thắng bán cam 19', '0123456789', 'Address 19', '2000-01-01', 1, 'student41@example.com', 'verifiA19', 'profile19', 1, NULL),
('student42', 'password', 1, 'USER20042', 'Thắng bán cam 20', '0123456789', 'Address 20', '2000-01-01', 1, 'student42@example.com', 'verifiA20', 'profile20', 1, NULL),
('student43', 'password', 1, 'USER20043', 'Thắng bán cam 21', '0123456789', 'Address 21', '2000-01-01', 1, 'student43@example.com', 'verifiA21', 'profile21', 1, NULL),
('student44', 'password', 1, 'USER20044', 'Thắng bán cam 22', '0123456789', 'Address 22', '2000-01-01', 1, 'student44@example.com', 'verifiA22', 'profile22', 1, NULL),
('student45', 'password', 1, 'USER20045', 'Thắng bán cam 23', '0123456789', 'Address 23', '2000-01-01', 1, 'student45@example.com', 'verifiA23', 'profile23', 1, NULL),
('student46', 'password', 1, 'USER20046', 'Thắng bán cam 24', '0123456789', 'Address 24', '2000-01-01', 1, 'student46@example.com', 'verifiA24', 'profile24', 1, NULL),
('student47', 'password', 1, 'USER20047', 'Thắng bán cam 25', '0123456789', 'Address 25', '2000-01-01', 1, 'student47@example.com', 'verifiA25', 'profile25', 1, NULL),
('student48', 'password', 1, 'USER20048', 'Thắng bán cam 26', '0123456789', 'Address 26', '2000-01-01', 1, 'student48@example.com', 'verifiA26', 'profile26', 1, NULL),
('student49', 'password', 1, 'USER20049', 'Thắng bán cam 27', '0123456789', 'Address 27', '2000-01-01', 1, 'student49@example.com', 'verifiA27', 'profile27', 1, NULL),
('student50', 'password', 1, 'USER20050', 'Trần Hữu Lân 6', '0123456789', 'Address 6', '2000-01-01', 1, 'student50@emple.com', 'verifyB6', 'profile6', 1, NULL),
('student51', 'password', 1, 'USER20051', 'Trần Hữu Lân 7', '0123456789', 'Address 7', '2000-01-01', 1, 'student51@example.com', 'verifyB7', 'profile7', 1, NULL),
('student52', 'password', 1, 'USER20052', 'Trần Hữu Lân 8', '0123456789', 'Address 8', '2000-01-01', 1, 'student52@example.com', 'verifyB8', 'profile8', 1, NULL),
('student53', 'password', 1, 'USER20053', 'Trần Hữu Lân 9', '0123456789', 'Address 9', '2000-01-01', 1, 'student53@example.com', 'verifyB9', 'profile9', 1, NULL),
('student54', 'password', 1, 'USER20054', 'Trần Hữu Lân 10', '0123456789', 'Address 10', '2000-01-01', 1, 'student54@example.com', 'verifyB10', 'profile10', 1, NULL),
('student55', 'password', 1, 'USER20055', 'Trần Hữu Lân 11', '0123456789', 'Address 11', '2000-01-01', 1, 'student55@example.com', 'verifyB11', 'profile11', 1, NULL),
('student56', 'password', 1, 'USER20056', 'Trần Hữu Lân 12', '0123456789', 'Address 12', '2000-01-01', 1, 'student56@example.com', 'verifyB12', 'profile12', 1, NULL),
('student57', 'password', 1, 'USER20057', 'Trần Hữu Lân 13', '0123456789', 'Address 13', '2000-01-01', 1, 'student57@example.com', 'verifyB13', 'profile13', 1, NULL),
('student58', 'password', 1, 'USER20058', 'Trần Hữu Lân 14', '0123456789', 'Address 14', '2000-01-01', 1, 'student58@example.com', 'verifyB14', 'profile14', 1, NULL),
('student59', 'password', 1, 'USER20059', 'Trần Hữu Lân 15', '0123456789', 'Address 15', '2000-01-01', 1, 'student59@example.com', 'verifyB15', 'profile15', 1, NULL),
('student60', 'password', 1, 'USER20060', 'Trần Hữu Lân 16', '0123456789', 'Address 16', '2000-01-01', 1, 'student60@example.com', 'verifyB16', 'profile16', 1, NULL),
('student61', 'password', 1, 'USER20061', 'Trần Hữu Lân 17', '0123456789', 'Address 17', '2000-01-01', 1, 'student61@example.com', 'verifyB17', 'profile17', 1, NULL),
('student62', 'password', 1, 'USER20062', 'Trần Hữu Lân 18', '0123456789', 'Address 18', '2000-01-01', 1, 'student62@example.com', 'verifyB18', 'profile18', 1, NULL),
('student63', 'password', 1, 'USER20063', 'Trần Hữu Lân 19', '0123456789', 'Address 19', '2000-01-01', 1, 'student63@example.com', 'verifyB19', 'profile19', 1, NULL),
('student64', 'password', 1, 'USER20064', 'Trần Hữu Lân 20', '0123456789', 'Address 20', '2000-01-01', 1, 'student64@example.com', 'verifyB20', 'profile20', 1, NULL),
('student65', 'password', 1, 'USER20065', 'Trần Hữu Lân 21', '0123456789', 'Address 21', '2000-01-01', 1, 'student65@example.com', 'verifyB21', 'profile21', 1, NULL),
('student66', 'password', 1, 'USER20066', 'Trần Hữu Lân 22', '0123456789', 'Address 22', '2000-01-01', 1, 'student66@example.com', 'verifyB22', 'profile22', 1, NULL),
('student67', 'password', 1, 'USER20067', 'Trần Hữu Lân 23', '0123456789', 'Address 23', '2000-01-01', 1, 'student67@example.com', 'verifyB23', 'profile23', 1, NULL),
('student68', 'password', 1, 'USER20068', 'Trần Hữu Lân 24', '0123456789', 'Address 24', '2000-01-01', 1, 'student68@example.com', 'verifyB24', 'profile24', 1, NULL),
('student69', 'password', 1, 'USER20069', 'Trần Hữu Lân 25', '0123456789', 'Address 25', '2000-01-01', 1, 'student69@example.com', 'verifyB25', 'profile25', 1, NULL),
('student70', 'password', 1, 'USER20070', 'Trần Hữu Lân 26', '0123456789', 'Address 26', '2000-01-01', 1, 'student70@example.com', 'verifyB26', 'profile26', 1, NULL),
('student71', 'password', 1, 'USER20071', 'Trần Hữu Lân 27', '0123456789', 'Address 27', '2000-01-01', 1, 'student71@example.com', 'verifyB27', 'profile27', 1, NULL),
('student72', 'password', 1, 'USER20072', 'Đinh Hữu Lân 6', '0123456789', 'Address 6', '2000-01-01', 1, 'studentA@example.com', 'verifiC6', 'profile6', 1, NULL),
('student73', 'password', 1, 'USER20073', 'Đinh Hữu Lân 7', '0123456789', 'Address 7', '2000-01-01', 1, 'studentB@example.com', 'verifiC7', 'profile7', 1, NULL),
('student74', 'password', 1, 'USER20074', 'Đinh Hữu Lân 8', '0123456789', 'Address 8', '2000-01-01', 1, 'studentC2@example.com', 'verifiC8', 'profile8', 1, NULL),
('student75', 'password', 1, 'USER20075', 'Đinh Hữu Lân 9', '0123456789', 'Address 9', '2000-01-01', 1, 'studentD@example.com', 'verifiC9', 'profile9', 1, NULL),
('student76', 'password', 1, 'USER20076', 'Đinh Hữu Lân 10', '0123456789', 'Address 10', '2000-01-01', 1, 'studentE@example.com', 'verifiC10', 'profile10', 1, NULL),
('student77', 'password', 1, 'USER20077', 'Đinh Hữu Lân 11', '0123456789', 'Address 11', '2000-01-01', 1, 'studentQ@example.com', 'verifiC11', 'profile11', 1, NULL),
('student78', 'password', 1, 'USER20078', 'Đinh Hữu Lân 12', '0123456789', 'Address 12', '2000-01-01', 1, 'studentW@example.com', 'verifiC12', 'profile12', 1, NULL),
('student79', 'password', 1, 'USER20079', 'Đinh Hữu Lân 13', '0123456789', 'Address 13', '2000-01-01', 1, 'studentR@example.com', 'verifiC13', 'profile13', 1, NULL),
('student80', 'password', 1, 'USER20080', 'Đinh Hữu Lân 14', '0123456789', 'Address 14', '2000-01-01', 1, 'studentT@example.com', 'verifiC14', 'profile14', 1, NULL),
('student81', 'password', 1, 'USER20081', 'Đinh Hữu Lân 15', '0123456789', 'Address 15', '2000-01-01', 1, 'studentY@example.com', 'verifiC15', 'profile15', 1, NULL),
('student82', 'password', 1, 'USER20082', 'Đinh Hữu Lân 16', '0123456789', 'Address 16', '2000-01-01', 1, 'studentU@example.com', 'verifiC16', 'profile16', 1, NULL),
('student83', 'password', 1, 'USER20083', 'Đinh Hữu Lân 17', '0123456789', 'Address 17', '2000-01-01', 1, 'studentI@example.com', 'verifiC17', 'profile17', 1, NULL),
('student84', 'password', 1, 'USER20084', 'Đinh Hữu Lân 18', '0123456789', 'Address 18', '2000-01-01', 1, 'studentO@example.com', 'verifiC18', 'profile18', 1, NULL),
('student85', 'password', 1, 'USER20085', 'Đinh Hữu Lân 19', '0123456789', 'Address 19', '2000-01-01', 1, 'studentP@example.com', 'verifiC19', 'profile19', 1, NULL),
('student86', 'password', 1, 'USER20086', 'Đinh Hữu Lân 20', '0123456789', 'Address 20', '2000-01-01', 1, 'studentZ@example.com', 'verifiC20', 'profile20', 1, NULL),
('student87', 'password', 1, 'USER20087', 'Đinh Hữu Lân 21', '0123456789', 'Address 21', '2000-01-01', 1, 'studentX@example.com', 'verifiC21', 'profile21', 1, NULL),
('student88', 'password', 1, 'USER20088', 'Đinh Hữu Lân 22', '0123456789', 'Address 22', '2000-01-01', 1, 'studentC1@example.com', 'verifiC22', 'profile22', 1, NULL),
('student89', 'password', 1, 'USER20089', 'Đinh Hữu Lân 23', '0123456789', 'Address 23', '2000-01-01', 1, 'studentV@example.com', 'verifiC23', 'profile23', 1, NULL),
('student90', 'password', 1, 'USER20090', 'Đinh Hữu Lân 24', '0123456789', 'Address 24', '2000-01-01', 1, 'studentB1@example.com', 'verifiC24', 'profile24', 1, NULL),
('student91', 'password', 1, 'USER20091', 'Đinh Hữu Lân 25', '0123456789', 'Address 25', '2000-01-01', 1, 'studentN@example.com', 'verifiC25', 'profile25', 1, NULL),
('student92', 'password', 1, 'USER20092', 'Đinh Hữu Lân 26', '0123456789', 'Address 26', '2000-01-01', 1, 'studentM@example.com', 'verifiC26', 'profile26', 1, NULL),
('student93', 'password', 1, 'USER20093', 'Đinh Hữu Lân 27', '0123456789', 'Address 27', '2000-01-01', 1, 'studentAC@example.com', 'verifiC27', 'profile27', 1, NULL),
('student94', 'password', 1, 'USER20094', 'Đinh Bộ  17', '0123456789', 'Address 17', '2000-01-01', 1, 'student94@example.com', 'verifiE17', 'profile17', 1, NULL),
('student95', 'password', 1, 'USER20095', 'Đinh Bộ  18', '0123456789', 'Address 18', '2000-01-01', 1, 'student95@example.com', 'verifiE18', 'profile18', 1, NULL),
('student96', 'password', 1, 'USER20096', 'Đinh Bộ  19', '0123456789', 'Address 19', '2000-01-01', 1, 'student96@example.com', 'verifiE19', 'profile19', 1, NULL),
('student97', 'password', 1, 'USER20097', 'Đinh Bộ  20', '0123456789', 'Address 20', '2000-01-01', 1, 'student97@example.com', 'verifiE20', 'profile20', 1, NULL),
('student98', 'password', 1, 'USER20098', 'Đinh Bộ  21', '0123456789', 'Address 21', '2000-01-01', 1, 'student98@example.com', 'verifiE21', 'profile21', 1, NULL),
('student99', 'password', 1, 'USER20099', 'Đinh Bộ  22', '0123456789', 'Address 22', '2000-01-01', 1, 'student99@example.com', 'verifiE22', 'profile22', 1, NULL),
('student100', 'password', 1, 'USER203100', 'Đinh Bộ  23', '0123456789', 'Address 23', '2000-01-01', 1, 'student100@example.com', 'verifiE23', 'profile23', 1, NULL),
('student101', 'password', 1, 'USER20024101', 'Đinh Bộ  24', '0123456789', 'Address 24', '2000-01-01', 1, 'tudent24@example.com', 'verifiE24', 'profile24', 1, NULL),
('student102', 'password', 1, 'USER20102', 'Đinh Bộ  25', '0123456789', 'Address 25', '2000-01-01', 1, 'tudent25@example.com', 'verifiE25', 'profile25', 1, NULL),
('student103', 'password', 1, 'USER20103', 'Đinh Bộ  26', '0123456789', 'Address 26', '2000-01-01', 1, 'tudent26@example.com', 'verifiE26', 'profile26', 1, NULL),
('student104', 'password', 1, 'USER20104', 'Đinh Bộ  27', '0123456789', 'Address 27', '2000-01-01', 1, 'tudent27@example.com', 'verifiE27', 'profile27', 1, NULL),
('student105', 'password', 1, 'USER2105', 'Đạt google 17', '0123456789', 'Address 17', '2000-01-01', 1, 'tudent17@example.com', 'verifiE17', 'profile17', 1, NULL),
('student106', 'password', 1, 'USER2106', 'Đạt google 18', '0123456789', 'Address 18', '2000-01-01', 1, 'tudent18@example.com', 'verifiE18', 'profile18', 1, NULL),
('student107', 'password', 1, 'USER209107', 'Đạt google 19', '0123456789', 'Address 19', '2000-01-01', 1, 'tudent19@example.com', 'verifiE19', 'profile19', 1, NULL),
('student108', 'password', 1, 'USER2108', 'Đạt google 20', '0123456789', 'Address 20', '2000-01-01', 1, 'tudent20@example.com', 'verifiE20', 'profile20', 1, NULL),
('student109', 'password', 1, 'USER20109', 'Đạt google 21', '0123456789', 'Address 21', '2000-01-01', 1, 'tudent21@example.com', 'verifiE21', 'profile21', 1, NULL),
('student110', 'password', 1, 'USER2110', 'Đạt google 22', '0123456789', 'Address 22', '2000-01-01', 1, 'tudent22@example.com', 'verifiE22', 'profile22', 1, NULL),
('student111', 'password', 1, 'USER20111', 'Đạt google 23', '0123456789', 'Address 23', '2000-01-01', 1, 'Astudent23@example.com', 'verifiE23', 'profile23', 1, NULL),
('student112', 'password', 1, 'USER20112', 'Đạt google 24', '0123456789', 'Address 24', '2000-01-01', 1, 'Bstudent24@example.com', 'verifiE24', 'profile24', 1, NULL),
('student113', 'password', 1, 'USER20113', 'Đạt google 25', '0123456789', 'Address 25', '2000-01-01', 1, 'Nstudent25@example.com', 'verifiE25', 'profile25', 1, NULL),
('student114', 'password', 1, 'USER20114', 'Đạt google 26', '0123456789', 'Address 26', '2000-01-01', 1, 'Astudent26@example.com', 'verifiE26', 'profile26', 1, NULL),
('student115', 'password', 1, 'USER20115', 'Đạt google 27', '0123456789', 'Address 27', '2000-01-01', 1, 'Mstudent27@example.com', 'verifiE27', 'profile27', 1, NULL),
('student116', 'password', 1, 'USER20116', 'Trinh đại gia pleiku 17', '0123456789', 'Address 17', '2000-01-01', 1, 'Atudent17@example.com', 'verifiH17', 'profile17', 1, NULL),
('student117', 'password', 1, 'USER20117', 'Trinh đại gia pleiku 18', '0123456789', 'Address 18', '2000-01-01', 1, 'Atudent18@example.com', 'verifiH18', 'profile18', 1, NULL),
('student118', 'password', 1, 'USER20118', 'Trinh đại gia pleiku 19', '0123456789', 'Address 19', '2000-01-01', 1, 'Atudent19@example.com', 'verifiH19', 'profile19', 1, NULL),
('student119', 'password', 1, 'USER20119', 'Trinh đại gia pleiku 20', '0123456789', 'Address 20', '2000-01-01', 1, 'Atudent20@example.com', 'verifiH20', 'profile20', 1, NULL),
('student120', 'password', 1, 'USER20120', 'Trinh đại gia pleiku 21', '0123456789', 'Address 21', '2000-01-01', 1, 'Atudent21@example.com', 'verifiH21', 'profile21', 1, NULL),
('student121', 'password', 1, 'USER20121', 'Trinh đại gia pleiku 22', '0123456789', 'Address 22', '2000-01-01', 1, 'Atudent22@example.com', 'verifiH22', 'profile22', 1, NULL),
('student122', 'password', 1, 'USER2122', 'Trinh đại gia pleiku 23', '0123456789', 'Address 23', '2000-01-01', 1, 'Atudent23@example.com', 'verifiH23', 'profile23', 1, NULL),
('student123', 'password', 1, 'USER20123', 'Trinh đại gia pleiku 24', '0123456789', 'Address 24', '2000-01-01', 1, 'Atudent24@example.com', 'verifiH24', 'profile24', 1, NULL),
('student124', 'password', 1, 'USER20124', 'Trinh đại gia pleiku 25', '0123456789', 'Address 25', '2000-01-01', 1, 'Atudent25@example.com', 'verifiH25', 'profile25', 1, NULL),
('student125', 'password', 1, 'USER29125', 'Trinh đại gia pleiku 26', '0123456789', 'Address 26', '2000-01-01', 1, 'Atudent26@example.com', 'verifiH26', 'profile26', 1, NULL),
('student126', 'password', 1, 'USER20126', 'Trinh đại gia pleiku 27', '0123456789', 'Address 27', '2000-01-01', 1, 'Atudent27@example.com', 'verifiH27', 'profile27', 1, NULL);

SELECT * FROM t14_user;

INSERT INTO t13_subject (C13_SUBJECT_CODE, C13_SUBJECT_NAME, C13_SUBJECT_DESC) VALUES
('MATH1', 'Toán 1', 'Description of Toán 1'),
('MATH2', 'Toán 2', 'Description of Toán 2'),
('MATH3', 'Toán 3', 'Description of Toán 3'),
('MATH4', 'Toán 4', 'Description of Toán 4'),
('MATH5', 'Toán 5', 'Description of Toán 5'),
('MATH6', 'Toán 6', 'Description of Toán 6'),
('MATH7', 'Toán 7', 'Description of Toán 7'),
('MATH8', 'Toán 8', 'Description of Toán 8'),
('MATH9', 'Toán 9', 'Description of Toán 9'),
('PHYS1', 'Lý 1', 'Description of Lý 1'),
('PHYS2', 'Lý 2', 'Description of Lý 2'),
('PHYS3', 'Lý 3', 'Description of Lý 3'),
('PHYS4', 'Lý 4', 'Description of Lý 4'),
('PHYS5', 'Lý 5', 'Description of Lý 5'),
('PHYS6', 'Lý 6', 'Description of Lý 6'),
('PHYS7', 'Lý 7', 'Description of Lý 7'),
('PHYS8', 'Lý 8', 'Description of Lý 8'),
('PHYS9', 'Lý 9', 'Description of Lý 9'),
('PHYS10', 'Lý 10', 'Description of Lý 10'),
('CHEM1', 'Hóa 1', 'Description of Hóa 1'),
('CHEM2', 'Hóa 2', 'Description of Hóa 2'),
('CHEM3', 'Hóa 3', 'Description of Hóa 3'),
('CHEM4', 'Hóa 4', 'Description of Hóa 4'),
('CHEM5', 'Hóa 5', 'Description of Hóa 5'),
('CHEM6', 'Hóa 6', 'Description of Hóa 6'),
('CHEM7', 'Hóa 7', 'Description of Hóa 7'),
('CHEM8', 'Hóa 8', 'Description of Hóa 8'),
('CHEM9', 'Hóa 9', 'Description of Hóa 9'),
('CHEM10', 'Hóa 10', 'Description of Hóa 10'),
('ENG1', 'Anh 1', 'Description of Anh 1'),
('ENG2', 'Anh 2', 'Description of Anh 2'),
('ENG3', 'Anh 3', 'Description of Anh 3'),
('ENG4', 'Anh 4', 'Description of Anh 4'),
('ENG5', 'Anh 5', 'Description of Anh 5'),
('ENG6', 'Anh 6', 'Description of Anh 6'),
('ENG7', 'Anh 7', 'Description of Anh 7'),
('ENG8', 'Anh 8', 'Description of Anh 8'),
('ENG9', 'Anh 9', 'Description of Anh 9'),
('ENG10', 'Anh 10', 'Description of Anh 10'),
('LIT1', 'Văn 1', 'Description of Văn 1'),
('LIT2', 'Văn 2', 'Description of Văn 2'),
('LIT3', 'Văn 3', 'Description of Văn 3'),
('LIT4', 'Văn 4', 'Description of Văn 4'),
('LIT5', 'Văn 5', 'Description of Văn 5'),
('LIT6', 'Văn 6', 'Description of Văn 6'),
('LIT7', 'Văn 7', 'Description of Văn 7'),
('LIT8', 'Văn 8', 'Description of Văn 8'),
('LIT9', 'Văn 9', 'Description of Văn 9'),
('LIT10', 'Văn 10', 'Description of Văn 10');


INSERT INTO t03_center (C03_CENTER_CODE, C03_CENTER_NAME, C03_CENTER_DESC, C03_CENTER_ADDRESS, C03_CENTER_PHONE, C03_CENTER_EMAIL, C03_CENTER_REGULATIONS, C03_IMAGE_PATH, C03_MANAGER_ID, C03_CENTER_STATUS)
VALUES
('CENTER1001', 'Center 1', 'Description of Center 1', '123 Street, City', '123456789', 'center1@example.com', 'Regulations of Center 1', 'path_to_image1', 2, 3),
('CENTER1002', 'Center 2', 'Description of Center 2', '456 Street, City', '987654321', 'center2@example.com', 'Regulations of Center 2', 'path_to_image2', 2, 3),
('CENTER1003', 'Center 3', 'Description of Center 3', '789 Street, City', '111223344', 'center3@example.com', 'Regulations of Center 3', 'path_to_image3', 2, 3),
('CENTER1004', 'Center 4', 'Description of Center 4', '1011 Street, City', '998877665', 'center4@example.com', 'Regulations of Center 4', 'path_to_image4', 2, 3),
('CENTER1005', 'Center 5', 'Description of Center 5', '1213 Street, City', '554433221', 'center5@example.com', 'Regulations of Center 5', 'path_to_image5', 2, 3),
('CENTER1006', 'Center 6', 'Description of Center 6', '1415 Street, City', '667788990', 'center6@example.com', 'Regulations of Center 6', 'path_to_image6', 2, 3),
('CENTER1007', 'Center 7', 'Description of Center 7', '1617 Street, City', '112233445', 'center7@example.com', 'Regulations of Center 7', 'path_to_image7', 3, 3),
('CENTER1008', 'Center 8', 'Description of Center 8', '1819 Street, City', '998877665', 'center8@example.com', 'Regulations of Center 8', 'path_to_image8', 3, 3),
('CENTER1009', 'Center 9', 'Description of Center 9', '2021 Street, City', '554433221', 'center9@example.com', 'Regulations of Center 9', 'path_to_image9', 3, 3),
('CENTER1010', 'Center 10', 'Description of Center 10', '2223 Street, City', '667788990', 'center10@example.com', 'Regulations of Center 10', 'path_to_image10', 3, 3),
('CENTER1011', 'Center 11', 'Description of Center 11', '2425 Street, City', '112233445', 'center11@example.com', 'Regulations of Center 11', 'path_to_image11', 3, 3),
('CENTER1012', 'Center 12', 'Description of Center 12', '2627 Street, City', '998877665', 'center12@example.com', 'Regulations of Center 12', 'path_to_image12', 3, 3),
('CENTER1013', 'Center 13', 'Description of Center 13', '2829 Street, City', '554433221', 'center13@example.com', 'Regulations of Center 13', 'path_to_image13', 2, 3),
('CENTER1014', 'Center 14', 'Description of Center 14', '3031 Street, City', '667788990', 'center14@example.com', 'Regulations of Center 14', 'path_to_image14', 2, 3),
('CENTER1015', 'Center 15', 'Description of Center 15', '3233 Street, City', '112233445', 'center15@example.com', 'Regulations of Center 15', 'path_to_image15', 2, 3),
('CENTER1016', 'Center 16', 'Description of Center 16', '3435 Street, City', '998877665', 'center16@example.com', 'Regulations of Center 16', 'path_to_image16', 4, 3),
('CENTER1017', 'Center 17', 'Description of Center 17', '3637 Street, City', '554433221', 'center17@example.com', 'Regulations of Center 17', 'path_to_image17', 4, 3),
('CENTER1018', 'Center 18', 'Description of Center 18', '3839 Street, City', '667788990', 'center18@example.com', 'Regulations of Center 18', 'path_to_image18', 3, 3),
('CENTER1019', 'Center 19', 'Description of Center 19', '4041 Street, City', '112233445', 'center19@example.com', 'Regulations of Center 19', 'path_to_image19', 3, 3),
('CENTER1020', 'Center 20', 'Description of Center 20', '4243 Street, City', '998877665', 'center20@example.com', 'Regulations of Center 20', 'path_to_image20', 3, 3);



INSERT INTO t01_course (C01_COURSE_NAME, C01_COURSE_CODE, C01_COURSE_DESC, C01_COURSE_START_DATE, C01_COURSE_END_DATE, C01_AMOUNT_OF_STUDENTS, C01_COURSE_FEE, C01_TOTAL_COURSE_FEE, C01_CENTER_ID, C01_SUBJECT_ID, C01_TEACHER_ID) 
VALUES
('Course 1', 'COURSE1001', 'Description of Class 1', '2023-07-01', '2024-06-30', 30, 200.00, DATEDIFF('2023-07-01', '2024-06-30') / 30.4375 * 200.00, 1, 1, 5),
('Course 2', 'COURSE1002', 'Description of Class 2', '2022-06-02', '2023-07-02', 32, 250.00, DATEDIFF('2022-06-02', '2023-07-02') / 30.4375 * 250.00, 1, 2, 8),
('Course 3', 'COURSE1003', 'Description of Class 3', '2023-06-03', '2024-07-03', 34, 300.00, DATEDIFF('2023-06-03', '2024-07-03') / 30.4375 * 300.00, 1, 3, 6),
('Course 4', 'COURSE1004', 'Description of Class 4', '2023-06-04', '2024-07-04', 40, 200.00, DATEDIFF('2023-06-04', '2024-07-04') / 30.4375 * 200.00, 2, 1, 6),
('Course 5', 'COURSE1005', 'Description of Class 5', '2023-06-05', '2024-07-05', 41, 200.00, DATEDIFF('2023-06-05', '2024-07-05') / 30.4375 * 200.00, 2, 2, 7),
('Course 6', 'COURSE1006', 'Description of Class 6', '2023-06-06', '2024-07-06', 27, 250.00, DATEDIFF('2023-06-06', '2024-07-06') / 30.4375 * 250.00, 2, 3, 8),
('Course 7', 'COURSE1007', 'Description of Class 7', '2022-06-07', '2023-07-07', 29, 350.00, DATEDIFF('2022-06-07', '2023-07-07') / 30.4375 * 350.00, 3, 1, 5),
('Course 8', 'COURSE1008', 'Description of Class 8', '2022-06-08', '2023-07-08', 32, 200.00, DATEDIFF('2022-06-08', '2023-07-08') / 30.4375 * 200.00, 3, 2, 6),
('Course 9', 'COURSE1009', 'Description of Class 9', '2022-06-09', '2023-07-09', 35, 250.00, DATEDIFF('2022-06-09', '2023-07-09') / 30.4375 * 250.00, 3, 3, 5),
('Course 10', 'COURSE1010', 'Description of Class 10', '2023-06-10', '2024-07-10', 27, 300.00, DATEDIFF('2023-06-10', '2024-07-10') / 30.4375 * 300.00, 4, 1, 8),
('Course 11', 'COURSE1011', 'Description of Class 11', '2023-06-11', '2024-07-11', 28, 350.00, DATEDIFF('2023-06-11', '2024-07-11') / 30.4375 * 350.00, 4, 2, 5),
('Course 12', 'COURSE1012', 'Description of Class 12', '2024-02-12', '2024-09-12', 32, 300.00, DATEDIFF('2024-02-12', '2024-09-12') / 30.4375 * 300.00, 4, 3, 5),
('Course 13', 'COURSE1013', 'Description of Class 13', '2024-02-13', '2024-10-13', 35, 200.00, DATEDIFF('2024-02-13', '2024-10-13') / 30.4375 * 200.00, 5, 1, 5),
('Course 14', 'COURSE1014', 'Description of Class 14', '2024-02-14', '2024-07-14', 38, 250.00, DATEDIFF('2024-02-14', '2024-07-14') / 30.4375 * 250.00, 5, 2, 8),
('Course 15', 'COURSE1015', 'Description of Class 15', '2024-01-15', '2024-08-15', 39, 200.00, DATEDIFF('2024-01-15', '2024-08-15') / 30.4375 * 200.00, 5, 3, 6);

INSERT INTO t01_course (C01_COURSE_NAME, C01_COURSE_CODE, C01_COURSE_DESC, C01_COURSE_START_DATE, C01_COURSE_END_DATE, C01_AMOUNT_OF_STUDENTS, C01_COURSE_FEE, C01_TOTAL_COURSE_FEE, C01_CENTER_ID, C01_SUBJECT_ID, C01_TEACHER_ID)
SELECT
    CONCAT('Course ', @counter := @counter + 1) AS C01_COURSE_NAME,
    CONCAT('COURSE', 1000 + @counter) AS C01_COURSE_CODE,
    CONCAT('Description of Class ', @counter + 15) AS C01_COURSE_DESC,
    DATE_ADD('2023-07-01', INTERVAL @counter DAY) AS C01_COURSE_START_DATE,
    DATE_ADD('2024-06-30', INTERVAL @counter DAY) AS C01_COURSE_END_DATE,
    30 + FLOOR(RAND() * 20) AS C01_AMOUNT_OF_STUDENTS,
    200.00 + FLOOR(RAND() * 151) AS C01_COURSE_FEE,
    (DATEDIFF(DATE_ADD('2024-06-30', INTERVAL @counter DAY), DATE_ADD('2023-07-01', INTERVAL @counter DAY)) / 30.4375) * (200.00 + FLOOR(RAND() * 151)) AS C01_TOTAL_COURSE_FEE,
    FLOOR(1 + RAND() * 6) AS C01_CENTER_ID,
    FLOOR(1 + RAND() * 49) AS C01_SUBJECT_ID,
    FLOOR(5 + RAND() * 15) AS C01_TEACHER_ID
FROM
    (SELECT @counter := 15) AS var_init,
    information_schema.tables
LIMIT 30;
SELECT * FROM t01_course;
-- ------------------------------------------------------------------------------------------------------------------

INSERT INTO t02_slot (C02_SLOT_START_TIME, C02_SLOT_END_TIME, C02_COURSE_ID, C02_ROOM_NAME)
VALUES
('2024-06-01 08:00:00', '2024-06-01 10:00:00', 1, 'Room A'),
('2024-06-01 10:30:00', '2024-06-01 12:30:00', 2, 'Room B'),
('2024-06-01 13:00:00', '2024-06-01 15:00:00', 3, 'Room C'),
('2024-06-01 15:30:00', '2024-06-01 17:30:00', 4, 'Room D'),
('2024-06-01 18:00:00', '2024-06-01 20:00:00', 5, 'Room E'),
('2024-06-02 08:00:00', '2024-06-02 10:00:00', 6, 'Room F'),
('2024-06-02 10:30:00', '2024-06-02 12:30:00', 7, 'Room G'),
('2024-06-02 13:00:00', '2024-06-02 15:00:00', 8, 'Room H'),
('2024-06-02 15:30:00', '2024-06-02 17:30:00', 9, 'Room I'),
('2024-06-02 18:00:00', '2024-06-02 20:00:00', 10, 'Room M'),
('2024-06-03 08:00:00', '2024-06-03 10:00:00', 11, 'Room N'),
('2024-06-03 10:30:00', '2024-06-03 12:30:00', 12, 'Room O'),
('2024-06-03 13:00:00', '2024-06-03 15:00:00', 13, 'Room P'),
('2024-06-03 15:30:00', '2024-06-03 17:30:00', 14, 'Room Q'),
('2024-06-03 18:00:00', '2024-06-03 20:00:00', 15, 'Room R'),
('2024-06-04 08:00:00', '2024-06-04 10:00:00', 16, 'Room S'),
('2024-06-04 10:30:00', '2024-06-04 12:30:00', 17, 'Room T'),
('2024-06-04 13:00:00', '2024-06-04 15:00:00', 18, 'Room U'),
('2024-06-04 15:30:00', '2024-06-04 17:30:00', 19, 'Room V'),
('2024-06-04 18:00:00', '2024-06-04 20:00:00', 20, 'Room X'),
('2024-06-05 08:00:00', '2024-06-05 10:00:00', 1, 'Room Y'),
('2024-06-05 10:30:00', '2024-06-05 12:30:00', 2, 'Room Z'),
('2024-06-05 13:00:00', '2024-06-05 15:00:00', 3, 'Room AA'),
('2024-06-05 15:30:00', '2024-06-05 17:30:00', 4, 'Room AB'),
('2024-06-05 18:00:00', '2024-06-05 20:00:00', 5, 'Room AC'),
('2024-06-06 08:00:00', '2024-06-06 10:00:00', 6, 'Room AD'),
('2024-06-06 10:30:00', '2024-06-06 12:30:00', 7, 'Room AE'),
('2024-06-06 13:00:00', '2024-06-06 15:00:00', 8, 'Room AF'),
('2024-06-06 15:30:00', '2024-06-06 17:30:00', 9, 'Room AG'),
('2024-06-06 18:00:00', '2024-06-06 20:00:00', 10, 'Room AH');


-- t06_feedback
INSERT INTO t06_feedback (C06_FEEDBACK_DESC, C06_CREATED_AT, C06_ACTOR_ID, C06_SEND_TO_USER, C06_COURSE_ID)
VALUES
('Feedback 1', '2024-06-01 08:00:00', 5, 'USER20011', 1),
('Feedback 2', '2024-06-02 10:00:00', 5, 'USER20011', 2),
('Feedback 3', '2024-06-03 12:00:00', 5, 'USER20011', 3),
('Feedback 4', '2024-06-04 14:00:00', 5, 'USER20011', 4),
('Feedback 5', '2024-06-05 16:00:00', 5, 'USER20011', 5),
('Feedback 6', '2024-06-06 18:00:00', 5, 'USER20011', 6),
('Feedback 7', '2024-06-07 20:00:00', 5, 'USER20011', 7),
('Feedback 8', '2024-06-08 22:00:00', 5, 'USER20011', 8),
('Feedback 9', '2024-06-09 08:00:00', 5, 'USER20011', 9),
('Feedback 10', '2024-06-10 10:00:00', 5, 'USER20011', 10),
('Feedback 11', '2024-06-11 12:00:00', 5, 'USER20011', 11),
('Feedback 12', '2024-06-12 14:00:00', 5, 'USER20012', 12),
('Feedback 13', '2024-06-13 16:00:00', 5, 'USER20012', 13),
('Feedback 14', '2024-06-14 18:00:00', 5, 'USER20012', 14),
('Feedback 15', '2024-06-15 20:00:00', 5, 'USER20012', 15),
('Feedback 16', '2024-06-16 22:00:00', 5, 'USER20012', 16),
('Feedback 17', '2024-06-17 08:00:00', 5, 'USER20012', 17),
('Feedback 18', '2024-06-18 10:00:00', 5, 'USER20012', 18),
('Feedback 19', '2024-06-19 12:00:00', 5, 'USER20012', 19),
('Feedback 20', '2024-06-20 14:00:00', 6, 'USER20012', 20),
('Feedback 21', '2024-06-21 16:00:00', 6, 'USER20012', 21),
('Feedback 22', '2024-06-22 18:00:00', 6, 'USER20012', 22),
('Feedback 23', '2024-06-23 20:00:00', 6, 'USER20012', 23),
('Feedback 24', '2024-06-24 22:00:00', 6, 'USER20012', 24),
('Feedback 25', '2024-06-25 08:00:00', 6, 'USER20012', 25),
('Feedback 26', '2024-06-26 10:00:00', 6, 'USER20012', 26),
('Feedback 27', '2024-06-27 12:00:00', 6, 'USER20012', 27),
('Feedback 28', '2024-06-28 14:00:00', 6, 'USER20012', 28),
('Feedback 29', '2024-06-29 16:00:00', 6, 'USER20012', 29),
('Feedback 30', '2024-06-30 18:00:00', 6, 'USER20012', 30),
('Feedback 31', '2024-07-01 08:00:00', 6, 'USER20012', 31),
('Feedback 32', '2024-07-02 10:00:00', 6, 'USER20012', 32),
('Feedback 33', '2024-07-03 12:00:00', 6, 'USER20012', 33),
('Feedback 34', '2024-07-04 14:00:00', 6, 'USER20012', 34); 


-- t09_attendance
INSERT INTO t09_attendance (C09_ATTENDANCE_STATUS, C09_STUDENT_ID, C09_SLOT_ID) VALUES
(1, 1, 1), (1, 6, 2), (1, 11, 3), (1, 16, 4), (1, 1, 5), (1, 6, 6), (1, 11, 7), (1, 16, 8), (1, 1, 9), (1, 6, 10), 
(1, 11, 11), (1, 16, 12), (1, 1, 13), (1, 6, 14), (1, 11, 15), (1, 16, 16), (1, 1, 17), (1, 6, 18), (1, 11, 19),
(1, 10, 1), (1, 10, 2), (1, 10, 3), (1, 10, 4), (1, 10, 5), (1, 10, 6), (1, 10, 7), (1, 10, 8), (1, 10, 9), (1, 10, 10), 
(1, 10, 11), (1, 10, 12), (1, 10, 13), (1, 10, 14), (1, 10, 15), (1, 10, 16), (1, 10, 17), (1, 10, 18), (1, 10, 19), 
(1, 15, 1), (1, 15, 2), (1, 15, 3), (1, 15, 4), (1, 15, 5), (1, 15, 6), (1, 15, 7), (1, 15, 8), (1, 15, 9), (1, 15, 10), 
(1, 15, 11), (1, 15, 12), (1, 15, 13), (1, 15, 14), (1, 15, 15), (1, 15, 16), (1, 15, 17), (1, 15, 18), (1, 15, 19), 
(1, 20, 1), (1, 20, 2), (1, 20, 3), (1, 20, 4), (1, 20, 5), (1, 20, 6), (1, 20, 7), (1, 20, 8), (1, 20, 9), (1, 20, 10), 
(1, 20, 11), (1, 20, 12), (1, 20, 13), (1, 20, 14), (1, 20, 15), (1, 20, 16), (1, 20, 17), (1, 20, 18), (1, 20, 19), 
(1, 25, 1), (1, 25, 2), (1, 25, 3), (1, 25, 4), (1, 25, 5), (1, 25, 6), (1, 25, 7), (1, 25, 8), (1, 25, 9), (1, 25, 10), 
(1, 25, 11), (1, 25, 12), (1, 25, 13), (1, 25, 14), (1, 25, 15), (1, 25, 16), (1, 25, 17), (1, 25, 18), (1, 25, 19), 
(1, 30, 1), (1, 30, 2), (1, 30, 3), (1, 30, 4), (1, 30, 5), (1, 30, 6), (1, 30, 7), (1, 30, 8), (1, 30, 9), (1, 30, 10), 
(1, 30, 11), (1, 30, 12), (1, 30, 13), (1, 30, 14), (1, 30, 15), (1, 30, 16), (1, 30, 17), (1, 30, 18), (1, 30, 19);



-- t07_material
INSERT INTO t07_material (C07_MATERIALS_NAME, C07_TEACHER_ID, C07_SUBJECT_ID) VALUES 
('Materials 1', 3, 1), ('Materials 2', 8, 2), ('Materials 3', 13, 3), ('Materials 4', 18, 4), 
('Materials 5', 3, 5), ('Materials 6', 8, 6), ('Materials 7', 13, 7), ('Materials 8', 18, 8), 
('Materials 9', 3, 9), ('Materials 10', 8, 10), ('Materials 11', 13, 11), ('Materials 12', 18, 12), 
('Materials 13', 3, 13), ('Materials 14', 8, 14), ('Materials 15', 13, 15), ('Materials 16', 18, 16), 
('Materials 17', 3, 17), ('Materials 18', 8, 18), ('Materials 19', 13, 19), ('Materials 20', 18, 20);


-- t10_result
INSERT INTO t10_result (C10_RESULT_TYPE, C10_RESULT_VAL, C10_STUDENT_ID, C10_COURSE_ID) VALUES
(1, 7, 1, 3), (1, 5, 6, 12), (1, 8, 11, 8), (1, 2, 16, 5), (1, 9, 1, 19), (1, 3, 6, 10),
(2, 6, 11, 15), (1, 4, 16, 2), (2, 10, 1, 17), (1, 1, 6, 4), (1, 8, 1, 7), (1, 3, 6, 14), (1, 5, 11, 3), 
(3, 6, 16, 18), (2, 2, 1, 10), (1, 4, 6, 5), (1, 9, 11, 19), (1, 7, 16, 12), (1, 10, 1, 2), (1, 1, 6, 15),
(2, 7, 10, 3), (1, 5, 10, 12), (1, 8, 10, 8), (1, 2, 10, 5), (1, 9, 10, 19), 
(2, 3, 15, 10), (3, 6, 15, 15), (2, 4, 15, 2), (1, 10, 15, 17), (1, 1, 15, 4),
(1, 8, 20, 7), (1, 3, 20, 14), (1, 5, 20, 3), (1, 6, 20, 18), (1, 2, 20, 10),
(2, 4, 25, 5), (2, 9, 25, 19), (2, 7, 25, 12), (1, 10, 25, 2), (1, 1, 25, 15),
(1, 7, 30, 1), (2, 5, 30, 6), (1, 8, 30, 11), (1, 2, 30, 16), (1, 9, 30, 1),
(1, 3, 31, 6), (1, 6, 31, 11), (1, 4, 31, 16), (1, 10, 31, 1), (1, 1, 31, 6);

-- Chèn lại dữ liệu với student id là 31, 32, 33, hoặc 34 (lựa chọn thủ công)
INSERT INTO t15_enrollment (C15_STUDENT_ID, C15_COURSE_ID) VALUES
(1, 5),(6, 8),(11, 15),(16, 3),(1, 10),(6, 18),(11, 2),(16, 7),(1, 13),(6, 1),
(11, 14),(16, 6),(1, 9),(6, 12),(11, 19),(16, 4),(1, 17),(6, 11),(11, 20),(16, 16),
(10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6), (10, 7), (10, 8), (10, 9), (10, 10), (10, 11), (10, 12), (10, 13), (10, 14), (10, 15), (10, 16), (10, 17), (10, 18), (10, 19),
(15, 1), (15, 2), (15, 3), (15, 4), (15, 5), (15, 6), (15, 7), (15, 8), (15, 9), (15, 10), (15, 11), (15, 12), (15, 13), (15, 14), (15, 15), (15, 16), (15, 17), (15, 18), (15, 19),
(20, 1), (20, 2), (20, 3), (20, 4), (20, 5), (20, 6), (20, 7), (20, 8), (20, 9), (20, 10), (20, 11), (20, 12), (20, 13), (20, 14), (20, 15), (20, 16), (20, 17), (20, 18), (20, 19),
(25, 1), (25, 2), (25, 3), (25, 4), (25, 5), (25, 6), (25, 7), (25, 8), (25, 9), (25, 10), (25, 11), (25, 12), (25, 13), (25, 14), (25, 15), (25, 16), (25, 17), (25, 18), (25, 19),
(30, 1), (30, 2), (30, 3), (30, 4), (30, 5), (30, 6), (30, 7), (30, 8), (30, 9), (30, 10), (30, 11), (30, 12), (30, 13), (30, 14), (30, 15), (30, 16), (30, 17), (30, 18), (30, 19),
(31, 1), (31, 2), (31, 3), (31, 4), (31, 5), (31, 6), (31, 7), (31, 8), (31, 9), (31, 10), (31, 11), (31, 12), (31, 13), (31, 14), (31, 15), (31, 16), (31, 17), (31, 18), (31, 19),
(32, 1), (32, 2), (32, 3), (32, 4), (32, 5), (32, 6), (32, 7), (32, 8), (32, 9), (32, 10), (32, 11), (32, 12), (32, 13), (32, 14), (32, 15), (32, 16), (32, 17), (32, 18), (32, 19),
(33, 1), (33, 2), (33, 3), (33, 4), (33, 5), (33, 6), (33, 7), (33, 8), (33, 9), (33, 10), (33, 11), (33, 12), (33, 13), (33, 14), (33, 15), (33, 16), (33, 17), (33, 18), (33, 19),
(34, 1), (34, 2), (34, 3), (34, 4), (34, 5), (34, 6), (34, 7), (34, 8), (34, 9), (34, 10), (34, 11), (34, 12), (34, 13);


INSERT INTO t16_user_center (C16_USER_ID, C16_CENTER_ID) VALUES
    (1, 5), (6, 8), (11, 15), (16, 3), (3, 10),
    (8, 18), (13, 2), (18, 7), (1, 13), (6, 1),
    (11, 14), (16, 6), (3, 9), (8, 12), (13, 19),
    (18, 4), (1, 17), (6, 11), (11, 20), (16, 16);


INSERT INTO t17_student_slot (C17_STUDENT_ID, C17_SLOT_ID) VALUES
    (1, 5), (6, 8), (11, 15), (16, 3), (1, 10),
    (6, 18), (11, 2), (16, 7), (1, 13), (6, 1),
    (11, 14), (16, 6), (1, 9), (6, 12), (11, 3),
    (16, 4), (1, 17), (6, 11), (11, 19), (16, 16);

    
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

INSERT INTO t20_public_notifications (C20_TITLE, C20_CONTENT, C20_CREATED_AT, C20_UPDATED_AT, C20_SEND_TO_CENTER) VALUES
('New Course Announcement', 'We are excited to introduce a new course on Data Science starting next month.', '2024-06-01 08:00:00', NULL, 'CENTER1001'),
('Summer Camp Enrollment', 'Enroll now for our summer camp program for students aged 10-15.', '2024-06-02 09:00:00', NULL, 'CENTER1001'),
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
('Classroom Change', 'Due to renovations, classes in Room 101 will be moved to Room 202.', 3, NULL, '2024-06-01 08:00:00', NULL, 'CENTER1001'),
('Attendance System Upgrade', 'Our attendance system will be upgraded this weekend.', 3, NULL, '2024-06-02 09:00:00', NULL, 'CENTER1001'),
('New Grading System', 'We are introducing a new grading system for the next semester.', 3, NULL, '2024-06-03 10:00:00', NULL, 'CENTER1001'),
('Class Timing Adjustment', 'Class timings have been adjusted to better fit student schedules.', 3, NULL, '2024-06-04 11:00:00', NULL, 'CENTER1001'),
('Library Schedule Change', 'The library will now be open from 8 AM to 8 PM.', 3, NULL, '2024-06-05 12:00:00', NULL, 'CENTER1001'),
('New Attendance Policy', 'Please review our updated attendance policy.', 3, NULL, '2024-06-06 13:00:00', NULL, 'CENTER1001'),
('Exam Room Change', 'The exam room for Math 101 has been changed to Room 303.', 3, NULL, '2024-06-07 14:00:00', NULL, 'CENTER1001'),
('Student ID Cards', 'New student ID cards will be distributed next week.', 3, NULL, '2024-06-08 15:00:00', NULL, 'CENTER1001'),
('Online Class Schedule', 'The online class schedule for the next term is now available.', 3, NULL, '2024-06-09 16:00:00', NULL, 'CENTER1001'),
('New Student Portal', 'We are launching a new student portal for better access to resources.', 3, NULL, '2024-06-10 17:00:00', NULL, 'CENTER1001'),
('Project Submission Guidelines', 'Please follow the new guidelines for project submissions.', 3, NULL, '2024-06-11 18:00:00', NULL, 'CENTER1001'),
('Faculty Meeting', 'A faculty meeting is scheduled for next Monday.', 3, NULL, '2024-06-12 19:00:00', NULL, 'CENTER1001'),
('Extra-Curricular Activities', 'Sign up for new extra-curricular activities available this semester.', 3, NULL, '2024-06-13 20:00:00', NULL, 'CENTER1001'),
('Holiday Homework', 'Check the portal for holiday homework assignments.', 3, NULL, '2024-06-14 21:00:00', NULL, 'CENTER1001'),
('New Textbooks', 'New textbooks are now available in the bookstore.', 3, NULL, '2024-06-15 22:00:00', NULL, 'CENTER1001'),
('Health and Safety Guidelines', 'Review the updated health and safety guidelines for the campus.', 3, NULL, '2024-06-16 23:00:00', NULL, 'CENTER1001'),
('E-Learning Tools', 'Explore new e-learning tools introduced for this term.', 3, NULL, '2024-06-17 08:00:00', NULL, 'CENTER1001'),
('Campus Security Update', 'New security measures have been implemented on campus.', 3, NULL, '2024-06-18 09:00:00', NULL, 'CENTER1001'),
('Student Counseling', 'Student counseling services are now available every weekday.', 3, NULL, '2024-06-19 10:00:00', NULL, 'CENTER1001'),
('Lab Equipment Upgrade', 'The lab has been upgraded with new equipment.', 3, NULL, '2024-06-20 11:00:00', NULL, 'CENTER1001'),
('New Cafeteria Menu', 'The cafeteria has introduced a new menu for the summer.', 3, NULL, '2024-06-21 12:00:00', NULL, 'CENTER1001'),
('Class Participation Policy', 'Our class participation policy has been updated.', 3, NULL, '2024-06-22 13:00:00', NULL, 'CENTER1001'),
('Exam Preparation Tips', 'Here are some tips to help you prepare for exams.', 3, NULL, '2024-06-23 14:00:00', NULL, 'CENTER1001'),
('New Computer Lab', 'A new computer lab is now open for student use.', 3, NULL, '2024-06-24 15:00:00', NULL, 'CENTER1001');
SELECT * FROM t21_center_posts;

