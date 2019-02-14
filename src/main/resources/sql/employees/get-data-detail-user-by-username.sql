select
	us.user_id userId,
    us.username username,
    us.enabled enabled,
    us.role_code roleCode,
    us.created_time createdTime,
    us.created_user createdUser,
	us.updated_time updatedTime,
	us.updated_user updatedUser,
	us.sign_in_count signInCount,
	us.current_sign_in_at currentSignInAt,
	us.current_sign_in_ip currentSignInIp,
	us.last_sign_in_at lastSignInAt,
	us.last_sign_in_ip lastSignInIp,
    emp.employee_id employeeId,
	emp.employee_code employeeCode,
	emp.first_name firstName,
	emp.last_name lastName,
	emp.email email,
	emp.phone phone,
	emp.date_of_birth dateOfBirth,
	emp.unit_id unitId,
	emp.avatar_id avatarId,
    rol.role_name roleName,
	fil.file_name avatarName,
	fil.file_path avatarPath
from users us
inner join employees emp on us.user_id = emp.user_id
left join roles rol on us.role_code = rol.role_code
left join files fil on emp.avatar_id = fil.file_id
WHERE us.username = :username AND us.status = 1 