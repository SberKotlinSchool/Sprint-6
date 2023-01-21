insert into acl_sid (id, principal, sid) values
(1, 1, 'admin'),
(2, 1, 'user');

insert into acl_class (id, class) values
(1, 'ru.sber.springmvc.domain.Record');

insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values
(1, 1, 1, null, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0);

insert into acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) values
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 2, 1, 1, 1, 1),
(4, 2, 1, 1, 1, 1, 1, 1),
(5, 2, 2, 1, 2, 1, 1, 1),
(6, 2, 3, 2, 1, 1, 1, 1),
(7, 3, 1, 1, 1, 1, 1, 1),
(8, 3, 2, 1, 2, 1, 1, 1),
(9, 3, 3, 2, 1, 1, 1, 1);
