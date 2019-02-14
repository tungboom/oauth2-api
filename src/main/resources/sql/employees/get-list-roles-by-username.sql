select
rol.role_id roleId,
rol.role_code roleCode,
rol.role_Name roleName,
pri.privilege_id privilegeId,
pri.privilege_code privilegeCode,
pri.privilege_name privilegeName,
per.permission_id permissionId,
per.permission_code permissionCode,
per.permission_name permissionName
from roles rol
inner join roles_privileges rol_pri on rol_pri.role_id = rol.role_id
inner join privileges pri on pri.privilege_id = rol_pri.privilege_id and pri.status = 1
inner join privileges_permissions pri_per on pri_per.privilege_id = pri.privilege_id
inner join permissions per on per.permission_id = pri_per.permission_id and per.status = 1
inner join users us on us.role_code = rol.role_code
where rol.status = 1 and us.username = :username and us.status = 1 