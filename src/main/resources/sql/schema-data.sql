INSERT INTO
  `user` (`email`, `password`, `create_at`, `update_at`)
VALUES
  ('test1@test.com', '12345', NOW(), NOW()),
  ('test2@test.com', '12345', NOW(), NOW()),
  ('test3@test.com', '12345', NOW(), NOW()),
  ('test4@test.com', '12345', NOW(), NOW()),
  ('test5@test.com', '12345', NOW(), NOW());

INSERT INTO
  `product` (`name`, `image`, `price`, `stock`, `status_type`, `create_at`, `update_at`)
VALUES
  ('상품1', '', 1000, 100, 'READY', NOW(), NOW()),
  ('상품2', '', 2000, 100, 'READY', NOW(), NOW()),
  ('상품3', '', 3000, 100, 'READY', NOW(), NOW()),
  ('상품4', '', 4000, 100, 'READY', NOW(), NOW()),
  ('상품5', '', 5000, 100, 'READY', NOW(), NOW());
