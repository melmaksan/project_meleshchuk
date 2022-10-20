-- SELECT DISTINCT service.description 
-- 	FROM service 
	
-- SELECT *
--   	FROM orders_to_service

     
-- INSERT into respond (respond, user_id) VALUES('hfjfhfjf', 1)
 
-- INSERT into user_to_orders  VALUES(8, 1);
-- INSERT into user_to_orders  VALUES(8, 4);
-- INSERT into user_to_orders  VALUES(9, 2);
-- INSERT into user_to_orders  VALUES(10, 5);
-- INSERT into user_to_orders  VALUES(10, 3);
-- INSERT into user_to_service (user_id, service_id) VALUES(3, 2);
-- INSERT into user_to_service (user_id, service_id) VALUES(4, 2);
-- INSERT into orders_to_service VALUES(1, 2);
-- INSERT into orders_to_service VALUES(2, 3);
-- INSERT into orders_to_service VALUES(3, 8);
-- INSERT into orders_to_service VALUES(4, 6);
-- INSERT into orders_to_service VALUES(5, 1);
 
-- INSERT into service (title, description, price, image) VALUES('Easy Waves', 'Woman Haircut', 160.00, '/images/service_img/woman_hc_1.jpg');
 
-- SELECT *
-- 	FROM user_to_service

-- SELECT * 
-- 	FROM service 

-- DELETE FROM service WHERE id = 3
        
-- INSERT into user (first_name, last_name, login, password, phone_num, role_id) VALUES('Sergiy', 'Sternenko', 'sergiy.user@mma.com', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '+380445566789', 1);
 
 -- SELECT user.id AS user_id, user.first_name, user.last_name, user.email 
 --    FROM user
 --             WHERE user.first_name = 'AA' AND user.last_name = 'BB' AND user.role_id = 1
 
-- SELECT *
--     FROM user_to_orders
--     Where user_id = 8
--     ORDER BY user_id
     
 -- INSERT into respond (respond, user_id) VALUES('hfjfttrttttttthfjf', 2)
 
-- SELECT *
--     FROM orders
     
-- DELETE FROM orders Where id=5

-- INSERT into role (id, name) VALUES(1, 'manage')

-- INSERT into payment_status (id, name) VALUES(2, 'PAID_STATUS')

-- INSERT into orders (time, status_id, payment_status_id) VALUES(now(), 1, 1) 

-- SELECT *
--   	FROM user_to_orders
--     Order by user_id
       
-- INSERT into user (id, first_name, last_name, login, password, email, phone_num, role_id) VALUES(2, 'AA', 'BB', 'aaa', 'bbb', 'ccc', '0101928374', 1)

-- SELECT user.id AS user_id, user.first_name, user.last_name, user.email 
-- 			FROM user
--             WHERE user.first_name = 'AA' AND user.last_name = 'BB' AND user.role_id = 1

-- INSERT into respond (respond, user_id) VALUES('hfjfttrttttttthfjf', 2)

-- SELECT user.id AS user_id, user.first_name, user.last_name, user.login, user.password, user.phone_num, user.rate, user.role_id, role.name AS role_name 
-- 	FROM user 
-- 	JOIN role ON user.role_id = role.id
--     WHERE user.role_id = 3
--     ORDER by rate DESC

-- SELECT *
--    	FROM user
    
-- DELETE FROM user WHERE id = 2

-- UPDATE user SET rate = 4.8 WHERE user.id = 4

SELECT *
	FROM orders

 
-- DELETE FROM orders 

-- INSERT into user_to_orders VALUES(3, 21);
-- INSERT into user_to_orders VALUES(9, 21);
-- INSERT into orders_to_service VALUES(21, 1);

