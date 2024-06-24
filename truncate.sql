USE swp391_db_releasee;
SET FOREIGN_KEY_CHECKS = 0;

truncate table t01_course;
truncate table t02_slot;
truncate table t03_center;
truncate table t04_payment_method;
-- truncate table t05_role;
truncate table t06_feedback;
truncate table t07_material;
truncate table t08_bill;
truncate table t09_attendance;
truncate table t10_result;
truncate table t11_system_notification;
truncate table t12_apply_center;
truncate table t14_user;
truncate table t15_enrollment;
truncate table t16_user_center;
truncate table t17_student_slot;
truncate table t18_room;
truncate table t19_view_system_notification;
truncate table t20_individual_notification;
truncate table t21_center_posts;
truncate table t22_center_notification;
truncate table t23_view_center_notification;

SET FOREIGN_KEY_CHECKS = 1;