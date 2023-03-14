$(async function () {
    await getUsers();
});

async function getUsers() {
    const table = $('#allUserTable').empty();
    fetch("/admin", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            data.forEach(user => {
                let allUsersTable = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name.substring(5) + '&nbsp;')}</td>
                        <td>
                            <a class="btn btn-info btn-sm editButton" data-bs-toggle="modal"
<!--                                onclick="editUser(${user.id})" -->
                                data-bs-target="#editUserModal">Изменить</a>
                        </td>
                        <td>
                            <a class="btn btn-danger btn-sm deleteButton" data-bs-toggle="modal"
                            data-bs-target="#deleteUserModal"
<!--                            onclick="deleteUser(${user.id})"-->
                            >Удалить</a>
                        </td>
                    </tr>
                )`;
                table.append(allUsersTable);
            })
        })
}

