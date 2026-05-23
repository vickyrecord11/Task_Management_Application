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

    let priority =
        document.getElementById("priority").value;

    let dueDate =
        document.getElementById("dueDate").value;

    let status =
        document.getElementById("status").value;

    if (name.trim() === "" ||
        category.trim() === "" ||
        description.trim() === "" ||
        dueDate.trim() === "") {

        alert("Please fill all fields");

        return;
    }


    let task = {

        name: name,

        category: category,

        description: description,

        priority: priority,

        dueDate: dueDate,

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

                    dueDate: dueDate,

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

    document.getElementById("dueDate").value =
        task.dueDate;

    document.getElementById("status").value =
        task.status;

    document.getElementById("taskForm").style.display =
        "block";
}


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

                <td>${task.dueDate}</td>

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
