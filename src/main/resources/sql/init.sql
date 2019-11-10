# init  sample data

insert into resource_entity(url, permission)
values ('/coupon/**', 'hasAnyAuthority("ROLE_SUPER")'),
       ('/product/**', 'hasAnyAuthority("ROLE_USER","ROLE_SUPER")'),
       ('/swagger-ui.html', 'permitAll'),
       ('/v2/api-docs', 'permitAll'),
       ('/webjars/**', 'permitAll'),
       ('/swagger-resources/**', 'permitAll'),
       ('/actuator/**', 'permitAll');
