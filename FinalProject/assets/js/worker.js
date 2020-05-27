window.onload = showWorker();

// Show Worker
let workerTable = document.getElementById("worker-table");

function showWorker() {
  let currentPage = 1;
  axios
    .get(`http://localhost:8080/api/n3/worker?currentPage=${currentPage}`)
    .then((res) => {
      if (res.data.length > 0) {
        workerTable.innerHTML = "";
        res.data.forEach((worker) => {
          renderWorkerHTML(worker);
        });
      } else {
        workerTable.innerHTML = '<td colspan="7">Không có nhân công.</td>';
      }
      paginationPage(currentPage);
    })
    .catch((err) => console.log(err));
}

// Pagination
let paginationEl = document.getElementById('pagination'); 
function paginationPage(currentPage) {
  paginationEl.innerHTML = `
  <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item ${currentPage === 1 ? 'disabled' : ''}" onClick="paginationHandler(${currentPage - 1})">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
        </li>
        ${currentPage === 1 ? "" : 
        `<li class="page-item" onClick="paginationHandler(${currentPage - 1})">
          <a class="page-link" href="#">${currentPage - 1}</a>
        </li>`
        }
       
        <li class="page-item active" >
          <a class="page-link" href="#">${currentPage}</a>
        </li>
        <li class="page-item" onClick="paginationHandler(${currentPage + 1})">
          <a class="page-link" href="#">${currentPage + 1}</a>
        </li>
        <li class="page-item" onClick="paginationHandler(${currentPage + 1})">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  `;
}

function paginationHandler(currentPage) {
  axios
    .get(`http://localhost:8080/api/n3/worker?currentPage=${currentPage}`)
    .then((res) => {
      if (res.data.length > 0) {
        workerTable.innerHTML = "";
        res.data.forEach((worker) => {
          renderWorkerHTML(worker);
        });
      } else {
        workerTable.innerHTML = '<td colspan="7">Không có nhân công.</td>';
      }
      paginationPage(currentPage);
    })
    .catch((err) => console.log(err));
}

// Render HTML
function renderWorkerHTML(worker) {
  let content = `
        <tr>
            <td>${worker.id}</td>
            <td>${worker.workerName}</td>
            <td>${worker.workerBirthday}</td>
            <td>${worker.workerNumber}</td>
            <td>${worker.workerAddress}</td>
            <td>
              <select onChange=changeWorkerStatus(this,${worker.id})>
                <option value="DANG_LAM_VIEC" ${
                  worker.workerStatus === "DANG_LAM_VIEC" ? 'selected' : ""
                }>Đang Làm Việc</option>
                <option value="DANG_RANH" ${
                  worker.workerStatus === "DANG_RANH" ? 'selected' : ""
                }>Đang Rảnh</option>
              </select>
            </td>
            <td>
              <i class="fas fa-trash-alt" data-toggle="modal" data-target="#acceptDeleteModalCenter" onClick=showDeleteModal(${worker.id})></i>
              <i class="fas fa-edit" data-toggle="modal" data-target=".worker-modal" onClick=showEditModal(${worker.id})></i>
            </td>
        </tr>
    `;

  workerTable.innerHTML += content;
}

// Change Status Event
function changeWorkerStatus(event, workerId) {
  const formData = new FormData();
  formData.append("workerId", workerId);
  formData.append("workerStatus", event.value);
  axios
    .post("http://localhost:8080/api/n3/worker/changeStatus", formData)
    .then((res) => console.log(res))
    .catch((err) => console.log(err));
}

// Add New Product
let workerName = document.getElementById("worker-name");
let workerBirthday = document.getElementById("worker-birthday");
let workerNumber = document.getElementById("worker-number");
let workerAddress = document.getElementById("worker-address");

let workerSaveBtn = document.getElementById("worker-save");

workerSaveBtn.addEventListener("click", addNewWorker);

function addNewWorker() {
  let workerData = {
    id: workerSaveBtn.getAttribute("data-id"),
    workerName: workerName.value,
    workerBirthday: workerBirthday.value,
    workerNumber: workerNumber.value,
    workerAddress: workerAddress.value,
  };

  axios
    .post("http://localhost:8080/api/n3/worker", workerData)
    .then(() => location.reload())
    .catch((err) => console.log(err));
}

function showDeleteModal(workerId) {
  let acceptLinkDirect = document.getElementById("accept-link-direct");
  acceptLinkDirect.setAttribute("data-id", workerId);

  acceptLinkDirect.addEventListener("click", function () {
    axios
      .get(`http://localhost:8080/api/n3/worker/delete/${workerId}`)
      .then(() => location.reload())
      .catch((err) => console.log(err));
  });
}

function showEditModal(workerId) {
  workerSaveBtn.setAttribute("data-id", workerId);

  axios
    .get(`http://localhost:8080/api/n3/worker/${workerId}`)
    .then((res) => {
      let workerRes = res.data;

      workerName.value = workerRes.workerName,
      workerBirthday.value = workerRes.workerBirthday,
      workerNumber.value = workerRes.workerNumber,
      workerAddress.value = workerRes.workerAddress;
    })
    .catch((err) => console.log(err));
}
