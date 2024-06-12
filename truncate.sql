USE swp391_db_releasee;
SET FOREIGN_KEY_CHECKS = 0;

truncate table t01_course;
truncate table t02_slot;
truncate table t03_center;
truncate table t04_payment_method;
truncate table t05_role;
truncate table t06_feedback;
truncate table t07_material;
truncate table t08_bill;
truncate table t09_attendance;
truncate table t10_result;
truncate table t11_system_notification;
truncate table t12_apply_center;
truncate table t13_subject;
truncate table t14_user;
truncate table t15_enrollment;
truncate table t16_user_center;
truncate table t17_student_slot;
truncate table t18_room;
truncate table t19_private_notifications;
truncate table t20_public_notifications;
truncate table t21_center_posts;

SET FOREIGN_KEY_CHECKS = 1;