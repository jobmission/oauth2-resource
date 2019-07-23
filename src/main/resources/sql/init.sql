# init  sample data

insert into resource_entity(url, roles)
values ('/coupon/**', 'ROLE_SUPER'),
       ('/product/**', 'ROLE_USER,ROLE_SUPER');
