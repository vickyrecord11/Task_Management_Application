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


function loadTasks() {

    fetch("http://localhost:8000/tasks")

        .then(response => response.json())

        .then(result => {

            let tasks = result.data;

            const tasksTableBody =
                document.getElementById("tasksTableBody");

            tasksTableBody.innerHTML = "";

            tasks.forEach((task, index) => {

                let row = tasksTableBody.insertRow();

                row.innerHTML = `

                <td>${index + 1}</td>

                <td>${task.name}</td>

                <td>${task.category}</td>

                <td>${task.description}</td>

                <td>${task.dueDate}</td>

                <td>
                
                <span class="status-pill progress-pill">
                
                    ${task.status} 
                
                </span> 
                
                </td> 
                
                <td> 
                
                <span class="priority-pill high-pill">
                
                    ${task.priority} 
                
                </span>
                
                </td>

                <td>

                    <div class="action-buttons">

                    <button class="edit-btn">

                        <img src="edit-svgrepo-com.svg"
                             class="action-icon">

                    </button>

                    <button class="delete-btn">

                        <img src="delete-svgrepo-com.svg"
                             class="action-icon">

                    </button>

                </div>

                </td>
            `;
            });
        });
}

loadTasks();
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

