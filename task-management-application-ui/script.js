// let editRow = null;

//let editId = null;


// function showForm() {

//     document.getElementById("taskForm").style.display =
//         "block";
// }


// function addTask() {

//     let name =
//         document.getElementById("name").value;

//     let category =
//         document.getElementById("category").value;

//     let description =
//         document.getElementById("description").value;

//     let priority =
//         document.getElementById("priority").value;

//     let dueDate =
//         document.getElementById("dueDate").value;

//     let status =
//         document.getElementById("status").value;

//     if (name.trim() === "" ||
//         category.trim() === "" ||
//         description.trim() === "" ||
//         dueDate.trim() === "") {

//         alert("Please fill all fields");

//         return;
//     }


//     let task = {

//         name: name,

//         category: category,

//         description: description,

//         priority: priority,

//         dueDate: dueDate,

//         status: status
//     };

//     fetch("http://localhost:8000/tasks", {

//         method: editId == null ? "POST" : "PUT",

//         headers: {

//             "Content-Type": "application/json"
//         },

//         body: JSON.stringify(

//             editId == null

//                 ? task

//                 : {

//                     id: editId,

//                     name: name,

//                     category: category,

//                     description: description,

//                     priority: priority,

//                     dueDate: dueDate,

//                     status: status
//                 }
//         )

//     })

//         .then(response => response.json())

//         .then(data => {

//             editId = null;

//             alert(data.message);

//             loadTasks();

//             document.getElementById("taskForm").reset();

//         });

//     }

//             document.getElementById("taskForm").style.display =
//                 "none";
//         });
// }

// function deleteTask(id) {

//     fetch(`http://localhost:8000/tasks?id=${id}`, {

//         method: "DELETE"

//     })

//         .then(response => response.json())

//         .then(data => {

//             alert(data.message);

//             loadTasks();
//         });
// }

// function editTask(task) {

//     editId = task.id;

//     document.getElementById("name").value =
//         task.name;

//     document.getElementById("category").value =
//         task.category;

//     document.getElementById("description").value =
//         task.description;

//     document.getElementById("priority").value =
//         task.priority;

//     document.getElementById("dueDate").value =
//         task.dueDate;

//     document.getElementById("status").value =
//         task.status;

//     document.getElementById("taskForm").style.display =
//         "block";
// }
let currentPage = 1;
const tasksPerPage = 5;

function loadTasks() {

    fetch("http://localhost:8000/tasks")

        .then(response => response.json())

        .then(result => {

            let tasks = result.data;

            window.tasksData = tasks;

            renderTasks(tasks);

        });
}
loadTasks();

function renderTasks(tasks) {

    const tasksTableBody =
        document.getElementById("tasksTableBody");


    tasksTableBody.innerHTML = "";

    const startIndex = (currentPage - 1) * tasksPerPage;

    const endIndex = startIndex + tasksPerPage;

    const paginatedTasks = tasks.slice(startIndex, endIndex);

    document.getElementById("pageNumber")
    .textContent = currentPage;

    const startTask =
        startIndex + 1;

    const endTask =
        Math.min(endIndex, tasks.length);

    document.getElementById("taskCount")
        .textContent =
        `Showing ${startTask} to ${endTask} of ${tasks.length} tasks`;

    paginatedTasks.forEach((task, index) => {

        let row = tasksTableBody.insertRow();

        row.dataset.id = task.id;

        row.innerHTML = `

            <td>${startIndex + index + 1}</td>

            <td>${task.name}</td>

            <td>${task.category}</td>

            <td>${task.description}</td>

            <td>${task.dueDate}</td>

            <td>

                <span class="status-pill progress-pill">

                    ${formatStatus(task.status)}

                </span>

            </td>

            <td>

                <span class="priority-pill high-pill">

                    ${formatPriority(task.priority)}

                </span>

            </td>

            <td>

                <div class="action-buttons">

                    <button class="edit-btn" data-id="${task.id}">

                        <img src="edit-svgrepo-com.svg"
                             class="action-icon">

                    </button>

                    <button class="delete-btn" data-id="${task.id}">

                        <img src="delete-svgrepo-com.svg"
                             class="action-icon">

                    </button>

                </div>

            </td>

        `;
    });
}

const modal = document.getElementById("taskModal");

function formatStatus(status) {

    const statusMap = {
        TO_DO: "To Do",
        IN_PROGRESS: "In Progress",
        DONE: "Done"
    };

    return statusMap[status] || status;
}

function formatPriority(priority) {

    const priorityMap = {
        HIGH: "High",
        MEDIUM: "Medium",
        LOW: "Low"
    };

    return priorityMap[priority] || priority;
}


function clearForm() {

    document.getElementById("taskName").value = "";
    document.getElementById("category").value = "";
    document.getElementById("description").value = "";
    document.getElementById("dueDate").value = "";
    document.getElementById("status").value = "";
    document.getElementById("priority").value = "";
}

document
    .getElementById("openModal")
    .addEventListener("click", () => {

        window.editTaskId = null;

        clearForm();

        modal.style.display = "flex";
    });

document
    .querySelector(".close-btn")
    .addEventListener("click", () => {
        modal.style.display = "none";
    });

document
    .querySelector(".cancel-btn")
    .addEventListener("click", () => {
        modal.style.display = "none";
    });

document.addEventListener("click", function (event) {

    const deleteBtn = event.target.closest(".delete-btn");

    if (!deleteBtn) return;

    const taskId = deleteBtn.dataset.id;

    fetch(`http://localhost:8000/tasks?id=${taskId}`, {
        method: "DELETE"
    })
        .then(() => {
            loadTasks();
        });

});

document.addEventListener("click", function (event) {

    const editBtn =
        event.target.closest(".edit-btn");

    if (!editBtn) return;

    const taskId =
        editBtn.dataset.id;

    const task =
        window.tasksData.find(
            t => t.id == taskId
        );

    if (!task) return;

    window.editTaskId = task.id;

    document.getElementById("taskName").value =
        task.name;

    document.getElementById("category").value =
        task.category;

    document.getElementById("description").value =
        task.description;

    document.getElementById("dueDate").value =
        task.dueDate;

    document.getElementById("status").value =
        task.status;

    document.getElementById("priority").value =
        task.priority;

    document.getElementById("taskModal")
        .style.display = "flex";
});

// document.querySelector(".save-btn")
//     .addEventListener("click", function () {

//         console.log("SAVE CLICKED");

//     });

// console.log("SCRIPT LOADED");

document.querySelector(".save-btn")
    .addEventListener("click", function () {

        const taskData = {

            name: document.getElementById("taskName").value,

            category: document.getElementById("category").value,

            description: document.getElementById("description").value,

            dueDate: document.getElementById("dueDate").value,

            status: document.getElementById("status").value,

            priority: document.getElementById("priority").value

        };

        if(window.editTaskId){

            taskData.id = window.editTaskId;
        

        fetch(
            `http://localhost:8000/tasks?id=${window.editTaskId}`,
            {
                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(taskData)
            }
        )
            .then(response => response.json())

            .then(result => {

                console.log(result);

                modal.style.display = "none";

                loadTasks();

            });

        }else{

        fetch(
            "http://localhost:8000/tasks",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(taskData)
            }
        )
        .then(response => response.json())
        .then(result => {

            console.log(result);

            modal.style.display = "none";

            loadTasks();
        });
    }

    });

document
    .getElementById("searchInput")
    .addEventListener("input", function () {

        const searchText =
            this.value.toLowerCase();

        const filteredTasks =
            window.tasksData.filter(task =>

                task.name.toLowerCase()
                    .includes(searchText)

            );

        renderTasks(filteredTasks);
    });

document
        .getElementById("prevBtn")
        .addEventListener("click", () => {

            if(currentPage > 1){

                currentPage--;

                renderTasks(window.tasksData);

            }
        });

document
        .getElementById("nextBtn")
        .addEventListener("click", () => {

            const totalPages =
                            Math.ceil(window.tasksData.length / tasksPerPage);

            if(currentPage < totalPages){

                currentPage++;

                renderTasks(window.tasksData);

            }
        });
// const tasksTableBody = document.getElementById("tasksTableBody");

// const tasks = [

//     {
//         name: "UI Design",
//         category: "Frontend",
//         description: "Create dashboard layout",
//         dueDate: "12 Aug 2025",
//         status: "In Progress",
//         priority: "High"
//     },

//     {
//         name: "Bug Fixing",
//         category: "Testing",
//         description: "Fix alignment and UI issues",
//         dueDate: "18 Aug 2025",
//         status: "Done",
//         priority: "Low"
//     },

//     {
//         name: "API Integration",
//         category: "Backend",
//         description: "Connect frontend with API",
//         dueDate: "20 Aug 2025",
//         status: "To Do",
//         priority: "High"
//     },

//     {
//         name: "Database Setup",
//         category: "Database",
//         description: "Configure MySQL database",
//         dueDate: "22 Aug 2025",
//         status: "In Progress",
//         priority: "Medium"
//     },

//     {
//         name: "Testing Module",
//         category: "QA",
//         description: "Test all application modules",
//         dueDate: "25 Aug 2025",
//         status: "Done",
//         priority: "Low"
//     }

// ];


// tasks.forEach((task, index) => {

//     tasksTableBody.innerHTML += `

//         <tr>

//             <td>${index + 1}</td>

//             <td>${task.name}</td>

//             <td>${task.category}</td>

//             <td>${task.description}</td>

//             <td>${task.dueDate}</td>

//             <td>

//                 <span class="status-pill progress-pill">

//                     ${task.status}

//                 </span>

//             </td>

//             <td>

//                 <span class="priority-pill high-pill">

//                     ${task.priority}

//                 </span>

//             </td>

//             <td>

//                 <div class="action-buttons">

//                     <button class="edit-btn">

//                         <img src="edit-svgrepo-com.svg"
//                              class="action-icon">

//                     </button>

//                     <button class="delete-btn">

//                         <img src="delete-svgrepo-com.svg"
//                              class="action-icon">

//                     </button>

//                 </div>

//             </td>

//         </tr>

//     `;

// });

