$(async function () {
    await getCurrentAdmin();
});

async function getCurrentAdmin() {
    const table = $('#currentAdminTable').empty();
    fetch("/admin/currentUser", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            $('#currentAdmin').append(data.username);
            let roles = data.roles.map(role => role.name.substring(5) + '&nbsp;');
            $('#currentAdminRoles').append(roles);
            let user = `$(
                <tr> 
                    <td>${data.id}</td>
                    <td>${data.firstName}</td>
                    <td>${data.lastName}</td>
                    <td>${data.age}</td>
                    <td>${data.username}</td>
                    <td>${roles}</td>
                </tr>    
                )`;
            table.append(user);
        })
}