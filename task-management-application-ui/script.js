let editRow = null;

let editId = null;


function showForm() {

    document.getElementById("taskForm").style.display =
        "block";
}


function addTask() {

    let name =
        document.getElementById("name").value;

    let category =
        document.getElementById("category").value;

    let description =
        document.getElementById("description").value;

    let dueDate =
        document.getElementById("dueDate").value;

    let priority =
        document.getElementById("priority").value;

    let status =
        document.getElementById("status").value;

    if (name.trim() === "" ||
        category.trim() === "" ||
        description.trim() === "") {

        alert("Please fill all fields");

        return;
    }

    //     let table =
    //         document.getElementById("taskTable")
    //             .getElementsByTagName("tbody")[0];

    //     if (editRow != null) {

    //         editRow.cells[1].innerHTML = name;

    //         editRow.cells[2].innerHTML = category;

    //         editRow.cells[3].innerHTML = description;

    //         editRow.cells[4].innerHTML = priority;

    //         editRow.cells[5].innerHTML = dueDate;

    //         editRow.cells[6].innerHTML = status;

    //         editRow = null;
    //     }

    //     else {

    //         let rowCount = table.rows.length + 1;

    //         let row = table.insertRow();

    //         row.innerHTML = `

    //             <td>${rowCount}</td>

    //             <td>${name}</td>

    //             <td>${category}</td>

    //             <td>${description}</td>

    //             <td>${priority}</td>

    //             <td>${dueDate}</td>

    //             <td>${status}</td>

    //             <td>

    //                 <button class="edit-btn"
    //                     onclick="editTask(this)">
    //                     Edit
    //                 </button>

    //                 <button class="delete-btn"
    //                     onclick="deleteTask(this)">
    //                     Delete
    //                 </button>

    //             </td>
    //         `;
    //     }

    //     document.getElementById("taskForm").reset();

    //     document.getElementById("taskForm").style.display =
    //         "none";
    // }

    let task = {

        name: name,

        category: category,

        description: description,

        priority: priority,

        status: status
    };

    fetch("http://localhost:8000/tasks", {

        method: editId == null ? "POST" : "PUT",

        headers: {

            "Content-Type": "application/json"
        },

        body: JSON.stringify(

            editId == null

                ? task

                : {

                    id: editId,

                    name: name,

                    category: category,

                    description: description,

                    priority: priority,

                    status: status
                }
        )

    })

        .then(response => response.json())

        .then(data => {

            editId = null;

            alert(data.message);

            loadTasks();

            document.getElementById("taskForm").reset();

            document.getElementById("taskForm").style.display =
                "none";
        });
}

// function deleteTask(button) {

//     let row =
//         button.parentElement.parentElement;

//     row.remove();

//     updateNumbers();
// }

function deleteTask(id) {

    fetch(`http://localhost:8000/tasks?id=${id}`, {

        method: "DELETE"

    })

        .then(response => response.json())

        .then(data => {

            alert(data.message);

            loadTasks();
        });
}

function editTask(task) {

    editId = task.id;

    document.getElementById("name").value =
        task.name;

    document.getElementById("category").value =
        task.category;

    document.getElementById("description").value =
        task.description;

    document.getElementById("priority").value =
        task.priority;

    document.getElementById("status").value =
        task.status;

    document.getElementById("taskForm").style.display =
        "block";
}

// function editTask(button) {

//     editRow =
//         button.parentElement.parentElement;

//     document.getElementById("name").value =
//         editRow.cells[1].innerHTML;

//     document.getElementById("category").value =
//         editRow.cells[2].innerHTML;

//     document.getElementById("description").value =
//         editRow.cells[3].innerHTML;

//     document.getElementById("priority").value =
//         editRow.cells[4].innerHTML;

//     document.getElementById("dueDate").value =
//         editRow.cells[5].innerHTML;

//     document.getElementById("status").value =
//         editRow.cells[6].innerHTML;

//     document.getElementById("taskForm").style.display =
//         "block";
// }

// function updateNumbers() {

//     let table =
//         document.getElementById("taskTable")
//             .getElementsByTagName("tbody")[0];

//     for (let i = 0; i < table.rows.length; i++) {

//         table.rows[i].cells[0].innerHTML = i + 1;
//     }
// }

function loadTasks() {

    fetch("http://localhost:8000/tasks")

        .then(response => response.json())

        .then(result => {

            let tasks = result.data;

            let table =
                document.getElementById("taskTable")
                    .getElementsByTagName("tbody")[0];

            table.innerHTML = "";

            tasks.forEach((task, index) => {

                let row = table.insertRow();

                row.innerHTML = `

                <td>${index + 1}</td>

                <td>${task.name}</td>

                <td>${task.category}</td>

                <td>${task.description}</td>

                <td>${task.priority}</td>

                <td>---</td>

                <td>${task.status}</td>

                <td>

                    <button class="edit-btn"
                        onclick='editTask(${JSON.stringify(task)})'>
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteTask(${task.id})">
                        Delete
                    </button>

                </td>
            `;
            });
        });
}

loadTasks();
