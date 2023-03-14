$(async function () {
    await getCurrentUser();
});

async function getCurrentUser() {

    fetch("/user/currentUser", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            $('#currentUser').append(data.username);
            let roles = data.roles.map(role => role.name.substring(5) + '&nbsp;');
            $('#currentUserRoles').append(roles);
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
            $('#currentUserTable').append(user);
        })
}